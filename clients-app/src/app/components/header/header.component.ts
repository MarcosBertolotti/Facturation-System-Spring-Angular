import { Component, OnInit } from '@angular/core';
import { AuthService } from '../users/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

title: string = 'Spring Boot';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  logout(): void
  {
    this.authService.logout();

    Swal.fire({
      title: 'Logout',
      text: 'You have successfully signed out!',
      icon: 'success'
    })

    this.router.navigate(['/clients']);
  }

}
