import { Routes } from '@angular/router';
import { Dashboard } from './dashboard/dashboard';
import { authGuard } from './core/auth-guard';
import { adminGuard } from './core/admin-guard';
import { professorGuard } from './core/professor-guard';
import { studentGuard } from './core/student-guard';
import { AdminDashboard } from './dashboard/admin-dashboard/admin-dashboard';
import { ProfessorDashboard } from './dashboard/professor-dashboard/professor-dashboard';
import { StudentDashboard } from './dashboard/student-dashboard/student-dashboard';
import { Login } from './shared/login/login';
import { Unauthorized } from './shared/unauthorized/unauthorized';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'student',
    component: StudentDashboard,
    canActivate: [authGuard, studentGuard],
  },
  {
    path: 'admin',
    component: AdminDashboard,
    canActivate: [authGuard, adminGuard],
  },
  {
    path: 'professor',
    component: ProfessorDashboard,
    canActivate: [authGuard, professorGuard],
  },
  {
    path: 'unauthorized',
    component: Unauthorized,
  },
  {
    path: '**',
    redirectTo: '/login',
  },
];
