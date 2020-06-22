import { Component, OnInit } from '@angular/core';
import { ModsService } from 'src/app/_services/mods.service';
import { User } from 'src/app/model/user';


@Component({
  selector: 'app-mods',
  templateUrl: './mods.component.html',
  styleUrls: ['./mods.component.css']
})
export class ModsComponent implements OnInit {
  public users: Array<User>;
  public username: string;
  constructor(private modsService: ModsService) { 
    //this.modInfo();

  }
  public modInfo(){
    this.modsService.modInfo().subscribe(data => this.users = data)
  }
  public addMod(username: string){
     this.modsService.addMod(username)
     this.modInfo();
  }
  public removeMod(userId: number){
    this.modsService.removeMod(userId)
    this.modInfo();
  }
  

  ngOnInit(): void {
  }

}