import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';
import { LoginDTO } from '../model/login-dto';



@Component({templateUrl: 'login.component.html'})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;


    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthService
    ) {}

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', [Validators.required, Validators.maxLength(8)]]
        });

    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onSubmit() {

        console.log('abc');
        let loginDTO = {
            username: this.loginForm.get('username').value,
            password: this.loginForm.get('password').value
        }
     /*   this.submitted = true;

        // reset alerts on submit
        this.error = null;
        this.success = null;
     

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;*/
        this.authenticationService.login(loginDTO)
            .subscribe(
                data => {
                    console.log(data);
                    localStorage.setItem('username',loginDTO.username);
                    console.log(loginDTO.username);
                    console.log(localStorage.getItem('username'));
                    this.router.navigate(['profile']);
                },
                error => {
                    
                    console.log(error);
                });
    }
}