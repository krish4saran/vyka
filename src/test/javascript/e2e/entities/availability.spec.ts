import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Availability e2e test', () => {

    let navBarPage: NavBarPage;
    let availabilityDialogPage: AvailabilityDialogPage;
    let availabilityComponentsPage: AvailabilityComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Availabilities', () => {
        navBarPage.goToEntity('availability');
        availabilityComponentsPage = new AvailabilityComponentsPage();
        expect(availabilityComponentsPage.getTitle()).toMatch(/Availabilities/);

    });

    it('should load create Availability dialog', () => {
        availabilityComponentsPage.clickOnCreateButton();
        availabilityDialogPage = new AvailabilityDialogPage();
        expect(availabilityDialogPage.getModalTitle()).toMatch(/Create or edit a Availability/);
        availabilityDialogPage.close();
    });

    it('should create and save Availabilities', () => {
        availabilityComponentsPage.clickOnCreateButton();
        availabilityDialogPage.dayOfWeekSelectLastOption();
        availabilityDialogPage.getBookedInput().isSelected().then(function (selected) {
            if (selected) {
                availabilityDialogPage.getBookedInput().click();
                expect(availabilityDialogPage.getBookedInput().isSelected()).toBeFalsy();
            } else {
                availabilityDialogPage.getBookedInput().click();
                expect(availabilityDialogPage.getBookedInput().isSelected()).toBeTruthy();
            }
        });
        availabilityDialogPage.getActiveInput().isSelected().then(function (selected) {
            if (selected) {
                availabilityDialogPage.getActiveInput().click();
                expect(availabilityDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                availabilityDialogPage.getActiveInput().click();
                expect(availabilityDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        availabilityDialogPage.setEffectiveDateInput(12310020012301);
        expect(availabilityDialogPage.getEffectiveDateInput()).toMatch('2001-12-31T02:30');
        availabilityDialogPage.setDeactivatedDateInput(12310020012301);
        expect(availabilityDialogPage.getDeactivatedDateInput()).toMatch('2001-12-31T02:30');
        availabilityDialogPage.profileSelectLastOption();
        availabilityDialogPage.save();
        expect(availabilityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AvailabilityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-availability div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class AvailabilityDialogPage {
    modalTitle = element(by.css('h4#myAvailabilityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    dayOfWeekSelect = element(by.css('select#field_dayOfWeek'));
    bookedInput = element(by.css('input#field_booked'));
    activeInput = element(by.css('input#field_active'));
    effectiveDateInput = element(by.css('input#field_effectiveDate'));
    deactivatedDateInput = element(by.css('input#field_deactivatedDate'));
    profileSelect = element(by.css('select#field_profile'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setDayOfWeekSelect = function (dayOfWeek) {
        this.dayOfWeekSelect.sendKeys(dayOfWeek);
    }

    getDayOfWeekSelect = function () {
        return this.dayOfWeekSelect.element(by.css('option:checked')).getText();
    }

    dayOfWeekSelectLastOption = function () {
        this.dayOfWeekSelect.all(by.tagName('option')).last().click();
    }
    getBookedInput = function () {
        return this.bookedInput;
    }
    getActiveInput = function () {
        return this.activeInput;
    }
    setEffectiveDateInput = function (effectiveDate) {
        this.effectiveDateInput.sendKeys(effectiveDate);
    }

    getEffectiveDateInput = function () {
        return this.effectiveDateInput.getAttribute('value');
    }

    setDeactivatedDateInput = function (deactivatedDate) {
        this.deactivatedDateInput.sendKeys(deactivatedDate);
    }

    getDeactivatedDateInput = function () {
        return this.deactivatedDateInput.getAttribute('value');
    }

    profileSelectLastOption = function () {
        this.profileSelect.all(by.tagName('option')).last().click();
    }

    profileSelectOption = function (option) {
        this.profileSelect.sendKeys(option);
    }

    getProfileSelect = function () {
        return this.profileSelect;
    }

    getProfileSelectedOption = function () {
        return this.profileSelect.element(by.css('option:checked')).getText();
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
