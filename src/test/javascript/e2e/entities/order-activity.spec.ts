import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('OrderActivity e2e test', () => {

    let navBarPage: NavBarPage;
    let orderActivityDialogPage: OrderActivityDialogPage;
    let orderActivityComponentsPage: OrderActivityComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OrderActivities', () => {
        navBarPage.goToEntity('order-activity');
        orderActivityComponentsPage = new OrderActivityComponentsPage();
        expect(orderActivityComponentsPage.getTitle()).toMatch(/Order Activities/);

    });

    it('should load create OrderActivity dialog', () => {
        orderActivityComponentsPage.clickOnCreateButton();
        orderActivityDialogPage = new OrderActivityDialogPage();
        expect(orderActivityDialogPage.getModalTitle()).toMatch(/Create or edit a Order Activity/);
        orderActivityDialogPage.close();
    });

    it('should create and save OrderActivities', () => {
        orderActivityComponentsPage.clickOnCreateButton();
        orderActivityDialogPage.setAmountInput('5');
        expect(orderActivityDialogPage.getAmountInput()).toMatch('5');
        orderActivityDialogPage.setCurrencyCodeInput('currencyCode');
        expect(orderActivityDialogPage.getCurrencyCodeInput()).toMatch('currencyCode');
        orderActivityDialogPage.activityTypeSelectLastOption();
        orderActivityDialogPage.setCreatedDateInput(12310020012301);
        expect(orderActivityDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        orderActivityDialogPage.setUpdatedDateInput(12310020012301);
        expect(orderActivityDialogPage.getUpdatedDateInput()).toMatch('2001-12-31T02:30');
        orderActivityDialogPage.setAmountLocalCurrencyInput('5');
        expect(orderActivityDialogPage.getAmountLocalCurrencyInput()).toMatch('5');
        orderActivityDialogPage.packageOrderSelectLastOption();
        orderActivityDialogPage.settlementSelectLastOption();
        orderActivityDialogPage.save();
        expect(orderActivityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OrderActivityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-order-activity div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class OrderActivityDialogPage {
    modalTitle = element(by.css('h4#myOrderActivityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    amountInput = element(by.css('input#field_amount'));
    currencyCodeInput = element(by.css('input#field_currencyCode'));
    activityTypeSelect = element(by.css('select#field_activityType'));
    createdDateInput = element(by.css('input#field_createdDate'));
    updatedDateInput = element(by.css('input#field_updatedDate'));
    amountLocalCurrencyInput = element(by.css('input#field_amountLocalCurrency'));
    packageOrderSelect = element(by.css('select#field_packageOrder'));
    settlementSelect = element(by.css('select#field_settlement'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setAmountInput = function (amount) {
        this.amountInput.sendKeys(amount);
    }

    getAmountInput = function () {
        return this.amountInput.getAttribute('value');
    }

    setCurrencyCodeInput = function (currencyCode) {
        this.currencyCodeInput.sendKeys(currencyCode);
    }

    getCurrencyCodeInput = function () {
        return this.currencyCodeInput.getAttribute('value');
    }

    setActivityTypeSelect = function (activityType) {
        this.activityTypeSelect.sendKeys(activityType);
    }

    getActivityTypeSelect = function () {
        return this.activityTypeSelect.element(by.css('option:checked')).getText();
    }

    activityTypeSelectLastOption = function () {
        this.activityTypeSelect.all(by.tagName('option')).last().click();
    }
    setCreatedDateInput = function (createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    }

    getCreatedDateInput = function () {
        return this.createdDateInput.getAttribute('value');
    }

    setUpdatedDateInput = function (updatedDate) {
        this.updatedDateInput.sendKeys(updatedDate);
    }

    getUpdatedDateInput = function () {
        return this.updatedDateInput.getAttribute('value');
    }

    setAmountLocalCurrencyInput = function (amountLocalCurrency) {
        this.amountLocalCurrencyInput.sendKeys(amountLocalCurrency);
    }

    getAmountLocalCurrencyInput = function () {
        return this.amountLocalCurrencyInput.getAttribute('value');
    }

    packageOrderSelectLastOption = function () {
        this.packageOrderSelect.all(by.tagName('option')).last().click();
    }

    packageOrderSelectOption = function (option) {
        this.packageOrderSelect.sendKeys(option);
    }

    getPackageOrderSelect = function () {
        return this.packageOrderSelect;
    }

    getPackageOrderSelectedOption = function () {
        return this.packageOrderSelect.element(by.css('option:checked')).getText();
    }

    settlementSelectLastOption = function () {
        this.settlementSelect.all(by.tagName('option')).last().click();
    }

    settlementSelectOption = function (option) {
        this.settlementSelect.sendKeys(option);
    }

    getSettlementSelect = function () {
        return this.settlementSelect;
    }

    getSettlementSelectedOption = function () {
        return this.settlementSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
