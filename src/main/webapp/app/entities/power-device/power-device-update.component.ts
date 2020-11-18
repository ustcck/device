import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ThresholdService from '../threshold/threshold.service';
import { IThreshold } from '@/shared/model/threshold.model';

import PatrolDeviceService from '../patrol-device/patrol-device.service';
import { IPatrolDevice } from '@/shared/model/patrol-device.model';

import AlertService from '@/shared/alert/alert.service';
import { IPowerDevice, PowerDevice } from '@/shared/model/power-device.model';
import PowerDeviceService from './power-device.service';

const validations: any = {
  powerDevice: {
    area: {},
    space: {},
    mainPart: {},
    subPart: {},
    name: {
      required,
    },
    recognizeType: {
      required,
      numeric,
    },
    recognizeContent: {
      required,
      numeric,
    },
    site: {},
    line: {},
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
export default class PowerDeviceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('powerDeviceService') private powerDeviceService: () => PowerDeviceService;
  public powerDevice: IPowerDevice = new PowerDevice();

  @Inject('thresholdService') private thresholdService: () => ThresholdService;

  public thresholds: IThreshold[] = [];

  @Inject('patrolDeviceService') private patrolDeviceService: () => PatrolDeviceService;

  public patrolDevices: IPatrolDevice[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.powerDeviceId) {
        vm.retrievePowerDevice(to.params.powerDeviceId);
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
  }

  public save(): void {
    this.isSaving = true;
    if (this.powerDevice.id) {
      this.powerDeviceService()
        .update(this.powerDevice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('deviceApp.powerDevice.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.powerDeviceService()
        .create(this.powerDevice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('deviceApp.powerDevice.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePowerDevice(powerDeviceId): void {
    this.powerDeviceService()
      .find(powerDeviceId)
      .then(res => {
        this.powerDevice = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.thresholdService()
      .retrieve()
      .then(res => {
        this.thresholds = res.data;
      });
    this.patrolDeviceService()
      .retrieve()
      .then(res => {
        this.patrolDevices = res.data;
      });
  }
}
