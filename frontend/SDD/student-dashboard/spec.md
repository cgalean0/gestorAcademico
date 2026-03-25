# SDD Spec: Student Dashboard

## Project: Gestor Académico FullStack

**Change**: Student Dashboard Feature  
**Spec Version**: 1.0  
**Created**: 2026-03-21

---

## 1. Overview

Este documento especifica los requisitos funcionales del Student Dashboard, incluyendo escenarios de usuario, flujos de navegación, y criterios de aceptación. El dashboard permite a los estudiantes gestionar su vida académica: ver cursos, calificaciones, asistencia, inscribirse en nuevos cursos, y gestionar su perfil.

---

## 2. User Profile

| Atributo          | Descripción                                   |
| ----------------- | --------------------------------------------- |
| **Usuario**       | Estudiante registrado en el sistema académico |
| **Rol**           | `STUDENT`                                     |
| **Autenticación** | JWT token válido                              |
| **Dispositivo**   | Desktop (primario), Tablet                    |

---

## 3. Layout Structure

### 3.1 Shell Layout

```
┌─────────────────────────────────────────────────────────────┐
│  SIDEBAR (240px)  │           HEADER (64px)                 │
│                   ├─────────────────────────────────────────┤
│  Logo             │  Breadcrumb + User Menu                 │
│  ─────────────    ├─────────────────────────────────────────┤
│  • Mis Cursos     │                                         │
│  • Calificaciones  │           MAIN CONTENT                  │
│  • Asistencia      │         (scrollable area)               │
│  • Inscripciones   │                                         │
│  • Mi Perfil       │                                         │
│                   │                                         │
│  ─────────────    │                                         │
│  [Logout]         │                                         │
└─────────────────────────────────────────────────────────────┘
```

### 3.2 Responsive Breakpoints

| Breakpoint          | Comportamiento                           |
| ------------------- | ---------------------------------------- |
| Desktop (≥1024px)   | Sidebar fijo 240px, contenido full       |
| Tablet (768-1023px) | Sidebar colapsable (hamburger menu)      |
| Mobile (<768px)     | Sidebar overlay, solo visible con toggle |

---

## 4. Feature Specifications

---

### 4.1 Mis Cursos (My Courses)

**Endpoint**: `GET /api/students/me/courses`

#### Descripción

Lista de todos los cursos en los que el estudiante está actualmente inscripto, con acceso a materiales y información básica del curso.

#### Estructura de Datos (Response)

```typescript
interface EnrolledCourse {
  id: string;
  courseId: string;
  courseName: string;
  professorName: string;
  schedule: string; // "Lun 10:00-12:00, Mié 10:00-12:00"
  room: string; // "Aula 301"
  enrolledAt: Date;
  status: 'ACTIVE' | 'COMPLETED' | 'DROPPED';
  materials: Material[];
  currentGrade?: number; // Promedio actual
}

interface Material {
  id: string;
  title: string;
  type: 'PDF' | 'VIDEO' | 'LINK' | 'ASSIGNMENT';
  url: string;
  uploadedAt: Date;
}
```

#### Escenarios

**Escenario 1: Ver lista de cursos inscriptos**

```
Given: El estudiante está autenticado
And:   Tiene cursos inscriptos
When:  Navega a "Mis Cursos"
Then:  Ve una lista de cursos con nombre, profesor, horario
And:   Cada curso muestra un indicador de estado (activo/completado)
And:   Ve el promedio actual si existe
```

**Escenario 2: Ver materiales de un curso**

```
Given: El estudiante está en "Mis Cursos"
And:   Tiene al menos un curso con materiales
When:  Hace click en un curso
Then:  Se expande/abre vista con lista de materiales
And:   Ve título, tipo (icono), y fecha de subida
And:   Puede hacer click para abrir/descargar el material
```

**Escenario 3: Sin cursos inscriptos**

```
Given: El estudiante está autenticado
And:   No tiene cursos inscriptos
When:  Navega a "Mis Cursos"
Then:  Ve mensaje: "No estás inscripto en ningún curso"
And:   Ve botón: "Ver cursos disponibles" (link a Inscripciones)
```

---

### 4.2 Calificaciones (Grades)

**Endpoint**: `GET /api/students/me/grades`

#### Descripción

Vista de todas las calificaciones del estudiante, organizadas por curso. Muestra notas parciales y promedio general.

