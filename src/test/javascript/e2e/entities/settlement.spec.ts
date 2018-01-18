import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Settlement e2e test', () => {

    let navBarPage: NavBarPage;
    let settlementDialogPage: SettlementDialogPage;
    let settlementComponentsPage: SettlementComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Settlements', () => {
        navBarPage.goToEntity('settlement');
        settlementComponentsPage = new SettlementComponentsPage();
        expect(settlementComponentsPage.getTitle()).toMatch(/Settlements/);

    });

    it('should load create Settlement dialog', () => {
        settlementComponentsPage.clickOnCreateButton();
        settlementDialogPage = new SettlementDialogPage();
        expect(settlementDialogPage.getModalTitle()).toMatch(/Create or edit a Settlement/);
        settlementDialogPage.close();
    });

    it('should create and save Settlements', () => {
        settlementComponentsPage.clickOnCreateButton();
        settlementDialogPage.setAmountInput('5');
        expect(settlementDialogPage.getAmountInput()).toMatch('5');
        settlementDialogPage.settlementTypeSelectLastOption();
        settlementDialogPage.setAttemptsInput('5');
        expect(settlementDialogPage.getAttemptsInput()).toMatch('5');
        settlementDialogPage.statusSelectLastOption();
        settlementDialogPage.setSettlementDateInput(12310020012301);
        expect(settlementDialogPage.getSettlementDateInput()).toMatch('2001-12-31T02:30');
        settlementDialogPage.setTransactionIdInput('transactionId');
        expect(settlementDialogPage.getTransactionIdInput()).toMatch('transactionId');
        settlementDialogPage.setProcessorResponseCodeInput('processorResponseCode');
        expect(settlementDialogPage.getProcessorResponseCodeInput()).toMatch('processorResponseCode');
        settlementDialogPage.setProcessorResponseTextInput('processorResponseText');
        expect(settlementDialogPage.getProcessorResponseTextInput()).toMatch('processorResponseText');
        settlementDialogPage.paymentSelectLastOption();
        settlementDialogPage.save();
        expect(settlementDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SettlementComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-settlement div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SettlementDialogPage {
    modalTitle = element(by.css('h4#mySettlementLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    amountInput = element(by.css('input#field_amount'));
    settlementTypeSelect = element(by.css('select#field_settlementType'));
    attemptsInput = element(by.css('input#field_attempts'));
    statusSelect = element(by.css('select#field_status'));
    settlementDateInput = element(by.css('input#field_settlementDate'));
    transactionIdInput = element(by.css('input#field_transactionId'));
    processorResponseCodeInput = element(by.css('input#field_processorResponseCode'));
    processorResponseTextInput = element(by.css('input#field_processorResponseText'));
    paymentSelect = element(by.css('select#field_payment'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setAmountInput = function (amount) {
        this.amountInput.sendKeys(amount);
    }

    getAmountInput = function () {
        return this.amountInput.getAttribute('value');
    }

    setSettlementTypeSelect = function (settlementType) {
        this.settlementTypeSelect.sendKeys(settlementType);
    }

    getSettlementTypeSelect = function () {
        return this.settlementTypeSelect.element(by.css('option:checked')).getText();
    }

    settlementTypeSelectLastOption = function () {
        this.settlementTypeSelect.all(by.tagName('option')).last().click();
    }
    setAttemptsInput = function (attempts) {
        this.attemptsInput.sendKeys(attempts);
    }

    getAttemptsInput = function () {
        return this.attemptsInput.getAttribute('value');
    }

    setStatusSelect = function (status) {
        this.statusSelect.sendKeys(status);
    }

    getStatusSelect = function () {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    statusSelectLastOption = function () {
        this.statusSelect.all(by.tagName('option')).last().click();
    }
    setSettlementDateInput = function (settlementDate) {
        this.settlementDateInput.sendKeys(settlementDate);
    }

    getSettlementDateInput = function () {
        return this.settlementDateInput.getAttribute('value');
    }

    setTransactionIdInput = function (transactionId) {
        this.transactionIdInput.sendKeys(transactionId);
    }

    getTransactionIdInput = function () {
        return this.transactionIdInput.getAttribute('value');
    }

    setProcessorResponseCodeInput = function (processorResponseCode) {
        this.processorResponseCodeInput.sendKeys(processorResponseCode);
    }

    getProcessorResponseCodeInput = function () {
        return this.processorResponseCodeInput.getAttribute('value');
    }

    setProcessorResponseTextInput = function (processorResponseText) {
        this.processorResponseTextInput.sendKeys(processorResponseText);
    }

    getProcessorResponseTextInput = function () {
        return this.processorResponseTextInput.getAttribute('value');
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
