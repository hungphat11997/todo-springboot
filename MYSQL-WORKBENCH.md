# Cấu hình MySQL Workbench để xem record

## 1. Connection trong MySQL Workbench

Tạo connection mới (hoặc chỉnh connection có sẵn) với các giá trị **trùng** với `application.properties`:

| Mục | Giá trị |
|-----|---------|
| **Connection Name** | Todo App (tùy đặt) |
| **Hostname** | `localhost` (hoặc `127.0.0.1`) |
| **Port** | `3306` |
| **Username** | `root` |
| **Password** | `mug3nnsx` |
| **Default Schema** | `todo_app` |

- **Store in Keychain** / **Store in Vault**: tích nếu muốn lưu mật khẩu.
- **Test Connection** → OK là kết nối được.

---

## 2. Trước khi chạy app

### Bật MySQL server

- Cách 1: Chuột phải **`start-mysql-service.cmd`** → **Run as administrator**.
- Cách 2: **Services** (Win+R → `services.msc`) → tìm **MySQL80** (hoặc MySQL) → Start.

### Tạo database (nếu chưa có)

Trong MySQL Workbench, mở **Query** và chạy:

```sql
CREATE DATABASE IF NOT EXISTS todo_app;
```

Hoặc chạy file **`todo_springboot.sql`** (File → Open SQL Script → chọn file → Execute).

---

## 3. Chạy app với MySQL (không dùng H2)

Để dữ liệu lưu vào MySQL và thấy trong Workbench, **không** dùng profile `dev`.

- **Chạy:**  
  `mvnw.cmd spring-boot:run`  
  (không thêm `-Dspring-boot.run.profiles=dev`)

- Hoặc double-click **`run-mysql.cmd`** (nếu có).

Sau khi app chạy, đăng ký / tạo task → vào MySQL Workbench, chọn schema **todo_app**, bảng **users** / **tasks** → Refresh → sẽ thấy record.

---

## 4. Tóm tắt

| Muốn xem data ở đâu | Cách chạy app |
|---------------------|----------------|
| **MySQL Workbench** | `mvnw.cmd spring-boot:run` (không profile dev) |
| **H2 Console** (http://localhost:8080/h2-console) | `run-dev.cmd` hoặc `mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev` |

Connection trong Workbench phải dùng đúng **host, port, user, password** như trên và default schema **todo_app**.
