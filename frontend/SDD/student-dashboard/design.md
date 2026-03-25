# SDD Design: Student Dashboard

## Project: Gestor Académico FullStack

**Design Version**: 1.0  
**Created**: 2026-03-21

---

## 1. Architecture Overview

### 1.1 Component Hierarchy

```
AppComponent
└── StudentDashboardComponent (shell)
    ├── StudentSidebarComponent
    ├── StudentHeaderComponent
    └── Router Outlet
        ├── MisCursosComponent
        │   └── CourseCardComponent
        │       └── MaterialListComponent
        ├── CalificacionesComponent
        │   └── GradeDetailComponent
        ├── AsistenciaComponent
        │   └── AttendanceDetailComponent
        ├── InscripcionesComponent
        │   ├── AvailableCourseCardComponent
        │   └── EnrollmentModalComponent
        └── MiPerfilComponent
            └── PasswordFormComponent
```

### 1.2 Directory Structure

```
src/app/dashboard/student-dashboard/
├── student-dashboard.component.ts       # Shell + routing
├── student-dashboard.component.scss     # Shell styles
├── student-dashboard.routes.ts          # Route configuration
├── components/
│   ├── sidebar/
│   │   ├── student-sidebar.component.ts
│   │   └── student-sidebar.component.scss
│   ├── header/
│   │   ├── student-header.component.ts
│   │   └── student-header.component.scss
│   ├── mis-cursos/
│   │   ├── mis-cursos.component.ts
│   │   ├── mis-cursos.component.scss
│   │   ├── course-card.component.ts
│   │   └── material-list.component.ts
│   ├── calificaciones/
│   │   ├── calificaciones.component.ts
│   │   ├── calificaciones.component.scss
│   │   └── grade-detail.component.ts
│   ├── asistencia/
│   │   ├── asistencia.component.ts
│   │   ├── asistencia.component.scss
│   │   └── attendance-detail.component.ts
│   ├── inscripciones/
│   │   ├── inscripciones.component.ts
│   │   ├── inscripciones.component.scss
│   │   ├── available-course-card.component.ts
│   │   └── enrollment-modal.component.ts
│   └── mi-perfil/
│       ├── mi-perfil.component.ts
│       ├── mi-perfil.component.scss
│       └── password-form.component.ts
├── services/
│   ├── student.service.ts              # Student data
│   ├── course.service.ts               # Courses + enrollment
│   └── attendance.service.ts           # Attendance data
├── models/
│   ├── student.model.ts
│   ├── course.model.ts
│   ├── grade.model.ts
│   ├── attendance.model.ts
│   └── enrollment.model.ts
└── guards/
    └── student.guard.ts                # Role guard (if needed)
```

---

## 2. Services Design

### 2.1 StudentService

```typescript
@Injectable({ providedIn: 'root' })
export class StudentService {
  private baseUrl = `${environment.apiUrl}/students`;

  // Perfil
  getProfile(): Observable<StudentProfile>;
  updatePassword(request: PasswordChangeRequest): Observable<void>;

  // Cursos (alias para enrollment)
  getEnrolledCourses(): Observable<EnrolledCourse[]>;
  getGrades(): Observable<GradeResponse[]>;
  getAttendance(): Observable<AttendanceResponse[]>;
}
```

### 2.2 CourseService

```typescript
@Injectable({ providedIn: 'root' })
export class CourseService {
  private baseUrl = `${environment.apiUrl}/courses`;

  getAvailableCourses(): Observable<AvailableCourse[]>;
  enrollInCourse(courseId: string): Observable<EnrollmentResponse>;
  cancelEnrollment(enrollmentId: string): Observable<void>;
}
```

### 2.3 Shared AuthService

```typescript
@Injectable({ providedIn: 'root' })
export class AuthService {
  getCurrentUser(): User | null;
  getToken(): string | null;
  logout(): void;
  isAuthenticated(): boolean;
  hasRole(role: string): boolean;
}
```

---

## 3. API Contracts

### 3.1 Endpoints

#### Student Endpoints

