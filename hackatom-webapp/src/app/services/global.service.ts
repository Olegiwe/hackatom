import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {QueryModifier} from '../components/domain/QueryModifier';
import {Unit} from '../components/domain/Unit';
import {Defect} from '../components/domain/Defect';
import {Page} from '../components/domain/Page';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(private http: HttpClient) {
  }

  private restPath = `${environment.apiUrl}/api`;

  public getUnitList(modifier: QueryModifier = null): Promise<Page<Unit>> {
    return this.http.post<Page<Unit>>(`${this.restPath}/get-unit-list`, modifier).toPromise();
  }

  async getDefectList(modifier: QueryModifier) {
    return this.http.post<Page<Defect>>(`${this.restPath}/get-defect-list`, modifier).toPromise();
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

  async getSchemaFromPdf(id: number) {
    const params = new HttpParams();
    return this.http.get(`${this.restPath}/schemas/${id}`,{ observe: 'response', responseType: 'blob'}).toPromise();
  }

}
