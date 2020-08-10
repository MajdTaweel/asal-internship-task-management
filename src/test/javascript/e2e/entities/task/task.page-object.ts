import { by, element, ElementFinder } from 'protractor';

export class TaskComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-task div table .btn-danger'));
  title = element.all(by.css('jhi-task div h2#page-heading span')).first();
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

export class TaskUpdatePage {
  pageTitle = element(by.id('jhi-task-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  titleInput = element(by.id('field_title'));
  statusSelect = element(by.id('field_status'));
  descriptionInput = element(by.id('field_description'));
  deadlineInput = element(by.id('field_deadline'));

  assigneeSelect = element(by.id('field_assignee'));
  releaseSelect = element(by.id('field_release'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
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

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setDeadlineInput(deadline: string): Promise<void> {
    await this.deadlineInput.sendKeys(deadline);
  }

  async getDeadlineInput(): Promise<string> {
    return await this.deadlineInput.getAttribute('value');
  }

  async assigneeSelectLastOption(): Promise<void> {
    await this.assigneeSelect.all(by.tagName('option')).last().click();
  }

  async assigneeSelectOption(option: string): Promise<void> {
    await this.assigneeSelect.sendKeys(option);
  }

  getAssigneeSelect(): ElementFinder {
    return this.assigneeSelect;
  }

  async getAssigneeSelectedOption(): Promise<string> {
    return await this.assigneeSelect.element(by.css('option:checked')).getText();
  }

  async releaseSelectLastOption(): Promise<void> {
    await this.releaseSelect.all(by.tagName('option')).last().click();
  }

  async releaseSelectOption(option: string): Promise<void> {
    await this.releaseSelect.sendKeys(option);
  }

  getReleaseSelect(): ElementFinder {
    return this.releaseSelect;
  }

  async getReleaseSelectedOption(): Promise<string> {
    return await this.releaseSelect.element(by.css('option:checked')).getText();
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

export class TaskDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-task-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-task'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
