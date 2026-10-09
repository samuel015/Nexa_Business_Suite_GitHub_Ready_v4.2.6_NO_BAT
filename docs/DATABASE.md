# Database deployment v4.2.6

Default local profile: XAMPP MariaDB 10.4+ (`mariadb`).

- JDBC: `jdbc:mariadb://127.0.0.1:3306/nexa_business`
- Driver: `org.mariadb.jdbc.Driver`
- Hibernate: `MariaDB103Dialect`
- Flyway migration path: `classpath:db/migration/mariadb`
- Hibernate schema policy: `ddl-auto=validate` (Flyway owns DDL)

## Local credentials

Database credentials are not stored as live secrets in the repository. Copy `.env.example` to `.env`, replace the placeholder values, and keep `.env` local. The file is ignored by Git.

`setup-mariadb.bat` loads `.env`, creates a temporary SQL file from the committed template, runs it with the local database administrator account, then deletes the temporary file.

MySQL 8 remains available through the optional `mysql` Spring profile and PostgreSQL through `postgres`.

After building the JAR, verify `/actuator/health` and the role workspaces with `VERIFY-PRESENTATION.bat`.
