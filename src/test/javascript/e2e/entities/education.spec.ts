import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Education e2e test', () => {

    let navBarPage: NavBarPage;
    let educationDialogPage: EducationDialogPage;
    let educationComponentsPage: EducationComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Educations', () => {
        navBarPage.goToEntity('education');
        educationComponentsPage = new EducationComponentsPage();
        expect(educationComponentsPage.getTitle()).toMatch(/Educations/);

    });

    it('should load create Education dialog', () => {
        educationComponentsPage.clickOnCreateButton();
        educationDialogPage = new EducationDialogPage();
        expect(educationDialogPage.getModalTitle()).toMatch(/Create or edit a Education/);
        educationDialogPage.close();
    });

    it('should create and save Educations', () => {
        educationComponentsPage.clickOnCreateButton();
        educationDialogPage.setCourseInput('course');
        expect(educationDialogPage.getCourseInput()).toMatch('course');
        educationDialogPage.setUniversityInput('university');
        expect(educationDialogPage.getUniversityInput()).toMatch('university');
        educationDialogPage.setStartInput('5');
        expect(educationDialogPage.getStartInput()).toMatch('5');
        educationDialogPage.setEndInput('5');
        expect(educationDialogPage.getEndInput()).toMatch('5');
        educationDialogPage.profileSelectLastOption();
        educationDialogPage.save();
        expect(educationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EducationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-education div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class EducationDialogPage {
    modalTitle = element(by.css('h4#myEducationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    courseInput = element(by.css('input#field_course'));
    universityInput = element(by.css('input#field_university'));
    startInput = element(by.css('input#field_start'));
    endInput = element(by.css('input#field_end'));
    profileSelect = element(by.css('select#field_profile'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setCourseInput = function(course) {
        this.courseInput.sendKeys(course);
    }

    getCourseInput = function() {
        return this.courseInput.getAttribute('value');
    }

    setUniversityInput = function(university) {
        this.universityInput.sendKeys(university);
    }

    getUniversityInput = function() {
        return this.universityInput.getAttribute('value');
    }

    setStartInput = function(start) {
        this.startInput.sendKeys(start);
    }

    getStartInput = function() {
        return this.startInput.getAttribute('value');
    }

    setEndInput = function(end) {
        this.endInput.sendKeys(end);
    }

    getEndInput = function() {
        return this.endInput.getAttribute('value');
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
