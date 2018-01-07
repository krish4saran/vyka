import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Level e2e test', () => {

    let navBarPage: NavBarPage;
    let levelDialogPage: LevelDialogPage;
    let levelComponentsPage: LevelComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Levels', () => {
        navBarPage.goToEntity('level');
        levelComponentsPage = new LevelComponentsPage();
        expect(levelComponentsPage.getTitle()).toMatch(/Levels/);

    });

    it('should load create Level dialog', () => {
        levelComponentsPage.clickOnCreateButton();
        levelDialogPage = new LevelDialogPage();
        expect(levelDialogPage.getModalTitle()).toMatch(/Create or edit a Level/);
        levelDialogPage.close();
    });

    it('should create and save Levels', () => {
        levelComponentsPage.clickOnCreateButton();
        levelDialogPage.levelSelectLastOption();
        levelDialogPage.profileSubjectSelectLastOption();
        levelDialogPage.save();
        expect(levelDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LevelComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-level div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class LevelDialogPage {
    modalTitle = element(by.css('h4#myLevelLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    levelSelect = element(by.css('select#field_level'));
    profileSubjectSelect = element(by.css('select#field_profileSubject'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setLevelSelect = function(level) {
        this.levelSelect.sendKeys(level);
    }

    getLevelSelect = function() {
        return this.levelSelect.element(by.css('option:checked')).getText();
    }

    levelSelectLastOption = function() {
        this.levelSelect.all(by.tagName('option')).last().click();
    }
    profileSubjectSelectLastOption = function() {
        this.profileSubjectSelect.all(by.tagName('option')).last().click();
    }

    profileSubjectSelectOption = function(option) {
        this.profileSubjectSelect.sendKeys(option);
    }

    getProfileSubjectSelect = function() {
        return this.profileSubjectSelect;
    }

    getProfileSubjectSelectedOption = function() {
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
