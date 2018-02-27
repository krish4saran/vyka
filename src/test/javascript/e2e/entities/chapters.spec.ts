import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Chapters e2e test', () => {

    let navBarPage: NavBarPage;
    let chaptersDialogPage: ChaptersDialogPage;
    let chaptersComponentsPage: ChaptersComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Chapters', () => {
        navBarPage.goToEntity('chapters');
        chaptersComponentsPage = new ChaptersComponentsPage();
        expect(chaptersComponentsPage.getTitle()).toMatch(/Chapters/);

    });

    it('should load create Chapters dialog', () => {
        chaptersComponentsPage.clickOnCreateButton();
        chaptersDialogPage = new ChaptersDialogPage();
        expect(chaptersDialogPage.getModalTitle()).toMatch(/Create or edit a Chapters/);
        chaptersDialogPage.close();
    });

    it('should create and save Chapters', () => {
        chaptersComponentsPage.clickOnCreateButton();
        chaptersDialogPage.setDescriptionInput('description');
        expect(chaptersDialogPage.getDescriptionInput()).toMatch('description');
        chaptersDialogPage.setNumberOfClassesInput('5');
        expect(chaptersDialogPage.getNumberOfClassesInput()).toMatch('5');
        chaptersDialogPage.levelSelectLastOption();
        chaptersDialogPage.save();
        expect(chaptersDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ChaptersComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-chapters div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ChaptersDialogPage {
    modalTitle = element(by.css('h4#myChaptersLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    descriptionInput = element(by.css('input#field_description'));
    numberOfClassesInput = element(by.css('input#field_numberOfClasses'));
    levelSelect = element(by.css('select#field_level'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
    }

    setNumberOfClassesInput = function (numberOfClasses) {
        this.numberOfClassesInput.sendKeys(numberOfClasses);
    }

    getNumberOfClassesInput = function () {
        return this.numberOfClassesInput.getAttribute('value');
    }

    levelSelectLastOption = function () {
        this.levelSelect.all(by.tagName('option')).last().click();
    }

    levelSelectOption = function (option) {
        this.levelSelect.sendKeys(option);
    }

    getLevelSelect = function () {
        return this.levelSelect;
    }

    getLevelSelectedOption = function () {
        return this.levelSelect.element(by.css('option:checked')).getText();
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
