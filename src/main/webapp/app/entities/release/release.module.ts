import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskmanagementSharedModule } from 'app/shared/shared.module';
import { ReleaseComponent } from './release.component';
import { ReleaseDetailComponent } from './release-detail.component';
import { ReleaseUpdateComponent } from './release-update.component';
import { ReleaseDeleteDialogComponent } from './release-delete-dialog.component';
import { releaseRoute } from './release.route';

@NgModule({
  imports: [TaskmanagementSharedModule, RouterModule.forChild(releaseRoute)],
  declarations: [ReleaseComponent, ReleaseDetailComponent, ReleaseUpdateComponent, ReleaseDeleteDialogComponent],
  entryComponents: [ReleaseDeleteDialogComponent],
})
export class TaskmanagementReleaseModule {}
