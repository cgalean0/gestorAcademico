# Design: Professor Dashboard

## Technical Approach

The Professor Dashboard will be implemented as an Angular 21 standalone component following the same architecture as the admin-dashboard. The component will use a single-page application pattern with internal section management via signals, avoiding unnecessary route changes for navigation items within the dashboard.

**Key Decisions:**
- Single dashboard component with internal section state (signal-based)
- Copy admin-dashboard layout patterns and CSS variable usage
- ViewEncapsulation.None for component-scoped styles
- Angular signals for reactive state management
- Centralized API service for all professor operations

## Architecture Decisions

### Decision: Single Component vs Multiple Routes

**Choice**: Single `professor-dashboard.component` with internal section switching
**Alternatives considered**: Separate route for each section (`/professor/courses`, `/professor/attendance`, etc.)
**Rationale**: Simpler navigation, consistent UX, less route configuration overhead. The dashboard is a bounded context, so internal navigation doesn't need URL changes.

### Decision: Signal-Based State Management

**Choice**: Angular signals for UI state (active section, loading states, form data)
**Alternatives considered**: Centralized NgRx store, BehaviorSubject services
**Rationale**: Signals are the modern Angular 21 approach, less boilerplate than NgRx, integrates well with the template for reactive updates.

### Decision: CSS Variables for Theming

**Choice**: Use existing CSS variables (`--color-bg-primary`, `--color-accent-gold`, etc.)
**Alternatives considered**: Shadow DOM encapsulation with scoped styles
**Rationale**: Consistent theme across admin and professor dashboards. ViewEncapsulation.None allows global CSS variable access.

## Data Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                    ProfessorDashboardComponent                   │
├─────────────────────────────────────────────────────────────────┤
│  SidebarComponent    │  HeaderComponent  │  MainContentComponent │
│  - Navigation items  │  - User name      │  - [CoursesSection]  │
│  - Active state      │  - Logout button  │  - [AttendanceSection]│
│  - Click handlers    │                   │  - [GradesSection]   │
│                      │                   │  - [ProfileSection]  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      ProfessorService                            │
├─────────────────────────────────────────────────────────────────┤
│  getCourses()              → GET /api/professor/courses          │
│  getEnrolledStudents()     → GET /api/professor/courses/:id/students
│  getAttendance()          → GET /api/professor/attendance        │
│  saveAttendance()         → POST /api/professor/attendance       │
│  getGrades()              → GET /api/professor/grades            │
│  saveGrades()             → POST /api/professor/grades           │
│  getProfile()             → GET /api/professor/profile           │
│  changePassword()         → POST /api/professor/password         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
                    http://localhost:8080/
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/app/dashboard/professor-dashboard/professor-dashboard.component.ts` | Create | Main dashboard component with signal-based section state |
| `src/app/dashboard/professor-dashboard/professor-dashboard.component.html` | Create | Template with sidebar, header, and section containers |
| `src/app/dashboard/professor-dashboard/professor-dashboard.component.css` | Create | Styles using CSS variables, responsive breakpoints |
| `src/app/core/guards/professor.guard.ts` | Create | Role-based guard checking for PROFESSOR role |
| `src/app/core/services/professor.service.ts` | Create | HTTP service for all professor API endpoints |
| `src/app/core/models/professor.model.ts` | Create | Interfaces: Course, Student, Attendance, Grade, ProfessorProfile |
| `src/app/app.routes.ts` | Modify | Add route: `{ path: 'professor', canActivate: [authGuard, professorGuard], component: ProfessorDashboardComponent }` |

## Interfaces / Contracts

```typescript
// professor.model.ts

export interface Course {
  id: string;
  name: string;
  code: string;
  enrolledCount: number;
  schedule: string;
}

export interface EnrolledStudent {
  id: string;
  name: string;
  email: string;
  enrolledDate: string;
}

export interface AttendanceRecord {
  id?: string;
  courseId: string;
  date: string;
  studentId: string;
  status: 'present' | 'absent' | 'tardy';
}

export interface AttendanceSession {
  courseId: string;
  date: string;
  records: AttendanceRecord[];
}

export interface Evaluation {
  id: string;
  name: string;
  weight: number;
  maxGrade: number;
}

export interface Grade {
  studentId: string;
  evaluationId: string;
  value: number;
}

export interface GradeSubmission {
  courseId: string;
  evaluationId: string;
  grades: Grade[];
}

export interface ProfessorProfile {
  id: string;
  name: string;
  email: string;
  department: string;
  hireDate: string;
  avatar?: string;
}

export interface PasswordChangeRequest {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}
```

## API Endpoints

| Method | Endpoint | Request Body | Response | Description |
|--------|----------|--------------|----------|-------------|
| GET | `/api/professor/courses` | - | `Course[]` | Get professor's assigned courses |
| GET | `/api/professor/courses/:id/students` | - | `EnrolledStudent[]` | Get enrolled students for a course |
| GET | `/api/professor/attendance` | Query: `courseId, date` | `AttendanceRecord[]` | Get attendance for date/course |
| POST | `/api/professor/attendance` | `AttendanceSession` | `{ success: boolean }` | Save attendance records |
| GET | `/api/professor/evaluations` | Query: `courseId` | `Evaluation[]` | Get evaluation types for course |
| GET | `/api/professor/grades` | Query: `courseId, studentId?` | `Grade[]` | Get grades, optionally filtered |
| POST | `/api/professor/grades` | `GradeSubmission` | `{ success: boolean }` | Save grades |
| GET | `/api/professor/profile` | - | `ProfessorProfile` | Get current professor profile |
| POST | `/api/professor/password` | `PasswordChangeRequest` | `{ success: boolean }` | Change password |

## Component Structure

```
ProfessorDashboardComponent
├── activeSection: Signal<'courses' | 'attendance' | 'grades' | 'profile'>
├── sidebar: SidebarComponent (inline)
├── header: HeaderComponent (inline)
└── main: MainContentComponent
    ├── CoursesSection (ngIf="activeSection() === 'courses'")
    ├── AttendanceSection (ngIf="activeSection() === 'attendance'")
    ├── GradesSection (ngIf="activeSection() === 'grades'")
    └── ProfileSection (ngIf="activeSection() === 'profile'")
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | ProfessorService methods | Mock HttpClient, verify request URLs and response handling |
| Unit | Section switching logic | Test signal updates on navigation clicks |
| Integration | API endpoint integration | e2e tests with real backend (or mock server) |
| Component | User interactions | Test click handlers, form submissions, validation |

## Migration / Rollback

No database migration required. All data is user-specific and the component is self-contained.

**Rollback Steps:**
1. Remove route from `app.routes.ts`
2. Delete component directory
3. Delete guard, service, and models
4. No data loss as all is frontend-only

## Open Questions

- [ ] Should attendance records be editable after a certain period (e.g., 7 days)?
- [ ] What grade scale should be used (0-10, 0-100, letter grades)?
- [ ] Should professors be able to export grades to PDF/Excel?
