# Security

- Session-based authentication melalui Spring Security.
- Password BCrypt cost 12.
- User disimpan pada tabel `app_user`.
- CSRF aktif menggunakan `CookieCsrfTokenRepository`.
- Mutation API mengirim header `X-XSRF-TOKEN`.
- Session fixation protection aktif.
- Maksimal 3 concurrent session per account pada konfigurasi aplikasi.
- Header CSP, X-Frame-Options DENY, nosniff, SameSite=Lax.
- Backend role enforcement menggunakan `@PreAuthorize`.

## Permission utama

| Capability | Admin | Owner | Manager | Kasir | Gudang |
|---|---:|---:|---:|---:|---:|
| Dashboard | ✓ | ✓ | ✓ | ✓ | ✓ |
| Reports | ✓ | ✓ | ✓ | - | - |
| Activity/Audit | ✓ | ✓ | ✓ | - | - |
| Modify Product | ✓ | - | ✓ | - | - |
| Modify Customer | ✓ | - | ✓ | - | - |
| Modify Supplier | ✓ | - | ✓ | - | - |
| Create Sale | ✓ | - | ✓ | ✓ | - |
| View Sales | ✓ | ✓ | ✓ | ✓ | ✓ |

> Demo credentials hanya untuk development/demo. Ganti untuk production.
