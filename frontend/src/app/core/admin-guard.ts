import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { SessionService } from './session-service';

export const adminGuard: CanActivateFn = (
  route : ActivatedRouteSnapshot,
  state : RouterStateSnapshot
  ) => {
  const user = inject(SessionService).getSessionData();
  const router = inject(Router);
  if (!user || user.role != "ADMIN") {
    return router.createUrlTree(["/unathorized"]);
  }
  return true;
};
