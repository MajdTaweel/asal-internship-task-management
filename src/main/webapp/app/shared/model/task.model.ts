import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITask {
  id?: string;
  title?: string;
  status?: TaskStatus;
  description?: string;
  deadline?: Moment;
  assignees?: IUser[];
  releaseId?: string;
}

export class Task implements ITask {
  constructor(
    public id?: string,
    public title?: string,
    public status?: TaskStatus,
    public description?: string,
    public deadline?: Moment,
    public assignees?: IUser[],
    public releaseId?: string
  ) {}
}
