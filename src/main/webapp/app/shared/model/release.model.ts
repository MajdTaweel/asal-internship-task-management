import { Moment } from 'moment';
import { ITask } from 'app/shared/model/task.model';
import { IUser } from 'app/core/user/user.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRelease {
  id?: string;
  title?: string;
  dateCreated?: string;
  createdBy?: string;
  type?: string;
  status?: Status;
  deadline?: Moment;
  tasks?: ITask[];
  users?: IUser[];
}

export class Release implements IRelease {
  constructor(
    public id?: string,
    public title?: string,
    public dateCreated?: string,
    public createdBy?: string,
    public type?: string,
    public status?: Status,
    public deadline?: Moment,
    public tasks?: ITask[],
    public users?: IUser[]
  ) {}
}
