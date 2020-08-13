import { Moment } from 'moment';
import { ITask } from 'app/shared/model/task.model';
import { IUser } from 'app/core/user/user.model';
import { ReleaseStatus } from 'app/shared/model/enumerations/release-status.model';

export interface IRelease {
  id?: string;
  title?: string;
  type?: string;
  status?: ReleaseStatus;
  deadline?: Moment;
  tasks?: ITask[];
  teams?: IUser[];
}

export class Release implements IRelease {
  constructor(
    public id?: string,
    public title?: string,
    public type?: string,
    public status?: ReleaseStatus,
    public deadline?: Moment,
    public tasks?: ITask[],
    public teams?: IUser[]
  ) {}
}
