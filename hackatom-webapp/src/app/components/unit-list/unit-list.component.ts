import { Component, OnInit } from '@angular/core';
import {GlobalService} from '../../services/global.service';
import {Unit} from '../domain/Unit';
import {HttpClient} from '@angular/common/http';
import {QueryModifier} from '../domain/QueryModifier';
import {PageFilter} from '../domain/PageFilter';

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
  modifier: QueryModifier;


  constructor(private service: GlobalService,
              public http: HttpClient) { }

  ngOnInit(): void {
    this.loadData();
  }

  async loadData() {
    const page = await this.service.getUnitList(this.modifier);
    this.units = page.payload;
    this.totalRecords = page.total;
  }

  async onLazyLoad(event: any) {
    const modifier = new QueryModifier();
    modifier.offset = event.first;
    modifier.limit = event.rows;
    modifier.sortField = event.sortField;
    modifier.sortOrder = event.sortOrder;
    Object.keys(event.filters).forEach(key => {
      modifier.filters.push(new PageFilter(key, event.filters[key].map(e => e.value), event.filters[key].map(e => e.matchMode)));
    });
    this.modifier = modifier;
    console.log(modifier, event);
    await this.loadData();
  }
}
