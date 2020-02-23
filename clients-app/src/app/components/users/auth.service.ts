import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url: string = 'http://localhost:8080/oauth/token';

  private credentials = btoa('angularapp' + ':' + '12345'); // encriptar en base64

  private _user: User;
  private _token: string;

  constructor(private http: HttpClient) { }

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + this.credentials
    })
  }

  login(user: User): Observable<any>
  {
    let params = new URLSearchParams(); // genera los parametros, ej: grant_type=password&username=marcos&password=12345

    params.set('grant_type', 'password');
    params.set('username', user.username);
    params.set('password', user.password);

    console.log(params.toString());

    return this.http.post<any>(this.url, params.toString(), this.httpOptions);
  }

  saveUser(accessToken: string): void
  {
    let payload = this.getPayload(accessToken);

    this._user = new User();

    this._user.firstName = payload.firstName;
    this._user.lastName = payload.lastName;
    this._user.email = payload.email;
    this._user.username = payload.user_name;
    this._user.roles = payload.authorities;

    sessionStorage.setItem("user", JSON.stringify(this._user))  // stringify hace lo contrario que el parse, convierte un objeto en un string
  }

  saveToken(accessToken: string): void
  {
    this._token = accessToken;
    sessionStorage.setItem("token", accessToken);
  }

  getPayload(accessToken: string): any
  {
    if(accessToken != null){
      return JSON.parse(atob(accessToken.split(".")[1]));  // atob decodifica en base64
    }
    return null;
  }

  isAuthenticated(): boolean
  {
    let payload = this.getPayload(this.token);

    if(payload != null && payload.user_name && payload.user_name.length > 0){
      return true;
    }
    return false;
  }

  hasRole(role: string): boolean{
    if(this.user.roles.includes(role)){
      return true;
    }
    return false;
  }

  logout(): void
  {
    this._token = null;
    this._user = null;
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
  }

  public get user(): User
  {
    if(this._user != null){
      return this._user;
    }else if (this._user == null && sessionStorage.getItem("user") != null){
      this._user = JSON.parse(sessionStorage.getItem("user")) as User;
      return this._user;
    }
    return new User();
  }

  public get token(): string
  {
    if(this._token != null){
      return this._token;
    }else if (this._token == null && sessionStorage.getItem("token") != null){
      this._token = sessionStorage.getItem("token");
      return this._token;
    }
    return null;
  }

}
