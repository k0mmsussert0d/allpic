import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  private baseUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) { }

  upload(file: File, name: string, isPublic: boolean): Observable<HttpEvent<any>> {
    let formData = new FormData();

    formData.append('file', file);
    console.log(file);
    const imgdetails = {
      title: name,
      isPublic: isPublic ?? false
    };
    formData.append('metadata', new Blob([JSON.stringify(imgdetails)], {type: 'application/json'}));
    //formData.append('metadata', JSON.stringify(imgdetails));

   console.log(formData.get('file'));
   console.log(formData.get('metadata'));


    return this.http.post<any>(`${this.baseUrl}/img/upload`, formData, {

    });

  }


}
