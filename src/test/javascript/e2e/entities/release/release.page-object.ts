import { by, element, ElementFinder } from 'protractor';

export class ReleaseComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-release div table .btn-danger'));
  title = element.all(by.css('jhi-release div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ReleaseUpdatePage {
  pageTitle = element(by.id('jhi-release-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  titleInput = element(by.id('field_title'));
  typeInput = element(by.id('field_type'));
  statusSelect = element(by.id('field_status'));
  deadlineInput = element(by.id('field_deadline'));

  teamSelect = element(by.id('field_team'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async setDeadlineInput(deadline: string): Promise<void> {
    await this.deadlineInput.sendKeys(deadline);
  }

  async getDeadlineInput(): Promise<string> {
    return await this.deadlineInput.getAttribute('value');
  }

  async teamSelectLastOption(): Promise<void> {
    await this.teamSelect.all(by.tagName('option')).last().click();
  }

  async teamSelectOption(option: string): Promise<void> {
    await this.teamSelect.sendKeys(option);
  }

  getTeamSelect(): ElementFinder {
    return this.teamSelect;
  }

  async getTeamSelectedOption(): Promise<string> {
    return await this.teamSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ReleaseDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-release-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-release'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
