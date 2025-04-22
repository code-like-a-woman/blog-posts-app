# 🚀 Blog Posts API

![CI](https://github.com/mdjc/blog-posts-app/actions/workflows/ci.yml/badge.svg)

A simple **Spring Boot API** for managing blog posts.

## 📌 Features

- CRUD operations for blog posts
- RESTful architecture
- Automated API documentation with **OpenAPI**
- Supports **OpenAPI 3**
- Built with **Spring Boot 3.4.3**
- Java 21
- JWT token verification
- Authentication with Spring Security
- Continuous Integration with **GitHub Actions**
- Optimized **Docker** build process

---

## 🛠️ Getting Started

### 🔹 Prerequisites

Ensure you have the following installed:

- **Docker** installed and running  
- (**Optional**) **Java 21** and **Maven 3.9.9** for local unit testing (not required to run the application)

---

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/mdjc/blog-posts-app.git
cd blog-posts-app
```

---

## 🔐 Environment Variables

The application requires the following environment variables for authentication and security:

| Variable         | Description                 |
|------------------|------------------------------|
| `API_SECRET_KEY` | Secret key for JWT tokens     |
| `USERNAME`       | Username for authentication  |
| `PASSWORD`       | Password for authentication  |

You can generate a random secret key for **JWT** with:

```bash
openssl rand -base64 32
```

---

## 🐳 Running the Application with Docker

1. **Build & Run the Container:**

```bash
docker build -t blog-posts-app .

docker run \
  --env API_SECRET_KEY=your-secret \
  --env USERNAME=your-username \
  --env PASSWORD=your-password \
  -p 8080:8080 \
  blog-posts-app
```

2. **Stop & Remove the Container:**

```bash
docker stop blog-posts-app && docker rm blog-posts-app
```

---

## 🧪 Running Unit Tests Locally (Optional)

If you prefer, you can still run the unit tests using Maven without needing Docker:

```bash
export API_SECRET_KEY=your-secret
export USERNAME=your-username
export PASSWORD=your-password
mvn test
```

✅ This allows for quick feedback during development without needing to push your code.

---

## 🔑 Accessing the API

1. **Authenticate to get a JWT token:**

```bash
curl -X POST "http://localhost:8080/auth/login?username=your-username&password=your-password"
```

2. **Invoke API endpoints using the JWT token:**

```bash
curl -H "Authorization: Bearer YOUR_JWT_TOKEN" http://localhost:8080/api/posts
```

---

## 📖 API Documentation

Once running, explore the API via:

- 📚 **Swagger UI:**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- 📄 **OpenAPI JSON:**  
  [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔄 Continuous Integration

CI tests run automatically on every push or pull request to the `main` branch via [GitHub Actions](https://github.com/mdjc/blog-posts-app/actions).

---

## 🤝 Contributing

Contributions are welcome! Feel free to **fork the repo**, create a **feature branch**, and submit a **pull request**.
