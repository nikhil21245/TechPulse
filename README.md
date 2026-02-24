# TechPulse - Microservices Architecture

A comprehensive learning platform built with Spring Boot microservices architecture.

## 🏗️ Architecture
```
├── eureka-server          # Service Registry (Port 8761)
├── api-gateway            # API Gateway with JWT Auth (Port 8080)
├── user-service           # User Management & Auth (Port 8081)
├── article-service        # Article Management (Port 8082)
└── quiz-service           # Quiz & Gamification (Port 8083)
```

## 🚀 Technologies

- **Backend:** Spring Boot 3.3.x, Java 17
- **Database:** MySQL 8.x
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Security:** JWT Authentication
- **Communication:** OpenFeign, REST
- **Documentation:** Swagger/OpenAPI
- **Build Tool:** Maven

## 📋 Prerequisites

- JDK 17 or higher
- MySQL 8.x
- Maven 3.6+
- Postman (optional, for testing)

## 🛠️ Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/nikhil21245/techpulse.git
cd techpulse
```

### 2. Create Databases
```sql
CREATE DATABASE techpulse_userdb;
CREATE DATABASE techpulse_articledb;
CREATE DATABASE techpulse_quizservicedb;
```

### 3. Configure Database

Update `application.yml` in each service with your MySQL credentials:
```yaml
spring:
  datasource:
    username: YOUR_MYSQL_USERNAME
    password: YOUR_MYSQL_PASSWORD
```

### 4. Start Services (in order)
```bash
# 1. Start Eureka Server
cd eureka-server
./mvnw spring-boot:run

# 2. Start User Service
cd user-service
./mvnw spring-boot:run

# 3. Start Article Service
cd article-service
./mvnw spring-boot:run

# 4. Start Quiz Service
cd quiz-service
./mvnw spring-boot:run

# 5. Start API Gateway
cd api-gateway
./mvnw spring-boot:run
```

### 5. Verify Services

- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- User Service Swagger: http://localhost:8081/swagger-ui.html
- Article Service Swagger: http://localhost:8082/swagger-ui.html
- Quiz Service Swagger: http://localhost:8083/swagger-ui.html

## 📚 API Documentation

### Using Swagger UI

1. Navigate to any service's Swagger URL
2. Click **"Authorize"** button
3. Use `/api/auth/login` to get JWT token
4. Paste token in authorization popup
5. Test all endpoints interactively!

### Key Endpoints

#### User Service (Port 8081)
- POST `/api/auth/register` - Register new user
- POST `/api/auth/login` - Login and get JWT
- GET `/api/users/{id}` - Get user profile
- GET `/api/users/all` - Get all users (protected)

#### Article Service (Port 8082)
- GET `/api/articles` - Browse articles (public)
- POST `/api/articles` - Create article (protected)
- PUT `/api/articles/{id}` - Update article (protected)
- DELETE `/api/articles/{id}` - Delete article (protected)

#### Quiz Service (Port 8083)
- GET `/api/quiz` - Browse quizzes (public)
- POST `/api/quiz` - Create quiz (protected)
- POST `/api/quiz/submit` - Submit quiz attempt (protected)
- GET `/api/questions/quiz/{quizId}` - Get quiz questions

## 🎯 Features

### User Service
- ✅ JWT-based authentication
- ✅ User registration & login
- ✅ Profile management
- ✅ XP points & gamification
- ✅ Quiz statistics tracking

### Article Service
- ✅ CRUD operations
- ✅ Author validation via Feign
- ✅ Category & tag management
- ✅ Trending articles
- ✅ Search functionality

### Quiz Service
- ✅ Quiz creation with questions
- ✅ Multiple choice questions
- ✅ Automatic scoring
- ✅ User stats update (Feign)
- ✅ Quiz attempts tracking

### Infrastructure
- ✅ Service discovery (Eureka)
- ✅ Centralized routing (Gateway)
- ✅ JWT authentication
- ✅ Load balancing
- ✅ Inter-service communication

## 🧪 Testing

### Quick Test Flow

1. **Register User**
```bash
POST http://localhost:8080/api/auth/register
{
  "name": "Test User",
  "email": "test@example.com",
  "password": "test123"
}
```

2. **Login**
```bash
POST http://localhost:8080/api/auth/login
{
  "email": "test@example.com",
  "password": "test123"
}
```

3. **Create Article** (use token from login)
```bash
POST http://localhost:8080/api/articles
Authorization: Bearer YOUR_TOKEN
X-User-Id: 1
{
  "title": "My First Article",
  "content": "Article content here",
  "category": "Technology"
}
```

## 🏆 Architecture Highlights

- **Microservices**: Independent, scalable services
- **Service Discovery**: Auto-registration with Eureka
- **API Gateway**: Single entry point with JWT validation
- **Inter-Service Communication**: Feign clients with Eureka
- **Security**: JWT tokens, protected endpoints
- **Observability**: Actuator endpoints, logging

## 📝 License

This project is licensed under the MIT License.

## 👤 Author

**Nikhil kusale**
- GitHub: [@nikhil21245](https://github.com/nikhil21245)
- LinkedIn: [Nihkil kusale](https://linkedin.com/in/nikhil-kusale-2567a0200)
- Email: nikhilkusale2018@gmail.com

## 🙏 Acknowledgments

- Spring Boot & Spring Cloud
- Netflix OSS (Eureka)
- MySQL
- Swagger/OpenAPI