#### Estructura de Datos (Response)

```typescript
interface GradeResponse {
  courseId: string;
  courseName: string;
  grades: Grade[];
  average: number;
  status: 'APPROVED' | 'FAILED' | 'IN_PROGRESS';
}

interface Grade {
  id: string;
  title: string; // "Parcial 1", "TP Final", etc.
  score: number; // 0-10
  weight: number; // Porcentaje (ej: 30)
  date: Date;
  observations?: string;
}
```

#### Escenarios

**Escenario 1: Ver calificaciones por curso**

```
Given: El estudiante está autenticado
And:   Tiene calificaciones registradas
When:  Navega a "Calificaciones"
Then:  Ve lista de cursos con calificaciones
And:   Cada curso muestra nombre y promedio general
And:   Hay un indicador visual (color) según estado:
       - Verde: Aprobado (≥6)
       - Rojo: Desaprobado (<6)
       - Amarillo: En curso (sin promedio final)
```

**Escenario 2: Ver detalle de calificaciones de un curso**

```
Given: El estudiante está en "Calificaciones"
When:  Hace click en un curso
Then:  Ve desglose de todas las notas del curso
And:   Muestra: título de evaluación, puntaje, peso, fecha
And:   Muestra observaciones si existen
And:   Muestra promedio ponderado calculado
```

**Escenario 3: Sin calificaciones**

```
Given: El estudiante está autenticado
And:   No tiene calificaciones en ningún curso
When:  Navega a "Calificaciones"
Then:  Ve mensaje: "Aún no tienes calificaciones registradas"
```

---

### 4.3 Asistencia (Attendance)

**Endpoint**: `GET /api/students/me/attendance`

#### Descripción

Historial completo de asistencia del estudiante, agrupado por curso y mes.

#### Estructura de Datos (Response)

```typescript
interface AttendanceResponse {
  courseId: string;
  courseName: string;
  totalClasses: number;
  presentCount: number;
  absentCount: number;
  lateCount: number;
  percentage: number; // Porcentaje de asistencia
  records: AttendanceRecord[];
}

interface AttendanceRecord {
  id: string;
  date: Date;
  status: 'PRESENT' | 'ABSENT' | 'LATE' | 'JUSTIFIED';
  courseName: string;
  schedule: string;
}
```

#### Escenarios

**Escenario 1: Ver resumen de asistencia**

```
Given: El estudiante está autenticado
And:   Tiene registros de asistencia
When:  Navega a "Asistencia"
Then:  Ve resumen por curso:
       - Total de clases
       - Presente / Ausente / Tarde (con iconos y colores)
       - Porcentaje de asistencia
       - Indicador: <75% = rojo (en riesgo)
```

**Escenario 2: Ver historial detallado de asistencia**

```
Given: El estudiante está en "Asistencia"
When:  Hace click en un curso
Then:  Ve lista de todas las fechas de clase
And:   Cada registro muestra: fecha, estado (icono), hora
And:   Estados con colores: Verde=Presente, Rojo=Ausente, Amarillo=Tarde
```

**Escenario 3: Asistencia en riesgo**

```
Given: El estudiante tiene asistencia < 75% en un curso
When:  Ve la sección de asistencia
Then:  Ve banner de advertencia: "Tu asistencia está por debajo del 75%"
And:   El curso aparece resaltado en rojo
```

---

### 4.4 Inscripciones (Enrollments)

**Endpoints**:

- `GET /api/courses/available` - Cursos disponibles
- `POST /api/students/me/enrollments` - Inscribirse
- `DELETE /api/students/me/enrollments/{id}` - Cancelar inscripción

#### Descripción

Permite al estudiante ver cursos disponibles y inscribirse. También puede cancelar inscripciones activas.

#### Estructura de Datos

```typescript
// Cursos disponibles
interface AvailableCourse {
  id: string;
  name: string;
  description: string;
  professorName: string;
  schedule: string;
  availableSpots: number;
  maxSpots: number;
  enrolledCount: number;
  prerequisites: string[]; // Nombres de cursos requeridos
}

// Request para inscripción
interface EnrollmentRequest {
  courseId: string;
}

// Response de inscripción
interface EnrollmentResponse {
  id: string;
  courseId: string;
  courseName: string;
  enrolledAt: Date;
  status: 'CONFIRMED' | 'PENDING' | 'CANCELLED';
}
```

