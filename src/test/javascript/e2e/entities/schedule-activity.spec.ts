import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ScheduleActivity e2e test', () => {

    let navBarPage: NavBarPage;
    let scheduleActivityDialogPage: ScheduleActivityDialogPage;
    let scheduleActivityComponentsPage: ScheduleActivityComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ScheduleActivities', () => {
        navBarPage.goToEntity('schedule-activity');
        scheduleActivityComponentsPage = new ScheduleActivityComponentsPage();
        expect(scheduleActivityComponentsPage.getTitle()).toMatch(/Schedule Activities/);

    });

    it('should load create ScheduleActivity dialog', () => {
        scheduleActivityComponentsPage.clickOnCreateButton();
        scheduleActivityDialogPage = new ScheduleActivityDialogPage();
        expect(scheduleActivityDialogPage.getModalTitle()).toMatch(/Create or edit a Schedule Activity/);
        scheduleActivityDialogPage.close();
    });

    it('should create and save ScheduleActivities', () => {
        scheduleActivityComponentsPage.clickOnCreateButton();
        scheduleActivityDialogPage.setAmountInput('5');
        expect(scheduleActivityDialogPage.getAmountInput()).toMatch('5');
        scheduleActivityDialogPage.setCreatedDateInput(12310020012301);
        expect(scheduleActivityDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        scheduleActivityDialogPage.setUpdatedDateInput(12310020012301);
        expect(scheduleActivityDialogPage.getUpdatedDateInput()).toMatch('2001-12-31T02:30');
        scheduleActivityDialogPage.scheduleSelectLastOption();
        scheduleActivityDialogPage.orderActivitySelectLastOption();
        scheduleActivityDialogPage.save();
        expect(scheduleActivityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ScheduleActivityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-schedule-activity div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ScheduleActivityDialogPage {
    modalTitle = element(by.css('h4#myScheduleActivityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    amountInput = element(by.css('input#field_amount'));
    createdDateInput = element(by.css('input#field_createdDate'));
    updatedDateInput = element(by.css('input#field_updatedDate'));
    scheduleSelect = element(by.css('select#field_schedule'));
    orderActivitySelect = element(by.css('select#field_orderActivity'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setAmountInput = function (amount) {
        this.amountInput.sendKeys(amount);
    }

    getAmountInput = function () {
        return this.amountInput.getAttribute('value');
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

    scheduleSelectLastOption = function () {
        this.scheduleSelect.all(by.tagName('option')).last().click();
    }

    scheduleSelectOption = function (option) {
        this.scheduleSelect.sendKeys(option);
    }

    getScheduleSelect = function () {
        return this.scheduleSelect;
    }

    getScheduleSelectedOption = function () {
        return this.scheduleSelect.element(by.css('option:checked')).getText();
    }

    orderActivitySelectLastOption = function () {
        this.orderActivitySelect.all(by.tagName('option')).last().click();
    }

    orderActivitySelectOption = function (option) {
        this.orderActivitySelect.sendKeys(option);
    }

    getOrderActivitySelect = function () {
        return this.orderActivitySelect;
    }

    getOrderActivitySelectedOption = function () {
        return this.orderActivitySelect.element(by.css('option:checked')).getText();
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
