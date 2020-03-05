import { Component, OnInit } from '@angular/core';
import { ClientService } from 'src/app/services/client.service';
import { RegionService } from 'src/app/services/region.service';
import { Client } from 'src/app/models/client';
import { Region } from 'src/app/models/region';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { CustomValidator } from 'src/app/models/custom-validator';

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {

  title: string = "Create Client";
  client: Client = new Client;
  clientForm: FormGroup;
  regions = new Array<Region>();

  private errors = new Array<String>();

  constructor(private clientService: ClientService, private regionService: RegionService) { }

  ngOnInit()
  {
    this.regionService.getRegions()
    .subscribe(
      response => { 
        this.regions = response;
        this.region.setValue(undefined);
      },
      error => console.log(error)
    );

    this.clientForm = new FormGroup({
      'firstName': new FormControl(null,
        [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),

      'lastName': new FormControl(null,
        [Validators.required, Validators.minLength(2), Validators.maxLength(20)]),

      'email': new FormControl(null,
        [Validators.required, Validators.email],
        [CustomValidator.emailExistsValidator(this.clientService)]),
      'createAt': new FormControl(null,
      [Validators.required]),
      'region': new FormControl(undefined,
        [Validators.required]),
    })
  }

  compareRegion(o1: Region, o2: Region): boolean
  {
    return o1 === undefined && o2 === undefined ? true : false;
  }

  public onSubmit(): void
  {
    this.client.firstName = this.firstName.value;
    this.client.lastName = this.firstName.value;
    this.client.email = this.email.value;
    this.client.createAt = this.createAt.value;
    this.client.region = this.region.value;

    console.log(this.client);

    this.clientService.add(this.client)
    .subscribe(
      response => {
        Swal.fire({
          title: response.message,
          icon: 'success'
        });
      },
      err => {
        this.errors = err.error.errors;
      }
    );
  }

  get firstName() { return this.clientForm.get('firstName') }
  get lastName() { return this.clientForm.get('lastName') }
  get email() { return this.clientForm.get('email') }
  get createAt() { return this.clientForm.get('createAt') }
  get region() { return this.clientForm.get('region') }

}
