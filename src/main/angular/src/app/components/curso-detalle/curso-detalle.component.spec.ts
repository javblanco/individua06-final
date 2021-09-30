import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { Categoria } from 'src/app/model/categoria';
import { Curso } from 'src/app/model/curso';
import { Tematica } from 'src/app/model/tematica';
import { CursoService } from 'src/app/service/curso.service';
import { TematicaService } from 'src/app/service/tematica.service';

import { CursoDetalleComponent } from './curso-detalle.component';

describe('CursoDetalleComponent', () => {
  let component: CursoDetalleComponent;
  let fixture: ComponentFixture<CursoDetalleComponent>;

  const tematicas: Tematica[] = [
    {id:1, nombre: 'Informática', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false},
    {id:2, nombre: 'Informática2', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false}, 
    {id:3, nombre: 'Informática3', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false}
  ]
  const cursos: Curso[] = [
    {id:1, nombre: 'Big Data1', descripcion: 'Big data y análisis', activo: true, cantidadAlumnos: 20, certificacion: true, duracion: 300, idTematica: 5, nombreTematica: 'Tecnología', numeroTemas: 5, precio:54.65},
    {id:2, nombre: 'Big Data2', descripcion: 'Big data y análisis', activo: true, cantidadAlumnos: 20, certificacion: true, duracion: 300, idTematica: 5, nombreTematica: 'Tecnología', numeroTemas: 5, precio:54.65},
    {id:3, nombre: 'Big Data3', descripcion: 'Big data y análisis', activo: true, cantidadAlumnos: 20, certificacion: true, duracion: 300, idTematica: 5, nombreTematica: 'Tecnología', numeroTemas: 5, precio:54.65}

  ]

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CursoDetalleComponent ],
      imports: [RouterTestingModule,
        ReactiveFormsModule],
        providers: [
          { provide: ComponentFixtureAutoDetect, useValue: true },
          {provide: NgbActiveModal},
          {
            provide: CursoService,
              useValue: jasmine.createSpyObj('CursoService', ['getCurso'])
          }  ,
          {
            provide: TematicaService,
              useValue: jasmine.createSpyObj('TematicaService', ['getTematicasActivas'])
          }
        ]
    })
    .compileComponents();

    let tematicapyService = TestBed.get(TematicaService);

    tematicapyService.getTematicasActivas.and.returnValue(of(tematicas));

    let cursoSpyService = TestBed.get(CursoService);

    cursoSpyService.getCurso.and.returnValue(of(cursos[0]))
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CursoDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Los inputs deberían estar vacios', async () => {
    component.curso = <Curso>{};
    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('input#curso-nombre')?.value).toBe('');
 
     });
  });

  it('El input de nombre debería ser "Big Data1"', async () => {
    component.curso = cursos[0];
    component.cursoForm.patchValue(
      {id: component.curso.id,
      nombre: component.curso.nombre,
      tematicas: component.curso.idTematica,
      descripcion: component.curso.descripcion,
      duracion: component.curso.duracion,
      cantidadAlumnos: component.curso.cantidadAlumnos,
      numeroTemas: component.curso.numeroTemas,
      certificacion: component.curso.certificacion,
      precio: component.curso.precio? component.curso.precio.toFixed(2) : ''
    });
    let compile = fixture.nativeElement;

    fixture.detectChanges();

    fixture.whenStable().then(async () => {
      await expect(compile.querySelector('input#curso-nombre')?.value).toBe('Big Data1');
 
     });
  });
});
