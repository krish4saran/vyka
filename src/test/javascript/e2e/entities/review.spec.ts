import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Review e2e test', () => {

    let navBarPage: NavBarPage;
    let reviewDialogPage: ReviewDialogPage;
    let reviewComponentsPage: ReviewComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Reviews', () => {
        navBarPage.goToEntity('review');
        reviewComponentsPage = new ReviewComponentsPage();
        expect(reviewComponentsPage.getTitle()).toMatch(/Reviews/);

    });

    it('should load create Review dialog', () => {
        reviewComponentsPage.clickOnCreateButton();
        reviewDialogPage = new ReviewDialogPage();
        expect(reviewDialogPage.getModalTitle()).toMatch(/Create or edit a Review/);
        reviewDialogPage.close();
    });

    it('should create and save Reviews', () => {
        reviewComponentsPage.clickOnCreateButton();
        reviewDialogPage.setRatingInput('5');
        expect(reviewDialogPage.getRatingInput()).toMatch('5');
        reviewDialogPage.setCommentsInput('comments');
        expect(reviewDialogPage.getCommentsInput()).toMatch('comments');
        reviewDialogPage.setCreatedDateInput('2000-12-31');
        expect(reviewDialogPage.getCreatedDateInput()).toMatch('2000-12-31');
        reviewDialogPage.setUserIdInput('5');
        expect(reviewDialogPage.getUserIdInput()).toMatch('5');
        reviewDialogPage.profileSubjectSelectLastOption();
        reviewDialogPage.save();
        expect(reviewDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ReviewComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-review div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ReviewDialogPage {
    modalTitle = element(by.css('h4#myReviewLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    ratingInput = element(by.css('input#field_rating'));
    commentsInput = element(by.css('textarea#field_comments'));
    createdDateInput = element(by.css('input#field_createdDate'));
    userIdInput = element(by.css('input#field_userId'));
    profileSubjectSelect = element(by.css('select#field_profileSubject'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setRatingInput = function (rating) {
        this.ratingInput.sendKeys(rating);
    }

    getRatingInput = function () {
        return this.ratingInput.getAttribute('value');
    }

    setCommentsInput = function (comments) {
        this.commentsInput.sendKeys(comments);
    }

    getCommentsInput = function () {
        return this.commentsInput.getAttribute('value');
    }

    setCreatedDateInput = function (createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    }

    getCreatedDateInput = function () {
        return this.createdDateInput.getAttribute('value');
    }

    setUserIdInput = function (userId) {
        this.userIdInput.sendKeys(userId);
    }

    getUserIdInput = function () {
        return this.userIdInput.getAttribute('value');
    }

    profileSubjectSelectLastOption = function () {
        this.profileSubjectSelect.all(by.tagName('option')).last().click();
    }

    profileSubjectSelectOption = function (option) {
        this.profileSubjectSelect.sendKeys(option);
    }

    getProfileSubjectSelect = function () {
        return this.profileSubjectSelect;
    }

    getProfileSubjectSelectedOption = function () {
        return this.profileSubjectSelect.element(by.css('option:checked')).getText();
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
