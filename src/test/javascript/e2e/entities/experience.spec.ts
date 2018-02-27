import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Experience e2e test', () => {

    let navBarPage: NavBarPage;
    let experienceDialogPage: ExperienceDialogPage;
    let experienceComponentsPage: ExperienceComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Experiences', () => {
        navBarPage.goToEntity('experience');
        experienceComponentsPage = new ExperienceComponentsPage();
        expect(experienceComponentsPage.getTitle()).toMatch(/Experiences/);

    });

    it('should load create Experience dialog', () => {
        experienceComponentsPage.clickOnCreateButton();
        experienceDialogPage = new ExperienceDialogPage();
        expect(experienceDialogPage.getModalTitle()).toMatch(/Create or edit a Experience/);
        experienceDialogPage.close();
    });

    it('should create and save Experiences', () => {
        experienceComponentsPage.clickOnCreateButton();
        experienceDialogPage.setTitleInput('title');
        expect(experienceDialogPage.getTitleInput()).toMatch('title');
        experienceDialogPage.setCompanyInput('company');
        expect(experienceDialogPage.getCompanyInput()).toMatch('company');
        experienceDialogPage.setBeginInput('2000-12-31');
        expect(experienceDialogPage.getBeginInput()).toMatch('2000-12-31');
        experienceDialogPage.setEndInput('2000-12-31');
        expect(experienceDialogPage.getEndInput()).toMatch('2000-12-31');
        experienceDialogPage.setDescriptionInput(absolutePath);
        experienceDialogPage.profileSelectLastOption();
        experienceDialogPage.save();
        expect(experienceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ExperienceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-experience div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ExperienceDialogPage {
    modalTitle = element(by.css('h4#myExperienceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    companyInput = element(by.css('input#field_company'));
    beginInput = element(by.css('input#field_begin'));
    endInput = element(by.css('input#field_end'));
    descriptionInput = element(by.css('input#file_description'));
    profileSelect = element(by.css('select#field_profile'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTitleInput = function (title) {
        this.titleInput.sendKeys(title);
    }

    getTitleInput = function () {
        return this.titleInput.getAttribute('value');
    }

    setCompanyInput = function (company) {
        this.companyInput.sendKeys(company);
    }

    getCompanyInput = function () {
        return this.companyInput.getAttribute('value');
    }

    setBeginInput = function (begin) {
        this.beginInput.sendKeys(begin);
    }

    getBeginInput = function () {
        return this.beginInput.getAttribute('value');
    }

    setEndInput = function (end) {
        this.endInput.sendKeys(end);
    }

    getEndInput = function () {
        return this.endInput.getAttribute('value');
    }

    setDescriptionInput = function (description) {
        this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput = function () {
        return this.descriptionInput.getAttribute('value');
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
