import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { LoginDTO } from '../model/login-dto';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private host: string = environment.apiUrl;
  constructor(private http: HttpClient, private cookieService: CookieService) { }

  public login(loginDTO: LoginDTO): Observable<string> {
    return this.http.post<any>(`${this.host}/auth/login`, loginDTO, {responseType: 'text' as 'json'});

  }

  public logout(){
    this.cookieService.delete('authentication');
  }

}
