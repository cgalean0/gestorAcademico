import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { LoginAccess } from '../core/LoginAccess';
import { environment } from '../../../environment';
import { ErrorHandlerService } from '../core/error-handler-service';
@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private linkUrl = environment.backUrl;

  constructor(private httpClient: HttpClient, private errorHandler: ErrorHandlerService) { }

  login(loginCredentials: LoginCredentials): Observable<LoginAccess> {
    return this.httpClient.post<LoginAccess>(this.linkUrl + "auth/login", loginCredentials, {withCredentials: true,
      headers: {
        'Authorization': 'Bearer '
      }
    })
      .pipe(
        catchError((error: HttpErrorResponse) => this.errorHandler.handleError(error))
      );
  }

  getMe(): Observable<User> {
    return this.httpClient.get<User>(this.linkUrl + "api/user/me")
    .pipe(
      catchError((error: HttpErrorResponse) => this.errorHandler.handleError(error))
    );
  }

  logout() : Observable<void> {
    return this.httpClient.post<void>(this.linkUrl + "auth/logout", {}, {withCredentials: true});
  }
}
