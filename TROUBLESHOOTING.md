# Troubleshooting v4.2.6

## 1. `start.bat` berhenti pada JDK
Jalankan `check-tools.bat`. Project dikunci ke Java 11. Pastikan `JAVA_HOME` menunjuk ke JDK 11.

## 2. Maven tidak ditemukan
Jalankan `mvn -version` di CMD. Jika gagal, set `MAVEN_HOME` atau tambahkan folder `bin` Maven ke PATH. Launcher juga otomatis mendeteksi `C:\Maven\apache-maven-*`.

Versi ini **tidak mendownload Maven**.

## 3. `MySQL tidak merespons di 127.0.0.1:3306`
Start service MySQL dari Windows Services atau MySQL Installer. Setelah itu jalankan `setup-mysql.bat`.

## 4. `database/user Nexa belum bisa diakses`
Jalankan `setup-mysql.bat`, masukkan password root MySQL, lalu ulangi `start.bat`.

Konfigurasi lokal default:
- database: `nexa_business`
- user: `nexa`
- password: diambil dari `DB_PASSWORD` pada file `.env` (tidak disimpan di Git)

## 5. Spring Boot gagal setelah Maven berjalan
Periksa `logs/launcher.log`, `logs/app.log`, dan `logs/error.log`.

Untuk mengirim diagnostik, jalankan `collect-error-log.bat` kemudian upload `diagnostics/Nexa_Error_Bundle_*.zip`.

## 6. Port 8080 dipakai aplikasi lain
Edit `.env` dan ubah:

```text
APP_PORT=8081
```

Lalu jalankan kembali `start.bat`.

## 7. Browser tidak otomatis terbuka
Buka manual `http://127.0.0.1:8080/login` (atau port yang Anda set).


## Java 11 tidak ditemukan
Pada v4.2.6 launcher akan mengunduh Temurin/OpenJDK 11 portable ke `.tools\jdk11`. Jika download gagal, periksa internet/firewall lalu jalankan `INSTALL-JAVA-AUTO.bat`. Instalasi portable tidak membutuhkan Administrator dan tidak mengubah instalasi Java sistem.
