import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PowerDeviceService from '../power-device/power-device.service';
import { IPowerDevice } from '@/shared/model/power-device.model';

import AlertService from '@/shared/alert/alert.service';
import { IPatrolDevice, PatrolDevice } from '@/shared/model/patrol-device.model';
import PatrolDeviceService from './patrol-device.service';

const validations: any = {
  patrolDevice: {
    name: {
      required,
    },
    source: {},
    serialNumber: {},
    installDate: {},
    status: {
      required,
      numeric,
    },
    deviceModel: {},
    createTime: {
      required,
    },
    updateTime: {
      required,
    },
    remark: {},
  },
};

@Component({
  validations,
})
export default class PatrolDeviceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('patrolDeviceService') private patrolDeviceService: () => PatrolDeviceService;
  public patrolDevice: IPatrolDevice = new PatrolDevice();

  @Inject('powerDeviceService') private powerDeviceService: () => PowerDeviceService;

  public powerDevices: IPowerDevice[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patrolDeviceId) {
        vm.retrievePatrolDevice(to.params.patrolDeviceId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    this.patrolDevice.powerDevices = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.patrolDevice.id) {
      this.patrolDeviceService()
        .update(this.patrolDevice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('deviceApp.patrolDevice.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.patrolDeviceService()
        .create(this.patrolDevice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('deviceApp.patrolDevice.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePatrolDevice(patrolDeviceId): void {
    this.patrolDeviceService()
      .find(patrolDeviceId)
      .then(res => {
        this.patrolDevice = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.powerDeviceService()
      .retrieve()
      .then(res => {
        this.powerDevices = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
