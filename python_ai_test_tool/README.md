# Requirement → Test Cases → Playwright Generator (Python + Streamlit)

Ứng dụng giúp tự động hóa quy trình:
1. Đọc Requirement từ Confluence.
2. Gọi AI để sinh Test Cases.
3. Sinh mã Automation script TypeScript + Playwright.

## 1) Cấu trúc thư mục

```text
python_ai_test_tool/
├─ main.py
├─ logic.py
├─ requirements.txt
├─ .env.example
├─ output/
└─ templates/
   └─ playwright_pom_template.ts
```

## 2) Cài đặt từng bước (cho người mới)

### Bước 1: Cài Python
- Cài Python 3.11 hoặc 3.12 từ trang chính thức: https://www.python.org/downloads/
- Sau khi cài, mở Terminal/CMD kiểm tra:

```bash
python --version
```

### Bước 2: Mở thư mục dự án
```bash
cd /workspace/WEBDRIVER_API_MAYVT_02/python_ai_test_tool
```

### Bước 3: Tạo môi trường ảo (venv)
```bash
python -m venv .venv
```

### Bước 4: Kích hoạt venv
- Windows (PowerShell):
```powershell
.\.venv\Scripts\Activate.ps1
```
- macOS/Linux:
```bash
source .venv/bin/activate
```

### Bước 5: Cài thư viện
```bash
pip install --upgrade pip
pip install -r requirements.txt
```

### Bước 6: Cấu hình biến môi trường
1. Copy file mẫu:
```bash
cp .env.example .env
```
2. Mở `.env` và điền đúng thông tin:
- `CONFLUENCE_BASE_URL`
- `CONFLUENCE_EMAIL`
- `CONFLUENCE_API_TOKEN`
- `OPENAI_API_KEY`
- `OPENAI_MODEL`

> Không commit file `.env` lên Git.

### Bước 7: Chạy ứng dụng
```bash
streamlit run main.py
```

Trình duyệt sẽ mở giao diện Streamlit tại URL local (thường là `http://localhost:8501`).

## 3) Bảo mật API Key

- Dùng file `.env` để chứa secret thay vì hard-code trong code.
- Thêm `.env` vào `.gitignore`.
- Không chụp màn hình/ghi log lộ token.
- Có thể rotate (đổi) key định kỳ trên Confluence/OpenAI.

## 4) Workflow sử dụng

1. Paste nhiều link Confluence vào ô input (mỗi dòng 1 link).
2. Nhập prompt bổ sung.
3. Nhấn **Generate**.
4. Kiểm tra Test Cases và Playwright code.
5. Download file `.md` và `.ts`.
