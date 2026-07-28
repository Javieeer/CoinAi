# 💰 CoinAI

> Plataforma inteligente para la gestión de finanzas personales y familiares impulsada por Inteligencia Artificial.

CoinAI es una aplicación diseñada para ayudar a las personas y familias a administrar sus ingresos, gastos, presupuestos, metas de ahorro y métodos de pago desde una única plataforma.

El proyecto está construido bajo una arquitectura modular y preparada para crecer hacia funcionalidades como sincronización entre dispositivos, automatizaciones bancarias, inteligencia artificial y análisis financiero.

---

# 📌 Características

Actualmente el backend incluye:

- ✅ Registro de usuarios
- ✅ Inicio de sesión con JWT
- ✅ Refresh Token
- ✅ Recuperación de contraseña
- ✅ Verificación de correo electrónico
- ✅ Gestión de familias
- ✅ Gestión de categorías
- ✅ Categorías predeterminadas del sistema
- ✅ Gestión de subcategorías
- ✅ Subcategorías predeterminadas
- ✅ Gestión de métodos de pago
- ✅ Gestión de movimientos
- ✅ Presupuestos
- ✅ Metas de ahorro
- ✅ Etiquetas
- ✅ Integración con OpenAI
- ✅ Integración con Cloudinary
- ✅ Exportación de reportes PDF
- ✅ Integración con Gmail
- ✅ Documentación Swagger/OpenAPI

---

# 🏗 Arquitectura

El proyecto está organizado como un **Monorepo**.

```
CoinAI/

├── backend/
│   └── api/
│
├── frontend/
│   └── flutter/     (Próximamente)
│
├── ia/              (Próximamente)
│
└── automatizaciones/ (Próximamente)
```

Actualmente el desarrollo se encuentra centrado en el backend.

---

# 🛠 Tecnologías

## Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- JWT
- MapStruct
- Lombok
- Gradle
- Swagger / OpenAPI

## Servicios externos

- OpenAI
- Resend
- Cloudinary
- Google OAuth
- Gmail API

---

# 📂 Arquitectura del Backend

```
src/main/java

com.coinai.api

├── auth
├── budget
├── category
├── family
├── goal
├── movement
├── paymentmethod
├── report
├── security
├── tag
├── user
├── ai
├── email
├── google
├── cloudinary
└── common
```

Cada módulo contiene:

```
controller
service
repository
entity
dto
mapper
exception
```

---

# 🔐 Seguridad

La aplicación implementa:

- JWT Access Token
- Refresh Token
- BCrypt para almacenamiento seguro de contraseñas
- Verificación de correo electrónico
- Recuperación de contraseña mediante token
- Autorización basada en usuario autenticado

---

# 🗄 Base de datos

Motor:

- PostgreSQL

Migraciones:

- Flyway

La estructura de la base de datos se versiona completamente mediante migraciones.

---

# 📄 Documentación API

Swagger se encuentra disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🚀 Cómo ejecutar el proyecto

## 1. Clonar

```bash
git clone https://github.com/Javieeer/CoinAi
```

---

## 2. Entrar al backend

```bash
cd backend/api
```

---

## 3. Configurar variables de entorno

Crear un archivo:

```
.env
```

Ejemplo:

```env
DB_HOST=
DB_PORT=
DB_NAME=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET=
JWT_ACCESS_EXPIRATION=
JWT_REFRESH_EXPIRATION=

OPENAI_API_KEY=

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=

CLOUD_NAME=
CLOUD_API_KEY=
CLOUD_API_SECRET=

RESEND_API_KEY=
```

---

## 4. Ejecutar

```bash
./gradlew bootRun
```

---

## 5. Ejecutar pruebas

```bash
./gradlew test
```

---

## 6. Generar build

```bash
./gradlew build
```

---

# 📋 Estado actual

## Finalizado

- Autenticación
- Seguridad JWT
- Refresh Token
- Recuperación de contraseña
- Verificación de correo
- Categorías
- Subcategorías
- Familias
- Métodos de pago
- Movimientos
- Presupuestos
- Metas
- IA
- Reportes

## En desarrollo

- Frontend Flutter
- Sincronización Offline
- Dashboard
- Automatizaciones

---

# 📌 Próximas funcionalidades

- Aplicación móvil Flutter
- Sincronización Offline
- Dashboard financiero
- Transferencias entre cuentas
- Tarjetas de crédito
- Gastos recurrentes
- Notificaciones
- Multi-moneda
- Automatizaciones bancarias
- Analítica mediante IA

---

# 🧪 Calidad

El proyecto incluye:

- Tests unitarios
- Validaciones de negocio
- DTOs
- Arquitectura modular
- Manejo global de excepciones
- Migraciones versionadas

---

# 👨‍💻 Autor

**Javier Alejandro Zapata Ramos**

Estudiante de Ingeniería de Sistemas

Proyecto desarrollado con fines de aprendizaje, portafolio y evolución hacia una plataforma completa de gestión financiera inteligente.