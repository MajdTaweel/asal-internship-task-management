import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskmanagementTestModule } from '../../../test.module';
import { ReleaseUpdateComponent } from 'app/entities/release/release-update.component';
import { ReleaseService } from 'app/entities/release/release.service';
import { Release } from 'app/shared/model/release.model';

describe('Component Tests', () => {
  describe('Release Management Update Component', () => {
    let comp: ReleaseUpdateComponent;
    let fixture: ComponentFixture<ReleaseUpdateComponent>;
    let service: ReleaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskmanagementTestModule],
        declarations: [ReleaseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReleaseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReleaseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReleaseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Release('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Release();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
