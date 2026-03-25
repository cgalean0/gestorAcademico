import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot } from '@angular/router';
import { SessionService } from './session-service';

export const authGuard: CanActivateFn = (
  route : ActivatedRouteSnapshot, 
  state : RouterStateSnapshot
  ) => {
  const sessionService = inject(SessionService);
  return sessionService.isAuthenticated();
};
