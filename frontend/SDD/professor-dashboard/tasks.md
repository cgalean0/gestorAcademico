# Tasks: Professor Dashboard

## Phase 1: Foundation / Models

- [ ] 1.1 Create `src/app/core/models/professor.model.ts` with all interfaces: Course, EnrolledStudent, AttendanceRecord, AttendanceSession, Evaluation, Grade, GradeSubmission, ProfessorProfile, PasswordChangeRequest
- [ ] 1.2 Export interfaces from a barrel file `src/app/core/models/index.ts` for clean imports

## Phase 2: Guard

- [ ] 2.1 Create `src/app/core/guards/professor.guard.ts` extending authGuard pattern
- [ ] 2.2 Inject AuthService to check user role from JWT token
- [ ] 2.3 Return `true` if role includes PROFESSOR, `false` otherwise with redirect
- [ ] 2.4 Export guard from `src/app/core/guards/index.ts`

## Phase 3: Service

- [ ] 3.1 Create `src/app/core/services/professor.service.ts` extending BaseApiService
- [ ] 3.2 Implement `getCourses(): Observable<Course[]>`
- [ ] 3.3 Implement `getEnrolledStudents(courseId: string): Observable<EnrolledStudent[]>`
- [ ] 3.4 Implement `getAttendance(courseId: string, date: string): Observable<AttendanceRecord[]>`
- [ ] 3.5 Implement `saveAttendance(session: AttendanceSession): Observable<{success: boolean}>`
- [ ] 3.6 Implement `getEvaluations(courseId: string): Observable<Evaluation[]>`
- [ ] 3.7 Implement `getGrades(courseId: string, studentId?: string): Observable<Grade[]>`
- [ ] 3.8 Implement `saveGrades(submission: GradeSubmission): Observable<{success: boolean}>`
- [ ] 3.9 Implement `getProfile(): Observable<ProfessorProfile>`
- [ ] 3.10 Implement `changePassword(request: PasswordChangeRequest): Observable<{success: boolean}>`
- [ ] 3.11 Export service from `src/app/core/services/index.ts`

## Phase 4: Dashboard Component - Core Structure

- [ ] 4.1 Create `src/app/dashboard/professor-dashboard/` directory
- [ ] 4.2 Create `professor-dashboard.component.ts` with standalone component configuration
- [ ] 4.3 Add imports: CommonModule, FormsModule, ReactiveFormsModule, RouterModule
- [ ] 4.4 Define signal: `activeSection = signal<'courses' | 'attendance' | 'grades' | 'profile'>('courses')`
- [ ] 4.5 Define navigation items array with icon, label, section key
- [ ] 4.6 Create `setSection(section)` method to update signal
- [ ] 4.7 Inject ProfessorService and AuthService
- [ ] 4.8 Add `isMobile = signal(false)` and window resize listener in ngOnInit

## Phase 5: Dashboard Template

- [ ] 5.1 Create `professor-dashboard.component.html`
- [ ] 5.2 Add sidebar navigation with 4 items: Mis Cursos, Asistencia, Calificaciones, Mi Perfil
- [ ] 5.3 Add header with user name from AuthService and logout button
- [ ] 5.4 Add hamburger menu button for mobile (visible when isMobile())
- [ ] 5.5 Add section containers with ngIf directives for each section
- [ ] 5.6 Add responsive classes using Tailwind breakpoints (md:, lg:)

## Phase 6: Dashboard Styles

- [ ] 6.1 Create `professor-dashboard.component.css`
- [ ] 6.2 Use CSS variables: `--color-bg-primary`, `--color-bg-secondary`, `--color-accent-gold`, etc.
- [ ] 6.3 Style sidebar with fixed positioning and dark background
- [ ] 6.4 Style active nav item with gold accent border
- [ ] 6.5 Style header with user info and logout button
- [ ] 6.6 Add responsive styles for mobile (collapsible sidebar)
- [ ] 6.7 Add responsive styles for tablet (icon-only sidebar)

## Phase 7: Courses Section

- [ ] 7.1 Create courses section template with loading state
- [ ] 7.2 Call `professorService.getCourses()` on section activation
- [ ] 7.3 Display course cards with name, code, student count
- [ ] 7.4 Add expandable section to show enrolled students on card click
- [ ] 7.5 Call `professorService.getEnrolledStudents(courseId)` when expanded
- [ ] 7.6 Display students list with name, email, enrollment date

## Phase 8: Attendance Section

- [ ] 8.1 Create attendance section template with course dropdown and date picker
- [ ] 8.2 Load courses list on section activation
- [ ] 8.3 Add form with course selection and date input
- [ ] 8.4 Display student attendance list with radio buttons (Present/Absent/Tardy)
- [ ] 8.5 Add "Guardar Asistencia" button with loading state
- [ ] 8.6 Call `professorService.saveAttendance()` on submit
- [ ] 8.7 Show success/error toast messages
- [ ] 8.8 Load existing attendance when editing past dates

## Phase 9: Grades Section

- [ ] 9.1 Create grades section template with course and evaluation dropdowns
- [ ] 9.2 Load courses and evaluations on section activation
- [ ] 9.3 Display grade table with students as rows and evaluations as columns
- [ ] 9.4 Add numeric input fields for each grade (0-10 scale with validation)
- [ ] 9.5 Add "Guardar Calificaciones" button
- [ ] 9.6 Call `professorService.saveGrades()` on submit
- [ ] 9.7 Show success/error toast messages
- [ ] 9.8 Add average calculation per student

## Phase 10: Profile Section

- [ ] 10.1 Create profile section template with personal data display
- [ ] 10.2 Call `professorService.getProfile()` on section activation
- [ ] 10.3 Display name, email, department, hire date in read-only fields
- [ ] 10.4 Add password change form with ReactiveFormsModule
- [ ] 10.5 Add validators: current password required, new password min 8 chars, confirm must match
- [ ] 10.6 Add "Cambiar Contraseña" button with loading state
- [ ] 10.7 Call `professorService.changePassword()` on submit
- [ ] 10.8 Show success/error messages and clear form on success

## Phase 11: Routing

- [ ] 11.1 Add route to `src/app/app.routes.ts`: `{ path: 'professor', canActivate: [authGuard, professorGuard], component: ProfessorDashboardComponent }`
- [ ] 11.2 Import ProfessorDashboardComponent
- [ ] 11.3 Import and register professorGuard
- [ ] 11.4 Update redirect in authGuard to include `/professor` for PROFESSOR role

## Phase 12: Testing

- [ ] 12.1 Write unit tests for ProfessorService methods (mock HttpClient)
- [ ] 12.2 Write unit tests for section switching logic in component
- [ ] 12.3 Write integration tests for password change flow
- [ ] 12.4 Write integration tests for attendance save flow

## Phase 13: Polish

- [ ] 13.1 Add loading spinners to all async operations
- [ ] 13.2 Add error handling with user-friendly messages
- [ ] 13.3 Add empty states for no courses, no students, etc.
- [ ] 13.4 Add confirmation dialog before saving attendance/grades
- [ ] 13.5 Test responsive design on multiple screen sizes
