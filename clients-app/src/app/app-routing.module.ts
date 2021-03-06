import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClientListComponent } from './components/clients/client-list/client-list.component';
import { ClientAddComponent } from './components/clients/client-add/client-add.component';
import { ClientUpdateComponent } from './components/clients/client-update/client-update.component';
import { ClientViewComponent } from './components/clients/client-view/client-view.component';
import { LoginComponent } from './components/users/login.component';
import { AuthGuard } from './components/users/guards/auth.guard';
import { RoleGuard } from './components/users/guards/role.guard';
import { BillViewComponent } from './components/bills/bill-view/bill-view.component';
import { BillAddComponent } from './components/bills/bill-add/bill-add.component';

const routes: Routes = [
    { path: 'clients', component: ClientListComponent },
    { path: 'clients/page/:page', component: ClientListComponent },
    { path: 'clients/add', component: ClientAddComponent, canActivate:[AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'} },
    { path: 'clients/update/:id', component: ClientUpdateComponent, canActivate:[AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'} },
    { path: 'clients/view/:id', component: ClientViewComponent },
    { path: 'login', component: LoginComponent }, 
    { path: 'bills/view/:id', component: BillViewComponent, canActivate:[AuthGuard, RoleGuard], data: {role: 'ROLE_USER'}},
    { path: 'bills/add/:clientId', component: BillAddComponent, canActivate:[AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'} },
    { path: '', redirectTo: '/clients', pathMatch: 'full' }
]

@NgModule({
    imports: [
        RouterModule.forRoot(
            routes,
            { enableTracing: true }
        )
    ],
    exports: [
        RouterModule
    ]
})

export class AppRoutingModule { }
