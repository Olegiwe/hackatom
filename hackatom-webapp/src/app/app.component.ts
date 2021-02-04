import {Component} from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'hackatom-webapp';
  items: MenuItem[] = [
    {
      label: 'Узлы',
      routerLink: 'unit-list'
    },
    {
      label: 'Дефекты',
      routerLink: 'defect-list'
    },
    {
      label: 'Схема',
      routerLink: 'schema'
    }
  ];
}
