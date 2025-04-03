# Mental Health App - Backend Microservices 🚀

Welcome to the **Mental Health App Backend** repository! 🎯 This backend system is designed to support the frontend of a mental health application using a modular, scalable **microservices architecture**. Each service is built with **Spring Boot** and **Spring Cloud**, ensuring reliability and high performance.

---

## 📌 Table of Contents

- [Overview](#overview)
- [Microservices](#microservices)
- [Getting Started](#getting-started)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Frontend Repository](#frontend-repository)
- [Contributing](#contributing)
- [License](#license)

---

## 🛠 Overview

This project follows a **Microservices Architecture**, where each service is **loosely coupled** and **independently deployable**. The services communicate via REST APIs and service discovery, ensuring **scalability**, **fault tolerance**, and **ease of maintenance**.

### 🔥 Key Features

✅ **API Gateway** - Centralized entry point for all requests.\  
✅ **Service Discovery** - Dynamic service registration using **Eureka**.\  
✅ **Streaming Data** - Implemented using **Spring WebFlux**.\  
✅ **User Management** - Authentication, registration, and profiles.\  
✅ **Scalable & Resilient** - Easily add new microservices without breaking the system.\  
✅ **Mental Health Chat Support** - Handles AI-driven and human-assisted chat interactions.

---

## 🔗 Microservices

| Service           | Path            | Description                                                       |
| ----------------- | --------------- | ----------------------------------------------------------------- |
| **API Gateway**   | `apigateway`    | Handles authentication, logging, and routes requests.             |
| **Chat Service**  | `chat-service`  | Manages mental health chat interactions using **Spring WebFlux**. |
| **Eureka Server** | `eureka-server` | Service registry for dynamic service discovery.                   |
| **User Service**  | `user-service`  | Manages authentication, registration, and user profiles.          |

---

## 🚀 Getting Started

### 📋 Prerequisites

Ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.8+**
- **Ollama with LLaMA 3**

### 🏗 Running the Services

#### 1️⃣ Clone the repository:

```bash
git clone https://github.com/thughari/Mental-Health-App-Backend.git
cd backend-microservices
```

#### 2️⃣ Start the **Eureka Server**:

```bash
cd eureka-server
./mvnw spring-boot:run
```

#### 3️⃣ Start the other services in the following order:

```bash
cd apigateway
./mvnw spring-boot:run
```

```bash
cd user-service
./mvnw spring-boot:run
```

```bash
cd chat-service
./mvnw spring-boot:run
```

#### 4️⃣ Access the services:

- **API Gateway**: [`http://localhost:8081`](http://localhost:8081)
- **Eureka Dashboard**: [`http://localhost:8761`](http://localhost:8761)

---

## 🛠 Technologies Used

- **Spring Boot** - Microservices Framework
- **Spring Cloud** - Service Discovery & Configuration
- **Eureka** - Service Registry
- **Maven** - Build & Dependency Management
- **JWT** - Authentication & Authorization
- **Spring WebFlux** - Reactive Data Streaming

---

## 📁 Project Structure

```bash
backend-microservices/
├── apigateway/       # API Gateway service
├── chat-service/     # Mental Health Chat microservice
├── eureka-server/    # Service registry
├── user-service/     # User management microservice
```

Each folder contains:

- `src/` - Source code
- `pom.xml` - Maven configuration
- `.mvn/` - Maven wrapper files

---

## 🔗 Frontend Repository

The frontend for this mental health application can be found at:
👉 [Frontend Repository - Mental Health App](https://github.com/thughari/Mental-Health-App)

---

## 🤝 Contributing

We welcome contributions! 🚀 To contribute:

1. **Fork** the repository.
2. Create a **feature branch**:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature"
   ```
4. Push the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a **Pull Request**.

---

## 📜 License

This project is licensed under the **Apache 2.0 License**. See the [LICENSE](LICENSE) file for details.

---

### 🎯 **Happy Coding!** 🚀

