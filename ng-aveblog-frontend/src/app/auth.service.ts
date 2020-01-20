import { Injectable } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import { RegisterPayload } from './auth/register-payload';
import { Observable } from 'rxjs';
import { LoginPayload } from './auth/login-payload';
import { JwtAutResponse } from './auth/jwt-aut-response';
import { map } from 'rxjs/operators';
import {LocalStorageService} from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  login(loginPayload: LoginPayload) {
    this.httpClient.post(this.url + 'login', loginPayload);
  }
 private url = "http://localhost:8080/";

  constructor(private httpClient: HttpClient, private localStorageService: LocalStorageService) { }

  register(registerPayload: RegisterPayload): Observable<boolean> {
    return this.httpClient.post<JwtAutResponse>(this.url + "signup", registerPayload).pipe(map(data=> {
        this.localStorageService.store('authenticationToken', data.authenticationToken);
        this.localStorageService.store('username', data.username);
        return true;
    }));
  }


  isAuthenticated(): boolean {
    return this.localStorageService.retrieve('username') != null; 
  }

  logout() {
    this.localStorageService.clear('authenticationToken');
    this.localStorageService.clear('username');
  }
  
}


