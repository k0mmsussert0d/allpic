import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/internal/operators';
import { Image } from '../model/image';

@Injectable({
  providedIn: 'root'
})
export class UserImagesService {
  private host: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }
   
    
  public userImage(username: string): Observable<Array<Image>> {
    return this.http.get<any>(`${this.host}/user/${username}/images`)
        .pipe(map(resp =>  {
         console.log(resp);
         return resp;   
        }));
  }
}
