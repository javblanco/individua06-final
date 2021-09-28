import { TestBed } from '@angular/core/testing';

import { HttpClientTestingModule } from '@angular/common/http/testing'

import { TematicaService } from './tematica.service';

describe('TematicaService', () => {
  let service: TematicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(TematicaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
