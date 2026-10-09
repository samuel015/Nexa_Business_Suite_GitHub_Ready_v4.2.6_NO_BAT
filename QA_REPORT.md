# QA Report - Nexa v4.2.6 / XAMPP MariaDB 10.4

## Perubahan yang diverifikasi secara statis

- Proyek Java 11, Spring Boot 2.7.18, MariaDB driver 2.7.12, Flyway-mysql 8.5.13.
- Default profile `mariadb`, Hibernate MariaDB103Dialect, migration vendor-specific.
- Script presentasi memakai jdbc:mariadb dan JAR v4.2.6.
- Setup XAMPP root tanpa password dicoba sebelum meminta password.
- Tidak terdapat SQL DROP/DELETE/TRUNCATE pada setup MariaDB.
- JavaScript seluruh role berhasil melewati pemeriksaan sintaks Node.js.
- POM/XML dan YAML berhasil diparse; batch file memakai CRLF Windows.

## Batas validasi (penting)

**Belum ada bukti build/runtime berhasil dengan MariaDB 10.4.27 pada PC pemilik.**
Lingkungan pembuat tidak memiliki akses ke Maven Central dan tidak memiliki
server XAMPP Windows; karena itu `mvn clean verify`, migration Flyway aktual,
startup Spring Boot, dan login end-to-end harus dijalankan di PC pengguna.
Jangan mengklaim '100% siap presentasi' sebelum FIRST-RUN, START-PRESENTATION,
dan VERIFY-PRESENTATION lulus pada mesin target.
