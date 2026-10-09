# QA Checklist

- [ ] `build.bat` sukses.
- [ ] Flyway membuat schema kosong.
- [ ] Hibernate schema validation sukses.
- [ ] `/actuator/health` = `UP`.
- [ ] 5 akun demo dapat login.
- [ ] Redirect workspace sesuai role.
- [ ] Owner tidak dapat memodifikasi master data.
- [ ] Manager dapat CRUD master data.
- [ ] Kasir dapat membuat transaksi.
- [ ] Gudang tidak dapat membuat transaksi.
- [ ] Stok berkurang dalam transaction yang sama saat sale dibuat.
- [ ] CSRF menolak mutation request tanpa token.
- [ ] Audit mencatat login dan perubahan data.
- [ ] PostgreSQL profile diuji.
- [ ] MySQL profile diuji sebelum deployment MySQL.
