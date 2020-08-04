import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.TaskmanagementTaskModule),
      },
      {
        path: 'release',
        loadChildren: () => import('./release/release.module').then(m => m.TaskmanagementReleaseModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TaskmanagementEntityModule {}
