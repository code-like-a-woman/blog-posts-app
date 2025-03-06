# 🚀 Blog Posts API

A simple **Spring Boot API** for managing blog posts.

## 📌 Features

- CRUD operations for blog posts
- RESTful architecture
- Automated API documentation with **OpenAPI**
- Supports **OpenAPI 3**
- Uses **Spring Boot 3.4.3**
- Java 21

## 🛠️ Getting Started
### 🔹 Prerequisites

Ensure you have the following installed before proceeding:

- **Java 21**
- **Apache Maven 3.9.9**

If you want to set up your local environment exactly like mine, follow these steps:\
🔗 [Building a REST API with Spring Boot in 10 Steps](https://code-like-a-woman.hashnode.dev/building-a-rest-api-with-spring-boot-in-10-steps)


### 1️⃣ Clone the Repository

```sh
git clone https://github.com/mdjc/blog-posts-app.git
cd blog-posts-app
```

### 2️⃣ Run Locally
```sh
mvn clean install
mvn spring-boot:run
```

### 🐳 Run with Docker
1. **Build & Run the Container**
   ```sh
   mvn clean package
   docker build -t blog-posts-app .
   docker run -d -p 8080:8080 --name blog-posts-app blog-posts-app
   ```
2. **Stop & Remove Container:**
   ```sh
   docker stop blog-posts-app && docker rm blog-posts-app
   ```
   
### Access the API:

`http://localhost:8080/posts`


## 📖 API Documentation

Once the application is running, explore the API using **Swagger UI**:

📌 **Swagger UI:**

```
http://localhost:8080/swagger-ui.html
```

📌 **OpenAPI JSON Definition:**

```
http://localhost:8080/v3/api-docs
```

## 🤝 Contributing

Contributions are welcome! Feel free to **fork the repo**, create a **new branch**, and submit a **pull request**.


