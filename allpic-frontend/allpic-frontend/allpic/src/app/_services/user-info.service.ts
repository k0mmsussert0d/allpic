import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserInfoService {
  private host: string = environment.apiUrl;
  constructor(private http: HttpClient) { }
   
    
  public userInfo(username: string): Observable<User> {
    return this.http.get<any>(`${this.host}/user/${username}`)
        .pipe(map(resp =>  {
         console.log(resp);
         return resp;   
        }));
  }
  
}
