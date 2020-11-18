import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPatrolDevice } from '@/shared/model/patrol-device.model';
import PatrolDeviceService from './patrol-device.service';

@Component
export default class PatrolDeviceDetails extends Vue {
  @Inject('patrolDeviceService') private patrolDeviceService: () => PatrolDeviceService;
  public patrolDevice: IPatrolDevice = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.patrolDeviceId) {
        vm.retrievePatrolDevice(to.params.patrolDeviceId);
      }
    });
  }

  public retrievePatrolDevice(patrolDeviceId) {
    this.patrolDeviceService()
      .find(patrolDeviceId)
      .then(res => {
        this.patrolDevice = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
