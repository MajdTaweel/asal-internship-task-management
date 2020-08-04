import { Moment } from 'moment';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ITask {
  id?: string;
  title?: string;
  dateCreated?: Moment;
  createdBy?: string;
  status?: Status;
  deadline?: Moment;
  releaseId?: string;
}

export class Task implements ITask {
  constructor(
    public id?: string,
    public title?: string,
    public dateCreated?: Moment,
    public createdBy?: string,
    public status?: Status,
    public deadline?: Moment,
    public releaseId?: string
  ) {}
}
