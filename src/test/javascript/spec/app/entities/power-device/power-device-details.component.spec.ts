/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PowerDeviceDetailComponent from '@/entities/power-device/power-device-details.vue';
import PowerDeviceClass from '@/entities/power-device/power-device-details.component';
import PowerDeviceService from '@/entities/power-device/power-device.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PowerDevice Management Detail Component', () => {
    let wrapper: Wrapper<PowerDeviceClass>;
    let comp: PowerDeviceClass;
    let powerDeviceServiceStub: SinonStubbedInstance<PowerDeviceService>;

    beforeEach(() => {
      powerDeviceServiceStub = sinon.createStubInstance<PowerDeviceService>(PowerDeviceService);

      wrapper = shallowMount<PowerDeviceClass>(PowerDeviceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { powerDeviceService: () => powerDeviceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPowerDevice = { id: 123 };
        powerDeviceServiceStub.find.resolves(foundPowerDevice);

        // WHEN
        comp.retrievePowerDevice(123);
        await comp.$nextTick();

        // THEN
        expect(comp.powerDevice).toBe(foundPowerDevice);
      });
    });
  });
});
