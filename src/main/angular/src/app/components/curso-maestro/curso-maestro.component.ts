import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalBorrarComponent } from 'src/app/modal/modal-borrar/modal-borrar.component';
import { Curso } from 'src/app/model/curso';
import { CursoService } from 'src/app/service/curso.service';

@Component({
  selector: 'app-curso-maestro',
  templateUrl: './curso-maestro.component.html',
  styleUrls: ['./curso-maestro.component.css']
})
export class CursoMaestroComponent implements OnInit {

  cursos: Curso[] = [];

  constructor(private cursoService: CursoService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getCursos();
  }

  getCursos(): void {
    this.cursoService.getCursos()
      .subscribe(
        res => this.cursos = res
      );
  }

  ver(): void {

  }

  baja(curso: Curso): void {

    this.modalService.open(ModalBorrarComponent)
      .result.then(
        () => {
          if (curso.id) {
            this.cursoService.bajaCurso(curso.id)
            .subscribe(() => curso.activo = false)
          }
        }
      );
  }

  alta(curso: Curso): void {

    this.modalService.open(ModalBorrarComponent)
      .result.then(
        () => {
          if (curso.id) {
            this.cursoService.altaCurso(curso.id)
            .subscribe(() => curso.activo = true)
          }
        }
      );
  }

}
