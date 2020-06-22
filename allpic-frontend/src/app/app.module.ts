import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { appRoutingModule } from './app-routing.module';
import { ErrorInterceptor } from './_helpers/error.interceptor'
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthService } from './auth/auth.service';
import { BackInterceptor } from './_helpers/back.interceptor';
import { UserprofileComponent } from './userprofile/userprofile.component';
import { AppFileUploadComponent } from './app-file-upload/app-file-upload.component';
import { ModsComponent } from './moderators/mods/mods.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UserprofileComponent,
    AppFileUploadComponent,
    ModsComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    appRoutingModule,
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: BackInterceptor, multi: true},
    {provide: AuthService}
  ],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
