import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { MatDatepickerModule, MatNativeDateModule } from '@angular/material';
import { MatMomentDateModule } from '@angular/material-moment-adapter';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { ClientListComponent } from './components/clients/client-list/client-list.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ClientAddComponent } from './components/clients/client-add/client-add.component';
import { ClientUpdateComponent } from './components/clients/client-update/client-update.component';
import { ClientViewComponent } from './components/clients/client-view/client-view.component';
import { registerLocaleData } from '@angular/common';
import localeAR from '@angular/common/locales/es-AR';
import { ClientService } from './services/client.service';
import { ClientPaginatorComponent } from './components/clients/client-paginator/client-paginator.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/users/login.component';
import { TokenInterceptor } from './components/users/interceptors/token.interceptor';
import { AuthInterceptor } from './components/users/interceptors/auth.interceptor';


registerLocaleData(localeAR, 'es-AR')

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ClientListComponent,
    ClientAddComponent,
    ClientUpdateComponent,
    ClientViewComponent,
    ClientPaginatorComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDatepickerModule, 
    MatMomentDateModule
  ],
  providers: [
    ClientService,
    { provide: LOCALE_ID, useValue: 'es-AR'},
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
