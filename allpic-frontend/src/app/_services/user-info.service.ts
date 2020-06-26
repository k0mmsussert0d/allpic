import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { environment } from '../../environments/environment';
import { listLazyRoutes } from '@angular/compiler/src/aot/lazy_routes';

@Injectable({
  providedIn: 'root'
})
export class UserInfoService {
  private host: string = environment.apiUrl;
  constructor(private http: HttpClient) { }

  imageToShow: any;

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);


    if (image) {
      reader.readAsDataURL(image);
    }
    return this.imageToShow;
  }


  public userInfo(username: string): Observable<User> {
    return this.http.get<any>(`${this.host}/user/${username}`)
      .pipe(map(resp => {
        console.log(resp);
        return resp;
      }));
  }
  public imageInfo(username: string): Array<any> {
    let list: Array<any> = new Array<any>();
    this.http.get<any>(`${this.host}/user/images`)
      .subscribe((resp: Array<any>) => {
        console.log(resp);
        for (let index = 0; index < resp.length; index++) {
          const element = resp[index];
          this.http.get<any>(`${this.host}/img/i/${element.token}`, {responseType: 'blob' as 'json'}).subscribe(data => {
            list.push(this.createImageFromBlob(data));
            console.log(data);
          },e => console.log(e))
          


        }
      });
      console.log(list);

          /*resp.forEach(element => {
            this.http.get<any>(`${this.host}/img/i/${element.token}`).subscribe(data =>{
              list.push(this.createImageFromBlob(data));
              console.log(data);
            })
  
          });
  
        });*/
          
        return list;
  }


  public imageHome(): Array<any> {
    let list: Array<any> = new Array<any>();
    this.http.get<any>(`${this.host}/img/recent`)
      .subscribe((resp: Array<any>) => {
        console.log(resp);
        for (let index = 0; index < resp.length; index++) {
          const element = resp[index];
          this.http.get<any>(`${this.host}/img/i/${element.token}`, {responseType: 'blob' as 'json'}).subscribe(data => {
            list.push(this.createImageFromBlob(data));
            console.log(data);
          },e => console.log(e))
          


        }
      });
    return list;
  }


}
