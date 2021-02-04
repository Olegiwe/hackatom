import {System} from './System';
import {Defect} from './Defect';

export class Unit {

  id: number;
  name: string;
  description: string;
  stationDesignation: string;
  workshopDesignation: string;
  type: string;
  system: System;
  defects: Defect[];


}
