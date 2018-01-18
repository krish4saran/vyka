import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CreditCardPayment e2e test', () => {

    let navBarPage: NavBarPage;
    let creditCardPaymentDialogPage: CreditCardPaymentDialogPage;
    let creditCardPaymentComponentsPage: CreditCardPaymentComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CreditCardPayments', () => {
        navBarPage.goToEntity('credit-card-payment');
        creditCardPaymentComponentsPage = new CreditCardPaymentComponentsPage();
        expect(creditCardPaymentComponentsPage.getTitle()).toMatch(/Credit Card Payments/);

    });

    it('should load create CreditCardPayment dialog', () => {
        creditCardPaymentComponentsPage.clickOnCreateButton();
        creditCardPaymentDialogPage = new CreditCardPaymentDialogPage();
        expect(creditCardPaymentDialogPage.getModalTitle()).toMatch(/Create or edit a Credit Card Payment/);
        creditCardPaymentDialogPage.close();
    });

    it('should create and save CreditCardPayments', () => {
        creditCardPaymentComponentsPage.clickOnCreateButton();
        creditCardPaymentDialogPage.ccTypeSelectLastOption();
        creditCardPaymentDialogPage.setLastFourInput('lastFour');
        expect(creditCardPaymentDialogPage.getLastFourInput()).toMatch('lastFour');
        creditCardPaymentDialogPage.setCardNameInput('cardName');
        expect(creditCardPaymentDialogPage.getCardNameInput()).toMatch('cardName');
        creditCardPaymentDialogPage.setPaymentNumberInput('paymentNumber');
        expect(creditCardPaymentDialogPage.getPaymentNumberInput()).toMatch('paymentNumber');
        creditCardPaymentDialogPage.paymentSelectLastOption();
        creditCardPaymentDialogPage.save();
        expect(creditCardPaymentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CreditCardPaymentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-credit-card-payment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class CreditCardPaymentDialogPage {
    modalTitle = element(by.css('h4#myCreditCardPaymentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    ccTypeSelect = element(by.css('select#field_ccType'));
    lastFourInput = element(by.css('input#field_lastFour'));
    cardNameInput = element(by.css('input#field_cardName'));
    paymentNumberInput = element(by.css('input#field_paymentNumber'));
    paymentSelect = element(by.css('select#field_payment'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCcTypeSelect = function (ccType) {
        this.ccTypeSelect.sendKeys(ccType);
    }

    getCcTypeSelect = function () {
        return this.ccTypeSelect.element(by.css('option:checked')).getText();
    }

    ccTypeSelectLastOption = function () {
        this.ccTypeSelect.all(by.tagName('option')).last().click();
    }
    setLastFourInput = function (lastFour) {
        this.lastFourInput.sendKeys(lastFour);
    }

    getLastFourInput = function () {
        return this.lastFourInput.getAttribute('value');
    }

    setCardNameInput = function (cardName) {
        this.cardNameInput.sendKeys(cardName);
    }

    getCardNameInput = function () {
        return this.cardNameInput.getAttribute('value');
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
