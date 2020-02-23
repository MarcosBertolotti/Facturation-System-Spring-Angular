import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { AuthService } from '../auth.service';
import Swal from 'sweetalert2';
import { catchError } from 'rxjs/operators'
import { Router } from '@angular/router';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {   // manejamos la respuesta, cuando recibimos, validamos los codigos Http

  constructor(private authService: AuthService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      catchError(e => {

        if(e.status == 401){  
          if(this.authService.isAuthenticated()){ 
            this.authService.logout();
          }
          this.router.navigate(['/login']);
        }

        if(e.status == 403){  // permiso dependiente de los roles
          Swal.fire({
            title: 'Access denied',
            text: 'Hello ' + this.authService.user.username + ', you haven\'t access to this resource!',
            icon: 'warning'
          })
          this.router.navigate(['/clients']);
        }
        return throwError(e);
      })
    );

  }

}