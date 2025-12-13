🍬 Sweet Shop Management System (Backend)

Welcome to the backend repository for the Sweet Shop Management System! This project is a robust, enterprise-grade RESTful API built with Spring Boot, designed to power a modern sweet shop application. It handles user authentication, inventory management, and secure transactions, serving as the core logic for the frontend application.

This project was developed as part of the AI Kata, demonstrating modern development practices, Test-Driven Development (TDD), and the effective use of AI tools in software engineering.

📖 Project Overview

The Sweet Shop Management System backend provides a secure and scalable API for managing a boutique sweet shop. It features:

Secure Authentication: User registration and login using JWT (JSON Web Tokens) for stateless security.

Role-Based Access Control (RBAC): Differentiates between standard Customers (who can browse and purchase) and Administrators (who manage inventory).

Inventory Management: Real-time tracking of sweet stock, with automatic prevention of purchases when stock is low.

Comprehensive API: Endpoints for CRUD operations on sweets, search functionality, and stock adjustments.

Data Seeding: Automatic initialization of an Admin user and sample data for easy setup.

🛠️ Tech Stack

Framework: Java 17+, Spring Boot 3.x

Database: PostgreSQL (Production), H2 (Testing)

Security: Spring Security, JWT (JJWT library), BCrypt

Build Tool: Maven

Testing: JUnit 5, Mockito (following TDD principles)

🚀 Setup & Run Instructions

Follow these steps to get the backend up and running on your local machine.

Prerequisites

Java Development Kit (JDK) 17 or higher installed.

PostgreSQL installed and running.

Maven (optional, as the project includes the ./mvnw wrapper).

1. Configure the Database

Create a new PostgreSQL database named sweetshop.

Open src/main/resources/application.properties.

Update the database connection details with your local credentials:

spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update


2. Run the Application

Open your terminal in the project's root directory and run the following command:

On Windows:

./mvnw.cmd spring-boot:run


On Mac/Linux:

./mvnw spring-boot:run


The server will start on http://localhost:8081.

3. Automatic Data Seeding

On the very first run, the application's DataInitializer will automatically create:

A default Admin User:

Email: admin@sweetshop.com

Password: admin123

A set of initial Sample Sweets to populate your inventory.

🔌 API Documentation

Here are the available endpoints. Most endpoints (except Auth and Search) require a valid Bearer Token in the Authorization header.

Authentication

POST /api/auth/register - Register a new customer.

POST /api/auth/login - Login and receive a JWT token.

Sweets (Public)

GET /api/sweets - Retrieve a list of all sweets.

GET /api/sweets/search - Search sweets by name, category, or price range.

Sweets (Protected - User)

POST /api/sweets/{id}/purchase - Purchase a specific quantity of a sweet (decreases stock).

Sweets (Protected - Admin Only)

POST /api/sweets - Add a new sweet to the inventory.

PUT /api/sweets/{id} - Update details of an existing sweet.

DELETE /api/sweets/{id} - Remove a sweet from the inventory.

POST /api/sweets/{id}/restock - Add stock to an existing sweet.

🤖 My AI Usage

Transparency Statement: In compliance with the AI Kata assessment guidelines, I utilized AI tools to accelerate development, debug complex integration issues, and ensure code quality. Below is a detailed breakdown of my collaboration with AI.

Primary Tool: Google Gemini

1. Debugging & Security Configuration

Challenge: I encountered a persistent 403 Forbidden error when trying to connect my React frontend to the backend, even after successfully logging in. The browser's console showed CORS errors.

AI Solution: I provided my SecurityConfig.java to Gemini. The AI correctly diagnosed that while I had a WebConfig for CORS, the Spring Security filter chain was blocking the "pre-flight" OPTIONS requests before they could reach my configuration. It generated the specific .cors(Customizer.withDefaults()) configuration for the SecurityFilterChain, which immediately resolved the issue.

2. Data Type Mismatches

Challenge: When testing the "Add Sweet" functionality from the frontend, I was receiving 400 Bad Request errors.

AI Solution: Gemini helped me analyze the JSON payload being sent and identified that my frontend form was sending numerical values (Price, Quantity) as Strings (e.g., "10.50"), while the Java Backend DTO expected Double and Integer. I used the AI's suggestion to implement parsing logic in the React handler to ensure data type consistency.

3. Boilerplate & TDD

Boilerplate: To speed up the initial setup, I asked Gemini to generate the DataInitializer class implementing CommandLineRunner. This automated the creation of the Admin user and initial inventory, saving significant time during testing resets.

Testing: To strictly adhere to TDD, I used Gemini to help generate the initial unit test skeletons for the SweetService and AuthService, ensuring I had a "Red" test state before implementing the business logic.

4. Reflection

Using AI significantly streamlined my workflow. Instead of getting stuck on complex configuration syntax (like Spring Security filter chains), I could focus on the high-level architecture and business logic. I acted as the "Architect," directing the AI to implement specific patterns and fixing integration issues, rather than just writing boilerplate code.

⚠️ Challenges & Solutions

1. The "White Screen" Crash (Frontend Integration)

Issue: During frontend integration, the application would crash immediately with a white screen and an "Invalid Hook Call" error.

Solution: I discovered I was calling useContext inside the main App component that was wrapping the Context Provider, rather than being inside it. With AI guidance, I refactored the routing to use wrapper components (ProtectedRoute) to safely consume the context.

2. Managing Inventory State

Issue: The frontend dashboard would not reflect stock changes immediately after a purchase was made.

Solution: I implemented a robust state refresh cycle (loadSweets()) that triggers specifically after a successful 200 OK response from the purchase endpoint, ensuring the UI always displays real-time data from the server.
