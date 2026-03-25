import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlerService {
  handleError(error: HttpErrorResponse): Observable<never> {
    let authError: AuthError;
    switch (error.status) {
      case 400:
        authError = {
          status: 400,
          message: 'Datos inválidos. Revisá las credenciales.'
        };
        break;
      case 401:
        authError = {
          status: 401,
          message: 'Credenciales incorrectas.'
        };
        break;
      case 403:
        authError = {
          status: 403,
          message: 'No tenés permisos para acceder.'
        };
        break;
      case 0:
        // Error de red: el servidor no responde
        authError = {
          status: 0,
          message: 'No se pudo conectar con el servidor.'
        };
        break;
      default:
        authError = {
          status: error.status,
          message: 'Ocurrió un error inesperado. Intentá más tarde.'
        };
    }
    return throwError(() => authError);
  }
}
