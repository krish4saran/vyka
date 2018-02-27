import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ClassLength e2e test', () => {

    let navBarPage: NavBarPage;
    let classLengthDialogPage: ClassLengthDialogPage;
    let classLengthComponentsPage: ClassLengthComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ClassLengths', () => {
        navBarPage.goToEntity('class-length');
        classLengthComponentsPage = new ClassLengthComponentsPage();
        expect(classLengthComponentsPage.getTitle()).toMatch(/Class Lengths/);

    });

    it('should load create ClassLength dialog', () => {
        classLengthComponentsPage.clickOnCreateButton();
        classLengthDialogPage = new ClassLengthDialogPage();
        expect(classLengthDialogPage.getModalTitle()).toMatch(/Create or edit a Class Length/);
        classLengthDialogPage.close();
    });

    it('should create and save ClassLengths', () => {
        classLengthComponentsPage.clickOnCreateButton();
        classLengthDialogPage.setClassLengthInput('5');
        expect(classLengthDialogPage.getClassLengthInput()).toMatch('5');
        classLengthDialogPage.getActiveInput().isSelected().then(function (selected) {
            if (selected) {
                classLengthDialogPage.getActiveInput().click();
                expect(classLengthDialogPage.getActiveInput().isSelected()).toBeFalsy();
            } else {
                classLengthDialogPage.getActiveInput().click();
                expect(classLengthDialogPage.getActiveInput().isSelected()).toBeTruthy();
            }
        });
        classLengthDialogPage.setCreatedInput(12310020012301);
        expect(classLengthDialogPage.getCreatedInput()).toMatch('2001-12-31T02:30');
        classLengthDialogPage.setUpdatedInput(12310020012301);
        expect(classLengthDialogPage.getUpdatedInput()).toMatch('2001-12-31T02:30');
        classLengthDialogPage.save();
        expect(classLengthDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ClassLengthComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-class-length div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ClassLengthDialogPage {
    modalTitle = element(by.css('h4#myClassLengthLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    classLengthInput = element(by.css('input#field_classLength'));
    activeInput = element(by.css('input#field_active'));
    createdInput = element(by.css('input#field_created'));
    updatedInput = element(by.css('input#field_updated'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setClassLengthInput = function (classLength) {
        this.classLengthInput.sendKeys(classLength);
    }

    getClassLengthInput = function () {
        return this.classLengthInput.getAttribute('value');
    }

    getActiveInput = function () {
        return this.activeInput;
    }
    setCreatedInput = function (created) {
        this.createdInput.sendKeys(created);
    }

    getCreatedInput = function () {
        return this.createdInput.getAttribute('value');
    }

    setUpdatedInput = function (updated) {
        this.updatedInput.sendKeys(updated);
    }

    getUpdatedInput = function () {
        return this.updatedInput.getAttribute('value');
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
