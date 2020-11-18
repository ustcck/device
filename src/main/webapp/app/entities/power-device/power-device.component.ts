import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPowerDevice } from '@/shared/model/power-device.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PowerDeviceService from './power-device.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PowerDevice extends mixins(AlertMixin) {
  @Inject('powerDeviceService') private powerDeviceService: () => PowerDeviceService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public powerDevices: IPowerDevice[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPowerDevices();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllPowerDevices();
  }

  public retrieveAllPowerDevices(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.powerDeviceService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.powerDevices = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPowerDevice): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePowerDevice(): void {
    this.powerDeviceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('deviceApp.powerDevice.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPowerDevices();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllPowerDevices();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
