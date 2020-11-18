import { Component, Vue, Inject } from 'vue-property-decorator';

import { IThreshold } from '@/shared/model/threshold.model';
import ThresholdService from './threshold.service';

@Component
export default class ThresholdDetails extends Vue {
  @Inject('thresholdService') private thresholdService: () => ThresholdService;
  public threshold: IThreshold = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.thresholdId) {
        vm.retrieveThreshold(to.params.thresholdId);
      }
    });
  }

  public retrieveThreshold(thresholdId) {
    this.thresholdService()
      .find(thresholdId)
      .then(res => {
        this.threshold = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
