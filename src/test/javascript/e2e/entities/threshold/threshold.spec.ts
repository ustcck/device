/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import ThresholdComponentsPage, { ThresholdDeleteDialog } from './threshold.page-object';
import ThresholdUpdatePage from './threshold-update.page-object';
import ThresholdDetailsPage from './threshold-details.page-object';

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

describe('Threshold e2e test', () => {
  let navBarPage: NavBarPage;
  let updatePage: ThresholdUpdatePage;
  let detailsPage: ThresholdDetailsPage;
  let listPage: ThresholdComponentsPage;
  let deleteDialog: ThresholdDeleteDialog;
  let beforeRecordsCount = 0;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    await navBarPage.login('admin', 'admin');
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });

  it('should load Thresholds', async () => {
    await navBarPage.getEntityPage('threshold');
    listPage = new ThresholdComponentsPage();

    await waitUntilAllDisplayed([listPage.title, listPage.footer]);

    expect(await listPage.title.getText()).not.to.be.empty;
    expect(await listPage.createButton.isEnabled()).to.be.true;

    await waitUntilAnyDisplayed([listPage.noRecords, listPage.table]);
    beforeRecordsCount = (await isVisible(listPage.noRecords)) ? 0 : await getRecordsCount(listPage.table);
  });
  describe('Create flow', () => {
    it('should load create Threshold page', async () => {
      await listPage.createButton.click();
      updatePage = new ThresholdUpdatePage();

      await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

      expect(await updatePage.title.getAttribute('id')).to.match(/deviceApp.threshold.home.createOrEditLabel/);
    });

    it('should create and save Thresholds', async () => {
      await updatePage.lowLimitInput.sendKeys('5');
      expect(await updatePage.lowLimitInput.getAttribute('value')).to.eq('5');

      await updatePage.highLimitInput.sendKeys('5');
      expect(await updatePage.highLimitInput.getAttribute('value')).to.eq('5');

      await updatePage.openCloseIndicatorInput.sendKeys('5');
      expect(await updatePage.openCloseIndicatorInput.getAttribute('value')).to.eq('5');

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

        deleteDialog = new ThresholdDeleteDialog();
        await waitUntilDisplayed(deleteDialog.dialog);

        expect(await deleteDialog.title.getAttribute('id')).to.match(/deviceApp.threshold.delete.question/);

        await click(deleteDialog.confirmButton);
        await waitUntilHidden(deleteDialog.dialog);

        expect(await isVisible(deleteDialog.dialog)).to.be.false;
        expect(await listPage.dangerAlert.isDisplayed()).to.be.true;

        await waitUntilCount(listPage.records, beforeRecordsCount);
        expect(await listPage.records.count()).to.eq(beforeRecordsCount);
      });

      it('should load details Threshold page and fetch data', async () => {
        const detailsButton = listPage.getDetailsButton(listPage.records.first());
        await click(detailsButton);

        detailsPage = new ThresholdDetailsPage();

        await waitUntilAllDisplayed([detailsPage.title, detailsPage.backButton, detailsPage.firstDetail]);

        expect(await detailsPage.title.getText()).not.to.be.empty;
        expect(await detailsPage.firstDetail.getText()).not.to.be.empty;

        await click(detailsPage.backButton);
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });

      it('should load edit Threshold page, fetch data and update', async () => {
        const editButton = listPage.getEditButton(listPage.records.first());
        await click(editButton);

        await waitUntilAllDisplayed([updatePage.title, updatePage.footer, updatePage.saveButton]);

        expect(await updatePage.title.getText()).not.to.be.empty;

        await clear(updatePage.lowLimitInput);
        await updatePage.lowLimitInput.sendKeys('6');
        expect(await updatePage.lowLimitInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.highLimitInput);
        await updatePage.highLimitInput.sendKeys('6');
        expect(await updatePage.highLimitInput.getAttribute('value')).to.eq('6');

        await clear(updatePage.openCloseIndicatorInput);
        await updatePage.openCloseIndicatorInput.sendKeys('6');
        expect(await updatePage.openCloseIndicatorInput.getAttribute('value')).to.eq('6');

        await updatePage.saveButton.click();

        await waitUntilHidden(updatePage.saveButton);

        expect(await isVisible(updatePage.saveButton)).to.be.false;
        expect(await listPage.infoAlert.isDisplayed()).to.be.true;
        await waitUntilCount(listPage.records, beforeRecordsCount + 1);
      });
    });
  });
});
