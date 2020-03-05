import { Component, OnInit } from '@angular/core';
import { BillService } from 'src/app/services/bill.service';
import { Bill } from 'src/app/models/bill';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bill-view',
  templateUrl: './bill-view.component.html',
  styleUrls: ['./bill-view.component.css']
})
export class BillViewComponent implements OnInit {

  bill: Bill;
  title: string = 'Bill';

  constructor(private billService: BillService, private route: ActivatedRoute) { }

  ngOnInit()
  {
    this.route.paramMap.subscribe(params => {

      let id = Number(params.get('id'));

      this.billService.getBill(id)
        .subscribe(
          response => this.bill = response,
          err => console.log(err)
        )
    })
  }

}
