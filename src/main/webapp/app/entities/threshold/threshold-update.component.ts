import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IThreshold, Threshold } from '@/shared/model/threshold.model';
import ThresholdService from './threshold.service';

const validations: any = {
  threshold: {
    lowLimit: {},
    highLimit: {},
    openCloseIndicator: {},
  },
};

@Component({
  validations,
})
export default class ThresholdUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('thresholdService') private thresholdService: () => ThresholdService;
  public threshold: IThreshold = new Threshold();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.thresholdId) {
        vm.retrieveThreshold(to.params.thresholdId);
      }
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
    if (this.threshold.id) {
      this.thresholdService()
        .update(this.threshold)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('deviceApp.threshold.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.thresholdService()
        .create(this.threshold)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('deviceApp.threshold.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveThreshold(thresholdId): void {
    this.thresholdService()
      .find(thresholdId)
      .then(res => {
        this.threshold = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
