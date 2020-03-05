import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { map, catchError, tap } from 'rxjs/operators'
import { Client } from 'src/app/models/client';
import { Router } from '@angular/router';
import { formatDate, DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private url: string = 'http://localhost:8080/api/clients'; 

  constructor(private http: HttpClient, private router: Router) { }

  /*
  private httpHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  })
*/

  getClients(page: number): Observable<any> 
  {
    return this.http.get(this.url + '/page/' + + page)
    .pipe(
      tap((response: any) => {

        console.log("ClientService Tap: 1");
        (response.content as Array<Client>).forEach(client => {
          console.log(client.firstName);
        });
      }),
      map((response: any) => {

        (response.content as Array<Client>).map(client => {
          client.firstName = client.firstName.toUpperCase();

          // let datePipe = new DatePipe('es-AR');
          // client.createAt = datePipe.transform(client.createAt, 'EEEE dd, MMMM, yyyy'); // 'EEEE dd-MM-yyyy', 'fullDate'
          // client.createAt = formatDate(client.createAt, 'dd-MM-yyyy', 'en-US');
          return client;
        });
        return response;
      }),
      tap(response => {
        
        console.log("ClientService Tap: 2");
        (response.content as Array<Client>).forEach(client => {
          console.log(client.firstName);
        })
      })
    );
  }


  getClientById(id): Observable<any>
  {
    return this.http.get(this.url + '/' + id)
    .pipe(
      catchError(e => {
        if(e.status != 401 && e.error.message){
          this.router.navigate(['/list']);
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }


  getUserByEmail(email: string): Observable<any>
  {
    return this.http.get(this.url + '/indentities?email=' + email)
    .pipe(
      catchError(e => {
        if(e.status != 401 && e.status != 409 && e.error.message){
          this.router.navigate['/list'];
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }



  add(client: Client): Observable<any>
  {
    return this.http.post(this.url, client)
    .pipe(
      catchError(e => {
        if(e.status == 400){
          return throwError(e);
        }
        if(e.error.message){
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }


  update(client: Client): Observable<any>
  {
    return this.http.put(this.url + '/' + client.id, client)
    .pipe(
      catchError(e => {
        if(e.status == 400){
          return throwError(e);
        }
        if(e.error.message){
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }


  delete(id): Observable<any>
  {
    return this.http.delete(this.url + '/' + id)
    .pipe(
      catchError(e => {
        if(e.error.message){
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }


  uploadPhoto(file: File, id): Observable<HttpEvent<{}>>
  {
    let formData = new FormData(); // nativa de js, equivalente a multipart/form-data del form
    formData.append("file", file);
    formData.append("id", id);

    const req = new HttpRequest('POST', this.url + "/upload", formData, {
      reportProgress: true
    });

    return this.http.request(req);
  }
  

} 
