import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('PaypalPayment e2e test', () => {

    let navBarPage: NavBarPage;
    let paypalPaymentDialogPage: PaypalPaymentDialogPage;
    let paypalPaymentComponentsPage: PaypalPaymentComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PaypalPayments', () => {
        navBarPage.goToEntity('paypal-payment');
        paypalPaymentComponentsPage = new PaypalPaymentComponentsPage();
        expect(paypalPaymentComponentsPage.getTitle()).toMatch(/Paypal Payments/);

    });

    it('should load create PaypalPayment dialog', () => {
        paypalPaymentComponentsPage.clickOnCreateButton();
        paypalPaymentDialogPage = new PaypalPaymentDialogPage();
        expect(paypalPaymentDialogPage.getModalTitle()).toMatch(/Create or edit a Paypal Payment/);
        paypalPaymentDialogPage.close();
    });

    it('should create and save PaypalPayments', () => {
        paypalPaymentComponentsPage.clickOnCreateButton();
        paypalPaymentDialogPage.setPaypalPayerIdInput('paypalPayerId');
        expect(paypalPaymentDialogPage.getPaypalPayerIdInput()).toMatch('paypalPayerId');
        paypalPaymentDialogPage.setPaypalPayerEmailIdInput('paypalPayerEmailId');
        expect(paypalPaymentDialogPage.getPaypalPayerEmailIdInput()).toMatch('paypalPayerEmailId');
        paypalPaymentDialogPage.setPayerFirstNameInput('payerFirstName');
        expect(paypalPaymentDialogPage.getPayerFirstNameInput()).toMatch('payerFirstName');
        paypalPaymentDialogPage.setPayerLastNameInput('payerLastName');
        expect(paypalPaymentDialogPage.getPayerLastNameInput()).toMatch('payerLastName');
        paypalPaymentDialogPage.setPaymentNumberInput('paymentNumber');
        expect(paypalPaymentDialogPage.getPaymentNumberInput()).toMatch('paymentNumber');
        paypalPaymentDialogPage.paymentSelectLastOption();
        paypalPaymentDialogPage.save();
        expect(paypalPaymentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PaypalPaymentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-paypal-payment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PaypalPaymentDialogPage {
    modalTitle = element(by.css('h4#myPaypalPaymentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    paypalPayerIdInput = element(by.css('input#field_paypalPayerId'));
    paypalPayerEmailIdInput = element(by.css('input#field_paypalPayerEmailId'));
    payerFirstNameInput = element(by.css('input#field_payerFirstName'));
    payerLastNameInput = element(by.css('input#field_payerLastName'));
    paymentNumberInput = element(by.css('input#field_paymentNumber'));
    paymentSelect = element(by.css('select#field_payment'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setPaypalPayerIdInput = function (paypalPayerId) {
        this.paypalPayerIdInput.sendKeys(paypalPayerId);
    }

    getPaypalPayerIdInput = function () {
        return this.paypalPayerIdInput.getAttribute('value');
    }

    setPaypalPayerEmailIdInput = function (paypalPayerEmailId) {
        this.paypalPayerEmailIdInput.sendKeys(paypalPayerEmailId);
    }

    getPaypalPayerEmailIdInput = function () {
        return this.paypalPayerEmailIdInput.getAttribute('value');
    }

    setPayerFirstNameInput = function (payerFirstName) {
        this.payerFirstNameInput.sendKeys(payerFirstName);
    }

    getPayerFirstNameInput = function () {
        return this.payerFirstNameInput.getAttribute('value');
    }

    setPayerLastNameInput = function (payerLastName) {
        this.payerLastNameInput.sendKeys(payerLastName);
    }

    getPayerLastNameInput = function () {
        return this.payerLastNameInput.getAttribute('value');
    }

    setPaymentNumberInput = function (paymentNumber) {
        this.paymentNumberInput.sendKeys(paymentNumber);
    }

    getPaymentNumberInput = function () {
        return this.paymentNumberInput.getAttribute('value');
    }

    paymentSelectLastOption = function () {
        this.paymentSelect.all(by.tagName('option')).last().click();
    }

    paymentSelectOption = function (option) {
        this.paymentSelect.sendKeys(option);
    }

    getPaymentSelect = function () {
        return this.paymentSelect;
    }

    getPaymentSelectedOption = function () {
        return this.paymentSelect.element(by.css('option:checked')).getText();
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
