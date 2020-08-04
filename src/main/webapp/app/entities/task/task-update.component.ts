import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { IRelease } from 'app/shared/model/release.model';
import { ReleaseService } from 'app/entities/release/release.service';

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  releases: IRelease[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(255)]],
    dateCreated: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    status: [null, [Validators.required]],
    deadline: [],
    releaseId: [],
  });

  constructor(
    protected taskService: TaskService,
    protected releaseService: ReleaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      if (!task.id) {
        const today = moment().startOf('day');
        task.dateCreated = today;
        task.deadline = today;
      }

      this.updateForm(task);

      this.releaseService.query().subscribe((res: HttpResponse<IRelease[]>) => (this.releases = res.body || []));
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      title: task.title,
      dateCreated: task.dateCreated ? task.dateCreated.format(DATE_TIME_FORMAT) : null,
      createdBy: task.createdBy,
      status: task.status,
      deadline: task.deadline ? task.deadline.format(DATE_TIME_FORMAT) : null,
      releaseId: task.releaseId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  trackById(index: number, item: IRelease): any {
    return item.id;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value
        ? moment(this.editForm.get(['dateCreated'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      status: this.editForm.get(['status'])!.value,
      deadline: this.editForm.get(['deadline'])!.value ? moment(this.editForm.get(['deadline'])!.value, DATE_TIME_FORMAT) : undefined,
      releaseId: this.editForm.get(['releaseId'])!.value,
    };
  }
}
