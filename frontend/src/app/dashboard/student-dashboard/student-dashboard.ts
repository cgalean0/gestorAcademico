import { Component, ViewEncapsulation } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth-service';
import { SessionService } from '../../core/session-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-dashboard',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './student-dashboard.html',
  styleUrl: './student-dashboard.css',
  encapsulation: ViewEncapsulation.None,
})
export class StudentDashboard {
  constructor(private authService: AuthService, private sessionService: SessionService, private router: Router){};

  seccionActual: string = 'mis-cursos';

  navegarA(seccion: string): void {
    this.seccionActual = seccion;
  }


  // ============================================
  // CERRAR SESIÓN
  // ============================================

  onLogout(): void {
    this.authService.logout().subscribe({
      next: () => {
        // Limpiar datos del service
        this.sessionService.clearSession();
        this.router.navigate(['/login']);
      },
      error: () => {
        this.router.navigate(['/login']);
      }
    })
  }

  

  actualizarPerfil(event: Event): void {
    event.preventDefault();
    // TODO: Implementar lógica
    console.log('Actualizar perfil');
  }
}
