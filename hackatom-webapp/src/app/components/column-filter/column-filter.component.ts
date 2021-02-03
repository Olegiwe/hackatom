import {Component, Input, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-column-filter',
  templateUrl: './column-filter.component.html',
  styleUrls: ['./column-filter.component.css']
})
export class ColumnFilterComponent implements OnInit {

  matchModeOptions = [{label: 'Начинается с', value: 'startsWith'}, {label: 'Содержит', value: 'contains'}, {label: 'Равно', value: 'equals'}];
  matchModeOptionsDate = [{label: 'C выбранной даты', value: 'after'}, {label: 'До выбранной даты', value: 'before'},
    {label: 'Выбранная дата', value: 'is'}];
  value;
  filter: any;
  @Input()
  type;
  @Input()
  fieldName;
  showMenu = true;


  @ViewChild('columnFilter')
  columnFilter;

  constructor() {
  }

  ngOnInit(): void {
  }

  apply() {
    if (this.filter != null) {
      this.filter(this.value);
    }
    this.columnFilter.overlayVisible = false;
  }

  setFilter(filter: any, value) {
    this.filter = filter;
    this.value = value;
    console.log(this.columnFilter);
  }

  clear() {
    if (this.filter != null) {
      this.filter(null);
    }
  }

  isTypeDate() {
    return this.type === 'date';
  }

  setOptions() {
    return this.isTypeDate() ? this.matchModeOptionsDate : this.matchModeOptions;
  }

}
