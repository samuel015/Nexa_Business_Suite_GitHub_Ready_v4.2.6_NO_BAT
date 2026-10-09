NEXA BUSINESS SUITE v4.2.6 - XAMPP MariaDB 10.4.27
=================================================
VERSI INI KHUSUS XAMPP ANDA: MariaDB 10.4.27, Windows 11, Java 11, Maven 3.9.9.
JANGAN jalankan START-PRESENTATION.bat dari folder Nexa v4.2.4 lama.

1. Extract ZIP ke folder BARU, jangan menimpa folder lama.
2. Buka XAMPP Control Panel, klik Start pada MySQL, pastikan hijau.
3. Double-click CHECK-MARIADB.bat untuk mengecek driver/profil.
4. Double-click MULAI-DI-SINI.bat (setup database lalu build & test dengan Maven).
   - XAMPP root tanpa password akan dicoba otomatis terlebih dulu.
   - Jika database user 'nexa' sudah siap, tahap setup dilewati.
   - Jika build gagal, periksa logs dan kirim hasil collect-error-log.bat.
5. Setelah muncul PC SIAP UNTUK PRESENTASI, jalankan START-PRESENTATION.bat.
6. Tunggu Tomcat started /actuator/health UP lalu browser membuka /login.
7. Jalankan VERIFY-PRESENTATION.bat agar kelima akun diuji lewat HTTP.

Login demo: admin/admin123, owner/owner123, manager/manager123,
kasir/kasir123, gudang/gudang123.

PERBAIKAN UTAMA:
- Profile default mariadb, driver org.mariadb.jdbc.Driver.
- Hibernate MariaDB103Dialect.
- Flyway plugin flyway-mysql 8.5.13.
- Migrasi khusus db/migration/mariadb.
- Kontrol root MariaDB tanpa password dari XAMPP.
- JAR target/nexa-business-suite-4.2.6.jar.

CATATAN: Paket ini hanya berisi source + script. JAR harus dibangun di PC
Anda melalui Maven, yang membutuhkan akses dependency Maven pada build pertama.
Tidak menghapus database, tabel, atau data pengguna yang sudah ada.
