/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import PowerDeviceComponentsPage, { PowerDeviceDeleteDialog } from './power-device.page-object';
import PowerDeviceUpdatePage from './power-device-update.page-object';
import PowerDeviceDetailsPage from './power-device-details.page-object';

import {
  clear,
  click,
  getRecordsCount,
  isVisible,
  selectLastOption,
  waitUntilAllDisplayed,
  waitUntilAnyDisplayed,
  waitUntilCount,
  waitUntilDisplayed,
  waitUntilHidden,
} from '../../util/utils';

const expect = chai.expect;

describe('PowerDevice e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: PowerDeviceUpdatePage;
  let detailsPage: PowerDeviceDetailsPage;
  let listPage: PowerDeviceComponentsPage;
  let deleteDialog: PowerDeviceDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load PowerDevices', async () => {
    await navBarPage.getEntityPage('power-device');
    listPage = new PowerDeviceComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create PowerDevice page', async () => {
      await listPage.createButton.click();
      updatePage = new PowerDeviceUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/deviceApp.powerDevice.home.createOrEditLabel/);
    });

    it('should create and save PowerDevices', async () => {
      await updatePage.areaInput.sendKeys('5');
      expect(await updatePage.areaInput.getAttribute('value')).to.eq('5');

      await updatePage.spaceInput.sendKeys('space');
      expect(await updatePage.spaceInput.getAttribute('value')).to.match(/space/);

      await updatePage.mainPartInput.sendKeys('mainPart');
      expect(await updatePage.mainPartInput.getAttribute('value')).to.match(/mainPart/);

      await updatePage.subPartInput.sendKeys('subPart');
      expect(await updatePage.subPartInput.getAttribute('value')).to.match(/subPart/);

      await updatePage.nameInput.sendKeys('name');
      expect(await updatePage.nameInput.getAttribute('value')).to.match(/name/);

      await updatePage.recognizeTypeInput.sendKeys('5');
      expect(await updatePage.recognizeTypeInput.getAttribute('value')).to.eq('5');

      await updatePage.recognizeContentInput.sendKeys('5');
      expect(await updatePage.recognizeContentInput.getAttribute('value')).to.eq('5');

      await updatePage.siteInput.sendKeys('site');
      expect(await updatePage.siteInput.getAttribute('value')).to.match(/site/);

      await updatePage.lineInput.sendKeys('line');
      expect(await updatePage.lineInput.getAttribute('value')).to.match(/line/);

      await updatePage.sourceInput.sendKeys('source');
      expect(await updatePage.sourceInput.getAttribute('value')).to.match(/source/);

      await updatePage.serialNumberInput.sendKeys('serialNumber');
      expect(await updatePage.serialNumberInput.getAttribute('value')).to.match(/serialNumber/);

      await updatePage.installDateInput.sendKeys('2001-01-01');
      expect(await updatePage.installDateInput.getAttribute('value')).to.eq('2001-01-01');

      await updatePage.statusInput.sendKeys('5');
      expect(await updatePage.statusInput.getAttribute('value')).to.eq('5');

      await updatePage.deviceModelInput.sendKeys('deviceModel');
      expect(await updatePage.deviceModelInput.getAttribute('value')).to.match(/deviceModel/);

      await updatePage.createTimeInput.sendKeys('2001-01-01');
      expect(await updatePage.createTimeInput.getAttribute('value')).to.eq('2001-01-01');

      await updatePage.updateTimeInput.sendKeys('2001-01-01');
      expect(await updatePage.updateTimeInput.getAttribute('value')).to.eq('2001-01-01');

      await updatePage.remarkInput.sendKeys('remark');
      expect(await updatePage.remarkInput.getAttribute('value')).to.match(/remark/);

      // await  selectLastOption(updatePage.thresholdSelect);

      expect(await updatePage.saveButton.isEnabled()).to.be.true;
      await updatePage.saveButton.click();

      await waitUntilHidden(updatePage.saveButton);
      expect(await isVisible(updatePage.saveButton)).to.be.false;

      await waitUntilDisplayed(listPage.successAlert);
      expect(await listPage.successAlert.isDisplayed()).to.be.true;

      await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      expect(await listPage.records.count()).to.eq(beforeRecordsCount + 1);
    });

    describe('Details, Update, Delete flow', () => {
      after(async () => {
        const deleteButton = listPage.getDeleteButton(listPage.records.first());
        await click(deleteButton);

        deleteDialog = new PowerDeviceDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/deviceApp.powerDevice.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details PowerDevice page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new PowerDeviceDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit PowerDevice page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await clear(updatePage.areaInput);
        await updatePage.areaInput.sendKeys('6');
        expect(await updatePage.areaInput.getAttribute('value')).to.eq('6');

        await updatePage.spaceInput.clear();
        await updatePage.spaceInput.sendKeys('modified');
        expect(await updatePage.spaceInput.getAttribute('value')).to.match(/modified/);

        await updatePage.mainPartInput.clear();
        await updatePage.mainPartInput.sendKeys('modified');
        expect(await updatePage.mainPartInput.getAttribute('value')).to.match(/modified/);

        await updatePage.subPartInput.clear();
        await updatePage.subPartInput.sendKeys('modified');
        expect(await updatePage.subPartInput.getAttribute('value')).to.match(/modified/);

        await updatePage.nameInput.clear();
        await updatePage.nameInput.sendKeys('modified');
        expect(await updatePage.nameInput.getAttribute('value')).to.match(/modified/);

        await clear(updatePage.recognizeTypeInput);
        await updatePage.recognizeTypeInput.sendKeys('6');
        expect(await updatePage.recognizeTypeInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.recognizeContentInput);
        await updatePage.recognizeContentInput.sendKeys('6');
        expect(await updatePage.recognizeContentInput.getAttribute('value')).to.eq('6');

        await updatePage.siteInput.clear();
        await updatePage.siteInput.sendKeys('modified');
        expect(await updatePage.siteInput.getAttribute('value')).to.match(/modified/);

        await updatePage.lineInput.clear();
        await updatePage.lineInput.sendKeys('modified');
        expect(await updatePage.lineInput.getAttribute('value')).to.match(/modified/);

        await updatePage.sourceInput.clear();
        await updatePage.sourceInput.sendKeys('modified');
        expect(await updatePage.sourceInput.getAttribute('value')).to.match(/modified/);

        await updatePage.serialNumberInput.clear();
        await updatePage.serialNumberInput.sendKeys('modified');
        expect(await updatePage.serialNumberInput.getAttribute('value')).to.match(/modified/);

        await updatePage.installDateInput.clear();
        await updatePage.installDateInput.sendKeys('2019-01-01');
        expect(await updatePage.installDateInput.getAttribute('value')).to.eq('2019-01-01');

        await clear(updatePage.statusInput);
        await updatePage.statusInput.sendKeys('6');
        expect(await updatePage.statusInput.getAttribute('value')).to.eq('6');

        await updatePage.deviceModelInput.clear();
        await updatePage.deviceModelInput.sendKeys('modified');
        expect(await updatePage.deviceModelInput.getAttribute('value')).to.match(/modified/);

        await updatePage.createTimeInput.clear();
        await updatePage.createTimeInput.sendKeys('2019-01-01');
        expect(await updatePage.createTimeInput.getAttribute('value')).to.eq('2019-01-01');

        await updatePage.updateTimeInput.clear();
        await updatePage.updateTimeInput.sendKeys('2019-01-01');
        expect(await updatePage.updateTimeInput.getAttribute('value')).to.eq('2019-01-01');

        await updatePage.remarkInput.clear();
        await updatePage.remarkInput.sendKeys('modified');
        expect(await updatePage.remarkInput.getAttribute('value')).to.match(/modified/);

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
