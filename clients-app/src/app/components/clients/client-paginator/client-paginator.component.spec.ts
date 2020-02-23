import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientPaginatorComponent } from './client-paginator.component';

describe('ClientPaginatorComponent', () => {
  let component: ClientPaginatorComponent;
  let fixture: ComponentFixture<ClientPaginatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClientPaginatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientPaginatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
