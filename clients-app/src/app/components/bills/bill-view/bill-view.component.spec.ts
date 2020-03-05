import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillViewComponent } from './bill-view.component';

describe('BillViewComponent', () => {
  let component: BillViewComponent;
  let fixture: ComponentFixture<BillViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
