import { IThreshold } from '@/shared/model/threshold.model';
import { IPatrolDevice } from '@/shared/model/patrol-device.model';

export interface IPowerDevice {
  id?: number;
  area?: number;
  space?: string;
  mainPart?: string;
  subPart?: string;
  name?: string;
  recognizeType?: number;
  recognizeContent?: number;
  site?: string;
  line?: string;
  source?: string;
  serialNumber?: string;
  installDate?: Date;
  status?: number;
  deviceModel?: string;
  createTime?: Date;
  updateTime?: Date;
  remark?: string;
  threshold?: IThreshold;
  patrolDevices?: IPatrolDevice[];
}

export class PowerDevice implements IPowerDevice {
  constructor(
    public id?: number,
    public area?: number,
    public space?: string,
    public mainPart?: string,
    public subPart?: string,
    public name?: string,
    public recognizeType?: number,
    public recognizeContent?: number,
    public site?: string,
    public line?: string,
    public source?: string,
    public serialNumber?: string,
    public installDate?: Date,
    public status?: number,
    public deviceModel?: string,
    public createTime?: Date,
    public updateTime?: Date,
    public remark?: string,
    public threshold?: IThreshold,
    public patrolDevices?: IPatrolDevice[]
  ) {}
}
