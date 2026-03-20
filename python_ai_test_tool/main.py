"""
Giao diện Streamlit cho luồng Requirement -> Test Cases -> Playwright Script.
"""

from __future__ import annotations

from datetime import datetime
from pathlib import Path

import streamlit as st

from logic import generate_test_assets, get_content


OUTPUT_DIR = Path(__file__).parent / "output"
OUTPUT_DIR.mkdir(exist_ok=True)

st.set_page_config(page_title="Requirement to Automation", page_icon="🧪", layout="wide")
st.title("🧪 Requirement → Test Cases → Playwright Script")
st.caption("Công cụ hỗ trợ QA/Automation đọc Requirement từ Confluence và sinh asset kiểm thử bằng AI")

with st.expander("📌 Hướng dẫn nhanh", expanded=False):
    st.markdown(
        """
        1. Nhập 1 hoặc nhiều URL Confluence (mỗi dòng 1 link).
        2. Nhập prompt bổ sung (ví dụ: ưu tiên test negative, boundary, role permission...).
        3. Nhấn **Generate** để chạy pipeline.
        4. Tải xuống file test cases và file TypeScript Playwright.
        """
    )

confluence_urls = st.text_area(
    "Confluence URLs (mỗi dòng 1 link)",
    height=150,
    placeholder="https://your-domain.atlassian.net/wiki/spaces/ABC/pages/123456789/Requirement-A\nhttps://your-domain.atlassian.net/wiki/pages/viewpage.action?pageId=987654321",
)

extra_prompt = st.text_area(
    "Prompt bổ sung cho AI",
    height=140,
    placeholder="Ví dụ: Ưu tiên flow đăng ký tài khoản, login, quên mật khẩu; sinh thêm edge cases.",
)

if st.button("Generate", type="primary", use_container_width=True):
    urls = [line.strip() for line in confluence_urls.splitlines() if line.strip()]

    if not urls:
        st.error("Bạn cần nhập ít nhất 1 URL Confluence hợp lệ.")
        st.stop()

    with st.spinner("Đang đọc Confluence và gọi AI... vui lòng chờ"):
        try:
            requirement_text = get_content(urls)
            assets = generate_test_assets(requirement_text=requirement_text, extra_prompt=extra_prompt)
        except Exception as exc:
            st.exception(exc)
            st.stop()

    st.success("Generate thành công!")

    st.subheader("✅ Test Cases (Markdown)")
    st.markdown(assets.test_cases_markdown)

    st.subheader("✅ Playwright TypeScript Code")
    st.code(assets.playwright_typescript_code, language="typescript")

    timestamp = datetime.utcnow().strftime("%Y%m%d_%H%M%S")
    tc_file = OUTPUT_DIR / f"test_cases_{timestamp}.md"
    ts_file = OUTPUT_DIR / f"playwright_{timestamp}.ts"

    tc_file.write_text(assets.test_cases_markdown, encoding="utf-8")
    ts_file.write_text(assets.playwright_typescript_code, encoding="utf-8")

    col1, col2 = st.columns(2)
    with col1:
        st.download_button(
            label="⬇️ Download Test Cases (.md)",
            data=assets.test_cases_markdown,
            file_name=tc_file.name,
            mime="text/markdown",
            use_container_width=True,
        )
    with col2:
        st.download_button(
            label="⬇️ Download Playwright Script (.ts)",
            data=assets.playwright_typescript_code,
            file_name=ts_file.name,
            mime="text/plain",
            use_container_width=True,
        )