#### Escenarios

**Escenario 1: Ver cursos disponibles**

```
Given: El estudiante está autenticado
When:  Navega a "Inscripciones"
Then:  Ve lista de cursos disponibles para inscripción
And:   Cada curso muestra: nombre, profesor, horario, cupos disponibles
And:   Ve botón "Inscribirse" en cursos con cupo
And:   Ve "Cupo lleno" en cursos sin disponibilidad
```

**Escenario 2: Inscribirse en un curso**

```
Given: El estudiante está en "Inscripciones"
And:   Ve un curso con cupos disponibles
And:   No está ya inscripto en ese curso
When:  Hace click en "Inscribirse"
Then:  Ve modal de confirmación con datos del curso
When:  Confirma la inscripción
Then:  Ve loading state
And:   El curso desaparece de "Disponibles"
And:   Ve toast: "Te insribiste exitosamente en [curso]"
And:   El curso aparece en "Mis Cursos"
```

**Escenario 3: Cancelar inscripción**

```
Given: El estudiante tiene una inscripción activa
When:  Navega a "Inscripciones" o "Mis Cursos"
And:   Ve un curso inscripto con opción de cancelar
When:  Hace click en "Cancelar inscripción"
Then:  Ve modal de confirmación: "¿Estás seguro?"
When:  Confirma la cancelación
Then:  La inscripción se cancela
And:   El curso vuelve a estar disponible en "Inscripciones"
And:   Desaparece de "Mis Cursos"
```

**Escenario 4: Sin cursos disponibles**

```
Given: El estudiante está autenticado
And:   No hay cursos disponibles para inscripción
When:  Navega a "Inscripciones"
Then:  Ve mensaje: "No hay cursos disponibles en este momento"
```

---

### 4.5 Mi Perfil (My Profile)

**Endpoints**:

- `GET /api/students/me` - Datos del perfil
- `PUT /api/students/me/password` - Cambiar contraseña

#### Descripción

Gestión de datos personales y seguridad de la cuenta.

#### Estructura de Datos

```typescript
interface StudentProfile {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  dni: string;
  birthDate: Date;
  phone?: string;
  address?: string;
  career: string; // Carrera que está cursando
  year: number; // Año/Cuatrimestre actual
  avatarUrl?: string;
  createdAt: Date;
}

interface PasswordChangeRequest {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}
```

#### Escenarios

**Escenario 1: Ver datos del perfil**

```
Given: El estudiante está autenticado
When:  Navega a "Mi Perfil"
Then:  Ve sus datos personales:
       - Nombre completo
       - Email
       - DNI
       - Fecha de nacimiento
       - Teléfono (si existe)
       - Dirección (si existe)
       - Carrera
       - Año actual
And:   Ve su avatar (o inicial si no tiene)
```

**Escenario 2: Cambiar contraseña exitosamente**

```
Given: El estudiante está en "Mi Perfil"
And:   Tiene sección "Cambiar Contraseña" visible
When:  Ingresa contraseña actual correcta
And:   Ingresa nueva contraseña (mínimo 8 caracteres)
And:   Confirma la nueva contraseña
When:  Hace click en "Guardar"
Then:  Ve loading state
And:   Ve toast: "Contraseña actualizada exitosamente"
And:   Los campos se limpian
```

**Escenario 3: Error al cambiar contraseña (contraseña incorrecta)**

```
Given: El estudiante está en "Mi Perfil"
And:   Ingresa contraseña actual incorrecta
And:   Ingresa nueva contraseña válida
And:   Confirma la nueva contraseña
When:  Hace click en "Guardar"
Then:  Ve error: "La contraseña actual es incorrecta"
And:   Los campos de contraseña se limpian
And:   Campo de contraseña actual recibe foco
```

**Escenario 4: Error al cambiar contraseña (contraseñas no coinciden)**

```
Given: El estudiante está en "Mi Perfil"
And:   Ingresa contraseña actual correcta
And:   Ingresa nueva contraseña
And:   Confirma con una diferente
When:  Hace click en "Guardar"
Then:  Ve error inline: "Las contraseñas no coinciden"
And:   Botón permanece deshabilitado o muestra error
```

**Escenario 5: Validación de nueva contraseña**

