# ProyectoBetancourt_Backend

## Configuracion del backend

Define `DB_PASSWORD` y `JWT_SECRET` en el entorno antes de iniciar la aplicacion.
`JWT_SECRET` debe ser una clave codificada en Base64 de al menos 32 bytes.

Opcionalmente, configura `JWT_EXPIRATION_MS` (predeterminado: 86400000),
`RECOVERY_EXPIRATION_MINUTES` (15), `FRONTEND_URL` (http://localhost:5173)
y `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME` y `MAIL_PASSWORD` para el servidor SMTP.