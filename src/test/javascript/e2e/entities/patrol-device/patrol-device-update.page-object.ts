import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class PatrolDeviceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('deviceApp.patrolDevice.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  nameInput: ElementFinder = element(by.css('input#patrol-device-name'));

  sourceInput: ElementFinder = element(by.css('input#patrol-device-source'));

  serialNumberInput: ElementFinder = element(by.css('input#patrol-device-serialNumber'));

  installDateInput: ElementFinder = element(by.css('input#patrol-device-installDate'));

  statusInput: ElementFinder = element(by.css('input#patrol-device-status'));

  deviceModelInput: ElementFinder = element(by.css('input#patrol-device-deviceModel'));

  createTimeInput: ElementFinder = element(by.css('input#patrol-device-createTime'));

  updateTimeInput: ElementFinder = element(by.css('input#patrol-device-updateTime'));

  remarkInput: ElementFinder = element(by.css('input#patrol-device-remark'));

  powerDeviceSelect = element(by.css('select#patrol-device-powerDevice'));
}
