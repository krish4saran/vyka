import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Award e2e test', () => {

    let navBarPage: NavBarPage;
    let awardDialogPage: AwardDialogPage;
    let awardComponentsPage: AwardComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Awards', () => {
        navBarPage.goToEntity('award');
        awardComponentsPage = new AwardComponentsPage();
        expect(awardComponentsPage.getTitle()).toMatch(/Awards/);

    });

    it('should load create Award dialog', () => {
        awardComponentsPage.clickOnCreateButton();
        awardDialogPage = new AwardDialogPage();
        expect(awardDialogPage.getModalTitle()).toMatch(/Create or edit a Award/);
        awardDialogPage.close();
    });

    it('should create and save Awards', () => {
        awardComponentsPage.clickOnCreateButton();
        awardDialogPage.setNameInput('name');
        expect(awardDialogPage.getNameInput()).toMatch('name');
        awardDialogPage.setReceivedDateInput('2000-12-31');
        expect(awardDialogPage.getReceivedDateInput()).toMatch('2000-12-31');
        awardDialogPage.setInstituteInput('institute');
        expect(awardDialogPage.getInstituteInput()).toMatch('institute');
        awardDialogPage.profileSelectLastOption();
        awardDialogPage.save();
        expect(awardDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class AwardComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-award div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class AwardDialogPage {
    modalTitle = element(by.css('h4#myAwardLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    receivedDateInput = element(by.css('input#field_receivedDate'));
    instituteInput = element(by.css('input#field_institute'));
    profileSelect = element(by.css('select#field_profile'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    }

    setReceivedDateInput = function(receivedDate) {
        this.receivedDateInput.sendKeys(receivedDate);
    }

    getReceivedDateInput = function() {
        return this.receivedDateInput.getAttribute('value');
    }

    setInstituteInput = function(institute) {
        this.instituteInput.sendKeys(institute);
    }

    getInstituteInput = function() {
        return this.instituteInput.getAttribute('value');
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
