export interface IThreshold {
  id?: number;
  lowLimit?: number;
  highLimit?: number;
  openCloseIndicator?: number;
}

export class Threshold implements IThreshold {
  constructor(public id?: number, public lowLimit?: number, public highLimit?: number, public openCloseIndicator?: number) {}
}
