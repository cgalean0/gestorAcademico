
## 🎓 Gestor Académico: Academic Management System
Gestor Académico es una plataforma integral diseñada para la administración eficiente de instituciones educativas. El sistema centraliza la gestión de alumnos, profesores, cursos y el ciclo de vida académico, ofreciendo una API robusta y escalable construida sobre una arquitectura modular.

---
### 🚀 Key Features
**Student Lifecycle Management**: Registro, seguimiento de inscripciones y gestión de estados académicos.

**Teacher & Course Coordination**: Asignación de docentes a materias y gestión de cargos.

**Grade & Attendance Tracking**: Sistema centralizado para el registro de calificaciones y asistencia.

**Role-Based Access Control (RBAC)**: Gestión de permisos diferenciada para alumnos, profesores y administradores.

**Scalable Architecture:** Diseñado bajo principios de Clean Architecture para facilitar el mantenimiento y la extensibilidad.
ayer: Mapeo objeto-relacional (ORM) para una gestión eficiente de los datos.

---
### 🛠️ Tech Stack
**Runtime**: Java 17+ (Spring Boot 3.x)

**Persistence**: Hibernate / JPA (Java Persistence API)

**Database**: MySQL

**Build Tool**: Maven

---
### 📊 Data Model
El sistema maneja una jerarquía compleja de entidades que garantiza la integridad referencial de los datos académicos:

**Personas**: Abstracción base para Alumnos y Profesores.

**Cursos y Materias**: Relación jerárquica para la oferta académica.

Inscripciones: Entidad asociativa que gestiona la relación alumno-materia con atributos adicionales (fechas, estados).

---
### 📦 Getting Started
**Prerequisites**
- JDK 17 or higher.
- MySQL running.

**Installation**
- Clone the repository.
- Configure application.properties with your database credentials.

**Build and run:**
---

### 🐳 Docker
**Build and run with Docker Compose:**
```bash
docker-compose up --build
```

**View logs:**
```bash
docker-compose logs -f
```

**Stop services:**
```bash
docker-compose down
```

**Stop and remove volumes:**
```bash
docker-compose down -v
```

**Swagger docs**

- http://localhost:8080/swagger-ui.html

---
### 📖 Detailed Documentation
Para conocer a fondo el modelo de datos, los diagramas de secuencia y la documentación de los endpoints, visita:
👉 [https://cgalean0-gestoracademico.mintlify.app/](https://cgalean0-gestoracademico.mintlify.app/introduction)
