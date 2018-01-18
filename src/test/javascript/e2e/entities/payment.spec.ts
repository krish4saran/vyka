import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Payment e2e test', () => {

    let navBarPage: NavBarPage;
    let paymentDialogPage: PaymentDialogPage;
    let paymentComponentsPage: PaymentComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Payments', () => {
        navBarPage.goToEntity('payment');
        paymentComponentsPage = new PaymentComponentsPage();
        expect(paymentComponentsPage.getTitle()).toMatch(/Payments/);

    });

    it('should load create Payment dialog', () => {
        paymentComponentsPage.clickOnCreateButton();
        paymentDialogPage = new PaymentDialogPage();
        expect(paymentDialogPage.getModalTitle()).toMatch(/Create or edit a Payment/);
        paymentDialogPage.close();
    });

    it('should create and save Payments', () => {
        paymentComponentsPage.clickOnCreateButton();
        paymentDialogPage.setTotalAmountInput('5');
        expect(paymentDialogPage.getTotalAmountInput()).toMatch('5');
        paymentDialogPage.setLocalCurrencyCodeInput('localCurrencyCode');
        expect(paymentDialogPage.getLocalCurrencyCodeInput()).toMatch('localCurrencyCode');
        paymentDialogPage.setSettlementCurrencyCodeInput('settlementCurrencyCode');
        expect(paymentDialogPage.getSettlementCurrencyCodeInput()).toMatch('settlementCurrencyCode');
        paymentDialogPage.setCapturedAmountInput('5');
        expect(paymentDialogPage.getCapturedAmountInput()).toMatch('5');
        paymentDialogPage.setCanceledAmountInput('5');
        expect(paymentDialogPage.getCanceledAmountInput()).toMatch('5');
        paymentDialogPage.setRefundAmountInput('5');
        expect(paymentDialogPage.getRefundAmountInput()).toMatch('5');
        paymentDialogPage.paymentViaSelectLastOption();
        paymentDialogPage.setCreatedDateInput(12310020012301);
        expect(paymentDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        paymentDialogPage.setUpdatedDateInput(12310020012301);
        expect(paymentDialogPage.getUpdatedDateInput()).toMatch('2001-12-31T02:30');
        paymentDialogPage.packageOrderSelectLastOption();
        paymentDialogPage.save();
        expect(paymentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PaymentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-payment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PaymentDialogPage {
    modalTitle = element(by.css('h4#myPaymentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    totalAmountInput = element(by.css('input#field_totalAmount'));
    localCurrencyCodeInput = element(by.css('input#field_localCurrencyCode'));
    settlementCurrencyCodeInput = element(by.css('input#field_settlementCurrencyCode'));
    capturedAmountInput = element(by.css('input#field_capturedAmount'));
    canceledAmountInput = element(by.css('input#field_canceledAmount'));
    refundAmountInput = element(by.css('input#field_refundAmount'));
    paymentViaSelect = element(by.css('select#field_paymentVia'));
    createdDateInput = element(by.css('input#field_createdDate'));
    updatedDateInput = element(by.css('input#field_updatedDate'));
    packageOrderSelect = element(by.css('select#field_packageOrder'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTotalAmountInput = function (totalAmount) {
        this.totalAmountInput.sendKeys(totalAmount);
    }

    getTotalAmountInput = function () {
        return this.totalAmountInput.getAttribute('value');
    }

    setLocalCurrencyCodeInput = function (localCurrencyCode) {
        this.localCurrencyCodeInput.sendKeys(localCurrencyCode);
    }

    getLocalCurrencyCodeInput = function () {
        return this.localCurrencyCodeInput.getAttribute('value');
    }

    setSettlementCurrencyCodeInput = function (settlementCurrencyCode) {
        this.settlementCurrencyCodeInput.sendKeys(settlementCurrencyCode);
    }

    getSettlementCurrencyCodeInput = function () {
        return this.settlementCurrencyCodeInput.getAttribute('value');
    }

    setCapturedAmountInput = function (capturedAmount) {
        this.capturedAmountInput.sendKeys(capturedAmount);
    }

    getCapturedAmountInput = function () {
        return this.capturedAmountInput.getAttribute('value');
    }

    setCanceledAmountInput = function (canceledAmount) {
        this.canceledAmountInput.sendKeys(canceledAmount);
    }

    getCanceledAmountInput = function () {
        return this.canceledAmountInput.getAttribute('value');
    }

    setRefundAmountInput = function (refundAmount) {
        this.refundAmountInput.sendKeys(refundAmount);
    }

    getRefundAmountInput = function () {
        return this.refundAmountInput.getAttribute('value');
    }

    setPaymentViaSelect = function (paymentVia) {
        this.paymentViaSelect.sendKeys(paymentVia);
    }

    getPaymentViaSelect = function () {
        return this.paymentViaSelect.element(by.css('option:checked')).getText();
    }

    paymentViaSelectLastOption = function () {
        this.paymentViaSelect.all(by.tagName('option')).last().click();
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
