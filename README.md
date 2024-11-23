# Motel Room Management Backend

Đây là project motelroom_management với công nghệ **Spring Boot**.

## Features

- Xác minh và phân quyền người dùng
- Hoạt động CRUD cho phòng và khách thuê
- Chức năng tìm kiếm phòng trống
- Phân tích cơ bản (ví dụ: tỷ lệ phòng đầy, theo dõi thu nhập)

---

## Technologies Used

- **Spring Boot**: Backend framework
- **Spring Data JPA**: Database interactions
- **MySQL**: Relational database
- **Spring Security**: Authentication and authorization
- **Lombok**: Simplify Java model creation
- **Maven**: Dependency management

---

## Prerequisites

Before running the project, ensure you have the following installed:

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://www.mysql.com/downloads/)

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/motel-room-management.git
cd motel-room-management

2. Configure the Database
Create a database in MySQL:

sql
Copy code
CREATE DATABASE motel_room_management;
Update the application.properties file with your database credentials:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/motel_room_management?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
3. Build and Run the Application
Use Maven to build and run the project:

bash
Copy code
mvn clean install
mvn spring-boot:run
The application will start on http://localhost:8080.

API Endpoints
Authentication
POST /api/auth/login: User login
POST /api/auth/register: User registration
Room Management
GET /api/rooms: Get all rooms
POST /api/rooms: Create a new room
PUT /api/rooms/{id}: Update room details
DELETE /api/rooms/{id}: Delete a room
Tenant Management
GET /api/tenants: Get all tenants
POST /api/tenants: Add a new tenant
DELETE /api/tenants/{id}: Remove a tenant
Project Structure
bash
Copy code
src/main/java/com/example/motelroom
├── config          # Security and application configuration
├── controller      # REST controllers
├── model           # Entity and DTO classes
├── repository      # JPA repositories
├── service         # Business logic
└── MotelRoomApplication.java # Main entry point
Future Enhancements
Add role-based access control (admin, user)
Integrate payment gateway
Implement advanced analytics for income tracking
Add real-time notifications for room availability
Contributing
Contributions are welcome! To contribute:

Fork the repository.
Create a feature branch (git checkout -b feature-name).
Commit your changes (git commit -m 'Add new feature').
Push to the branch (git push origin feature-name).
Create a pull request.
License
This project is licensed under the MIT License - see the LICENSE file for details.

Contact
If you have any questions or feedback, feel free to contact me:

Email: your.email@example.com
GitHub: your-username
r
Copy code

### Ghi chú:
- Thay các placeholder như `your-username`, `your.email@example.com`, và các thông tin tùy chỉnh để phù hợp với dự án thực tế.
- Bạn có thể mở rộng phần API nếu dự án có nhiều endpoint hơn.
