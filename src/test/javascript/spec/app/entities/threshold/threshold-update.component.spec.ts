/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ThresholdUpdateComponent from '@/entities/threshold/threshold-update.vue';
import ThresholdClass from '@/entities/threshold/threshold-update.component';
import ThresholdService from '@/entities/threshold/threshold.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Threshold Management Update Component', () => {
    let wrapper: Wrapper<ThresholdClass>;
    let comp: ThresholdClass;
    let thresholdServiceStub: SinonStubbedInstance<ThresholdService>;

    beforeEach(() => {
      thresholdServiceStub = sinon.createStubInstance<ThresholdService>(ThresholdService);

      wrapper = shallowMount<ThresholdClass>(ThresholdUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          thresholdService: () => thresholdServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.threshold = entity;
        thresholdServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(thresholdServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.threshold = entity;
        thresholdServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(thresholdServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
