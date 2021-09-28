import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TematicaMaestroComponent } from './tematica-maestro.component';

describe('TematicaMaestroComponent', () => {
  let component: TematicaMaestroComponent;
  let fixture: ComponentFixture<TematicaMaestroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TematicaMaestroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TematicaMaestroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
