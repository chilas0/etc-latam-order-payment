# Order Processing Microservices

## Overview

This project implements a distributed order processing system using a microservices architecture based on Spring Boot, Apache Kafka, PostgreSQL, Docker and Docker Compose.

The solution simulates the lifecycle of an order from creation to payment processing using asynchronous communication between services.

The system consists of two independent microservices:

* **OrderMS**: Responsible for order creation, persistence and order status management.
* **PaymentMS**: Responsible for payment processing simulation.

Communication between services is performed asynchronously using Apache Kafka.

Sensitive card information is protected using RSA encryption. OrderMS encrypts card data using a public key before publishing events, and PaymentMS decrypts the information using the corresponding private key.

---

# Architecture

## High-Level Flow

```mermaid
flowchart LR

    Client[Client]

    OrderMS[OrderMS]
    PaymentMS[PaymentMS]

    PostgreSQL[(PostgreSQL)]

    Kafka[(Apache Kafka)]

    Topic1[order-placed]
    Topic2[payment-processed]

    Client -->|POST /orders| OrderMS

    OrderMS -->|Save Order (PENDING)| PostgreSQL

    OrderMS -->|Publish Event| Topic1

    Topic1 --> PaymentMS

    PaymentMS -->|Decrypt Card| PaymentMS

    PaymentMS -->|Process Payment| PaymentMS

    PaymentMS -->|Publish Event| Topic2

    Topic2 --> OrderMS

    OrderMS -->|Update Order Status| PostgreSQL

    Client -->|GET /orders/{id}| OrderMS
```

---

# Solution Components

## OrderMS

Responsibilities:

* Create orders through REST API.
* Validate incoming requests.
* Encrypt card information using RSA.
* Store orders in PostgreSQL.
* Publish `order-placed` events.
* Consume `payment-processed` events.
* Update order status.

---

## PaymentMS

Responsibilities:

* Consume `order-placed` events.
* Decrypt card information.
* Simulate payment processing.
* Publish `payment-processed` events.

---

## Apache Kafka

Kafka acts as the asynchronous communication layer between services.

Topics used:

* `order-placed`
* `payment-processed`

---

## PostgreSQL

Stores order information and current order status.

---

## Kafka UI

Provides a graphical interface to inspect Kafka topics and messages.

---

## pgAdmin

Provides a graphical interface for PostgreSQL administration.

---

# Repository Structure

This solution is composed of two repositories.

Clone both repositories into the same parent directory.

Expected structure:

```text
IdeaProjects
│
├── etc-latam-order-payment
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── README.md
│   └── src
│
└── etc-latam-payment-order
    ├── Dockerfile
    └── src
```

---

# Prerequisites

Before running the project, ensure the following software is installed:

Required Software

- Java 21 LTS
- Maven 3.9+
- Docker Desktop
- Git
- IntelliJ IDEA Community Edition (recommended)

Installation Links

Java: Especificar
https://www.oracle.com/java/technologies/downloads/
https://adoptium.net/es/temurin/releases?version=21
Pasos
Seleccione:
Version: 21 (LTS)
Operating System: Windows
Architecture: x64
Descargue el archivo .msi.
Ejecútelo como administrador.
Durante la instalación asegúrese de marcar:
Add to PATH
Set JAVA_HOME variable
Finalice la instalación.

```bash
java -version
```

Maven: Especificar
https://maven.apache.org/download.cgi


```bash
Binary zip archive
apache-maven-3.9.x-bin.zip
```
2. Descomprimir
3. Muevelo a C:\Program Files\Apache\Maven
4. Configurar Variables de Entorno
   Crear MAVEN_HOME
   Posible valor C:\Herramientas\apache-maven-3.9.11
   Agregar al PATH
   %MAVEN_HOME%\bin
5. Cerrar y abrir CMD

Luego ejecutar:
```bash
mvn -version
```
Debe mostrar algo parecido a:
```bash
Apache Maven 3.9.11

Java version: 21
```
Docker Desktop:
https://www.docker.com/products/docker-desktop/

Crea una cuenta 

Git:
https://git-scm.com/download/win
Crea una cuenta 

IntelliJ IDEA Community:
https://www.jetbrains.com/idea/download/

instale 
Seleccione Add "bin" folder to "PATH"
.java
.pom
Intellij IDEA
Verify installation:

Postman:

```bash
java -version
mvn -version
docker --version
git --version
```

---

# Clone the Repositories

Clone both repositories into the same parent directory:

```bash
mkdir IdeaProjects

cd IdeaProjects

git clone https://github.com/chilas0/etc-latam-order-payment.git

git clone https://github.com/chilas0/etc-latam-payment-order.git
```

Example:

