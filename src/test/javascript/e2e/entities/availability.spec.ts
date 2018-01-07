import { browser, element, by } from 'protractor';
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
        availabilityDialogPage.getAvailabileInput().isSelected().then(function(selected) {
            if (selected) {
                availabilityDialogPage.getAvailabileInput().click();
                expect(availabilityDialogPage.getAvailabileInput().isSelected()).toBeFalsy();
            } else {
                availabilityDialogPage.getAvailabileInput().click();
                expect(availabilityDialogPage.getAvailabileInput().isSelected()).toBeTruthy();
            }
        });
        availabilityDialogPage.timeZoneSelectLastOption();
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
    availabileInput = element(by.css('input#field_availabile'));
    timeZoneSelect = element(by.css('select#field_timeZone'));
    profileSelect = element(by.css('select#field_profile'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setDayOfWeekSelect = function(dayOfWeek) {
        this.dayOfWeekSelect.sendKeys(dayOfWeek);
    }

    getDayOfWeekSelect = function() {
        return this.dayOfWeekSelect.element(by.css('option:checked')).getText();
    }

    dayOfWeekSelectLastOption = function() {
        this.dayOfWeekSelect.all(by.tagName('option')).last().click();
    }
    getAvailabileInput = function() {
        return this.availabileInput;
    }
    setTimeZoneSelect = function(timeZone) {
        this.timeZoneSelect.sendKeys(timeZone);
    }

    getTimeZoneSelect = function() {
        return this.timeZoneSelect.element(by.css('option:checked')).getText();
    }

    timeZoneSelectLastOption = function() {
        this.timeZoneSelect.all(by.tagName('option')).last().click();
    }
    profileSelectLastOption = function() {
        this.profileSelect.all(by.tagName('option')).last().click();
    }

    profileSelectOption = function(option) {
        this.profileSelect.sendKeys(option);
    }

    getProfileSelect = function() {
        return this.profileSelect;
    }

    getProfileSelectedOption = function() {
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
