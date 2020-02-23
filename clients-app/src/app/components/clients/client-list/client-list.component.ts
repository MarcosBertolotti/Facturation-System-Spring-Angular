import { Component, OnInit } from '@angular/core';
import { Client } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client.service';
import Swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { ModalService } from 'src/app/services/modal.service';
import { AuthService } from '../../users/auth.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  clients = new Array<Client>();
  paginator: any;
  clientSelected: Client;

  constructor(private clientService: ClientService, private modalSevice: ModalService , private authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit() {

    this.route.paramMap.subscribe( params => {

      let page: number = Number(params.get('page'));

      if(!page){
        page = 0;
      }

      this.clientService.getClients(page)
        .pipe(
          tap(response => {

            console.log('clientListComponent tap 3');
  
            (response.content as Array<Client>).forEach(client => {
              console.log(client.firstName);
            });
          })
        )
        .subscribe(
          response => {
            this.clients = response.content as Array<Client>
            this.paginator = response;
          },
          err => console.log(err)
        );
    });
    
    this.modalSevice.notifyUpload.subscribe(client => { /* para actualizar la foto automaticamente al subirla */
      this.clients = this.clients.map(c => {
        if(c.id == client.id){
          c.photo = client.photo;
        }
        return c;
      })
    });
  }

  deleteById(id: number): void
  {
    this.clientService.delete(id)
    .subscribe(
      response => {
        this.deleteByIdOfList(id);
        Swal.fire({
          title: response.message,
          icon: 'success'
        });
      },
      err => console.log(err)
    )
  }

  deleteByIdOfList(id: number): void
  {
    this.clients = this.clients.filter(c => c.id != id);
  }

  openModal(client: Client)
  {
    this.clientSelected = client;
    this.modalSevice.openModal();
  }


}