```bash
git clone https://github.com/your-user/etc-latam-order-payment.git

git clone https://github.com/your-user/etc-latam-payment-order.git
```

---

# Build the Applications

Build OrderMS:

```bash
cd etc-latam-order-payment

mvn clean package -DskipTests
or
.\mvnw.cmd clean package -DskipTest
```

Build PaymentMS:

```bash
cd ../etc-latam-payment-order

mvn clean package -DskipTests
or
.\mvnw.cmd clean package -DskipTest
```

These commands generate executable JAR files inside the `target` directory.

---

# Create Docker Images

Build OrderMS image:

```bash
cd ../etc-latam-order-payment

docker build -t order-ms:latest .
```

Build PaymentMS image:

```bash
cd ../etc-latam-payment-order

docker build -t payment-ms:latest .
```

Verify images:

```bash
docker images
```

Expected images:

```text
order-ms
payment-ms
```

---

# Start the Environment

Navigate to:

```bash
cd ../etc-latam-order-payment
```

Start all services:

```bash
docker compose up -d
```

Verify:

```bash
docker ps
```

Expected containers:

```text
order-postgres
order-kafka
order-ms
payment-ms
kafka-ui
order-pgadmin
```

---

# Available Services

| Service   | URL                   |
| --------- | --------------------- |
| OrderMS   | http://localhost:8080 |
| PaymentMS | http://localhost:8083 |
| Kafka UI  | http://localhost:8081 |
| pgAdmin   | http://localhost:8082 |

---

# Ports

| Service    | Port |
| ---------- | ---- |
| OrderMS    | 8080 |
| PaymentMS  | 8083 |
| Kafka UI   | 8081 |
| pgAdmin    | 8082 |
| PostgreSQL | 5433 |
| Kafka      | 9092 |

---

# API Documentation

Official Postman Documentation:

https://documenter.getpostman.com/view/14557619/2sBXwqsAyt

---

# Create Order

## Endpoint

```http
POST http://localhost:8080/orders
```

## Request Body

```json
{
  "productName": "Laptop",
  "amount": 1200.00,
  "cardNumber": "1234567890123456"
}
```

## cURL

```bash
curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
    "productName": "Laptop",
    "amount": 1200.00,
    "cardNumber": "1234567890123456"
}'
```

---

# Get Order By Id

## Endpoint

```http
GET http://localhost:8080/orders/{id}
```

Example:

```http
GET http://localhost:8080/orders/1
```

## cURL

```bash
curl --location 'http://localhost:8080/orders/1'
```

---

# Kafka Validation

Kafka UI:

```text
http://localhost:8081
```

Expected topics:

```text
order-placed
payment-processed
```

Expected flow:

```text
POST /orders
      ↓
order-placed
      ↓
PaymentMS
      ↓
payment-processed
      ↓
OrderMS
      ↓
PAID / FAILED_PAYMENT
```

---

# Database Validation

pgAdmin:

```text
http://localhost:8082
```

Credentials:

```text
Email: admin@admin.com
Password: admin
```

Register PostgreSQL server:

```text
Host: postgres
Port: 5432
Database: orderdb
Username: postgres
Password: postgres
Role: postgres
```

Query example:

```sql
SELECT *
FROM orders
ORDER BY id DESC;
```

---

# Security

Sensitive card information is never transmitted in plain text through Kafka.

OrderMS encrypts card data using an RSA public key.

PaymentMS decrypts card data using the corresponding RSA private key.

This ensures that sensitive information remains protected during asynchronous communication.

---

# Technical Decisions

## Kafka

Kafka was selected to implement asynchronous communication and decouple business processes.

## RSA Encryption

RSA encryption was implemented to protect sensitive card information during event transmission.

## Docker

Docker and Docker Compose were used to provide a reproducible and portable execution environment.

## PostgreSQL

PostgreSQL was selected as the relational database due to its robustness and compatibility with Spring Data JPA.

---

# Complete Validation Scenario

1. Execute POST /orders.
2. Verify a new order is created with status PENDING.
3. Verify an event appears in the order-placed topic.
4. Verify PaymentMS consumes the event.
5. Verify PaymentMS publishes payment-processed.
6. Verify OrderMS updates the order status.
7. Execute GET /orders/{id}.
8. Verify the final status is PAID or FAILED_PAYMENT.

---

# Future Improvements

* Flyway database migrations
* Unit tests
* Integration tests
* Dead Letter Queue (DLQ)
* Retry policies
* OpenAPI / Swagger
* Distributed tracing
* Centralized logging
* Observability with Prometheus and Grafana

---

# Stop the Environment

Stop all services:

```bash
docker compose down
```

Remove containers, networks and volumes:

```bash
docker compose down -v
```
