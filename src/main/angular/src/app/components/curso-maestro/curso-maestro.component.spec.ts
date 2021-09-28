import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CursoMaestroComponent } from './curso-maestro.component';

describe('CursoMaestroComponent', () => {
  let component: CursoMaestroComponent;
  let fixture: ComponentFixture<CursoMaestroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursoMaestroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CursoMaestroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
