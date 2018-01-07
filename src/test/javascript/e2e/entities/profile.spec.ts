import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Profile e2e test', () => {

    let navBarPage: NavBarPage;
    let profileDialogPage: ProfileDialogPage;
    let profileComponentsPage: ProfileComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Profiles', () => {
        navBarPage.goToEntity('profile');
        profileComponentsPage = new ProfileComponentsPage();
        expect(profileComponentsPage.getTitle()).toMatch(/Profiles/);

    });

    it('should load create Profile dialog', () => {
        profileComponentsPage.clickOnCreateButton();
        profileDialogPage = new ProfileDialogPage();
        expect(profileDialogPage.getModalTitle()).toMatch(/Create or edit a Profile/);
        profileDialogPage.close();
    });

    it('should create and save Profiles', () => {
        profileComponentsPage.clickOnCreateButton();
        profileDialogPage.setUserIdInput('5');
        expect(profileDialogPage.getUserIdInput()).toMatch('5');
        profileDialogPage.setDescriptionInput('description');
        expect(profileDialogPage.getDescriptionInput()).toMatch('description');
        profileDialogPage.getActiveInput().isSelected().then(function(selected) {
            if (selected) {
                profileDialogPage.getActiveInput().click();
                expect(profileDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                profileDialogPage.getActiveInput().click();
                expect(profileDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        profileDialogPage.setImageInput(absolutePath);
        profileDialogPage.setVideo1Input(absolutePath);
        profileDialogPage.setVideo2Input(absolutePath);
        profileDialogPage.getBackgroundCheckedInput().isSelected().then(function(selected) {
            if (selected) {
                profileDialogPage.getBackgroundCheckedInput().click();
                expect(profileDialogPage.getBackgroundCheckedInput().isSelected()).toBeFalsy();
            } else {
                profileDialogPage.getBackgroundCheckedInput().click();
                expect(profileDialogPage.getBackgroundCheckedInput().isSelected()).toBeTruthy();
            }
        });
        profileDialogPage.setCreatedInput(12310020012301);
        expect(profileDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        profileDialogPage.setUpdatedInput(12310020012301);
        expect(profileDialogPage.getUpdatedInput()).toMatch('2001-12-31T02:30');
        profileDialogPage.locationSelectLastOption();
        // profileDialogPage.languageSelectLastOption();
        profileDialogPage.save();
        expect(profileDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ProfileComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-profile div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ProfileDialogPage {
    modalTitle = element(by.css('h4#myProfileLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userIdInput = element(by.css('input#field_userId'));
    descriptionInput = element(by.css('textarea#field_description'));
    activeInput = element(by.css('input#field_active'));
    imageInput = element(by.css('input#file_image'));
    video1Input = element(by.css('input#file_video1'));
    video2Input = element(by.css('input#file_video2'));
    backgroundCheckedInput = element(by.css('input#field_backgroundChecked'));
    createdInput = element(by.css('input#field_created'));
    updatedInput = element(by.css('input#field_updated'));
    locationSelect = element(by.css('select#field_location'));
    languageSelect = element(by.css('select#field_language'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setUserIdInput = function(userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function() {
        return this.userIdInput.getAttribute('value');
    }

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    }

    getActiveInput = function() {
        return this.activeInput;
    }
    setImageInput = function(image) {
        this.imageInput.sendKeys(image);
    }

    getImageInput = function() {
        return this.imageInput.getAttribute('value');
    }

    setVideo1Input = function(video1) {
        this.video1Input.sendKeys(video1);
    }

    getVideo1Input = function() {
        return this.video1Input.getAttribute('value');
    }

    setVideo2Input = function(video2) {
        this.video2Input.sendKeys(video2);
    }

    getVideo2Input = function() {
        return this.video2Input.getAttribute('value');
    }

    getBackgroundCheckedInput = function() {
        return this.backgroundCheckedInput;
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

    locationSelectLastOption = function() {
        this.locationSelect.all(by.tagName('option')).last().click();
    }

    locationSelectOption = function(option) {
        this.locationSelect.sendKeys(option);
    }

    getLocationSelect = function() {
        return this.locationSelect;
    }

    getLocationSelectedOption = function() {
        return this.locationSelect.element(by.css('option:checked')).getText();
    }

    languageSelectLastOption = function() {
        this.languageSelect.all(by.tagName('option')).last().click();
    }

    languageSelectOption = function(option) {
        this.languageSelect.sendKeys(option);
    }

    getLanguageSelect = function() {
        return this.languageSelect;
    }

    getLanguageSelectedOption = function() {
        return this.languageSelect.element(by.css('option:checked')).getText();
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
