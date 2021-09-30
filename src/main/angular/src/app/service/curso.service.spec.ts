import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Curso } from '../model/curso';

import { CursoService } from './curso.service';

describe('CursoService', () => {
  let service: CursoService;

  let httpClientSpy: { get: jasmine.Spy };

  const cursos: Curso[] = [
    {id:1, nombre: 'Big Data1', descripcion: 'Big data y análisis', activo: true, cantidadAlumnos: 20, certificacion: true, duracion: 300, idTematica: 5, nombreTematica: 'Tecnología', numeroTemas: 5, precio:54.65},
    {id:2, nombre: 'Big Data2', descripcion: 'Big data y análisis', activo: true, cantidadAlumnos: 20, certificacion: true, duracion: 300, idTematica: 5, nombreTematica: 'Tecnología', numeroTemas: 5, precio:54.65},
    {id:3, nombre: 'Big Data3', descripcion: 'Big data y análisis', activo: true, cantidadAlumnos: 20, certificacion: true, duracion: 300, idTematica: 5, nombreTematica: 'Tecnología', numeroTemas: 5, precio:54.65}

  ]

  beforeEach(() => {
    TestBed.configureTestingModule({});
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    
    service = new CursoService(httpClientSpy as any);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('El método getCursos debería devolver 4 registros', () => {
    httpClientSpy.get.and.returnValue(of(cursos));

    service.getCursos().subscribe(
      resultado => expect(resultado.length).toBe(3)
    );
  });
});
