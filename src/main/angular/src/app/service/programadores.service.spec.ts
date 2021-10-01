import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ProgramadoresService } from './programadores.service';

describe('ProgramadoresService', () => {
  let service: ProgramadoresService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ProgramadoresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
