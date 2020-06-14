import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { Image } from '../model/image';
import { UserInfoService } from '../_services/user-info.service';
import { UserImagesService } from '../_services/user-images.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {
  public user: User;
  public images: Array<Image>;
  constructor(private userprofile: UserInfoService, private userimage: UserImagesService) {
    this.getImage();
    this.getInfo();

   }

  getInfo(): void{
    const token = localStorage.getItem('username');
    this.userprofile.userInfo(token).subscribe(data => {this.user = data});
  }
  getImage(): void{
    const token = localStorage.getItem('username');
    this.userimage.userImage(token).subscribe(data => {this.images = data});
  }

  ngOnInit(): void {
  }

}
