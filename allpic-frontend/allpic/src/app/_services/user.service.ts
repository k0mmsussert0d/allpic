import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class UserService {
    private host: string = environment.apiUrl;
    constructor(private http: HttpClient) { }

    register(user) {
        return this.http.post(`${this.host}/auth/register`, user);
    }

}