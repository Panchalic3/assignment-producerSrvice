# Kafka Producer Service

## 📌 Overview

Kafka Producer Service is a Spring Boot application that exposes REST APIs to ingest event data and publish it to an
Apache Kafka topic. It validates incoming requests, enriches them with system-generated fields, and ensures reliable
message publishing.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Apache Kafka
- PostgreSQL
- Lombok
- JUnit 5 & Mockito

## 📂 Project Structure

src/main/java/com/assignment/producer/
│
├── controller
├── service
├── model
├── util
├── exception
├── config
└── ProducerServiceApplication.java

---

## ⚙️ Setup & Installation

## Clone the Repository

git clone https://github.com/Panchalic3/assignment-producerSrvice
cd assignment-producerSrvice

### 2. Build the project

mvn clean install

### 3. Run the application

mvn spring-boot:run

---

## 📡 API Endpoints

### ➤ Ingest Event

**POST** `/api/ingest`

#### ➤ Request Body

```json
{
  "userName": "Panchali",
  "data": "Sample event"
}
```

#### ✅ Success Response

Message sent to Kafka

#### ❌ Validation Errors

```json
{
  "userName": "userName is a mandatory field",
  "data": "data cannot exceed 100 characters"
}
```

### 🔄 Processing Flow

- Client sends request to /api/ingest
- Request is validated using @Valid
- Data is passed to service layer
- Mapper enriches payload with:
  eventId (UUID) and timestamp
- Data is converted to JSON
- Message is sent to Kafka topic

### 🧪 Testing

### Run all tests:

mvn test

####  ✔ Test Coverage Includes:
- Controller layer (MockMvc)
- Service layer (Mockito)
- Mapper (Unit Test)
- Global Exception Handling

## 👩‍💻 Author
**Panchali**