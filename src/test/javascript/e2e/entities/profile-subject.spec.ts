import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ProfileSubject e2e test', () => {

    let navBarPage: NavBarPage;
    let profileSubjectDialogPage: ProfileSubjectDialogPage;
    let profileSubjectComponentsPage: ProfileSubjectComponentsPage;

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
    profileSelect = element(by.css('select#field_profile'));
    subjectSelect = element(by.css('select#field_subject'));

    getModalTitle() {
        return this.modalTitle.getText();
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

    subjectSelectLastOption = function() {
        this.subjectSelect.all(by.tagName('option')).last().click();
    }

    subjectSelectOption = function(option) {
        this.subjectSelect.sendKeys(option);
    }

    getSubjectSelect = function() {
        return this.subjectSelect;
    }

    getSubjectSelectedOption = function() {
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
