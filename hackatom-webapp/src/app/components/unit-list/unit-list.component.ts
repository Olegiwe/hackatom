import { Component, OnInit } from '@angular/core';
import {GlobalService} from '../../services/global.service';
import {Unit} from '../domain/Unit';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-unit-list',
  templateUrl: './unit-list.component.html',
  styleUrls: ['./unit-list.component.css'],
  providers: [GlobalService]
})
export class UnitListComponent implements OnInit {
  units: Unit[];
  selectedUnit: Unit;

  loading: false;
  totalRecords: number;
  columns = [
    {header: 'id', field: 'id', type: 'text', width: '6%'},
    {header: 'Наименование оборудования', field: 'name', type: 'text', width: '6%'},
    {header: 'Описание', field: 'description', type: 'text', width: '6%'},
    {header: 'Станционное обозначение', field: 'stationDesignation', type: 'text', width: '6%'},
    {header: 'Цеховое обозначение', field: 'workshopDesignation', type: 'text', width: '6%'},
    {header: 'Тип', field: 'type', type: 'text', width: '6%'},
    {header: 'Система', field: 'system.name', type: 'text', width: '6%'},
  ];


  constructor(private service: GlobalService,
              public http: HttpClient) { }

  ngOnInit(): void {
    this.loadData();
  }

  async loadData() {
    this.units = await this.service.getUnitList();
  }

  onLazyLoad(event: any) {

  }
}
