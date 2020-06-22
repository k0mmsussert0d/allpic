import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ModsService {
  
  private baseUrl: string = environment.apiUrl;
  host: string = environment.apiUrl;
  
  constructor(private http: HttpClient) { }
  public modInfo(): Observable<Array<User>> {
    return this.http.get<any>(`${this.host}/mod/`)
      .pipe(map(resp => {
        console.log(resp);
        return resp;
      }));
  }
  public addMod(username: string): Observable<any> {
    return this.http.put<any>(`${this.host}/mod/${username}`,{})
      .pipe(map(resp => {
        console.log(resp);
        return resp;
      }));
  }
  public removeMod(userId: number): Observable<void> {
    return this.http.delete<any>(`${this.host}/mod/${userId}`)
      .pipe(map(resp => {
        console.log(resp);
        return resp;
      }));
  }
}  