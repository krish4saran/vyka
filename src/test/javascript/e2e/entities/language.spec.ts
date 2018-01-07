import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Language e2e test', () => {

    let navBarPage: NavBarPage;
    let languageDialogPage: LanguageDialogPage;
    let languageComponentsPage: LanguageComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Languages', () => {
        navBarPage.goToEntity('language');
        languageComponentsPage = new LanguageComponentsPage();
        expect(languageComponentsPage.getTitle()).toMatch(/Languages/);

    });

    it('should load create Language dialog', () => {
        languageComponentsPage.clickOnCreateButton();
        languageDialogPage = new LanguageDialogPage();
        expect(languageDialogPage.getModalTitle()).toMatch(/Create or edit a Language/);
        languageDialogPage.close();
    });

    it('should create and save Languages', () => {
        languageComponentsPage.clickOnCreateButton();
        languageDialogPage.languageSelectLastOption();
        languageDialogPage.save();
        expect(languageDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LanguageComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-language div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class LanguageDialogPage {
    modalTitle = element(by.css('h4#myLanguageLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    languageSelect = element(by.css('select#field_language'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setLanguageSelect = function(language) {
        this.languageSelect.sendKeys(language);
    }

    getLanguageSelect = function() {
        return this.languageSelect.element(by.css('option:checked')).getText();
    }

    languageSelectLastOption = function() {
        this.languageSelect.all(by.tagName('option')).last().click();
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
