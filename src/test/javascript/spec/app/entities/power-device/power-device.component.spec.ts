/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PowerDeviceComponent from '@/entities/power-device/power-device.vue';
import PowerDeviceClass from '@/entities/power-device/power-device.component';
import PowerDeviceService from '@/entities/power-device/power-device.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('PowerDevice Management Component', () => {
    let wrapper: Wrapper<PowerDeviceClass>;
    let comp: PowerDeviceClass;
    let powerDeviceServiceStub: SinonStubbedInstance<PowerDeviceService>;

    beforeEach(() => {
      powerDeviceServiceStub = sinon.createStubInstance<PowerDeviceService>(PowerDeviceService);
      powerDeviceServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PowerDeviceClass>(PowerDeviceComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          powerDeviceService: () => powerDeviceServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      powerDeviceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPowerDevices();
      await comp.$nextTick();

      // THEN
      expect(powerDeviceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.powerDevices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      powerDeviceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(powerDeviceServiceStub.retrieve.called).toBeTruthy();
      expect(comp.powerDevices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      powerDeviceServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(powerDeviceServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      powerDeviceServiceStub.retrieve.reset();
      powerDeviceServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(powerDeviceServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.powerDevices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      powerDeviceServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePowerDevice();
      await comp.$nextTick();

      // THEN
      expect(powerDeviceServiceStub.delete.called).toBeTruthy();
      expect(powerDeviceServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
