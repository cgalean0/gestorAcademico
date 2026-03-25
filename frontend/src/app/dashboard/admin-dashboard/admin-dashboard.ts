import { Component, ViewEncapsulation } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth-service';
import { Router } from '@angular/router';
import { SessionService } from '../../core/session-service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
  encapsulation: ViewEncapsulation.None,
})
export class AdminDashboard {

  constructor(private authService: AuthService, private sessionService: SessionService, private router: Router){};

  seccionActual: string = 'estudiantes';

  // ============================================
  // NAVEGACIÓN
  // ============================================

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

  // ============================================
  // BÚSQUEDA
  // ============================================

  buscarEstudiante(event: Event): void {
    const valor = (event.target as HTMLInputElement).value;
    // TODO: Implementar lógica de búsqueda de estudiantes
    console.log('Buscar estudiante:', valor);
  }

  buscarProfesor(event: Event): void {
    const valor = (event.target as HTMLInputElement).value;
    // TODO: Implementar lógica de búsqueda de profesores
    console.log('Buscar profesor:', valor);
  }

  // ============================================
  // FORMULARIOS
  // ============================================

  guardarConfiguraciones(event: Event): void {
    event.preventDefault();
    // TODO: Implementar lógica de guardar configuraciones
    console.log('Guardar configuraciones');
  }

  actualizarPerfil(event: Event): void {
    event.preventDefault();
    // TODO: Implementar lógica de actualizar perfil
    console.log('Actualizar perfil');
  }

  // ============================================
  // ACCIONES DE TABLAS
  // ============================================

  editarEstudiante(id: number): void {
    // TODO: Implementar edición de estudiante
    console.log('Editar estudiante:', id);
  }

  eliminarEstudiante(id: number): void {
    // TODO: Implementar eliminación de estudiante
    console.log('Eliminar estudiante:', id);
  }

  verEstudiante(id: number): void {
    // TODO: Implementar vista de detalle de estudiante
    console.log('Ver estudiante:', id);
  }

  editarProfesor(id: number): void {
    // TODO: Implementar edición de profesor
    console.log('Editar profesor:', id);
  }

  eliminarProfesor(id: number): void {
    // TODO: Implementar eliminación de profesor
    console.log('Eliminar profesor:', id);
  }

  verProfesor(id: number): void {
    // TODO: Implementar vista de detalle de profesor
    console.log('Ver profesor:', id);
  }
}
