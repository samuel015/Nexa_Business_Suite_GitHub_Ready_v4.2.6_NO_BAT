# Nexa Business Suite

A role-based business management web application built with **Java 11**, **Spring Boot 2.7.18**, **Spring Security**, **Spring Data JPA / Hibernate**, **Flyway**, and **Thymeleaf**.

The project is designed as a portfolio/demo application for managing products, customers, suppliers, sales, dashboards, reports, and activity history with different workspaces for each role.

## Highlights

- Role-based dashboards: Admin, Owner, Manager, Cashier, and Warehouse
- Session-based authentication with Spring Security
- BCrypt password hashing
- CSRF protection and security headers
- Product, customer, and supplier management
- Sales / point-of-sale workflow
- Dashboard metrics, reports, notifications, and audit activity
- Flyway database migrations
- MariaDB / MySQL support, with optional PostgreSQL profile
- Maven tests using H2
- GitHub Actions CI for automatic test execution

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Backend | Spring Boot 2.7.18 |
| Security | Spring Security 5.7 |
| Persistence | Spring Data JPA / Hibernate 5.6 |
| Migration | Flyway 8.5.13 |
| UI | Thymeleaf, HTML, CSS, Vanilla JavaScript |
| Database | MariaDB / MySQL; PostgreSQL optional |
| Build | Maven |
| Testing | Spring Boot Test, Spring Security Test, H2 |

## Project Structure

```text
src/main/java/com/nexa/business/
├── config/       # security, users, startup data
├── domain/       # JPA entities
├── dto/          # API request/response DTOs
├── repository/   # Spring Data repositories
├── service/      # business and audit services
└── web/          # controllers and REST API

src/main/resources/
├── db/migration/ # Flyway migrations
├── static/       # CSS/JavaScript/assets
└── templates/    # Thymeleaf workspaces
```

## Requirements

- JDK 11+
- Maven 3.8+
- MariaDB 10.4+ or MySQL 8
- Windows helper scripts are included for local/demo use

## Quick Start — Windows + MariaDB/MySQL

1. Clone the repository and enter the project directory.
2. Create your local environment file:

```cmd
copy .env.example .env
```

3. Edit `.env` and replace the placeholder database passwords.
4. Start MariaDB/MySQL.
5. For XAMPP MariaDB, run:

```cmd
setup-mariadb.bat
```

6. Build and test:

```cmd
mvn clean test
```

7. Run the application:

```cmd
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

For the presentation-oriented Windows flow, you can also run `MULAI-DI-SINI.bat` after `.env` is configured.

## Demo Accounts

These accounts are created for local development/demo only:

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| Owner | `owner` | `owner123` |
| Manager | `manager` | `manager123` |
| Cashier | `kasir` | `kasir123` |
| Warehouse | `gudang` | `gudang123` |

> Do not use these demo credentials in a real deployment.

## Database Profiles

Default profile:

```text
mariadb
```

Optional profiles:

```text
mysql
postgres
```

Database credentials are supplied through environment variables. `.env` is excluded from Git; use `.env.example` as a template.

## Tests

Run:

```cmd
mvn clean test
```

The test profile uses an in-memory H2 database, so the main database server is not required for the automated test suite.

## Security

The application includes BCrypt password hashing, CSRF protection, session fixation protection, role-level API authorization, CSP, X-Frame-Options, and audit logging.

See [SECURITY.md](SECURITY.md) and [docs/SECURITY.md](docs/SECURITY.md).

## Documentation

- [Architecture](docs/ARCHITECTURE.md)
- [Database](docs/DATABASE.md)
- [Security](docs/SECURITY.md)
- [QA Checklist](docs/QA_CHECKLIST.md)
- [Troubleshooting](TROUBLESHOOTING.md)
- [Changelog](CHANGELOG.md)

## Notes

This repository is intended as a development and portfolio project. Review security, credentials, logging, deployment configuration, and dependency versions before using it in a production environment.
