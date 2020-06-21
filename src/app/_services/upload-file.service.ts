import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpEvent, HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  upload(file: File, name: string, isPublic: boolean): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    

    formData.append('file', file);
    console.log(file);
    const imgdetails = {
      title: name,
      isPublic: isPublic 
    };
    formData.append('metadata', new Blob([JSON.stringify(imgdetails)], {type: 'application/json'}));
    //formData.append('metadata', JSON.stringify(imgdetails));

    
   console.log(formData.get('file'));
   console.log(formData.get('metadata'));

    let headers = new HttpHeaders({
      'ignore': 'ignore'
    })
    const req = new HttpRequest('POST', `${this.baseUrl}/img/upload`, formData, {
      headers: headers,
      reportProgress: true,
      
    });

    return this.http.request(req);
    
  }

  
}
