import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class PowerDeviceUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('deviceApp.powerDevice.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  areaInput: ElementFinder = element(by.css('input#power-device-area'));

  spaceInput: ElementFinder = element(by.css('input#power-device-space'));

  mainPartInput: ElementFinder = element(by.css('input#power-device-mainPart'));

  subPartInput: ElementFinder = element(by.css('input#power-device-subPart'));

  nameInput: ElementFinder = element(by.css('input#power-device-name'));

  recognizeTypeInput: ElementFinder = element(by.css('input#power-device-recognizeType'));

  recognizeContentInput: ElementFinder = element(by.css('input#power-device-recognizeContent'));

  siteInput: ElementFinder = element(by.css('input#power-device-site'));

  lineInput: ElementFinder = element(by.css('input#power-device-line'));

  sourceInput: ElementFinder = element(by.css('input#power-device-source'));

  serialNumberInput: ElementFinder = element(by.css('input#power-device-serialNumber'));

  installDateInput: ElementFinder = element(by.css('input#power-device-installDate'));

  statusInput: ElementFinder = element(by.css('input#power-device-status'));

  deviceModelInput: ElementFinder = element(by.css('input#power-device-deviceModel'));

  createTimeInput: ElementFinder = element(by.css('input#power-device-createTime'));

  updateTimeInput: ElementFinder = element(by.css('input#power-device-updateTime'));

  remarkInput: ElementFinder = element(by.css('input#power-device-remark'));

  thresholdSelect = element(by.css('select#power-device-threshold'));
}
