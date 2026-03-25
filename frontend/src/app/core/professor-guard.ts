import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionService } from './session-service';

export const professorGuard: CanActivateFn = (route, state) => {
  const session = inject(SessionService).getSessionData();
  const router = inject(Router);
  if (!session || session.role != "PROFESSOR") {
    return router.createUrlTree(["/login"]);
  }
  return true;
};
