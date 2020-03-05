import { Component, OnInit } from '@angular/core';
import { Bill } from 'src/app/models/bill';
import { ClientService } from 'src/app/services/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import Swal from 'sweetalert2';
import {Observable} from 'rxjs';
import {map, flatMap} from 'rxjs/operators';
import { BillService } from 'src/app/services/bill.service';
import { Product } from 'src/app/models/product';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material';
import { ItemBill } from 'src/app/models/item-bill';

@Component({
  selector: 'app-bill-add',
  templateUrl: './bill-add.component.html',
  styleUrls: ['./bill-add.component.css']
})
export class BillAddComponent implements OnInit {

  title: string = 'New Bill';
  bill: Bill = new Bill();
  billForm: FormGroup;

  autocompleteControl = new FormControl();
  filteredProducts: Observable<Array<Product>>;

  private formSubmitAttempt: boolean;
  
  constructor(private clientService: ClientService, private route: ActivatedRoute, private billService: BillService, private router: Router) { }

  ngOnInit()
  {
    this.route.paramMap.subscribe(params => {
      let clientId = Number(params.get('clientId'));

      this.clientService.getClientById(clientId)
        .subscribe(
          response => this.bill.client = response,
          err => console.log(err)
        )
    });

    this.billForm = new FormGroup({
      'description': new FormControl(null,
        [Validators.required]),

      'observation': new FormControl(null,
        [])
      });

      this.filteredProducts = this.autocompleteControl.valueChanges // cuando cambia el valor
      .pipe(
        map(value => typeof value === 'string' ? value : value.name), // convertir el objeto producto al nombre del producto string
        flatMap(value => value ? this._filter(value) : new Array())   // filtra segun lo que escribamos, flatMap, aplanar los valores de un observable dentro de otro observable, si no existe enviamos un arreglo vacio simbolizando que no hay caracteres escritos
      );
  }

  private _filter(value: string): Observable<Array<Product>> {

    const filterValue = value.toLowerCase();

    return this.billService.filterProducts(filterValue);
  }

  showName(product?: Product): string | undefined // ? indicamos que es opcional, puede estar o no
  {
    return product ? product.name : undefined; 
  }

  selectProduct(event: MatAutocompleteSelectedEvent): void 
  {
    let product = event.option.value as Product; // obtenemos producto seleccionado en el campo

    if(this.existsItem(product.id)){
      this.incrementQuantity(product.id);
    } else {
      let newItem = new ItemBill();
      newItem.product = product;
  
      this.bill.items.push(newItem);
    }

    this.autocompleteControl.setValue(''); // limpiamos el autocomplete para volver a buscar otro producto y agregar otra linea al bill
    event.option.focus(); // resetamos el focus
    event.option.deselect();  // quitamos el producto que habiamos seleccionado
  }

  updateQuantity(id: number, event: any): void
  {
    let quantity: number = event.target.value as number;

    if(quantity <= 0){
      return this.deleteItemBill(id); // return para salir del metodo y no continue
    }

    this.bill.items = this.bill.items.map((item: ItemBill) => {
      if(id === item.product.id){
        item.quantity = quantity;
      }
      return item;
    });
  }

  existsItem(id: number): boolean 
  {
    let exists = false;

    this.bill.items.forEach((item: ItemBill) => {
      if(id === item.product.id){
        exists = true;
      }
    });
    return exists;
  }

  incrementQuantity(id: number): void
  {
    this.bill.items = this.bill.items.map((item: ItemBill) => {
      if(id === item.product.id){
        ++item.quantity;
      }
      return item;
    });
  }

  deleteItemBill(id: number): void
  {
    this.bill.items = this.bill.items.filter((item: ItemBill) => id != item.product.id);
  }

  create(): void
  { 
    this.formSubmitAttempt = true;

    if(this.bill.items.length == 0){
      this.autocompleteControl.setErrors({ 'invalid': true });
    }

    if(this.billForm.valid && this.bill.items.length > 0) {

      this.bill.description = this.description.value;
      this.bill.observation = this.observation.value;

      this.billService.create(this.bill)
      .subscribe(
        response => {
          Swal.fire({
            'title': this.title,
            'text': 'Bill ' + response.description + ' created successfully!',
            'icon': 'success'
          });
          this.router.navigate(['/bills/view', response.id]);
        },
        err => console.log(err)
      );
    }
    
  }

  get description() { return this.billForm.get('description') }
  get observation() { return this.billForm.get('observation') }

}
