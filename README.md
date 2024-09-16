# Zand Credit Card Entrypoint

Zand Credit Card Entrypoint is a microservice responsible for handling the initial onboarding process of credit card applications. It serves as the entry point for receiving and processing customer data, integrating with third-party services to assess eligibility based on financial history, risk analysis, and compliance with AML/KYC regulations.

## Features

- **Credit Card Onboarding**: Manages the submission and validation of credit card applications.
- **Service Integration**: Connects with internal microservices for financial history checks, AML/KYC compliance, and risk assessment.

## Technologies

- **Java 21**
- **Spring Boot**
- **Microservices Architecture**

## Requirements

Before running the project, ensure you have the following installed:

- Java 21
- Gradle

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/JhonatanMota/zand-credit-card-entrypoint.git
   
2. Navigate to the project directory:
    ```bash
    cd zand-credit-card-entrypoint
   
3. Build the project:
    ```bash
   ./gradlew build

4. Run the application:
    ```bash
   ./gradlew bootRun

## Usage
Once the application is running, you can access the following endpoints:

- [**Credit Card Application**](https://localhost:8080/api/credit-cards)
- [**Credit Card Application Status**](https://localhost:8080/api/credit-cards/{emiratesId}/status/{status})
- [**Swagger documentation**](https://localhost:8080/swagger-ui/)