import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProgramadoresService } from 'src/app/service/programadores.service';

import { ProgramadoresMaestroComponent } from './programadores-maestro.component';

describe('ProgramadoresMaestroComponent', () => {
  let component: ProgramadoresMaestroComponent;
  let fixture: ComponentFixture<ProgramadoresMaestroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProgramadoresMaestroComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgramadoresMaestroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
