import { Component, OnInit } from '@angular/core';
import { UserInfoService } from '../_services/user-info.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public images: Array<any>;
  constructor(private imageHomeService: UserInfoService) { 
    this.getImage();
  }
  getImage(): void{
    this.images = this.imageHomeService.imageHome();
  }
  ngOnInit(): void {
  }

}
