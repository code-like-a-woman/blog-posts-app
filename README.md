# 🚀 Blog Posts API

A simple **Spring Boot API** for managing blog posts.

## 📌 Features

- CRUD operations for blog posts
- RESTful architecture
- Automated API documentation with **OpenAPI**
- Supports **OpenAPI 3**
- Uses **Spring Boot 3.4.3**
- Java 21
- JWT token verification with version 0.12.6
- Authentication with Spring Security
- Automated testing pipeline with **GitHub Actions**

## 🛠️ Getting Started
### 🔹 Prerequisites

Ensure you have the following installed before proceeding:

- **Java 21**
- **Apache Maven 3.9.9**

If you want to set up your local environment exactly like mine, follow these steps:\
🔗 [Building a REST API with Spring Boot in 10 Steps](https://code-like-a-woman.hashnode.dev/building-a-rest-api-with-spring-boot-in-10-steps)
Otherwise, you can use docker to run this app, see instructions down below.

### 1️⃣ Clone the Repository

```sh
git clone https://github.com/mdjc/blog-posts-app.git
cd blog-posts-app
```
## 🔐 Environment Variables

The project has basic JWT and security config. You need to set the following environment variables for that purpose.
| Variable         | Description           |
|------------------|------------------------|
| `API_SECRET_KEY` | Your API secret key    |
| `USERNAME`       | Your username          |
| `PASSWORD`       | Your password          |


### Set it locally:
**Linux/macOS:**
1. Generate a public key:

```bash 
openssl rand -base64 32
```

2. Set your environment variables:

```bash
export API_SECRET_KEY=your-secret
export USERNAME=your-username
export PASSWORD=your-password
```

### 2️⃣ Run Locally
```sh
mvn clean install
mvn spring-boot:run
```

### 🧪 Running Tests Locally

To run the tests securely with the same environment config:

```bash
export API_SECRET_KEY=your-secret
export USERNAME=your-username
export PASSWORD=your-password
mvn test
```

### 🐳 Run with Docker
1. **Build & Run the Container**

```sh
mvn clean package

docker build -t blog-posts-app .

docker run \
  --env API_SECRET_KEY=your-secret \
  --env USERNAME=your-username \
  --env PASSWORD=your-password \
  -d -p 8080:8080 \
  --name blog-posts-app \
  blog-posts-app
```

   
2. **Stop & Remove Container:**

```sh
docker stop blog-posts-app && docker rm blog-posts-app
```
   
### Access the API:

1. Authenticate to get a JWT token: `curl -X POST "http://localhost:8080/auth/login?username=your-user-name&password=your-password"`
2. Invoke any endpoint with the generated token:
`curl -H "Authorization: Bearer YOUR_JWT_TOKEN_FROM_PREVIOUS_STEP" http://localhost:8080/api/posts `


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

## 🔄 Continuous Integration

CI tests run automatically on every push or pull request to the `main` branch via [GitHub Actions](https://github.com/mdjc/blog-posts-app/actions).

