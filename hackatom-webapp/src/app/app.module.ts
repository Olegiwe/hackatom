import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ColumnFilterComponent } from './components/column-filter/column-filter.component';
import {CalendarModule} from 'primeng/calendar';
import {TableModule} from 'primeng/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SchemaComponent } from './components/schema/schema.component';
import { AppRoutingModule } from './components/app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    ColumnFilterComponent,
    SchemaComponent
  ],
  imports: [
    BrowserModule,
    CalendarModule,
    TableModule,
    BrowserAnimationsModule,
    AppRoutingModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
