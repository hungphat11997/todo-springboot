# Hướng dẫn test API

App dùng **HTTP Basic Auth**: mỗi request (trừ `/api/auth/**`) cần header:
```
Authorization: Basic <base64(username:password)>
```
Ví dụ: `admin` / `admin123` → Base64 là `YWRtaW46YWRtaW4xMjM=`.

---

## 1. Dùng file `.http` trong VS Code / Cursor

1. Cài extension **REST Client** (Huachao Mao): `Ctrl+Shift+X` → tìm "REST Client" → Install.
2. Mở file **`api-test.http`** trong project.
3. Click **"Send Request"** phía trên mỗi request (hoặc `Ctrl+Alt+R`), response hiện bên cạnh.

Đảm bảo server đang chạy: `run-dev.cmd` hoặc `mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev`.

---

## 2. Dùng Postman

1. Tạo request, chọn method (GET/POST/PUT/DELETE), URL: `http://localhost:8080/api/...`
2. **Authorization** tab → Type: **Basic Auth** → nhập username: `admin`, password: `admin123`.
3. Với POST/PUT/PATCH: tab **Body** → **raw** → **JSON**, nhập nội dung.

Ví dụ nhanh:
- `POST http://localhost:8080/api/auth/register` — Body (JSON): `{"username":"user1","password":"pass123","role":"ROLE_USER"}`
- `POST http://localhost:8080/api/auth/login` — Body: `{"username":"admin","password":"admin123"}`
- `GET http://localhost:8080/api/me` — Basic Auth: admin / admin123
- `GET http://localhost:8080/api/tasks` — Basic Auth
- `POST http://localhost:8080/api/tasks` — Basic Auth, Body: `{"title":"Task 1","description":"Mo ta"}`

---

## 3. Dùng curl (terminal)

```bash
# Đăng ký
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{\"username\":\"user1\",\"password\":\"pass123\",\"role\":\"ROLE_USER\"}"

# Login (không lưu session, chỉ test)
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"admin123\"}"

# Gọi API cần auth (Basic = admin:admin123)
curl -u admin:admin123 http://localhost:8080/api/me
curl -u admin:admin123 http://localhost:8080/api/tasks
curl -u admin:admin123 -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" -d "{\"title\":\"Task 1\",\"description\":\"Mo ta\"}"
```

---

## Danh sách API

| Method | URL | Auth | Mô tả |
|--------|-----|------|------|
| POST | /api/auth/register | Không | Đăng ký user |
| POST | /api/auth/login | Không | Đăng nhập |
| GET | /api/me | User | Thông tin user hiện tại |
| GET | /api/tasks | User | Danh sách task của tôi |
| GET | /api/tasks/{id} | User | Chi tiết 1 task |
| POST | /api/tasks | User | Tạo task |
| PUT | /api/tasks/{id} | User | Sửa task |
| DELETE | /api/tasks/{id} | User | Xóa task |
| GET | /api/admin/users | Admin | Tất cả user |
| GET | /api/admin/users/{id} | Admin | Chi tiết user |
| PATCH | /api/admin/users/{id} | Admin | Cập nhật user (role, active) |
| GET | /api/admin/tasks | Admin | Tất cả task |
| GET | /api/admin/users/{userId}/tasks | Admin | Task theo user |

**Tài khoản mặc định (tạo khi chạy lần đầu):** `admin` / `admin123` (ROLE_ADMIN).
