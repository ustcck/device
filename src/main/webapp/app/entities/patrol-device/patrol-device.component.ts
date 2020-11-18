import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPatrolDevice } from '@/shared/model/patrol-device.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PatrolDeviceService from './patrol-device.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PatrolDevice extends mixins(AlertMixin) {
  @Inject('patrolDeviceService') private patrolDeviceService: () => PatrolDeviceService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public patrolDevices: IPatrolDevice[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPatrolDevices();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllPatrolDevices();
  }

  public retrieveAllPatrolDevices(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.patrolDeviceService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.patrolDevices = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPatrolDevice): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePatrolDevice(): void {
    this.patrolDeviceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('deviceApp.patrolDevice.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPatrolDevices();
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
    this.retrieveAllPatrolDevices();
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