```typescript
// GET /api/students/me
GET /api/students/me
Headers: Authorization: Bearer {token}
Response: StudentProfile

// GET /api/students/me/courses
GET /api/students/me/courses
Headers: Authorization: Bearer {token}
Response: EnrolledCourse[]

// GET /api/students/me/grades
GET /api/students/me/grades
Headers: Authorization: Bearer {token}
Query: ?courseId={id} (optional)
Response: GradeResponse[]

// GET /api/students/me/attendance
GET /api/students/me/attendance
Headers: Authorization: Bearer {token}
Query: ?courseId={id}&month={YYYY-MM} (optional)
Response: AttendanceResponse[]

// PUT /api/students/me/password
PUT /api/students/me/password
Headers: Authorization: Bearer {token}
Body: PasswordChangeRequest
Response: 200 OK | 400 Bad Request
```

#### Course Endpoints

```typescript
// GET /api/courses/available
GET /api/courses/available
Headers: Authorization: Bearer {token}
Response: AvailableCourse[]

// POST /api/students/me/enrollments
POST /api/students/me/enrollments
Headers: Authorization: Bearer {token}
Body: { courseId: string }
Response: EnrollmentResponse

// DELETE /api/students/me/enrollments/{id}
DELETE /api/students/me/enrollments/{id}
Headers: Authorization: Bearer {token}
Response: 204 No Content
```

### 3.2 TypeScript Interfaces

```typescript
// ===== STUDENT =====
interface StudentProfile {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  dni: string;
  birthDate: string; // ISO date
  phone?: string;
  address?: string;
  career: string;
  year: number;
  avatarUrl?: string;
  createdAt: string;
}

interface PasswordChangeRequest {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}

// ===== COURSE =====
interface EnrolledCourse {
  id: string;
  courseId: string;
  courseName: string;
  professorName: string;
  schedule: string;
  room: string;
  enrolledAt: string;
  status: 'ACTIVE' | 'COMPLETED' | 'DROPPED';
  materials: Material[];
  currentGrade?: number;
}

interface Material {
  id: string;
  title: string;
  type: 'PDF' | 'VIDEO' | 'LINK' | 'ASSIGNMENT';
  url: string;
  uploadedAt: string;
}

interface AvailableCourse {
  id: string;
  name: string;
  description: string;
  professorName: string;
  schedule: string;
  availableSpots: number;
  maxSpots: number;
  enrolledCount: number;
  prerequisites: string[];
}

// ===== GRADES =====
interface GradeResponse {
  courseId: string;
  courseName: string;
  grades: Grade[];
  average: number;
  status: 'APPROVED' | 'FAILED' | 'IN_PROGRESS';
}

interface Grade {
  id: string;
  title: string;
  score: number;
  weight: number;
  date: string;
  observations?: string;
}

// ===== ATTENDANCE =====
interface AttendanceResponse {
  courseId: string;
  courseName: string;
  totalClasses: number;
  presentCount: number;
  absentCount: number;
  lateCount: number;
  percentage: number;
  records: AttendanceRecord[];
}

interface AttendanceRecord {
  id: string;
  date: string;
  status: 'PRESENT' | 'ABSENT' | 'LATE' | 'JUSTIFIED';
  courseName: string;
  schedule: string;
}

// ===== ENROLLMENT =====
interface EnrollmentResponse {
  id: string;
  courseId: string;
  courseName: string;
  enrolledAt: string;
  status: 'CONFIRMED' | 'PENDING' | 'CANCELLED';
}
```

---

## 4. State Management

### 4.1 Component-Level State ( Signals )

```typescript
@Component({...})
export class MisCursosComponent {
  // Signals para estado reactivo
  private courses = signal<EnrolledCourse[]>([]);
  private loading = signal<boolean>(true);
  private error = signal<string | null>(null);

  // Computed
  readonly activeCourses = computed(() =>
    this.courses().filter(c => c.status === 'ACTIVE')
  );
  readonly completedCourses = computed(() =>
    this.courses().filter(c => c.status === 'COMPLETED')
  );

  // Public readonly
  readonly isLoading = this.loading.asReadonly();
  readonly coursesError = this.error.asReadonly();
}
```

### 4.2 Service-Level State

