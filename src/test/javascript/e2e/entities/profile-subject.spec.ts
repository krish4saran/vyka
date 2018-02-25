import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ProfileSubject e2e test', () => {

    let navBarPage: NavBarPage;
    let profileSubjectDialogPage: ProfileSubjectDialogPage;
    let profileSubjectComponentsPage: ProfileSubjectComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ProfileSubjects', () => {
        navBarPage.goToEntity('profile-subject');
        profileSubjectComponentsPage = new ProfileSubjectComponentsPage();
        expect(profileSubjectComponentsPage.getTitle()).toMatch(/Profile Subjects/);

    });

    it('should load create ProfileSubject dialog', () => {
        profileSubjectComponentsPage.clickOnCreateButton();
        profileSubjectDialogPage = new ProfileSubjectDialogPage();
        expect(profileSubjectDialogPage.getModalTitle()).toMatch(/Create or edit a Profile Subject/);
        profileSubjectDialogPage.close();
    });

    it('should create and save ProfileSubjects', () => {
        profileSubjectComponentsPage.clickOnCreateButton();
        profileSubjectDialogPage.levelSelectLastOption();
        profileSubjectDialogPage.setRateInput('5');
        expect(profileSubjectDialogPage.getRateInput()).toMatch('5');
        profileSubjectDialogPage.getSponsoredInput().isSelected().then(function (selected) {
            if (selected) {
                profileSubjectDialogPage.getSponsoredInput().click();
                expect(profileSubjectDialogPage.getSponsoredInput().isSelected()).toBeFalsy();
            } else {
                profileSubjectDialogPage.getSponsoredInput().click();
                expect(profileSubjectDialogPage.getSponsoredInput().isSelected()).toBeTruthy();
            }
        });
        profileSubjectDialogPage.getActiveInput().isSelected().then(function (selected) {
            if (selected) {
                profileSubjectDialogPage.getActiveInput().click();
                expect(profileSubjectDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                profileSubjectDialogPage.getActiveInput().click();
                expect(profileSubjectDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        profileSubjectDialogPage.setTotalRatingInput('5');
        expect(profileSubjectDialogPage.getTotalRatingInput()).toMatch('5');
        profileSubjectDialogPage.profileSelectLastOption();
        profileSubjectDialogPage.subjectSelectLastOption();
        profileSubjectDialogPage.save();
        expect(profileSubjectDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProfileSubjectComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-profile-subject div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ProfileSubjectDialogPage {
    modalTitle = element(by.css('h4#myProfileSubjectLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    levelSelect = element(by.css('select#field_level'));
    rateInput = element(by.css('input#field_rate'));
    sponsoredInput = element(by.css('input#field_sponsored'));
    activeInput = element(by.css('input#field_active'));
    totalRatingInput = element(by.css('input#field_totalRating'));
    profileSelect = element(by.css('select#field_profile'));
    subjectSelect = element(by.css('select#field_subject'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setLevelSelect = function (level) {
        this.levelSelect.sendKeys(level);
    }

    getLevelSelect = function () {
        return this.levelSelect.element(by.css('option:checked')).getText();
    }

    levelSelectLastOption = function () {
        this.levelSelect.all(by.tagName('option')).last().click();
    }
    setRateInput = function (rate) {
        this.rateInput.sendKeys(rate);
    }

    getRateInput = function () {
        return this.rateInput.getAttribute('value');
    }

    getSponsoredInput = function () {
        return this.sponsoredInput;
    }
    getActiveInput = function () {
        return this.activeInput;
    }
    setTotalRatingInput = function (totalRating) {
        this.totalRatingInput.sendKeys(totalRating);
    }

    getTotalRatingInput = function () {
        return this.totalRatingInput.getAttribute('value');
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

    subjectSelectLastOption = function () {
        this.subjectSelect.all(by.tagName('option')).last().click();
    }

    subjectSelectOption = function (option) {
        this.subjectSelect.sendKeys(option);
    }

    getSubjectSelect = function () {
        return this.subjectSelect;
    }

    getSubjectSelectedOption = function () {
        return this.subjectSelect.element(by.css('option:checked')).getText();
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
