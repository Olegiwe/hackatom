import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UnitListComponent} from './components/unit-list/unit-list.component';
import {DefectListComponent} from './components/defect-list/defect-list.component';
import {SchemaComponent} from './components/schema/schema.component';

const routes: Routes = [
  {path: 'unit-list', component: UnitListComponent},
  {path: 'defect-list', component: DefectListComponent},
  {path: 'schema', component: SchemaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
