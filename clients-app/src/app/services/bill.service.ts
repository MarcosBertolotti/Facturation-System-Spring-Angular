import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bill } from '../models/bill';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class BillService {

  private url: string = 'http://localhost:8080/api/bills';
  private urlProducts: string = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) { }

  getBill(id: number): Observable<Bill>
  {
    return this.http.get<Bill>(this.url + '/' + id);
  }

  delete(id: number): Observable<Bill>
  {
    return this.http.delete<Bill>(this.url + '/' + id);
  }

  filterProducts(term: string): Observable<Array<Product>>
  {
    return this.http.get<Array<Product>>(this.urlProducts + '/filter/' + term);
  }

  create(bill: Bill): Observable<Bill>
  {
    return this.http.post<Bill>(this.url, bill);
  }
}
