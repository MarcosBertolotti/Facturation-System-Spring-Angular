import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'paginator-nav',
  templateUrl: './client-paginator.component.html',
  styleUrls: ['./client-paginator.component.css']
})
export class ClientPaginatorComponent implements OnInit, OnChanges {

  @Input()
  paginator: any;

  pages: number[];

  from: number;
  to: number;

  constructor() { }

  ngOnInit() 
  {
    this.initPaginator();
  }

  ngOnChanges(changes: SimpleChanges) // obtenemos el cambio del input del objeto paginator
  {
    let paginatorUpdated = changes['paginator'];

    if(paginatorUpdated.previousValue){
      this.initPaginator();
    }

  }

  private initPaginator(): void
  {
    this.from = Math.min(Math.max(1, this.paginator.number-1), this.paginator.totalPages-5); // Math min tiene dos calculos, el maximo entre 1 y nuestra pagina actual-1, y el segundo argumento, el total de paginas -5
    this.to = Math.max(Math.min(this.paginator.totalPages, this.paginator.number+3), 6);       // Math max tiene el minimo entre el total de paginas y nuestra pagina actual +3, segundo valor a comparar 6

    Math.max(1,2);

    if(this.paginator.totalPages > 5){
      this.pages = new Array(this.to - this.from + 1).fill(0).map((value, index) => index + this.from);
    }else {
      this.pages = new Array(this.paginator.totalPages).fill(0).map((value, index) => index + 1);
    }
  }

}
