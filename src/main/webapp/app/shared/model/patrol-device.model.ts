import { IPowerDevice } from '@/shared/model/power-device.model';

export interface IPatrolDevice {
  id?: number;
  name?: string;
  source?: string;
  serialNumber?: string;
  installDate?: Date;
  status?: number;
  deviceModel?: string;
  createTime?: Date;
  updateTime?: Date;
  remark?: string;
  powerDevices?: IPowerDevice[];
}

export class PatrolDevice implements IPatrolDevice {
  constructor(
    public id?: number,
    public name?: string,
    public source?: string,
    public serialNumber?: string,
    public installDate?: Date,
    public status?: number,
    public deviceModel?: string,
    public createTime?: Date,
    public updateTime?: Date,
    public remark?: string,
    public powerDevices?: IPowerDevice[]
  ) {}
}
