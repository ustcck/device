/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PowerDeviceUpdateComponent from '@/entities/power-device/power-device-update.vue';
import PowerDeviceClass from '@/entities/power-device/power-device-update.component';
import PowerDeviceService from '@/entities/power-device/power-device.service';

import ThresholdService from '@/entities/threshold/threshold.service';

import PatrolDeviceService from '@/entities/patrol-device/patrol-device.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PowerDevice Management Update Component', () => {
    let wrapper: Wrapper<PowerDeviceClass>;
    let comp: PowerDeviceClass;
    let powerDeviceServiceStub: SinonStubbedInstance<PowerDeviceService>;

    beforeEach(() => {
      powerDeviceServiceStub = sinon.createStubInstance<PowerDeviceService>(PowerDeviceService);

      wrapper = shallowMount<PowerDeviceClass>(PowerDeviceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          powerDeviceService: () => powerDeviceServiceStub,

          thresholdService: () => new ThresholdService(),

          patrolDeviceService: () => new PatrolDeviceService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.powerDevice = entity;
        powerDeviceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(powerDeviceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.powerDevice = entity;
        powerDeviceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(powerDeviceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
