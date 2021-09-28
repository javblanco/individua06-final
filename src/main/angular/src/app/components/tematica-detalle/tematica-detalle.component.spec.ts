import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TematicaDetalleComponent } from './tematica-detalle.component';

describe('TematicaDetalleComponent', () => {
  let component: TematicaDetalleComponent;
  let fixture: ComponentFixture<TematicaDetalleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TematicaDetalleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TematicaDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
