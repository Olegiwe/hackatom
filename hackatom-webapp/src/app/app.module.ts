import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ColumnFilterComponent} from './components/column-filter/column-filter.component';
import {CalendarModule} from 'primeng/calendar';
import {TableModule} from 'primeng/table';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { UnitListComponent } from './components/unit-list/unit-list.component';
import {TooltipModule} from 'primeng/tooltip';
import {GlobalService} from './services/global.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {MenuModule} from 'primeng/menu';
import {MenubarModule} from 'primeng/menubar';
import {ToolbarModule} from 'primeng/toolbar';
import { DefectListComponent } from './components/defect-list/defect-list.component';
import {SchemaComponent} from "./components/schema/schema.component";
import {DialogModule} from "primeng/dialog";
import { RegionAddComponent } from './components/region-add/region-add.component';

@NgModule({
  declarations: [
    AppComponent,
    ColumnFilterComponent,
    SchemaComponent,
    UnitListComponent,
    DefectListComponent,
    RegionAddComponent
  ],
    imports: [
        BrowserModule,
        CalendarModule,
        TableModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        TooltipModule,
        HttpClientModule,
        MenuModule,
        MenubarModule,
        ToolbarModule,
        DialogModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
