import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Schedule e2e test', () => {

    let navBarPage: NavBarPage;
    let scheduleDialogPage: ScheduleDialogPage;
    let scheduleComponentsPage: ScheduleComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Schedules', () => {
        navBarPage.goToEntity('schedule');
        scheduleComponentsPage = new ScheduleComponentsPage();
        expect(scheduleComponentsPage.getTitle()).toMatch(/Schedules/);

    });

    it('should load create Schedule dialog', () => {
        scheduleComponentsPage.clickOnCreateButton();
        scheduleDialogPage = new ScheduleDialogPage();
        expect(scheduleDialogPage.getModalTitle()).toMatch(/Create or edit a Schedule/);
        scheduleDialogPage.close();
    });

    it('should create and save Schedules', () => {
        scheduleComponentsPage.clickOnCreateButton();
        scheduleDialogPage.setAvailabilityIdInput('5');
        expect(scheduleDialogPage.getAvailabilityIdInput()).toMatch('5');
        scheduleDialogPage.setStartDateInput(12310020012301);
        expect(scheduleDialogPage.getStartDateInput()).toMatch('2001-12-31T02:30');
        scheduleDialogPage.setEndDateInput(12310020012301);
        expect(scheduleDialogPage.getEndDateInput()).toMatch('2001-12-31T02:30');
        scheduleDialogPage.statusSelectLastOption();
        scheduleDialogPage.setCreatedDateInput(12310020012301);
        expect(scheduleDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        scheduleDialogPage.setUpdatedDateInput(12310020012301);
        expect(scheduleDialogPage.getUpdatedDateInput()).toMatch('2001-12-31T02:30');
        scheduleDialogPage.setAmountInput('5');
        expect(scheduleDialogPage.getAmountInput()).toMatch('5');
        scheduleDialogPage.packageOrderSelectLastOption();
        scheduleDialogPage.save();
        expect(scheduleDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ScheduleComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-schedule div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ScheduleDialogPage {
    modalTitle = element(by.css('h4#myScheduleLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    availabilityIdInput = element(by.css('input#field_availabilityId'));
    startDateInput = element(by.css('input#field_startDate'));
    endDateInput = element(by.css('input#field_endDate'));
    statusSelect = element(by.css('select#field_status'));
    createdDateInput = element(by.css('input#field_createdDate'));
    updatedDateInput = element(by.css('input#field_updatedDate'));
    amountInput = element(by.css('input#field_amount'));
    packageOrderSelect = element(by.css('select#field_packageOrder'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setAvailabilityIdInput = function (availabilityId) {
        this.availabilityIdInput.sendKeys(availabilityId);
    }

    getAvailabilityIdInput = function () {
        return this.availabilityIdInput.getAttribute('value');
    }

    setStartDateInput = function (startDate) {
        this.startDateInput.sendKeys(startDate);
    }

    getStartDateInput = function () {
        return this.startDateInput.getAttribute('value');
    }

    setEndDateInput = function (endDate) {
        this.endDateInput.sendKeys(endDate);
    }

    getEndDateInput = function () {
        return this.endDateInput.getAttribute('value');
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

    setAmountInput = function (amount) {
        this.amountInput.sendKeys(amount);
    }

    getAmountInput = function () {
        return this.amountInput.getAttribute('value');
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
