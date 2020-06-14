import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserInfoService {
  private host: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }
   
    
  public userInfo(username: string): Observable<User> {
    return this.http.get<any>(`${this.host}/user/${username}`)
        .pipe(map(resp =>  {
         console.log(resp);
         return resp;   
        }));
  }
  
}
