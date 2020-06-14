import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { LoginDTO } from '../model/login-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private host: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

  public login(loginDTO: LoginDTO): Observable<string> {
    return this.http.post<any>(`${this.host}/auth/login`, loginDTO, {responseType: 'text' as 'json'});
       
  }
  

}