```typescript
@Injectable({ providedIn: 'root' })
export class StudentStore {
  private profile = signal<StudentProfile | null>(null);
  private enrolledCourses = signal<EnrolledCourse[]>([]);
  private grades = signal<GradeResponse[]>([]);
  private attendance = signal<AttendanceResponse[]>([]);

  readonly currentProfile = this.profile.asReadonly();
  readonly myCourses = this.enrolledCourses.asReadonly();
  readonly myGrades = this.grades.asReadonly();
  readonly myAttendance = this.attendance.asReadonly();

  // Cache invalidation methods
  invalidateCourses(): void { ... }
  invalidateGrades(): void { ... }
  invalidateAttendance(): void { ... }
}
```

---

## 5. Styling Guidelines

### 5.1 CSS Variables (from global styles)

```css
:root {
  /* Backgrounds */
  --color-bg-primary: #0f0f0f; /* Main background */
  --color-bg-secondary: #1a1a1a; /* Cards, sidebar */
  --color-bg-tertiary: #252525; /* Hover states */

  /* Text */
  --color-text-primary: #ffffff;
  --color-text-secondary: #a0a0a0;
  --color-text-muted: #666666;

  /* Accents */
  --color-accent-gold: #d4af37; /* Primary accent */
  --color-accent-gold-light: #f5d060;
  --color-accent-gold-dark: #b8941f;

  /* Status */
  --color-success: #22c55e;
  --color-warning: #eab308;
  --color-error: #ef4444;
  --color-info: #3b82f6;

  /* Borders */
  --color-border: #333333;
  --color-border-light: #444444;
}
```

### 5.2 Component Styling Pattern

```typescript
@Component({
  selector: 'app-mis-cursos',
  standalone: true,
  encapsulation: ViewEncapsulation.None,  // IMPORTANT
  imports: [CommonModule, RouterModule],
  template: `...`,
  styles: [`
    .courses-container {
      padding: 1.5rem;
    }

    .course-card {
      background: var(--color-bg-secondary);
      border: 1px solid var(--color-border);
      border-radius: 0.75rem;
      padding: 1.25rem;
      margin-bottom: 1rem;
      transition: border-color 0.2s;

      &:hover {
        border-color: var(--color-accent-gold);
      }
    }

    .course-name {
      color: var(--color-text-primary);
      font-size: 1.125rem;
      font-weight: 600;
    }

    .course-professor {
      color: var(--color-text-secondary);
    }

    .grade-badge {
      display: inline-flex;
      align-items: center;
      padding: 0.25rem 0.75rem;
      border-radius: 9999px;
      font-size: 0.875rem;
      font-weight: 500;

      &.approved {
        background: rgba(34, 197, 94, 0.15);
        color: var(--color-success);
      }

      &.failed {
        background: rgba(239, 68, 68, 0.15);
        color: var(--color-error);
      }
    }
  `]
})
export class MisCursosComponent { ... }
```

### 5.3 Sidebar Styling

```typescript
// sidebar.component.ts styles
const styles = `
  .student-sidebar {
    width: 240px;
    min-height: 100vh;
    background: var(--color-bg-secondary);
    border-right: 1px solid var(--color-border);
    display: flex;
    flex-direction: column;
  }

  .sidebar-logo {
    padding: 1.5rem;
    border-bottom: 1px solid var(--color-border);
  }

  .sidebar-nav {
    flex: 1;
    padding: 1rem 0;
  }

  .nav-item {
    display: flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.75rem 1.5rem;
    color: var(--color-text-secondary);
    text-decoration: none;
    transition: all 0.2s;
    border-left: 3px solid transparent;

    &:hover {
      background: var(--color-bg-tertiary);
      color: var(--color-text-primary);
    }

    &.active {
      background: rgba(212, 175, 55, 0.1);
      color: var(--color-accent-gold);
      border-left-color: var(--color-accent-gold);
    }
  }

  .nav-icon {
    width: 20px;
    height: 20px;
  }
`;
```

---

## 6. Guards

### 6.1 AuthGuard (if not exists, create)

```typescript
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true;
  }

  router.navigate(['/auth/login'], {
    queryParams: { returnUrl: state.url },
  });
  return false;
};
```

