# Producer Service

## 📌 Overview

Producer Service is a Spring Boot application that exposes REST APIs to ingest event data and publish it to an Apache Kafka topic. It enriches incoming requests with system-generated fields and ensures reliable message delivery using retry and fallback mechanisms.

---

## 🚀 Tech Stack

* Java 21
* Spring Boot 3.x
* Spring Web
* Apache Kafka
* Lombok
* JUnit 5 & Mockito
* JPA

---

## 📁 Project Structure

```
src/main/java/com/assignment/producer/
├── controller
├── service
├── model
├── util
├── exception
├── config
└── ProducerServiceApplication.java
```

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```
git clone https://github.com/Panchalic3/assignment-producerSrvice
cd assignment-producerSrvice
```

### 2. Build the project

```
mvn clean install
```

### 3. Run the application

```
mvn spring-boot:run
```

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

```
Message sent to Kafka
```

#### ❌ Validation Errors

```json
{
  "userName": "userName is a mandatory field",
  "data": "data cannot exceed 100 characters"
}
```

---

## 🔄 Processing Flow

* Client sends request to `/api/ingest`
* Request is validated using `@Valid`
* Data is passed to service layer
* Mapper enriches payload with:

  * `eventId` (UUID)
  * `timestamp`
* Data is converted to JSON
* Message is sent to Kafka topic

---

## ♻️ Retry & Failure Handling

To ensure reliable message delivery, the producer implements retry and fallback mechanisms:

* Kafka producer retries sending messages on transient failures using configured retry properties
* If Kafka is unavailable or message sending fails:

  * The event is persisted in a retry table (database)
  * Retry count is tracked for each failed event
  * Status is maintained using an enum (`SENT`, `FAILED`)
* A scheduled job/process can retry sending failed events later

This ensures:

* No data loss
* Improved system resilience
* Graceful handling of Kafka downtime

---

## 🧪 Testing

### Run all tests:

```
mvn test
```

#### ✔ Test Coverage Includes:

* Controller layer (MockMvc)
* Service layer (Mockito)
* Mapper (Unit Test)
* Global Exception Handling
* Util

---

## 👩‍💻 Author

**Panchali**
