# 📘 Expense Tracker with Budgeting Goals

A backend system built using **Java**, **Spring Boot**, **Spring Cloud**, and **Docker** that allows users to track expenses, set budgeting goals, and view analytics of their spending habits.


## 🚀 Features

- **User Authentication** using JWT
- **Expense Tracking** with category and tags
- **Budget Goal Setting** (monthly/weekly)
- **Spending Analytics** (by category/date)
- **Report Service**: Generate and email monthly reports
- **Service Discovery** using Eureka
- **API Gateway** for centralized routing
- **Dockerized Microservices** with MySQL


## 🧱 Microservices Architecture

| Service              | Port  | Description                          |
|----------------------|-------|--------------------------------------|
| `eureka-server`      | 8761  | Service discovery                    |
| `api-gateway`        | 8080  | Gateway for routing APIs            |
| `auth-service`       | 8081  | User registration, login, JWT auth  |
| `expense-service`    | 8082  | Add, view, delete expenses          |
| `goal-service`       | 8083  | Set and track budget goals          |
| `analytics-service`  | 8084  | Analyze spending trends             |
| `report-service`     | 8085  | Send Monthly Reports                |
| `mysql`              | 3306  | Shared relational database          |


## 🧰 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Cloud Netflix Eureka**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL**
- **Docker + Docker Compose**


## 🐳 Run with Docker Compose

### 1. Build JARs for all services

```bash
cd auth-service && mvn clean package -DskipTests=true && cd ..
cd expense-service && mvn clean package -DskipTests=true && cd ..
cd goal-service && mvn clean package -DskipTests=true && cd ..
cd analytics-service && mvn clean package -DskipTests=true && cd ..
cd report-service && mvn clean package -DskipTests=true && cd ..
cd api-gateway && mvn clean package -DskipTests=true && cd ..
cd eureka-server && mvn clean package -DskipTests=true && cd ..
```

### 2. Build and start all services with Docker Compose

```bash
docker-compose up --build
```

### 3. Visit the services:

- **Eureka Server:** [http://localhost:8761](http://localhost:8761)
- **API Gateway:** [http://localhost:8080](http://localhost:8080)


## 🔐 Sample Endpoints

| Method | Endpoint                     | Description                  |
|--------|------------------------------|------------------------------|
| POST   | `/auth/register`             | Register a new user         |
| POST   | `/auth/login`                | Login and get JWT token     |
| GET    | `/api/expenses`              | List all expenses           |
| POST   | `/api/goals`                 | Create a budget goal        |
| GET    | `/api/analytics/overview`     | View analytics summary     |


## 📦 Environment Variables (used in Docker Compose)

Each service reads DB config like:

```properties
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/expense_tracker
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root
```


## 🧪 Future Enhancements

- Email/SMS reminders
- User roles and admin panel
- OAuth2 / Google login
- Export data as CSV
