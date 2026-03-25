# SDD Tasks: Student Dashboard

## Project: Gestor Académico FullStack

**Tasks Version**: 1.0  
**Created**: 2026-03-21  
**Based on**: spec.md + design.md

---

## Task Checklist

### Fase 1: Foundation (Estructura Base)

- [ ] **1.1** Crear directorio `src/app/dashboard/student-dashboard/`
- [ ] **1.2** Crear sub-directorios: `components/`, `services/`, `models/`, `guards/`
- [ ] **1.3** Crear archivo `models/index.ts` exportando todas las interfaces
- [ ] **1.4** Crear archivo `models/student.model.ts` con StudentProfile, PasswordChangeRequest
- [ ] **1.5** Crear archivo `models/course.model.ts` con EnrolledCourse, Material, AvailableCourse
- [ ] **1.6** Crear archivo `models/grade.model.ts` con GradeResponse, Grade
- [ ] **1.7** Crear archivo `models/attendance.model.ts` con AttendanceResponse, AttendanceRecord
- [ ] **1.8** Crear archivo `models/enrollment.model.ts` con EnrollmentResponse
- [ ] **1.9** Crear archivo `models/index.ts` barrel export

### Fase 2: Services

- [ ] **2.1** Crear `services/student.service.ts`:
  - [ ] `getProfile()` → GET /api/students/me
  - [ ] `getEnrolledCourses()` → GET /api/students/me/courses
  - [ ] `getGrades()` → GET /api/students/me/grades
  - [ ] `getAttendance()` → GET /api/students/me/attendance
  - [ ] `updatePassword(request)` → PUT /api/students/me/password
- [ ] **2.2** Crear `services/course.service.ts`:
  - [ ] `getAvailableCourses()` → GET /api/courses/available
  - [ ] `enrollInCourse(courseId)` → POST /api/students/me/enrollments
  - [ ] `cancelEnrollment(id)` → DELETE /api/students/me/enrollments/{id}
- [ ] **2.3** Crear archivo `services/index.ts` barrel export

### Fase 3: Guards (si studentGuard no existe)

- [ ] **3.1** Verificar si existe `authGuard` en el proyecto
- [ ] **3.2** Verificar si existe `studentGuard` en el proyecto
- [ ] **3.3** Si no existe, crear `guards/student.guard.ts`:
  - [ ] CanActivateFn que verifica rol STUDENT
  - [ ] Redirige a login si no tiene permisos
- [ ] **3.4** Crear archivo `guards/index.ts` barrel export

### Fase 4: Shared Components

- [ ] **4.1** Crear `components/shared/loading-skeleton.component.ts`:
  - [ ] Input: `count` (número de skeletons)
  - [ ] Skeleton animation shimmer
  - [ ] Estilos: card background, title/subtitle lines
- [ ] **4.2** Crear `components/shared/empty-state.component.ts`:
  - [ ] Inputs: `icon`, `title`, `message`, `ctaLabel`, `ctaRoute`
  - [ ] Diseño centrado con call-to-action opcional
- [ ] **4.3** Crear `components/shared/toast.component.ts` (o verificar si existe en el proyecto):
  - [ ] Método para mostrar toast de éxito/error
  - [ ] Auto-dismiss después de 3-5 segundos
- [ ] **4.4** Crear archivo `components/shared/index.ts` barrel export

### Fase 5: Shell Component

- [ ] **5.1** Crear `student-dashboard.component.ts`:
  - [ ] Template con sidebar + header + router-outlet
  - [ ] Signal para sidebar expand/collapse (responsive)
  - [ ] OnPush o default change detection
  - [ ] ViewEncapsulation.None
- [ ] **5.2** Crear `student-dashboard.component.scss`:
  - [ ] Layout: flex con sidebar fixed y main content scrollable
  - [ ] Responsive: sidebar overlay en tablet/mobile
  - [ ] Variables CSS del tema

### Fase 6: Sidebar Component

- [ ] **6.1** Crear `components/sidebar/student-sidebar.component.ts`:
  - [ ] 5 items de navegación con iconos
  - [ ] RouterLink activo con estilo diferenciado
  - [ ] Logo/título en header del sidebar
  - [ ] Botón de logout al final
- [ ] **6.2** Crear `components/sidebar/student-sidebar.component.scss`:
  - [ ] Estilos: nav items con hover y active states
  - [ ] Borde dorado izquierdo en item activo
  - [ ] Iconos (usar lucide o similar del proyecto)
- [ ] **6.3** Crear archivo `components/sidebar/index.ts`

### Fase 7: Header Component

- [ ] **7.1** Crear `components/header/student-header.component.ts`:
  - [ ] Breadcrumb dinámico basado en ruta actual
  - [ ] Nombre del estudiante
  - [ ] Avatar con inicial
  - [ ] Menú desplegable (opcional: ver perfil, logout)
