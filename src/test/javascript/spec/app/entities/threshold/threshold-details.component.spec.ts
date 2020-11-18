/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ThresholdDetailComponent from '@/entities/threshold/threshold-details.vue';
import ThresholdClass from '@/entities/threshold/threshold-details.component';
import ThresholdService from '@/entities/threshold/threshold.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Threshold Management Detail Component', () => {
    let wrapper: Wrapper<ThresholdClass>;
    let comp: ThresholdClass;
    let thresholdServiceStub: SinonStubbedInstance<ThresholdService>;

    beforeEach(() => {
      thresholdServiceStub = sinon.createStubInstance<ThresholdService>(ThresholdService);

      wrapper = shallowMount<ThresholdClass>(ThresholdDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { thresholdService: () => thresholdServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundThreshold = { id: 123 };
        thresholdServiceStub.find.resolves(foundThreshold);

        // WHEN
        comp.retrieveThreshold(123);
        await comp.$nextTick();

        // THEN
        expect(comp.threshold).toBe(foundThreshold);
      });
    });
  });
});
