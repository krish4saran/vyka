import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('PackageOrder e2e test', () => {

    let navBarPage: NavBarPage;
    let packageOrderDialogPage: PackageOrderDialogPage;
    let packageOrderComponentsPage: PackageOrderComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PackageOrders', () => {
        navBarPage.goToEntity('package-order');
        packageOrderComponentsPage = new PackageOrderComponentsPage();
        expect(packageOrderComponentsPage.getTitle()).toMatch(/Package Orders/);

    });

    it('should load create PackageOrder dialog', () => {
        packageOrderComponentsPage.clickOnCreateButton();
        packageOrderDialogPage = new PackageOrderDialogPage();
        expect(packageOrderDialogPage.getModalTitle()).toMatch(/Create or edit a Package Order/);
        packageOrderDialogPage.close();
    });

    it('should create and save PackageOrders', () => {
        packageOrderComponentsPage.clickOnCreateButton();
        packageOrderDialogPage.setRateInput('5');
        expect(packageOrderDialogPage.getRateInput()).toMatch('5');
        packageOrderDialogPage.setTotalAmountInput('5');
        expect(packageOrderDialogPage.getTotalAmountInput()).toMatch('5');
        packageOrderDialogPage.setQuantityInput('5');
        expect(packageOrderDialogPage.getQuantityInput()).toMatch('5');
        packageOrderDialogPage.setProfileSubjectIdInput('5');
        expect(packageOrderDialogPage.getProfileSubjectIdInput()).toMatch('5');
        packageOrderDialogPage.statusSelectLastOption();
        packageOrderDialogPage.setCreatedDateInput(12310020012301);
        expect(packageOrderDialogPage.getCreatedDateInput()).toMatch('2001-12-31T02:30');
        packageOrderDialogPage.setUpdatedDateInput(12310020012301);
        expect(packageOrderDialogPage.getUpdatedDateInput()).toMatch('2001-12-31T02:30');
        packageOrderDialogPage.setStudentIdInput('5');
        expect(packageOrderDialogPage.getStudentIdInput()).toMatch('5');
        packageOrderDialogPage.save();
        expect(packageOrderDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PackageOrderComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-package-order div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PackageOrderDialogPage {
    modalTitle = element(by.css('h4#myPackageOrderLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    rateInput = element(by.css('input#field_rate'));
    totalAmountInput = element(by.css('input#field_totalAmount'));
    quantityInput = element(by.css('input#field_quantity'));
    profileSubjectIdInput = element(by.css('input#field_profileSubjectId'));
    statusSelect = element(by.css('select#field_status'));
    createdDateInput = element(by.css('input#field_createdDate'));
    updatedDateInput = element(by.css('input#field_updatedDate'));
    studentIdInput = element(by.css('input#field_studentId'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setRateInput = function (rate) {
        this.rateInput.sendKeys(rate);
    }

    getRateInput = function () {
        return this.rateInput.getAttribute('value');
    }

    setTotalAmountInput = function (totalAmount) {
        this.totalAmountInput.sendKeys(totalAmount);
    }

    getTotalAmountInput = function () {
        return this.totalAmountInput.getAttribute('value');
    }

    setQuantityInput = function (quantity) {
        this.quantityInput.sendKeys(quantity);
    }

    getQuantityInput = function () {
        return this.quantityInput.getAttribute('value');
    }

    setProfileSubjectIdInput = function (profileSubjectId) {
        this.profileSubjectIdInput.sendKeys(profileSubjectId);
    }

    getProfileSubjectIdInput = function () {
        return this.profileSubjectIdInput.getAttribute('value');
    }

    setStatusSelect = function (status) {
        this.statusSelect.sendKeys(status);
    }

    getStatusSelect = function () {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    statusSelectLastOption = function () {
        this.statusSelect.all(by.tagName('option')).last().click();
    }
    setCreatedDateInput = function (createdDate) {
        this.createdDateInput.sendKeys(createdDate);
    }

    getCreatedDateInput = function () {
        return this.createdDateInput.getAttribute('value');
    }

    setUpdatedDateInput = function (updatedDate) {
        this.updatedDateInput.sendKeys(updatedDate);
    }

    getUpdatedDateInput = function () {
        return this.updatedDateInput.getAttribute('value');
    }

    setStudentIdInput = function (studentId) {
        this.studentIdInput.sendKeys(studentId);
    }

    getStudentIdInput = function () {
        return this.studentIdInput.getAttribute('value');
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
