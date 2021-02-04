import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {QueryModifier} from '../components/domain/QueryModifier';
import {Unit} from '../components/domain/Unit';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(private http: HttpClient) { }

  private restPath = `${environment.apiUrl}/api`;

  public getUnitList(modifier: QueryModifier = null): Promise<Unit[]> {
    const params = this.createParams({modifier});
    return this.http.post<Unit[]>(`${this.restPath}/get-unit-list`, {params}).toPromise();
  }


  createParams(data) {
    let params = new HttpParams();
    Object.keys(data).forEach(e => {
      if (data[e] != null) {
        params = params.set(e, data[e]);
      }
    });
    return params;
  }
}
