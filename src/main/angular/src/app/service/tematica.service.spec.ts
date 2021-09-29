import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing'

import { TematicaService } from './tematica.service';
import { Tematica } from '../model/tematica';
import { Categoria } from '../model/categoria';
import { of } from 'rxjs';

describe('TematicaService', () => {
  let service: TematicaService;
  let httpClientSpy: { get: jasmine.Spy };

  const tematicas: Tematica[] = [
    {id:1, nombre: 'Informática', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false},
    {id:2, nombre: 'Informática2', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false}, 
    {id:3, nombre: 'Informática3', descripcion: 'Esta temática es de informática', referencia: 'ABC-65', categoria: Categoria.tecnonologia,
    subtematicas: 'Big Data, Machine Learning', activo: true, eliminable: false}
  ]
  beforeEach(() => {
    TestBed.configureTestingModule({});
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    
    service = new TematicaService(httpClientSpy as any);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('El método getTematicas debería devolver 4 registros', () => {
    httpClientSpy.get.and.returnValue(of(tematicas));

    service.getTematicas().subscribe(
      resultado => expect(resultado.length).toBe(3)
    );
  });
});