### 6.2 StudentGuard (role-based, if needed)

```typescript
export const studentGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);

  if (authService.hasRole('STUDENT')) {
    return true;
  }

  // Optionally redirect to admin dashboard or show access denied
  return false;
};
```

### 6.3 Route Configuration

```typescript
// student-dashboard.routes.ts
export const STUDENT_ROUTES: Routes = [
  {
    path: 'dashboard',
    canActivate: [authGuard], // Add studentGuard if available
    children: [
      { path: '', redirectTo: 'courses', pathMatch: 'full' },
      {
        path: 'courses',
        loadComponent: () => import('./components/mis-cursos/mis-cursos.component'),
      },
      {
        path: 'grades',
        loadComponent: () => import('./components/calificaciones/calificaciones.component'),
      },
      {
        path: 'attendance',
        loadComponent: () => import('./components/asistencia/asistencia.component'),
      },
      {
        path: 'enrollments',
        loadComponent: () => import('./components/inscripciones/inscripciones.component'),
      },
      {
        path: 'profile',
        loadComponent: () => import('./components/mi-perfil/mi-perfil.component'),
      },
    ],
  },
];
```

---

## 7. HTTP Interceptors

### 7.1 Auth Interceptor

```typescript
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const authService = inject(AuthService);
    const token = authService.getToken();

    if (token) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`),
      });
      return next.handle(cloned);
    }

    return next.handle(req);
  }
}
```

### 7.2 Error Interceptor

```typescript
@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        const router = inject(Router);
        const toastService = inject(ToastService);

        if (error.status === 401) {
          // Token expired or invalid
          authService.logout();
          router.navigate(['/auth/login']);
          toastService.error('Sesión expirada. Por favor, iniciá sesión nuevamente.');
        } else if (error.status === 403) {
          toastService.error('No tenés permisos para realizar esta acción.');
        } else if (error.status >= 500) {
          toastService.error('Error del servidor. Intentalo más tarde.');
        }

        return throwError(() => error);
      }),
    );
  }
}
```

---

## 8. Shared Components

### 8.1 Loading Skeleton

```typescript
@Component({
  selector: 'app-loading-skeleton',
  standalone: true,
  template: `
    <div class="skeleton-list">
      @for (item of items; track item) {
        <div class="skeleton-card">
          <div class="skeleton-title"></div>
          <div class="skeleton-subtitle"></div>
          <div class="skeleton-badge"></div>
        </div>
      }
    </div>
  `,
  styles: [
    `
      .skeleton-card {
        background: var(--color-bg-secondary);
        border-radius: 0.75rem;
        padding: 1.25rem;
        margin-bottom: 1rem;
      }

      .skeleton-title,
      .skeleton-subtitle,
      .skeleton-badge {
        background: linear-gradient(
          90deg,
          var(--color-bg-tertiary) 25%,
          var(--color-border) 50%,
          var(--color-bg-tertiary) 75%
        );
        background-size: 200% 100%;
        animation: shimmer 1.5s infinite;
        border-radius: 0.25rem;
      }

      @keyframes shimmer {
        0% {
          background-position: 200% 0;
        }
        100% {
          background-position: -200% 0;
        }
      }
    `,
  ],
})
export class LoadingSkeletonComponent {
  @Input() count = 3;
  get items() {
    return Array(this.count).fill(0);
  }
}
```

### 8.2 Empty State

```typescript
@Component({
  selector: 'app-empty-state',
  standalone: true,
  template: `
    <div class="empty-state">
      <div class="empty-icon">{{ icon }}</div>
      <h3 class="empty-title">{{ title }}</h3>
      <p class="empty-message">{{ message }}</p>
      @if (ctaLabel && ctaRoute) {
        <a [routerLink]="ctaRoute" class="empty-cta">
          {{ ctaLabel }}
        </a>
      }
    </div>
  `,
  styles: [
    `
      .empty-state {
        text-align: center;
        padding: 3rem 1.5rem;
      }

      .empty-icon {
        font-size: 3rem;
        margin-bottom: 1rem;
      }

      .empty-title {
        color: var(--color-text-primary);
        font-size: 1.25rem;
        font-weight: 600;
        margin-bottom: 0.5rem;
      }

      .empty-message {
        color: var(--color-text-secondary);
        margin-bottom: 1.5rem;
      }

      .empty-cta {
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.75rem 1.5rem;
        background: var(--color-accent-gold);
        color: var(--color-bg-primary);
        border-radius: 0.5rem;
        font-weight: 500;
        text-decoration: none;
        transition: background 0.2s;

        &:hover {
          background: var(--color-accent-gold-light);
        }
      }
    `,
  ],
})
export class EmptyStateComponent {
  @Input() icon = '📭';
  @Input() title = 'Sin datos';
  @Input() message = 'No hay elementos para mostrar.';
  @Input() ctaLabel?: string;
  @Input() ctaRoute?: string;
}
```

---

## 9. Testing Strategy

### 9.1 Unit Tests

- Services: Mock HTTP, test API calls and error handling
- Components: Test rendering with different states (loading, error, empty, data)
- Guards: Test auth/role checks

### 9.2 Integration Tests

- Form submissions (password change)
- Enrollment flow
- Navigation between sections

### 9.3 E2E Tests (optional)

- Full enrollment flow
- Password change flow
- Error scenarios

---

## 10. Mock Data (Development)

```typescript
// mock-data.ts
export const MOCK_STUDENT: StudentProfile = {
  id: '1',
  email: 'juan.perez@universidad.edu',
  firstName: 'Juan',
  lastName: 'Pérez',
  dni: '40123456',
  birthDate: '2000-05-15',
  phone: '+54 11 5555 1234',
  address: 'Calle Falsa 123, Buenos Aires',
  career: 'Ingeniería en Sistemas',
  year: 3,
  avatarUrl: undefined,
  createdAt: '2023-03-01',
};

