# SDD Proposal: Student Dashboard

## Project: Gestor Académico FullStack

**Change**: Student Dashboard Feature  
**Status**: Draft  
**Created**: 2026-03-21  
**Author**: SDD Process

---

## 1. Intent

Proveer a los estudiantes del sistema académico una interfaz dedicada para visualizar y gestionar su información académica: cursos inscriptos, calificaciones, asistencia, e inscripciones a nuevos cursos. El dashboard seguirá el mismo patrón visual del admin-dashboard existente con un tema académico oscuro con acentos dorados.

**Impacto esperado**:

- Los estudiantes pueden ver su progreso académico en un solo lugar
- Reducción de fricción para inscibirse en cursos
- Mejora en la experiencia del estudiante (Student Experience)

---

## 2. Scope

### In Scope (Este SDD)

- **Ruta**: `/dashboard` con guards `authGuard` + `studentGuard`
- **5 secciones principales**:
  1. **Mis Cursos**: Lista de cursos inscriptos con materiales
  2. **Calificaciones**: Ver notas por curso
  3. **Asistencia**: Historial de asistencia
  4. **Inscripciones**: Inscribirse en cursos disponibles
  5. **Mi Perfil**: Datos personales + cambio de contraseña

### Out of Scope

- Admin para gestionar estudiantes (ya existe en admin-dashboard)
- Notificaciones push
- Descarga de certificados/constancias
- Chat o mensajería entre estudiantes y profesores
- Integración con calendar externo

---

## 3. Approach

### Arquitectura de Componentes

```
student-dashboard/
├── student-dashboard.component.ts (shell + routing)
├── components/
│   ├── sidebar/
│   ├── header/
│   ├── mis-cursos/
│   ├── calificaciones/
│   ├── asistencia/
│   ├── inscripciones/
│   └── mi-perfil/
├── services/
│   ├── student.service.ts
│   ├── course.service.ts
│   └── auth.service.ts (shared)
└── models/
    ├── student.model.ts
    ├── course.model.ts
    └── grade.model.ts
```

### Patrones de Diseño

- **Standalone Components**: Todos los componentes serán standalone
- **ViewEncapsulation.None**: Para estilos scope-free
- **Container/Presentational**: Separar lógica de presentación
- **Signal-based state**: Usar signals de Angular 21 para estado reactivo

### API Endpoints (Backend a implementar)

```
GET    /api/students/me                    → Datos del estudiante
GET    /api/students/me/courses            → Cursos inscriptos
GET    /api/courses/available              → Cursos disponibles para inscripción
POST   /api/students/me/enrollments         → Inscribirse en curso
DELETE /api/students/me/enrollments/{id}    → Cancelar inscripción
GET    /api/students/me/grades              → Todas las calificaciones
GET    /api/students/me/grades?courseId=X   → Calificaciones por curso
GET    /api/students/me/attendance          → Historial de asistencia
PUT    /api/students/me/password            → Cambiar contraseña
```

### Responsabilidades

- **Frontend (Angular)**: UI, routing, consumo de APIs, gestión de estado local
- **Backend**: Validación, persistencia, reglas de negocio

---

## 4. Constraints

1. **Autenticación**: JWT válido requerido en todos los endpoints
2. **Autorización**: Solo rol `STUDENT` puede acceder al dashboard
3. **Compatibilidad**: Debe funcionar en browsers modernos (Chrome, Firefox, Safari, Edge)
4. **Responsive**: Mínimo soporte hasta 768px (tablet landscape)
5. **Tokens**: Refresh token handling si expira el JWT

---

## 5. Risks & Mitigations

| Risk                                    | Probability | Impact | Mitigation                                         |
| --------------------------------------- | ----------- | ------ | -------------------------------------------------- |
| Backend endpoints no implementados      | High        | Medium | Definir contratos claros, usar mocks si necesario  |
| studentGuard no existe                  | Medium      | Low    | Usar authGuard + verificación de rol en componente |
| Cambios en esquema de datos del backend | Medium      | Medium | Usar interfaces tipadas, validar con Zod/similar   |
| Session expiry durante uso              | Low         | Medium | Implementar refresh token flow                     |

---

## 6. Success Criteria

1. ✅ Estudiante puede ver lista de cursos inscriptos
2. ✅ Estudiante puede ver calificaciones por curso
3. ✅ Estudiante puede ver historial de asistencia
4. ✅ Estudiante puede inscribirse en cursos disponibles
5. ✅ Estudiante puede cambiar su contraseña
6. ✅ UI consistente con admin-dashboard existente
7. ✅ Responsive hasta 768px
8. ✅ Tests unitarios para servicios y componentes principales
