# 💳 Spring Boot Card Service

This Spring Boot application manages virtual cards, allowing users to create cards, top up or spend from them, and track transactions. It demonstrates RESTful API principles, entity relationships with JPA, and basic concurrency handling using optimistic locking.

---

## 🧱 Entities

### 🪪 Card Entity

The `Card` entity represents a virtual card.

**Attributes:**

- `id`: `Long`  
  Auto-generated primary key.

- `cardholderName`: `String`  
  The name of the cardholder.

- `balance`: `BigDecimal(10,2)`  
  Card balance with 2 decimal precision.

- `createdAt`: `Instant`  
  Timestamp indicating when the card was created, stored in ISO-8601 format.

- `version`: `Long`  
  Used for optimistic locking to prevent concurrent modification issues. This field is not returned in API responses.

---

### 💸 Transaction Entity

The `Transaction` entity stores information related to each spend or top-up operation on a card.

**Attributes:**

- `id`: `Long`  
  Auto-generated primary key.

- `amount`: `BigDecimal(10,2)`  
  The amount of the transaction.

- `type`: `String`  
  Indicates whether the transaction was a `"spend"` or a `"topup"`.

- `timestamp`: `LocalDateTime`  
  Date and time the transaction occurred.

- `card`: `Card`  
  The card associated with this transaction. Mapped with a `@ManyToOne` relationship and foreign key.

---

## 🧭 Controller - `CardController`

The `CardController` exposes RESTful endpoints for managing cards and transactions. DTOs were implemented and used to trasfer data between different parts of the application.

### `POST /cards`  
Create a new card.

**Request Body (cardCreate DTO):**
```json
{
  "cardholderName": "Alice",
  "initialBalance": 100.00
}
```

**Response:**
```json
{
  "id": 1,
  "cardholderName": "Alice",
  "balance": 100.00,
  "createdAt": "2025-06-14T12:00:00Z"
}
```

---

### `GET /cards`  
Retrieve a list of all cards.

**Response:**
```json
[
  {
    "id": 1,
    "cardholderName": "Alice",
    "balance": 100.00,
    "createdAt": "2025-06-14T12:00:00Z"
  },
  {
    "id": 2,
    "cardholderName": "Mike",
    "balance": 90.00,
    "createdAt": "2025-06-14T12:01:00Z"
  },
  {
    "id": 3,
    "cardholderName": "John",
    "balance": 40.00,
    "createdAt": "2025-06-14T12:02:00Z"
  }
]
```

---

### `GET /cards/{id}`  
Retrieve a specific card by ID.

**Path Variable:**
- `id`: Card ID

**Response:**
```json
{
  "id": 1,
  "cardholderName": "Alice",
  "balance": 100.00,
  "createdAt": "2025-06-14T12:00:00Z"
}
```

---

### `POST /cards/{id}/spend`  
Spend from a card's balance.

**Path Variable:**
- `id`: Card ID

**Request Body (Transaction):**
```json
{
  "amount": 25.00
}
```

**Response (cardFundsChange DTO):**
```json
{
  "cardId": 1,
  "remainingBalance": 75.00
}
```

---

### `POST /cards/{id}/topup`  
Top up a card's balance.

**Path Variable:**
- `id`: Card ID

**Request Body (Transaction):**
```json
{
  "amount": 50.00
}
```

**Response (cardFundsChange DTO):**
```json
{
  "cardId": 1,
  "remainingBalance": 125.00
}
```

---

### `GET /cards/{id}/transactions`  
Retrieve a list of all transactions for a specific card.

**Path Variable:**
- `id`: Card ID

**Response:**
```json
[
  {
    "id": 1,
    "amount": 25.00,
    "type": "spend",
    "timestamp": "2025-06-14T12:30:00"
  },
  {
    "id": 2,
    "amount": 50.00,
    "type": "topup",
    "timestamp": "2025-06-14T13:00:00"
  }
]
```

---

## 🗄 Database

An in-memory H2 database is used to store all the records, whilst Spring Data JPA is used for CRUD operations.

```conf
# H2 Database
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:dcbapp
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

---

## 🔐 Optimistic Locking

The application uses optimistic locking (`@Version`) on the `Card` entity to avoid race conditions during balance updates. If two users try to spend or top up the same card simultaneously, only one transaction will succeed, and the other will throw an `ObjectOptimisticLockingFailureException`.

---

## 🧪 Testing

Unit and integration tests cover service and controller logic, including:

- Card creation and retrieval
- Spending and topping up
- Version conflict handling


Run tests using:

```bash
./mvnw test
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (or in-memory H2 for testing)

### Running the App

```bash
./mvnw spring-boot:run
```

The app will be available at `http://localhost:8082`.

---

## 📁 Project Structure

```
src
├── controller
│   └── CardController.java
├── dto
│   ├── cardCreate.java
│   └── cardFundsChange.java
├── entity
│   ├── Card.java
│   └── Transaction.java
├── repository
│   ├── CardRepo.java
│   └── TransactionRepo.java
├── service
│   ├── card
│   │   ├── CardService.java
│   │   └── CardServiceImpl.java
│   └── transaction
│       ├── TransactionService.java
│       └── TransactionServiceImpl.java
└── SpringBootApplication.java
```

