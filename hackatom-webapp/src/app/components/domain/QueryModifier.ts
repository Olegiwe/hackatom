import {PageFilter} from './PageFilter';


export class QueryModifier {

  limit: number;
  offset: number;
  sortField: string;
  sortOrder: number;
  filters: PageFilter[] = [];
}
