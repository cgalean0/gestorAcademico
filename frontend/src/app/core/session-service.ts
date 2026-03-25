import { Injectable } from '@angular/core';
import { LoginAccess } from './LoginAccess';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  private userSession = new BehaviorSubject<LoginAccess | null>(null);
  private userData = new BehaviorSubject<User | null>(null);

  saveSession(data: LoginAccess) {  
    this.userSession.next(data);
  }
  saveMe(data: User) {
    this.userData.next(data);
  }
  getSessionData() : LoginAccess | null {
    return this.userSession?.value;
  }
  isAuthenticated() : boolean {
    return this.userSession.value != null;
  }
  getUserData() : User | null {
    return this.userData!.value;
  }

  clearSession() : void {
    this.userData.next(null);
  }
}
