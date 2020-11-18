import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPowerDevice } from '@/shared/model/power-device.model';
import PowerDeviceService from './power-device.service';

@Component
export default class PowerDeviceDetails extends Vue {
  @Inject('powerDeviceService') private powerDeviceService: () => PowerDeviceService;
  public powerDevice: IPowerDevice = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.powerDeviceId) {
        vm.retrievePowerDevice(to.params.powerDeviceId);
      }
    });
  }

  public retrievePowerDevice(powerDeviceId) {
    this.powerDeviceService()
      .find(powerDeviceId)
      .then(res => {
        this.powerDevice = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
