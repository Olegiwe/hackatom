import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UnitListComponent} from './components/unit-list/unit-list.component';

const routes: Routes = [{path: 'unit-list', component: UnitListComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
