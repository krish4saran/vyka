import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('SubjectLevel e2e test', () => {

    let navBarPage: NavBarPage;
    let subjectLevelDialogPage: SubjectLevelDialogPage;
    let subjectLevelComponentsPage: SubjectLevelComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SubjectLevels', () => {
        navBarPage.goToEntity('subject-level');
        subjectLevelComponentsPage = new SubjectLevelComponentsPage();
        expect(subjectLevelComponentsPage.getTitle()).toMatch(/Subject Levels/);

    });

    it('should load create SubjectLevel dialog', () => {
        subjectLevelComponentsPage.clickOnCreateButton();
        subjectLevelDialogPage = new SubjectLevelDialogPage();
        expect(subjectLevelDialogPage.getModalTitle()).toMatch(/Create or edit a Subject Level/);
        subjectLevelDialogPage.close();
    });

    it('should create and save SubjectLevels', () => {
        subjectLevelComponentsPage.clickOnCreateButton();
        subjectLevelDialogPage.levelSelectLastOption();
        subjectLevelDialogPage.setDescriptionInput(absolutePath);
        subjectLevelDialogPage.subjectSelectLastOption();
        subjectLevelDialogPage.save();
        expect(subjectLevelDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SubjectLevelComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-subject-level div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SubjectLevelDialogPage {
    modalTitle = element(by.css('h4#mySubjectLevelLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    levelSelect = element(by.css('select#field_level'));
    descriptionInput = element(by.css('input#file_description'));
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
    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
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