- [ ] **7.2** Crear `components/header/student-header.component.scss`:
  - [ ] Altura: 64px
  - [ ] Background secondary
  - [ ] Border bottom
- [ ] **7.3** Crear archivo `components/header/index.ts`

### Fase 8: Routes Configuration

- [ ] **8.1** Crear `student-dashboard.routes.ts`:
  - [ ] Path: 'dashboard'
  - [ ] CanActivate: [authGuard] (agregar studentGuard si existe)
  - [ ] Lazy loading para cada sección
  - [ ] Redirect '' → 'courses'
- [ ] **8.2** Actualizar app.routes.ts para incluir student routes
- [ ] **8.3** Verificar que funcione la navegación

### Fase 9: Mis Cursos (Courses)

- [ ] **9.1** Crear `components/mis-cursos/mis-cursos.component.ts`:
  - [ ] Inject StudentService
  - [ ] Signals: courses, loading, error
  - [ ] Computed: activeCourses, completedCourses
  - [ ] OnInit: cargar cursos
- [ ] **9.2** Crear `components/mis-cursos/mis-cursos.component.scss`:
  - [ ] Grid de course cards
  - [ ] Empty state cuando no hay cursos
- [ ] **9.3** Crear `components/mis-cursos/course-card.component.ts`:
  - [ ] Inputs: course, expanded
  - [ ] Output: toggleExpand
  - [ ] Muestra: nombre, profesor, horario, sala, estado, promedio
  - [ ] Sección expandable para materiales
- [ ] **9.4** Crear `components/mis-cursos/material-list.component.ts`:
  - [ ] Input: materials array
  - [ ] Lista de materiales con iconos por tipo (PDF, VIDEO, LINK)
  - [ ] Click abre/descarga el material
- [ ] **9.5** Crear `components/mis-cursos/index.ts`
- [ ] **9.6** Agregar loading skeleton
- [ ] **9.7** Agregar empty state con CTA a inscripciones

### Fase 10: Calificaciones (Grades)

- [ ] **10.1** Crear `components/calificaciones/calificaciones.component.ts`:
  - [ ] Inject StudentService
  - [ ] Signals: grades, loading, error, selectedCourse
  - [ ] OnInit: cargar calificaciones
- [ ] **10.2** Crear `components/calificaciones/calificaciones.component.scss`:
  - [ ] Lista de cursos con badges de estado
  - [ ] Colores: verde (≥6), rojo (<6), amarillo (sin promedio)
- [ ] **10.3** Crear `components/calificaciones/grade-detail.component.ts`:
  - [ ] Input: gradeResponse
  - [ ] Lista de evaluaciones con: título, puntaje, peso, fecha
  - [ ] Muestra promedio ponderado
  - [ ] Observaciones si existen
- [ ] **10.4** Crear `components/calificaciones/index.ts`
- [ ] **10.5** Agregar loading skeleton
- [ ] **10.6** Agregar empty state

### Fase 11: Asistencia (Attendance)

- [ ] **11.1** Crear `components/asistencia/asistencia.component.ts`:
  - [ ] Inject StudentService
  - [ ] Signals: attendance, loading, error, selectedCourse
  - [ ] Computed: coursesAtRisk (<75%)
  - [ ] OnInit: cargar asistencia
- [ ] **11.2** Crear `components/asistencia/asistencia.component.scss`:
  - [ ] Cards con resumen por curso
  - [ ] Iconos para: presente, ausente, tarde
  - [ ] Banner de advertencia para <75%
- [ ] **11.3** Crear `components/asistencia/attendance-detail.component.ts`:
  - [ ] Input: attendanceResponse
  - [ ] Lista de registros por fecha
  - [ ] Colores por estado
- [ ] **11.4** Crear `components/asistencia/index.ts`
- [ ] **11.5** Agregar loading skeleton
- [ ] **11.6** Agregar empty state

### Fase 12: Inscripciones (Enrollments)

- [ ] **12.1** Crear `components/inscripciones/inscripciones.component.ts`:
  - [ ] Inject CourseService + StudentService
  - [ ] Signals: availableCourses, myEnrollments, loading, error
  - [ ] OnInit: cargar ambos
- [ ] **12.2** Crear `components/inscripciones/inscripciones.component.scss`:
  - [ ] Tabs o secciones: disponibles / inscripto
  - [ ] Grid de course cards
  - [ ] Indicador de cupos disponibles
- [ ] **12.3** Crear `components/inscripciones/available-course-card.component.ts`:
  - [ ] Input: course, isEnrolled
  - [ ] Muestra: nombre, descripción, profesor, horario, cupos
  - [ ] Botón "Inscribirse" o "Ya inscripto"
