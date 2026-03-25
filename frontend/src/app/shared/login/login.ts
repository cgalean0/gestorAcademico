import { Component, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../auth/auth-service';
import { SessionService } from '../../core/session-service';
import { Router } from '@angular/router';

const ROLE_ROUTES: Record<string, string> = {
  "ADMIN": "/admin",
  "PROFESSOR": "/professor",
  "STUDENT": "/student"
};

@Component({
  selector: 'app-auth',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
  encapsulation: ViewEncapsulation.None,
})
export class Login {

  constructor(private authService: AuthService,
              private sessionService: SessionService,
              private router: Router) { }

  loginForm = new FormGroup({
    userName: new FormControl<string>('', [
      Validators.required,
      Validators.maxLength(30),
      Validators.pattern("^[a-zA-Z0-9]+$"),
      Validators.minLength(4)
    ]),
    passwd: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(8)
    ])
  })

  get userName() {
    return this.loginForm.get('userName');
  }

  get passwd() {
    return this.loginForm.get('passwd');
  }

  errorMessage = '';
  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    const loginCredentials: LoginCredentials = {
      userName: this.loginForm.get('userName')?.value ?? '',
      password: this.loginForm.get('passwd')?.value ?? ''
    }
    this.authService.login(loginCredentials).subscribe(
      {
        next: (access) => {
          // Login exitoso
          this.sessionService.saveSession(access);
          // redirigir segun rol
          const route = ROLE_ROUTES[access.role] ?? "/unauthorized"
          return this.router.navigate([route]);
        },
        error: (err: AuthError) => {
          this.errorMessage = err.message;
        }
      }
    )
  }
}