```
Given: El estudiante está en "Mi Perfil"
And:   Ingresa nueva contraseña con menos de 8 caracteres
When:  Escribe en el campo de confirmación
Then:  Ve validación en tiempo real:
       - Mínimo 8 caracteres ✓
       - Al menos una mayúscula (recomendado)
       - Al menos un número (recomendado)
```

---

## 5. Navigation & Routing

### 5.1 Route Configuration

```typescript
const routes: Routes = [
  {
    path: 'dashboard',
    canActivate: [authGuard], // + studentGuard si existe
    children: [
      { path: '', redirectTo: 'courses', pathMatch: 'full' },
      { path: 'courses', component: MisCursosComponent },
      { path: 'grades', component: CalificacionesComponent },
      { path: 'attendance', component: AsistenciaComponent },
      { path: 'enrollments', component: InscripcionesComponent },
      { path: 'profile', component: MiPerfilComponent },
    ],
  },
];
```

### 5.2 Active Section Detection

- La sidebar usa `router.url` o `router.events` para determinar sección activa
- Item activo tiene estilos diferenciados (background, borde izquierdo dorado)

---

## 6. Error Handling

### 6.1 API Errors

| Status Code      | Handling                                          |
| ---------------- | ------------------------------------------------- |
| 401 Unauthorized | Redirect a login, mostrar toast "Sesión expirada" |
| 403 Forbidden    | Mostrar mensaje de acceso denegado                |
| 404 Not Found    | Mostrar mensaje de recurso no encontrado          |
| 500 Server Error | Toast genérico "Error del servidor"               |
| Network Error    | Toast "Sin conexión a internet"                   |

### 6.2 Loading States

- Skeleton loaders para listas
- Spinner para acciones (inscripción, cambio de contraseña)
- Disabled buttons durante procesamiento

### 6.3 Empty States

- Cada sección tiene mensaje de "sin datos" con call-to-action cuando aplica

---

## 7. Acceptance Criteria

### AC-1: Autenticación y Acceso

- [ ] Solo usuarios autenticados con rol STUDENT acceden al dashboard
- [ ] Usuarios no autenticados son redirigidos a /auth/login
- [ ] Token expirado muestra mensaje y redirige a login

### AC-2: Mis Cursos

- [ ] Lista de cursos muestra: nombre, profesor, horario, sala
- [ ] Expandir curso muestra materiales
- [ ] Click en material abre/descarga el archivo
- [ ] Sin cursos muestra estado vacío con CTA

### AC-3: Calificaciones

- [ ] Lista de cursos con promedio y estado (aprobado/desaprobado)
- [ ] Click en curso muestra detalle de evaluaciones
- [ ] Estados visuales: verde (≥6), rojo (<6), amarillo (sin promedio)
- [ ] Sin calificaciones muestra estado vacío

### AC-4: Asistencia

- [ ] Resumen por curso con contadores (presente/ausente/tarde)
- [ ] Porcentaje de asistencia visible
- [ ] Cursos <75% aparecen con banner de advertencia
- [ ] Detalle muestra historial por fecha

### AC-5: Inscripciones

- [ ] Lista de cursos disponibles con cupos
- [ ] Botón "Inscribirse" visible si hay cupo
- [ ] Modal de confirmación antes de inscribir
- [ ] Toast de éxito/error después de acción
- [ ] Cancelar inscripción funciona correctamente

### AC-6: Mi Perfil

- [ ] Muestra todos los datos del estudiante
- [ ] Cambio de contraseña valida: actual correcta, nueva ≥8 chars, confirmación igual
- [ ] Errores mostrados inline con mensaje claro
- [ ] Éxito muestra toast y limpia campos

### AC-7: UI/UX

- [ ] Sidebar con 5 items de navegación
- [ ] Item activo tiene estilo visual diferenciado
- [ ] Responsive hasta 768px (tablet)
- [ ] Dark theme con acentos dorados consistente
- [ ] Loading states para todas las operaciones async
- [ ] Estados vacíos con mensajes útiles y CTA

---

## 8. Non-Functional Requirements

| Requisito       | Criterio                                            |
| --------------- | --------------------------------------------------- |
| Performance     | Primera carga < 3s en conexión normal               |
| Accesibilidad   | Navegación por teclado funcional, labels ARIA       |
| SEO             | No aplica (dashboard privado)                       |
| Browser Support | Chrome, Firefox, Safari, Edge (últimas 2 versiones) |
