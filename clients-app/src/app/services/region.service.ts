import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Region } from '../models/region';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from '../components/users/auth.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RegionService {

  private url: string = 'http://localhost:8080/api/clients/regions'; 

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) { }

  getRegions(): Observable<Region[]>
  {
    return this.http.get<Region[]>(this.url);
  }

}
