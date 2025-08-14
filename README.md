
# Litflix — Online Book Store API 📚

> A Spring Boot REST API for managing books, categories, shopping carts, and orders with JWT-based authentication and Liquibase-managed schema migrations.

<img width="284" height="430" alt="Screen Shot 2025-08-13 at 22 18 40" src="https://github.com/user-attachments/assets/d6fd8ecc-5928-4053-ab12-3c054b791fad" />
---

## 📑 Table of Contents
1. [✨ Features](#-features)  
2. [🛠 Tech Stack](#-tech-stack)  
3. [📂 Project Structure](#-project-structure)  
4. [🌐 API Overview](#-api-overview)  
5. [🗄 Database Schema](#-database-schema)  
6. [🚀 Getting Started](#-getting-started)  
7. [📸 Screenshots & Demos](#-screenshots--demos)  
8. [🤝 Contributing](#-contributing)  
9. [🗺 Roadmap](#-roadmap)  
10. [📜 License](#-license)  

---

## ✨ Features
- 🔐 **Authentication & Authorization**
  - JWT login/registration with role-based access (`ROLE_USER`, `ROLE_ADMIN`)
- 📚 **Books**
  - CRUD for books (admin)
  - Public browsing & filtered search by title/author
  - Category assignments
- 🏷 **Categories**
  - CRUD (admin) and public listing
- 🛒 **Shopping Cart**
  - Add, update, and remove items; view cart
- 📦 **Orders**
  - Place order from cart, view history, view order items
- 🗄 **Database Migrations**
  - Managed by Liquibase YAML changelogs
- 🛠 **Mapping & Validation**
  - DTOs with MapStruct
  - Bean validation (including custom `@ValidIsbn`)

---

## 🛠 Tech Stack
- ☕ Java 17
- 🚀 Spring Boot
- 🌐 Spring Web, Security, Data JPA
- 🗃 Hibernate ORM
- 🔄 MapStruct
- 🛢 Liquibase
- 🐬 MySQL
- 📦 Maven

---

## 📂 Project Structure
```
BOOT-INF/classes/
  application.properties
  liquibase.properties
  com/store/litflix/
    LitflixApplication.class
    config/
    controller/
    dto/
    mapper/
    model/
    repository/
    service/
  db/changelog/
    db.changelog-master.yaml
    changes/
```

---

## 🌐 API Overview
<details>
<summary>Click to expand API endpoints</summary>

### 🔐 Auth
- `POST /api/auth/register` — create account
- `POST /api/auth/login` — login with JWT

### 📚 Books
- `GET /api/books` — list/search
- `GET /api/books/{id}` — details
- `POST /api/books` — create (admin)
- `PUT /api/books/{id}` — update (admin)
- `DELETE /api/books/{id}` — delete (admin)

### 🏷 Categories
- CRUD endpoints

### 🛒 Cart
- `GET /api/cart` — view
- `POST /api/cart/items` — add
- `PUT /api/cart/items/{itemId}` — update
- `DELETE /api/cart/items/{itemId}` — remove

### 📦 Orders
- `POST /api/orders` — place order
- `GET /api/orders` — list my orders
- `GET /api/orders/{orderId}/items` — order items

</details>

---

## 🗄 Database Schema (Liquibase)
- **books** — id, title, author, price, isbn, description, cover_image
- **users** — id, email, password, first_name, last_name, shipping_address
- **roles** — id, name (`ROLE_USER`, `ROLE_ADMIN`); **users_roles** join table
- **categories** — id, name, description; **books_categories** join table
- **shopping_carts** — id (FK to users.id, shared PK)
- **cart_items** — id, cart_id, book_id, quantity
- **orders** — id, user_id, order_date, total, status
- **order_items** — id, order_id, book_id, quantity, price

---

## 🚀 Getting Started

### 📋 Prerequisites
- JDK 17+
- Maven 3.9+
- MySQL 8+

### 🗄 Database Setup
```sql
CREATE DATABASE bookstore_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### ▶ Run (Maven)
```bash
mvn spring-boot:run
```

### ▶ Run (JAR)
```bash
mvn -DskipTests package
java -jar target/litflix-0.0.1-SNAPSHOT.jar
```

---

## 📸 Screenshots & Demos
*(Replace with actual images or GIFs)*

![Demo Screenshot](https://via.placeholder.com/800x400.png?text=Litflix+Demo)
![API Flow](https://via.placeholder.com/800x400.png?text=API+Flow)

---

## 🤝 Contributing
We welcome contributions! 🙌  
Here’s how you can help:
1. Fork the repo
2. Create a new branch (`git checkout -b feature/awesome-feature`)
3. Commit your changes (`git commit -m 'Add awesome feature'`)
4. Push to your branch (`git push origin feature/awesome-feature`)
5. Open a Pull Request

Please follow our coding standards and include tests where possible.

---

## 🗺 Roadmap
- 📄 Swagger/OpenAPI documentation
- 🐳 Docker Compose setup
- ⚙ CI/CD workflow
- 📊 Pagination defaults
- 🖼 Image upload support

---

## 📜 License
MIT (update as needed)