export const MOCK_ENROLLED_COURSES: EnrolledCourse[] = [
  {
    id: '1',
    courseId: 'algoritmos-1',
    courseName: 'Algoritmos y Estructuras de Datos I',
    professorName: 'Dr. García',
    schedule: 'Lun 10:00-12:00, Mié 10:00-12:00',
    room: 'Aula 301',
    enrolledAt: '2024-03-01',
    status: 'ACTIVE',
    currentGrade: 8.5,
    materials: [
      {
        id: 'm1',
        title: 'Apunte Tema 1 - Recursión',
        type: 'PDF',
        url: '/materials/recursion.pdf',
        uploadedAt: '2024-03-15',
      },
      {
        id: 'm2',
        title: 'Video: Listas Enlazadas',
        type: 'VIDEO',
        url: '/materials/linked-lists.mp4',
        uploadedAt: '2024-03-20',
      },
    ],
  },
];

// ... more mock data for grades, attendance, available courses
```

---

## 11. Dependencies

### 11.1 Required Angular Packages

```json
{
  "@angular/core": "^21.0.0",
  "@angular/router": "^21.0.0",
  "@angular/common/http": "^21.0.0",
  "rxjs": "^7.8.0"
}
```

### 11.2 Already in Project (assumed)

- Tailwind CSS v4
- Lucide icons or similar (check existing admin-dashboard)

---

## 12. Implementation Phases

### Phase 1: Foundation

1. Create directory structure
2. Implement shell component (sidebar + header + routing)
3. Set up services with interfaces
4. Configure routes with guards

### Phase 2: Mis Cursos

1. MisCursosComponent with course list
2. CourseCardComponent with material expansion
3. Loading and empty states

### Phase 3: Calificaciones

1. CalificacionesComponent with grades overview
2. GradeDetailComponent for course grades
3. Visual indicators for pass/fail status

### Phase 4: Asistencia

1. AsistenciaComponent with attendance summary
2. AttendanceDetailComponent with history
3. Warning banner for <75% attendance

### Phase 5: Inscripciones

1. InscripcionesComponent with available courses
2. EnrollmentModalComponent
3. Enrollment/cancellation flows

### Phase 6: Mi Perfil

1. MiPerfilComponent with profile display
2. PasswordFormComponent with validation
3. Success/error handling

### Phase 7: Polish

1. Loading skeletons
2. Error handling
3. Responsive adjustments
4. Tests
