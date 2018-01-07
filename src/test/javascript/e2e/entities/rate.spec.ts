import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Rate e2e test', () => {

    let navBarPage: NavBarPage;
    let rateDialogPage: RateDialogPage;
    let rateComponentsPage: RateComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Rates', () => {
        navBarPage.goToEntity('rate');
        rateComponentsPage = new RateComponentsPage();
        expect(rateComponentsPage.getTitle()).toMatch(/Rates/);

    });

    it('should load create Rate dialog', () => {
        rateComponentsPage.clickOnCreateButton();
        rateDialogPage = new RateDialogPage();
        expect(rateDialogPage.getModalTitle()).toMatch(/Create or edit a Rate/);
        rateDialogPage.close();
    });

    it('should create and save Rates', () => {
        rateComponentsPage.clickOnCreateButton();
        rateDialogPage.setRateInput('5');
        expect(rateDialogPage.getRateInput()).toMatch('5');
        rateDialogPage.setCreatedInput(12310020012301);
        expect(rateDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        rateDialogPage.setUpdatedInput(12310020012301);
        expect(rateDialogPage.getUpdatedInput()).toMatch('2001-12-31T02:30');
        rateDialogPage.profileSubjectSelectLastOption();
        rateDialogPage.classLengthSelectLastOption();
        rateDialogPage.save();
        expect(rateDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class RateComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-rate div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class RateDialogPage {
    modalTitle = element(by.css('h4#myRateLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    rateInput = element(by.css('input#field_rate'));
    createdInput = element(by.css('input#field_created'));
    updatedInput = element(by.css('input#field_updated'));
    profileSubjectSelect = element(by.css('select#field_profileSubject'));
    classLengthSelect = element(by.css('select#field_classLength'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setRateInput = function(rate) {
        this.rateInput.sendKeys(rate);
    }

    getRateInput = function() {
        return this.rateInput.getAttribute('value');
    }

    setCreatedInput = function(created) {
        this.createdInput.sendKeys(created);
    }

    getCreatedInput = function() {
        return this.createdInput.getAttribute('value');
    }

    setUpdatedInput = function(updated) {
        this.updatedInput.sendKeys(updated);
    }

    getUpdatedInput = function() {
        return this.updatedInput.getAttribute('value');
    }

    profileSubjectSelectLastOption = function() {
        this.profileSubjectSelect.all(by.tagName('option')).last().click();
    }

    profileSubjectSelectOption = function(option) {
        this.profileSubjectSelect.sendKeys(option);
    }

    getProfileSubjectSelect = function() {
        return this.profileSubjectSelect;
    }

    getProfileSubjectSelectedOption = function() {
        return this.profileSubjectSelect.element(by.css('option:checked')).getText();
    }

    classLengthSelectLastOption = function() {
        this.classLengthSelect.all(by.tagName('option')).last().click();
    }

    classLengthSelectOption = function(option) {
        this.classLengthSelect.sendKeys(option);
    }

    getClassLengthSelect = function() {
        return this.classLengthSelect;
    }

    getClassLengthSelectedOption = function() {
        return this.classLengthSelect.element(by.css('option:checked')).getText();
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
