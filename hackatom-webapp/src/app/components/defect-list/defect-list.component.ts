import { Component, OnInit } from '@angular/core';
import {Defect} from '../domain/Defect';
import {GlobalService} from '../../services/global.service';
import {QueryModifier} from '../domain/QueryModifier';
import {PageFilter} from '../domain/PageFilter';

@Component({
  selector: 'app-defect-list',
  templateUrl: './defect-list.component.html',
  styleUrls: ['./defect-list.component.css']
})
export class DefectListComponent implements OnInit {
  defects: Defect[];
  selectedDefect: Defect;
  loading: false;
  totalRecords: number;
  modifier: QueryModifier;
  columns = [
    {header: 'id', field: 'id', type: 'text', width: '100px'},
    {header: 'Номер дефекта', field: 'number', type: 'text', width: '200px'},
    {header: 'Дата', field: 'date', type: 'date', width: '150px'},
    {header: 'Статус', field: 'status', type: 'text', width: '150px'},
    {header: 'Зарегистрировал', field: 'regPerson', type: 'text', width: '250px'},
    {header: 'Ответственный', field: 'responsible', type: 'text', width: '250px'},
    {header: 'Наименование оборудования', field: 'unit.name', type: 'text', width: '300px'},
    {header: 'Описание', field: 'description', type: 'text', width: '300px'},
    {header: 'Выполненная работа', field: 'result', type: 'text', width: '300px'},
  ];

  constructor(private globalService: GlobalService) { }

  ngOnInit(): void {
  }

  async loadData() {
    const page = await this.globalService.getDefectList(this.modifier);
    console.log(page);
    this.defects = page.payload;
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
