import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import Swal from 'sweetalert2';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  title: string = "Please Sign In!";
  user: User = new User();
  userForm: FormGroup;

  constructor(private authService: AuthService, private router: Router) { }
  
  ngOnInit()
  {
    if(this.authService.isAuthenticated()){
      Swal.fire({
        title: 'Login',
        text: 'Hello ' + this.authService.user.username + ', you are already authenticated!',
        icon: 'info'
      })
      this.router.navigate(['/clients']);
    }

    this.userForm = new FormGroup({
      'username': new FormControl(null,
        [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),

      'password': new FormControl(null,
        [Validators.required])
    });
  }

  login(): void
  {
    this.user.username = this.username.value;
    this.user.password = this.password.value;

    console.log(this.user);

    this.authService.login(this.user)
    .subscribe(
      response => {
        console.log(response);

        this.authService.saveUser(response.access_token);
        this.authService.saveToken(response.access_token);
        let user = this.authService.user;

        this.router.navigate(['/clients']);

        Swal.fire({
          title: 'Login',
          text: 'Hello ' + user.username + ', you have successfully logged in!',
          icon: 'success'
        })
      },
      err => {
        if(err.status == 400){
          Swal.fire({
            title: "Error Login",
            text: "Incorrect username or password!",
            icon: "error"
          })
        }
      }
    );

  }
  
  get username() { return this.userForm.get('username') }
  get password() { return this.userForm.get('password') }

}
