import { Component, OnInit } from '@angular/core';
import { Client } from 'src/app/models/client';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { ClientService } from 'src/app/services/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { RegionService } from 'src/app/services/region.service';
import { Region } from 'src/app/models/region';

@Component({
  selector: 'app-client-update',
  templateUrl: './client-update.component.html',
  styleUrls: ['./client-update.component.css']
})
export class ClientUpdateComponent implements OnInit {

  title: string = "Update Client";
  client: Client = new Client;
  clientForm: FormGroup;
  regions = new Array<Region>();

  private errors = new Array<String>();

  constructor(private clientService: ClientService, private regionService: RegionService,
    private route: ActivatedRoute, private router: Router) { }

  ngOnInit()
  {
    let clientId = Number(this.route.snapshot.paramMap.get('id'));

    this.regionService.getRegions()
    .subscribe(
      response => { 
        this.regions = response;
      },
      err => console.log(err)
    );

    this.clientService.getClientById(clientId)
    .subscribe(
      response => {
        this.client = response;
        this.setValuesStudentForm();
      },
      err => console.log(err)
    );
    
    this.clientForm = new FormGroup({
      'firstName': new FormControl(this.client.firstName,
        [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),

      'lastName': new FormControl(this.client.lastName,
        [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),

      'email': new FormControl(this.client.email,
        [Validators.required, Validators.email],
        []),
      'region': new FormControl(this.client.region,
        [Validators.required])
    });
  
  }

  onSubmit()
  {
    this.client.bills = null; // cuando editamos un cliente, no debemos actualizar sus facturas

    this.client.firstName = this.firstName.value;
    this.client.lastName = this.lastName.value;
    this.client.email = this.email.value;
    this.client.region = this.region.value;

    this.clientService.update(this.client)
    .subscribe(
      response => {
        Swal.fire({
          title: response.message,
          icon: 'success'
        });
      },
      err => {
        this.errors = err.error.errors as Array<string>;
        console.error('Status Code error: ' + err.status);
        console.error(err.error.errors);
      }
    )
  }

  private setValuesStudentForm(): void
  {
    this.clientForm.setValue({
      'firstName': this.client.firstName,
      'lastName': this.client.lastName,
      'email': this.client.email,
      'region': this.client.region
    })
  }

  compareRegion(o1: Region, o2: Region): boolean
  {
    return o1 == null || o2 == null ? false : o1.id === o2.id
  }

  get firstName() { return this.clientForm.get('firstName') }
  get lastName() { return this.clientForm.get('lastName') }
  get email() { return this.clientForm.get('email') }
  get region() { return this.clientForm.get('region') }

}
