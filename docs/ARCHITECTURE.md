# Architecture

## Modul utama

- `config` — Spring Security, database-backed UserDetailsService, seed data.
- `domain` — JPA entities.
- `repository` — Spring Data JPA repositories.
- `service` — transaksi bisnis, inventory, reporting, audit trail.
- `web` — MVC workspace controller, auth API, business REST API, exception handler.
- `templates` — lima workspace HTML terpisah.
- `static` — CSS + JavaScript role-specific.
- `db/migration/{vendor}` — Flyway migration PostgreSQL/MySQL.

## Transaction boundary

Perubahan produk/customer/supplier/penjualan terjadi di service layer dengan `@Transactional`.
Pembuatan penjualan mengurangi stok dan membuat row sale dalam satu transaction.

## Workspace isolation

Route:

- `/admin`
- `/owner`
- `/manager`
- `/cashier`
- `/warehouse`

Masing-masing memakai HTML + JS berbeda. `WorkspaceController` mengarahkan akun yang membuka workspace salah kembali ke workspace miliknya.

API authorization tetap dilakukan di backend dengan Spring Security method authorization.
