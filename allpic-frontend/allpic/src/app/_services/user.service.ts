import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class UserService {
    private host: string = 'http://localhost:8080';
    constructor(private http: HttpClient) { }

    register(user) {
        return this.http.post(`${this.host}/auth/register`, user);
    }

}