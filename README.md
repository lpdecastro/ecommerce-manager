# E-Commerce Manager - Spring Boot REST API Project
E-Commerce Manager is a powerful Spring Boot application offering a comprehensive suite of REST APIs to
manage your e-commerce platform. This Java-based project includes functionalities for authentication, 
customer and seller management, product catalog, shopping cart, and order processing.

## Table of Contents
- [1 - Features](#1---features)
- [2 - ER Diagram](#2---er-diagram)
- [3 - Software Architecture](#3---software-architecture)
- [4 - API Endpoints](#4---api-endpoints)
- [5 - Installation](#5---installation)
- [6 - API Usage](#6---api-usage)
- [7 - Technology Stack](#7---technology-stack)
- [8 - Other Projects](#8---other-projects)

## 1 - Features
- **User Authentication and Authorization**: Secure access with role-based permissions.
- **Product Management**: Add, edit, delete, and view products.
- **Order Management**: Track and manage customer orders.
- **Cart Item Management**: Add, update, and remove items in the shopping cart.
- **Customer Management**: Maintain customer profiles and purchase histories.
- **Seller Management**: Manage seller profiles and their product listings.

## 2 - ER Diagram
![ER Diagram](assets/er-diagram.png)

## 3 - Software Architecture

### 3.1 - System Context
![C4 Context](assets/c4-context.png)

### 3.2 - System Container
![C4 Container](assets/c4-container.png)

### 3.3 - System Component
![C4 Component](assets/c4-component.png)

## 4 - API Endpoints

You can view the full API documentation here: 
[E-Commerce Manager](https://documenter.getpostman.com/view/31412288/2sA3QniaEC)

### Authentication
- `POST /.rest/auth/register/customer`: Register customer
- `POST /.rest/auth/register/seller`: Register seller
- `POST /.rest/auth/login`: Login
- `POST /.rest/auth/logout`: Logout

### Customers
- `GET /.rest/customers/current`: Get current customer
- `GET /.rest/customers/{customerId}`: Get customer by ID
- `GET /.rest/customers`: Get all customers
- `PUT /.rest/customers/current`: Update current customer
- `PUT /.rest/customers/current/password`: Update current customer password
- `PUT /.rest/customers/address/{type}`: Update current customer address
- `PUT /.rest/customers/creditCard/{creditCardId}`: Update current customer credit card details
- `DELETE .rest/customers/current/address/{type}`: Delete current customer address

### Sellers
- `GET /.rest/sellers/current`: Get current seller
- `GET /.rest/sellers/{sellerId}`: Get seller by ID
- `GET /.rest/sellers`: Get all sellers
- `PUT /.rest/sellers/current`: Update current seller
- `PUT /.rest/sellers/current/password`: Update current seller password
- `PUT /.rest/sellers/address/{type}`: Update current seller address
- `DELETE .rest/sellers/current/address/{type}`: Delete current seller address

### Products
- `GET /.rest/products`: Get all products
- `GET /.rest/products/{productId}`: Get product by ID
- `POST /.rest/products`: Create a new product
- `PUT /.rest/products/{productId}`: Update product by ID
- `DELETE /.rest/products/{productId}`: Delete product by ID

### Cart Items
- `GET /.rest/cart/items`: Get all items in the cart
- `POST /.rest/cart/items`: Add item to the cart
- `PUT /.rest/cart/items/{itemId}`: Update item in the cart
- `DELETE /.rest/cart/items/{itemId}`: Remove item from the cart

### Orders
- `GET /.rest/orders`: Get all orders
- `GET /.rest/orders/{orderId}`: Get order by ID
- `POST /.rest/orders`: Create a new order
- `PUT /.rest/orders/{orderId}`: Update order by ID
- `DELETE /.rest/orders/{orderId}`: Cancel order by ID


## 5 - Installation

### Prerequisites
- Java 17 or higher
- Maven 3.9.6 or higher

### Installation
1. **Clone the repository**
    ```bash
   git clone https://github.com/lpdecastro/ecommerce-manager.git
   cd ecommerce-manager
   ```
2. **Build the project**
   ```bash
   mvn clean install
   ```
3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   The application will be accessible at `http://localhost:8080`.

### Configuration
Update the `src/main/resources/application.properties` file to configure the application settings. Here are some key properties:
```properties
# Database settings for H2 (development)
spring.datasource.url=jdbc:h2:mem:ecommerce_db
spring.datasource.username=sa
spring.datasource.password=

# Enable H2 console for in-memory database access
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Defer JPA initialization until the datasource is fully available
spring.jpa.defer-datasource-initialization=true

# Security settings
app.jwt.secret-key=3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b
app.jwt.expiration-in-ms=3600000
```
## 6 - API Usage
1. **Register as a Customer**

   Open Postman and make a POST request to register as a customer:
   ```http
   POST /.rest/auth/register/customer HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   {
      "username": "customer",
      "password": "customer"
   }
   ```

2. **Login as a Customer**

   Make a POST request to login as a customer:
   ```http
   POST /.rest/auth/login HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   {
       "username": "customer2",
       "password": "customer1"
   }
   ```
   You will receive a response with an access token:
   ```json
   {
       "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJjdXN0b21lcjIiLCJpYXQiOjE3MTYzMzA5ODAsImV4cCI6MTcxNjMzNDU4MH0.NKby0j-8VmLosAdOqh1AqihHDGlxRtrbjyGuBNIRUGTg3Dy5wPOlAYcTM3UQqIvj",
       "tokenType": "Bearer",
       "expiresIn": 3600
   }
   ```

3. **Access Current Customer Information**
   
   Copy the access token and add an Authorization header of type Bearer token. Make a GET request like:
   ```http
   GET /.rest/customers/current HTTP/1.1
   Host: localhost:8080
   Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJjdXN0b21lcjIiLCJpYXQiOjE3MTYzMzA5ODAsImV4cCI6MTcxNjMzNDU4MH0.NKby0j-8VmLosAdOqh1AqihHDGlxRtrbjyGuBNIRUGTg3Dy5wPOlAYcTM3UQqIvj
   ```
   You will receive a response like below:
   ```json
   {
      "customerId": 1,
      "firstName": null,
      "lastName": null,
      "mobile": null,
      "email": null,
      "username": "customer",
      "password": "$2a$10$yCzB.FMyXMsyLenE96lgmeiUvMfwTNt6t3y4RwXO3GZuyZOsd.zty",
      "createdAt": "2024-05-22T08:55:59.542793",
      "updatedAt": "2024-05-22T08:55:59.542839",
      "addresses": [],
      "creditCards": [],
      "role": {
         "roleId": 1,
         "name": "CUSTOMER",
         "description": "Default customer role",
         "createdAt": "2024-05-22T08:55:44.105505",
         "updatedAt": "2024-05-22T08:55:44.105505"
      }
   }
   ```

4. **Access Other Customer-Authenticated APIs**

   Use the access token to access other customer-authorized APIs such as managing your information,
   cart, and orders.

   You can also register and login as a seller and access seller-authenticated APIs.

## 7 - Technology Stack
- **Java**: Primary programming language used for developing the backend.
- **Spring Boot**: Backend framework for building the application.
- **Spring Security**: For securing the application.
- **Spring Data JPA**: For database interactions.
- **Hibernate**: ORM tool for mapping Java objects to database tables.
- **H2 Database**: In-memory database for development and testing.
- **JSON Web Token**: For securing API endpoints with token-based authentication.
- **Maven**: For project build and dependency management.

## 8 - Other Projects