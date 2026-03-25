# Proposal: Professor Dashboard

## Intent

Create a complete Professor Dashboard for the Gestor Académico FullStack application, providing professors with dedicated interfaces to manage their courses, take attendance, enter grades, and view their profile. This dashboard will follow the same design patterns as the existing admin-dashboard, maintaining consistency across the application.

## Scope

### In Scope
- **Route & Guard**: `/professor` path with `authGuard` + `professorGuard`
- **Layout Component**: Sidebar navigation + header + main content area (matching admin-dashboard pattern)
- **Mis Cursos**: List courses assigned to the professor, view enrolled students per course
- **Asistencia**: Take and manage attendance by date and course
- **Calificaciones**: Enter and manage grades by student and evaluation type
- **Mi Perfil**: View/edit personal data, change password
- **Dark academic theme** with CSS variables (--color-bg-primary, --color-accent-gold, etc.)
- **Responsive design** following mobile-first approach
- **API integration** with backend endpoints (to be defined)

### Out of Scope
- Admin functionality (reserved for admin-dashboard)
- Student enrollment management (professors can only view enrolled students)
- Course creation/editing (professors cannot modify course structure)
- Report generation (future enhancement)
- Email/notification features (future enhancement)

## Approach

The Professor Dashboard will be implemented as a new Angular standalone component following the existing admin-dashboard architecture:

1. **Component Structure**: Single `professor-dashboard.component` with internal section management (show/hide based on active navigation item)
2. **Routing**: Add route `/professor` with guards for authentication and role verification
3. **Layout Pattern**: Sidebar (navigation) + Header (user info, logout) + Main Content (dynamic based on selected section)
4. **State Management**: Use Angular signals for reactive UI state
5. **API Integration**: Create `ProfessorService` to handle all HTTP calls to backend
6. **Styling**: Use existing CSS variables and Tailwind CSS v4 utilities with `ViewEncapsulation.None`

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/app/dashboard/professor-dashboard/` | New | Professor Dashboard component and sub-components |
| `src/app/core/guards/professor.guard.ts` | New | Role-based guard for professor access |
| `src/app/core/services/professor.service.ts` | New | API service for professor operations |
| `src/app/core/models/professor.model.ts` | New | TypeScript interfaces for professor data |
| `src/app/app.routes.ts` | Modified | Add `/professor` route |
| `src/app/dashboard/admin-dashboard/` | Reference | Copy layout patterns and styling |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Backend API not ready | High | Define API contracts in spec, mock data for development |
| Responsive issues on tablet | Medium | Test on iPad-sized viewports, use Tailwind breakpoints |
| Session timeout during use | Low | Implement token refresh or show login prompt |

## Rollback Plan

1. Remove the `/professor` route from `app.routes.ts`
2. Delete `src/app/dashboard/professor-dashboard/` directory
3. Delete `src/app/core/guards/professor.guard.ts`
4. Delete `src/app/core/services/professor.service.ts`
5. Delete `src/app/core/models/professor.model.ts`
6. No database changes required (all data is user-specific)

## Dependencies

- Angular 21 with standalone components
- Tailwind CSS v4 configured in the project
- Existing auth infrastructure (JWT, auth service)
- Existing admin-dashboard as design reference
- Backend API (endpoints defined in design)

## Success Criteria

- [ ] Professor can login and access dashboard at `/professor` route
- [ ] Sidebar shows 4 navigation items: Mis Cursos, Asistencia, Calificaciones, Mi Perfil
- [ ] Mis Cursos displays assigned courses with enrolled student count
- [ ] Asistencia allows selecting course and date to take attendance
- [ ] Calificaciones allows selecting course and entering grades by evaluation
- [ ] Mi Perfil displays personal data and allows password change
- [ ] Layout matches admin-dashboard visual design (dark academic theme)
- [ ] Responsive design works on desktop, tablet, and mobile
- [ ] JWT token includes PROFESSOR role for guard verification
