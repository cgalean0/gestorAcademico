
#  🎓 Gestor Académico

  

<p  align="center">

<img  src="https://img.shields.io/badge/Full%20Stack-Java%20+%20Angular-ff69b4?style=for-the-badge"  alt="Full Stack">

<img  src="https://img.shields.io/badge/Spring%20Boot-4.0.0-6DB33F?style=for-the-badge&logo=springboot"  alt="Spring Boot">

<img  src="https://img.shields.io/badge/Angular-21.2.0-DD0031?style=for-the-badge&logo=angular"  alt="Angular">

<img  src="https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker"  alt="Docker">

</p>

  

> **Sistema de gestión académica integral** que permite administrar estudiantes, profesores, carreras, materias, planes de estudio, inscripciones y más. Construido con arquitectura moderna full-stack.

  

---

  

##  📋 Tabla de Contenidos

  

-  [Descripción](#-descripción)
-  [Funcionalidades](#-funcionalidades)
-  [Stack Tecnológico](#-stack-tecnológico)
-  [Estructura del Proyecto](#-estructura-del-proyecto)
-  [Modelos de Datos](#-modelos-de-datos)
-  [API REST](#-api-rest)
-  [Seguridad](#-seguridad)
-  [Primeros Pasos](#-primeros-pasos)
-  [Docker](#-docker)
-  [Usuarios de Prueba](#-usuarios-de-prueba)
-  [Estado del Proyecto](#-estado-del-proyecto)
-  [Próximos Pasos](#-próximos-pasos)


---

  

##  🎯 Descripción

  

Sistema de gestión académica diseñado para instituciones educativas que necesitan administrar:

  

-  **Usuarios**: Administradores, Profesores y Estudiantes con roles diferenciados

-  **Carreras**: Gestión de carreras con duración y materias asociadas

-  **Materias**: Catálogo de materias con códigos únicos

-  **Planes de Estudio**: Vinculación de materias a carreras por año

-  **Inscripciones**: Sistema de inscripciones a materias

  

###  Características Principales

  

- 🔐 **Autenticación JWT** con tokens en cookies HTTP-only

- 🛡️ **Control de acceso basado en roles** (RBAC)

- 📊 **Dashboards diferenciados** por tipo de usuario

- 🔄 **API RESTful** documentada con Swagger/OpenAPI

- 🐳 **Docker-ready** para despliegue en contenedores

- 🔄 **Sincronización Redis** para invalidación de tokens

  

---

##  ✨ Funcionalidades

###  👨‍💼 Gestión de Usuarios (Admin)

| Funcionalidad              | Descripción                                 | Estado |
| -------------------------- | ------------------------------------------- | ------ |
| CRUD Usuarios              | Crear, leer, actualizar y eliminar usuarios | ✅      |
| Gestión de Administradores | Alta y baja de administradores              | ✅      |
| Gestión de Profesores      | Alta, baja y asignación de materias         | ✅      |
| Gestión de Estudiantes     | Alta, baja e inscripciones                  | ✅      |
| Gestión de Carreras        | Crear y administrar carreras                | ✅      |
| Gestión de Materias        | Catálogo de materias                        | ✅      |
| Planes de Estudio          | Asociar materias a carreras                 | ✅      |

###  👨‍🏫 Dashboard del Profesor

| Funcionalidad            | Descripción               | Estado           |
| ------------------------ | ------------------------- | ---------------- |
| Ver Cursos               | Listado de cursos a cargo | ✅ UI             |
| Tomar Asistencia         | Registro de asistencia    | 🔄 En desarrollo |
| Gestionar Calificaciones | Carga de notas            | 🔄 En desarrollo |
| Mi Perfil                | Ver y editar perfil       | ✅ UI             |

###  🎓 Dashboard del Estudiante

| Funcionalidad      | Descripción             | Estado |
| ------------------ | ----------------------- | ------ |
| Mis Cursos         | Materias inscritas      | ✅ UI   |
| Ver Calificaciones | Consultar notas         | ✅ UI   |
| Mi Asistencia      | Historial de asistencia | ✅ UI   |
| Inscripciones      | Inscribirse a materias  | ✅ UI   |
| Mi Perfil          | Ver perfil y legajo     | ✅ UI   |

  

---


##  💻 Stack Tecnológico

###  Backend

| Categoría     | Tecnología                  | Versión     |
| ------------- | --------------------------- | ----------- |
| Framework     | Spring Boot                 | 4.0.0       |
| Lenguaje      | Java                        | 21+         |
| Seguridad     | Spring Security + JWT       | -           |
| Persistencia  | Spring Data JPA + Hibernate | -           |
| Base de Datos | MariaDB (Prod) / H2 (Dev)   | 10.11+      |
| Cache         | Redis                       | 7.2         |
| Mapeo DTO     | MapStruct                   | 1.5.5.Final |
| Documentación | SpringDoc OpenAPI           | 2.8.4       |
| Builder       | Lombok                      | -           |
| Contenedores  | Docker + Docker Compose     | -           |

###  Frontend

| Categoría   | Tecnología                  | Versión |
| ----------- | --------------------------- | ------- |
| Framework   | Angular                     | 21.2.0  |
| Lenguaje    | TypeScript                  | ~5.9.2  |
| Estado      | RxJS                        | ~7.8.0  |
| Formularios | Reactive Forms              | -       |
| Estilos     | CSS3 (Custom Design System) | -       |
| Testing     | Vitest + Jsdom              | 4.0.8   |
| Build       | Angular CLI + Vite          | 21.2.3  |

###  Infraestructura

| Servicio | Imagen           | Propósito               |
| -------- | ---------------- | ----------------------- |
| MariaDB  | mariadb:latest   | Base de datos principal |
| Redis    | redis:7.2-alpine | Blacklist de tokens JWT |
| Backend  | (build)          | API REST Spring Boot    |
| Frontend | (build)          | Aplicación Angular      |

---

  

##  🗄 Modelos de Datos

###  Modelo de Usuario

  

```typescript

// Frontend (TypeScript)

interface User {

idUser: number;

name: string;

lastName: string;

userEmail: string;

phone: string;

role:  'ADMIN'  |  'PROFESSOR'  |  'STUDENT';

fileNumber?: string;

typeStudent?:  'Beginner'  |  'Advanced';

}

```

  

###  Modelo de Carrera

  

```typescript

interface Career {

idCareer: number;

name: string;

duration: number;  // En años

}

```

  

###  Modelo de Materia

  

```typescript

interface Subject {

idSubject: number;

code: string;  // Código único

name: string;

}

```

  

---

##  🌐 API REST

###  Endpoints de Autenticación

| Método | Endpoint       | Descripción    | Auth |
| ------ | -------------- | -------------- | ---- |
| POST   | `/auth/login`  | Iniciar sesión | No   |
| POST   | `/auth/logout` | Cerrar sesión  | Sí   |

###  Endpoints de Usuarios

| Método | Endpoint                      | Descripción               | Roles            |
| ------ | ----------------------------- | ------------------------- | ---------------- |
| GET    | `/api/users`                  | Listar todos los usuarios | ADMIN            |
| GET    | `/api/users/{id}`             | Obtener usuario por ID    | ADMIN, PROFESSOR |
| GET    | `/api/users/me`               | Usuario actual            | Todos            |
| POST   | `/api/users`                  | Crear usuario             | ADMIN            |
| PUT    | `/api/users/{id}`             | Actualizar usuario        | ADMIN            |
| DELETE | `/api/users/{id}`             | Eliminar usuario          | ADMIN            |
| POST   | `/api/users/admin/professors` | Crear profesor            | ADMIN            |
| POST   | `/api/users/admin/students`   | Crear estudiante          | ADMIN            |
| GET    | `/api/users/role/students`    | Listar estudiantes        | ADMIN, PROFESSOR |
| GET    | `/api/users/role/professors`  | Listar profesores         | ADMIN            |

###  Endpoints de Carreras

| Método | Endpoint            | Descripción        | Roles            |
| ------ | ------------------- | ------------------ | ---------------- |
| GET    | `/api/careers`      | Listar carreras    | Público          |
| GET    | `/api/careers/{id}` | Obtener carrera    | Público          |
| POST   | `/api/careers`      | Crear carrera      | ADMIN            |
| PUT    | `/api/careers/{id}` | Actualizar carrera | ADMIN, PROFESSOR |
| DELETE | `/api/careers/{id}` | Eliminar carrera   | ADMIN            |

  

###  Endpoints de Materias

| Método | Endpoint             | Descripción        | Roles            |
| ------ | -------------------- | ------------------ | ---------------- |
| GET    | `/api/subjects`      | Listar materias    | Público          |
| GET    | `/api/subjects/{id}` | Obtener materia    | Público          |
| POST   | `/api/subjects`      | Crear materia      | ADMIN            |
| PUT    | `/api/subjects/{id}` | Actualizar materia | ADMIN, PROFESSOR |
| DELETE | `/api/subjects/{id}` | Eliminar materia   | ADMIN            |

---

##  🔒 Seguridad

###  Roles y Permisos

| Rol           | Permisos                                            |
| ------------- | --------------------------------------------------- |
| **ADMIN**     | Acceso total: CRUD de todas las entidades           |
| **PROFESSOR** | Lectura general, actualización de carreras/materias |
| **STUDENT**   | Acceso solo a su propio perfil                      |

###  Implementación

-  **JWT Tokens**: Generados con JJWT 0.12.6

-  **Almacenamiento**: Cookies HTTP-only (no accesibles por JavaScript)

-  **Invalidación**: Blacklist en Redis para logout

-  **Contraseñas**: BCrypt hashing

-  **CORS**: Configurado para desarrollo local

  

###  Variables de Entorno Requeridas

  

```bash

# Producción

JWT_SECRET=<tu-secreto-minimo-256-bits>

JWT_EXPIRATION=86400000  # 24 horas en ms

SPRING_PROFILES_ACTIVE=prod

SPRING_DATASOURCE_URL=jdbc:mariadb://db:3306/gestoracademico

SPRING_DATASOURCE_USERNAME=gestoruser

SPRING_DATASOURCE_PASSWORD=gestorpassword

SPRING_REDIS_HOST=redis_db

SPRING_REDIS_PORT=6379

```

  

---

  

##  🚀 Primeros Pasos

  

###  Requisitos Previos

  

- Java 21+

- Node.js 18+ y npm

- Docker y Docker Compose (para despliegue)

- Maven 3.9+ (para desarrollo backend)

  

###  Desarrollo Local

  

####  1. Backend (Spring Boot)

  

```bash

cd  gestorAcademico

  

# Opción A: Perfil de desarrollo (H2 en memoria)

./mvnw  spring-boot:run

  

# Opción B: Con perfil prod y variables de entorno

SPRING_PROFILES_ACTIVE=prod  \

JWT_SECRET=tu-secreto-aqui  \

./mvnw  spring-boot:run

```

  

La consola H2 estará disponible en: `http://localhost:8080/h2-console`

  

####  2. Frontend (Angular)

  

```bash

cd  frontend

  

# Instalar dependencias

npm  install

  

# Iniciar servidor de desarrollo

npm  start

# Abrir: http://localhost:4200

```

  

###  Docker Compose (Todo el Stack)

  

```bash

# Construir e iniciar todos los servicios

docker-compose  up  -d

  

# Ver logs

docker-compose  logs  -f

  

# Detener servicios

docker-compose  down

```

  

Servicios disponibles:

- Frontend: http://localhost:4200

- Backend API: http://localhost:8080

- Swagger UI: http://localhost:8080/swagger-ui.html

- H2 Console: http://localhost:8080/h2-console (dev only)

- MariaDB: localhost:3306

- Redis: localhost:6379

  

---

  

##  🐳 Docker

  

###  Servicios

| Servicio | Puerto  | Descripción          |
| -------- | ------- | -------------------- |
| frontend | 4200→80 | Angular app ( Nginx) |
| app      | 8080    | Spring Boot API      |
| db       | 3306    | MariaDB database     |
| redis_db | 6379    | Redis cache          |
###  Construcción Manual

  

```bash

# Construir imagen backend

cd  gestorAcademico

docker  build  -t  gestoracademico-app  .

  

# Construir imagen frontend

cd  ../frontend

docker  build  -t  gestoracademico-frontend  .

```

  

---

  

##  👥 Usuarios de Prueba

  

El sistema incluye datos iniciales con usuarios de prueba:

  

| Username    | Password | Rol       | Descripción               |
| ----------- | -------- | --------- | ------------------------- |
| test        | pepe1234 | ADMIN     | Administrador del sistema |
| testStudent | pass1234 | STUDENT   | Estudiante (Beginner)     |
| testProf    | pass1234 | PROFESSOR | Profesor                  |

> ⚠️ **Importante**: Estos usuarios son para desarrollo. Cambiar contraseñas en producción.

---

##  📊 Estado del Proyecto

###  ✅ Completado

####  Backend

-  [x] Autenticación JWT completa

-  [x] Logout con blacklist Redis

-  [x] CRUD de Usuarios con roles

-  [x] CRUD de Carreras

-  [x] CRUD de Materias

-  [x] Gestión de Planes de Estudio

-  [x] Documentación Swagger/OpenAPI

-  [x] Docker multi-contenedor

-  [x] Perfiles Spring (dev/prod)

-  [x] Mapeo DTO con MapStruct
####  Frontend

-  [x] Login con validación

-  [x] Redirección por rol

-  [x] Dashboard Administrador

-  [x] Dashboard Profesor

-  [x] Dashboard Estudiante

-  [x] Guards de rutas

-  [x] Manejo de errores

-  [x] Diseño UI académico

###  🔄 En Desarrollo

- [ ] Registro de Asistencia

- [ ] Gestión de Calificaciones

- [ ] Inscripciones de Estudiantes a Materias

- [ ] Asignación de Profesores a Materias

###  📋 Pendiente

- [ ] Notificaciones por email

- [ ] Reportes y estadísticas

- [ ] Recuperación de contraseña

- [ ] Refresh tokens

- [ ] Tests unitarios/integración

---

##  🎯 Próximos Pasos

###  Fase 1: Completar Funcionalidades Core

1.  **Inscripciones**: Sistema completo de inscripción de estudiantes a materias

2.  **Asistencia**: Registro y seguimiento de asistencia

3.  **Calificaciones**: Carga de notas y evaluaciones

###  Fase 2: Experiencia de Usuario

1.  **Loading states**: Spinners y skeleton loaders

2.  **Notificaciones**: Toast messages

3.  **Paginación**: Lógica completa de paginación

4.  **Búsqueda/Filtro**: Funcionalidad avanzada

###  Fase 3: Seguridad y Escalabilidad

1.  **Refresh tokens**: Tokens con expiración corta

2.  **Email**: Notificaciones automáticas

3.  **Tests**: Cobertura de tests

4.  **CI/CD**: Pipeline de despliegue


---

##  📚 Recursos Adicionales

-  **Swagger UI**: http://localhost:8080/swagger-ui.html

-  **Spring Boot Docs**: https://docs.spring.io/spring-boot/

-  **Angular Docs**: https://angular.dev/

-  **Docker Docs**: https://docs.docker.com/

---

##  📄 Licencia

Este proyecto es parte del repositorio **gestorAcademicoFullStack**.

---

  

<p  align="center">

<strong>Gestor Académico</strong> — Gestión académica moderna y eficiente

<br>

<sub>Full Stack Java + Angular</sub>

</p>