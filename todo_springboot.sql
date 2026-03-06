-- =========================
-- CREATE DATABASE
-- =========================
CREATE DATABASE IF NOT EXISTS todo_app;
USE todo_app;

-- =========================
-- USERS TABLE
-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- TASKS TABLE
-- =========================
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_tasks_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- =========================
-- INDEX (tăng tốc query)
-- =========================
CREATE INDEX idx_tasks_user_id ON tasks(user_id);

-- =========================
-- TẠO ADMIN MẶC ĐỊNH
-- password: admin123 (đã mã hoá BCrypt)
-- Chạy app lần đầu hoặc dùng DataLoader trong Spring để tạo admin
-- =========================
-- INSERT INTO users (username, password, role, active)
-- VALUES (
--     'admin',
--     '$2a$10$...',  -- BCrypt hash của "admin123"
--     'ROLE_ADMIN',
--     TRUE
-- );
