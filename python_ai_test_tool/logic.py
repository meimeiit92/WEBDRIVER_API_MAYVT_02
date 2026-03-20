"""
Module xử lý nghiệp vụ chính:
1) Đọc nội dung Requirement từ Confluence.
2) Gọi LLM để sinh Test Cases + mã Playwright TypeScript.

Tác giả: Senior Python Developer & Automation Architect
"""

from __future__ import annotations

import json
import os
import re
from dataclasses import dataclass
from typing import List

from atlassian import Confluence
from dotenv import load_dotenv
from openai import OpenAI


# Load biến môi trường từ file .env
load_dotenv()


@dataclass
class GeneratedAssets:
    """Đối tượng trả về sau khi gọi AI."""

    test_cases_markdown: str
    playwright_typescript_code: str


def _extract_page_id_from_url(url: str) -> str:
    """
    Trích xuất page_id từ URL Confluence.

    Hỗ trợ 2 dạng URL phổ biến:
    - .../pages/viewpage.action?pageId=123456
    - .../spaces/KEY/pages/123456/page-title
    """
    query_match = re.search(r"[?&]pageId=(\d+)", url)
    if query_match:
        return query_match.group(1)

    path_match = re.search(r"/pages/(\d+)", url)
    if path_match:
        return path_match.group(1)

    raise ValueError(f"Không tìm thấy page_id trong URL: {url}")


def get_content(urls: List[str]) -> str:
    """
    Đọc nội dung từ danh sách link Confluence và trả về một khối text duy nhất.

    Yêu cầu biến môi trường:
    - CONFLUENCE_BASE_URL
    - CONFLUENCE_EMAIL
    - CONFLUENCE_API_TOKEN
    """
    base_url = os.getenv("CONFLUENCE_BASE_URL", "").strip()
    email = os.getenv("CONFLUENCE_EMAIL", "").strip()
    api_token = os.getenv("CONFLUENCE_API_TOKEN", "").strip()

    if not base_url or not email or not api_token:
        raise EnvironmentError(
            "Thiếu cấu hình Confluence. Vui lòng kiểm tra CONFLUENCE_BASE_URL, "
            "CONFLUENCE_EMAIL, CONFLUENCE_API_TOKEN trong file .env"
        )

    confluence = Confluence(url=base_url, username=email, password=api_token, cloud=True)

    collected_chunks = []
    for raw_url in urls:
        url = raw_url.strip()
        if not url:
            continue

        page_id = _extract_page_id_from_url(url)
        page = confluence.get_page_by_id(page_id=page_id, expand="body.storage,title")

        title = page.get("title", "(Không có tiêu đề)")
        storage_value = page.get("body", {}).get("storage", {}).get("value", "")

        # Lưu ý: storage_value là HTML. Với prompt LLM, giữ nguyên HTML thường vẫn hữu ích
        # vì còn các heading, table, list.
        collected_chunks.append(
            f"\n===== CONFLUENCE PAGE: {title} (ID: {page_id}) =====\n{storage_value}\n"
        )

    if not collected_chunks:
        raise ValueError("Không có URL hợp lệ để đọc nội dung Confluence.")

    return "\n".join(collected_chunks)


def _build_prompt(requirement_text: str, extra_prompt: str, pom_template: str) -> str:
    """Tạo prompt chuẩn gửi cho LLM."""
    return f"""
Bạn là Senior QA Automation Engineer.

Nhiệm vụ:
1) Phân tích Requirement bên dưới.
2) Sinh danh sách Test Cases chi tiết bằng tiếng Việt (bao gồm: ID, Mục tiêu, Tiền điều kiện, Bước test, Kỳ vọng).
3) Từ test cases, sinh mã TypeScript + Playwright theo mô hình Page Object Model.
4) Code phải sạch, có comment ngắn gọn, dễ bảo trì.
5) Kết quả trả về dạng JSON hợp lệ theo schema:
{json.dumps({'test_cases_markdown': 'string', 'playwright_typescript_code': 'string'}, ensure_ascii=False)}

Additional Prompt từ người dùng:
{extra_prompt}

POM template tham khảo (hãy giữ style tương tự):
{pom_template}

Requirement content:
{requirement_text}
""".strip()


def generate_test_assets(requirement_text: str, extra_prompt: str) -> GeneratedAssets:
    """
    Gọi LLM để sinh Test Cases và code Playwright TypeScript.

    Yêu cầu biến môi trường:
    - OPENAI_API_KEY
    - OPENAI_MODEL (optional, mặc định: gpt-4.1)
    """
    api_key = os.getenv("OPENAI_API_KEY", "").strip()
    model = os.getenv("OPENAI_MODEL", "gpt-4.1").strip()

    if not api_key:
        raise EnvironmentError("Thiếu OPENAI_API_KEY trong file .env")

    template_path = os.path.join(os.path.dirname(__file__), "templates", "playwright_pom_template.ts")
    with open(template_path, "r", encoding="utf-8") as f:
        pom_template = f.read()

    prompt = _build_prompt(requirement_text=requirement_text, extra_prompt=extra_prompt, pom_template=pom_template)

    client = OpenAI(api_key=api_key)
    response = client.chat.completions.create(
        model=model,
        temperature=0.2,
        response_format={"type": "json_object"},
        messages=[
            {"role": "system", "content": "Bạn là chuyên gia QA Automation và Test Design."},
            {"role": "user", "content": prompt},
        ],
    )

    content = response.choices[0].message.content
    if not content:
        raise RuntimeError("LLM trả về rỗng, không thể parse kết quả.")

    data = json.loads(content)
    test_cases_markdown = data.get("test_cases_markdown", "").strip()
    playwright_typescript_code = data.get("playwright_typescript_code", "").strip()

    if not test_cases_markdown or not playwright_typescript_code:
        raise RuntimeError("JSON từ LLM thiếu test_cases_markdown hoặc playwright_typescript_code.")

    return GeneratedAssets(
        test_cases_markdown=test_cases_markdown,
        playwright_typescript_code=playwright_typescript_code,
    )
