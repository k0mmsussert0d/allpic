import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { UserService, } from '../_services';
import { AuthService } from '../auth/auth.service';


@Component({ templateUrl: 'register.component.html' })
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    error: string;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthService,
        private userService: UserService
    ) {}

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            username: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(8)]]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }


    onSubmit() {
        let registerDTO = {
            username: this.registerForm.get('username').value,
            email: this.registerForm.get('email').value,
            password: this.registerForm.get('password').value
        }
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }

        this.loading = true;
        this.userService.register(registerDTO)
            .pipe(first())
            .subscribe(
                data => {
                    this.router.navigate(['/login'], { queryParams: { registered: true }});
                },
                error => {
                    this.error = error;
                    this.loading = false;
                });
    }
}