import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { Categoria } from 'src/app/model/categoria';
import { Tematica } from 'src/app/model/tematica';
import { CursoService } from 'src/app/service/curso.service';
import { TematicaService } from 'src/app/service/tematica.service';

import { TematicaMaestroComponent } from './tematica-maestro.component';

describe('TematicaMaestroComponent', () => {
  let component: TematicaMaestroComponent;
  let fixture: ComponentFixture<TematicaMaestroComponent>;

  const tematicas: Tematica[] = [
    {id:1, nombre: 'Informática', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false},
    {id:2, nombre: 'Informática2', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false}, 
    {id:3, nombre: 'Informática3', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false}
  ]
  

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TematicaMaestroComponent ],
      imports: [HttpClientTestingModule,
      RouterTestingModule],
       providers: [
        { provide: ComponentFixtureAutoDetect, useValue: true },
        {provide: NgbActiveModal},
        {
          provide: TematicaService,
            useValue: jasmine.createSpyObj('TematicaService', ['getTematicas'])
        } 
      ]
    })
    .compileComponents();

    let tematicapyService = TestBed.get(TematicaService);

    tematicapyService.getTematicas.and.returnValue(of(tematicas));

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TematicaMaestroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Debería haber 3 elementos', () => {
    fixture.detectChanges();
    
    let compile = fixture.nativeElement as HTMLElement;
    expect(compile.querySelectorAll('table tbody tr').length).toBe(3);

  })
});
