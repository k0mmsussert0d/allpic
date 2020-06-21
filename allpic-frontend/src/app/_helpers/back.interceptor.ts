import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class BackInterceptor implements HttpInterceptor {

  constructor() {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    if(request.headers.has('ignore')){
      
      request = request.clone({
        headers: new HttpHeaders({
          'Content-Type': undefined,
          'Access-Control-Allow-Origin': '*'
  
        }),
          withCredentials: true
      });
      console.log(request.body.get('file'));
    }else{
      // request = request.clone({
      //   headers: new HttpHeaders({
      //     'Content-Type':  'application/json',
      //     'Access-Control-Allow-Origin': '*'
  
      //   }),
      //     withCredentials: true
      // });
    }
    

    return next.handle(request);
}
}
