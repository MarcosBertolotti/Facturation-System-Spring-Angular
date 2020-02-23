import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      if(!this.authService.isAuthenticated()){
        this.router.navigate(['/login']);
        return false;
      }  

      let role = next.data['role'] as string;   // next data contiene un arreglo con los parametros.
      console.log(role);

      if(this.authService.hasRole(role)){
        return true;
      }

      Swal.fire({
        title: 'Access denied',
        text: 'Hello ' + this.authService.user.username + ', you haven\'t access to this resource!',
        icon: 'warning'
      })
      this.router.navigate(['/clients']);

    return false;
  }
  
}