- [ ] **12.4** Crear `components/inscripciones/enrollment-modal.component.ts`:
  - [ ] Input: course, isOpen
  - [ ] Output: confirm, cancel
  - [ ] Modal con datos del curso y confirmar/cancelar
- [ ] **12.5** Crear `components/inscripciones/index.ts`
- [ ] **12.6** Implementar flujo de inscripción:
  - [ ] Click → abrir modal → confirmar → POST → toast éxito
  - [ ] Error handling con toast
- [ ] **12.7** Implementar cancelar inscripción:
  - [ ] Botón en cursos inscriptos
  - [ ] Confirmación → DELETE → toast
- [ ] **12.8** Agregar loading skeleton
- [ ] **12.9** Agregar empty state

### Fase 13: Mi Perfil (Profile)

- [ ] **13.1** Crear `components/mi-perfil/mi-perfil.component.ts`:
  - [ ] Inject StudentService
  - [ ] Signals: profile, loading, error
  - [ ] OnInit: cargar perfil
- [ ] **13.2** Crear `components/mi-perfil/mi-perfil.component.scss`:
  - [ ] Layout: avatar + datos personales
  - [ ] Sección de cambiar contraseña
- [ ] **13.3** Crear `components/mi-perfil/password-form.component.ts`:
  - [ ] FormGroup: currentPassword, newPassword, confirmPassword
  - [ ] Validators: required, minLength(8), passwordMatch
  - [ ] Validación en tiempo real
  - [ ] Submit → PUT → toast éxito/error
  - [ ] Limpiar campos en éxito
- [ ] **13.4** Crear `components/mi-perfil/index.ts`
- [ ] **13.5** Agregar loading skeleton

### Fase 14: Responsive & Polish

- [ ] **14.1** Implementar sidebar toggle (hamburger menu) para tablet/mobile
- [ ] **14.2** Ajustar grid layouts para 768px+
- [ ] **14.3** Verificar overflow/scroll en main content
- [ ] **14.4** Asegurar que todos los colores usen CSS variables
- [ ] **14.5** Verificar hover/focus states para accesibilidad

### Fase 15: Testing

- [ ] **15.1** Crear specs para StudentService:
  - [ ] Test getProfile()
  - [ ] Test getEnrolledCourses()
  - [ ] Test getGrades()
  - [ ] Test getAttendance()
  - [ ] Test updatePassword() - success y error
- [ ] **15.2** Crear specs para CourseService:
  - [ ] Test getAvailableCourses()
  - [ ] Test enrollInCourse()
  - [ ] Test cancelEnrollment()
- [ ] **15.3** Crear specs para components principales:
  - [ ] Test rendering con mock data
  - [ ] Test loading state
  - [ ] Test error state
  - [ ] Test empty state
- [ ] **15.4** Verificar que no haya errores de TypeScript

### Fase 16: Mock Data (Development)

- [ ] **16.1** Crear archivo `mock-data.ts` con datos de prueba:
  - [ ] StudentProfile mock
  - [ ] EnrolledCourse[] mocks (3 cursos)
  - [ ] GradeResponse[] mocks (con evaluaciones)
  - [ ] AttendanceResponse[] mocks
  - [ ] AvailableCourse[] mocks (3 cursos)
- [ ] **16.2** Opcional: crear mock interceptor para desarrollo local

---

## Progress Tracking

| Fase                 | Tareas | Completado |
| -------------------- | ------ | ---------- |
| 1. Foundation        | 9      | [ ]        |
| 2. Services          | 3      | [ ]        |
| 3. Guards            | 4      | [ ]        |
| 4. Shared Components | 4      | [ ]        |
| 5. Shell             | 2      | [ ]        |
| 6. Sidebar           | 3      | [ ]        |
| 7. Header            | 3      | [ ]        |
| 8. Routes            | 3      | [ ]        |
| 9. Mis Cursos        | 7      | [ ]        |
| 10. Calificaciones   | 6      | [ ]        |
| 11. Asistencia       | 6      | [ ]        |
| 12. Inscripciones    | 8      | [ ]        |
| 13. Mi Perfil        | 5      | [ ]        |
| 14. Responsive       | 5      | [ ]        |
| 15. Testing          | 4      | [ ]        |
| 16. Mock Data        | 2      | [ ]        |
| **TOTAL**            | **74** | **0**      |

---

## Notes

- **Importante**: Usar `ViewEncapsulation.None` en todos los componentes para que los estilos funcionen correctamente con las CSS variables del tema.
- **Signals**: Preferir signals sobre BehaviorSubject para el estado local de los componentes.
- **Lazy Loading**: Cada sección se carga de forma lazy para mejorar el tiempo de carga inicial.
- **Consistencia**: Mantener los mismos patrones de nomenclatura y estructura que el admin-dashboard existente.
