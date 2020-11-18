/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PatrolDeviceDetailComponent from '@/entities/patrol-device/patrol-device-details.vue';
import PatrolDeviceClass from '@/entities/patrol-device/patrol-device-details.component';
import PatrolDeviceService from '@/entities/patrol-device/patrol-device.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PatrolDevice Management Detail Component', () => {
    let wrapper: Wrapper<PatrolDeviceClass>;
    let comp: PatrolDeviceClass;
    let patrolDeviceServiceStub: SinonStubbedInstance<PatrolDeviceService>;

    beforeEach(() => {
      patrolDeviceServiceStub = sinon.createStubInstance<PatrolDeviceService>(PatrolDeviceService);

      wrapper = shallowMount<PatrolDeviceClass>(PatrolDeviceDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { patrolDeviceService: () => patrolDeviceServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPatrolDevice = { id: 123 };
        patrolDeviceServiceStub.find.resolves(foundPatrolDevice);

        // WHEN
        comp.retrievePatrolDevice(123);
        await comp.$nextTick();

        // THEN
        expect(comp.patrolDevice).toBe(foundPatrolDevice);
      });
    });
  });
});
