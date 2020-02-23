import { Component, OnInit, Input } from '@angular/core';
import { Client } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client.service';
import Swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from 'src/app/services/modal.service';
import { AuthService } from '../../users/auth.service';

@Component({
  selector: 'app-client-view',
  templateUrl: './client-view.component.html',
  styleUrls: ['./client-view.component.css']
})
export class ClientViewComponent implements OnInit {

@Input()
client: Client;
title: string = "Client Details";
private photoSelected: File;
progress: number = 0;

  constructor(private clientService: ClientService, private modalService: ModalService, private authService: AuthService) { }

  ngOnInit() {
  }

  selectPhoto(event): void
  {
    this.photoSelected = event.target.files[0];
    this.progress = 0;

    console.log(this.photoSelected);

    if(this.photoSelected.type.indexOf('image') < 0) {  // busca en la cadena si hay una coincidencia con image, si la encuentra retorna la posicion, si no -1
      Swal.fire({
        title: "Error select image",
        text: "File must be image type!",
        icon: "error"
      });
      this.photoSelected = null;
    }
  }

  uploadPhoto()
  {
    if(!this.photoSelected){
      Swal.fire({
        title: "Error Upload",
        text: "You must select a photo!",
        icon: "error"
      });
    }else {

      this.clientService.uploadPhoto(this.photoSelected, this.client.id)
      .subscribe(event => {

        if(event.type === HttpEventType.UploadProgress){
          this.progress = Math.round((event.loaded/event.total)*100);

        }else if(event.type === HttpEventType.Response){
          
          let response: any = event.body;
          this.client = response.client as Client;

          this.modalService.notifyUpload.emit(this.client);

          Swal.fire({
            title: "Photo has been uploaded completely!",
            text: response.message,
            icon: "success"
          });
        }
      });
    }
  }

  closeModal()
  {
    this.modalService.closeModal();
    this.photoSelected = null;
    this.progress = 0;
  }

}
