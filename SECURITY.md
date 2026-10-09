# Security Policy

This repository is intended for development, demonstration, and portfolio use.

- Never commit `.env`, production credentials, access tokens, private keys, or database dumps containing sensitive data.
- The demo application accounts documented in the README are sample credentials only and must be changed before any real deployment.
- Database passwords are loaded from environment variables / `.env`; `.env` is ignored by Git.
- If you discover a security issue, avoid posting live credentials or exploit details in a public issue. Use a private GitHub security advisory when the repository enables it.

See [`docs/SECURITY.md`](docs/SECURITY.md) for the application security controls implemented in the project.
