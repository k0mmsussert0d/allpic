import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../model/user';
import { environment } from '../../environments/environment';

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
     this.http.get<any>(`${this.host}/user/${username}/images`)
      .subscribe(resp => {
        console.log(resp);
        resp.array.forEach(element => {
          this.http.get<any>(`${this.host}/img/i/${element.path}`).subscribe(data =>
            list.push(this.createImageFromBlob(data))

          )
        });

      });
      console.log(list);
      return list;
  }

}
