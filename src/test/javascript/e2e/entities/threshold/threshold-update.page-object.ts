import { by, element, ElementFinder } from 'protractor';

import AlertPage from '../../page-objects/alert-page';

export default class ThresholdUpdatePage extends AlertPage {
  title: ElementFinder = element(by.id('deviceApp.threshold.home.createOrEditLabel'));
  footer: ElementFinder = element(by.id('footer'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));

  lowLimitInput: ElementFinder = element(by.css('input#threshold-lowLimit'));

  highLimitInput: ElementFinder = element(by.css('input#threshold-highLimit'));

  openCloseIndicatorInput: ElementFinder = element(by.css('input#threshold-openCloseIndicator'));
}
