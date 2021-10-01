import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbActiveModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { CursoProgramado } from 'src/app/model/programador';
import { ProgramadoresService } from 'src/app/service/programadores.service';
import { ToastContainerComponent } from 'src/app/toast/toast-container/toast-container.component';

import { ModalProgramadorComponent } from './modal-programador.component';

describe('ModalProgramadorComponent', () => {
  let component: ModalProgramadorComponent;
  let fixture: ComponentFixture<ModalProgramadorComponent>;

  let ngActiveModal = new NgbActiveModal();

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalProgramadorComponent,
      ToastContainerComponent ],
      imports: [HttpClientTestingModule,
      NgbModalModule,
    ReactiveFormsModule],
      providers: [
        {provide: NgbActiveModal}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalProgramadorComponent);
    component = fixture.componentInstance;
    component.programadorInput= <CursoProgramado>{};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
