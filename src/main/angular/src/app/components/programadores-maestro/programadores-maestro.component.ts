import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalAltaComponent } from 'src/app/modal/modal-alta/modal-alta.component';
import { ModalBajaComponent } from 'src/app/modal/modal-baja/modal-baja.component';
import { CursoProgramado } from 'src/app/model/programador';
import { ProgramadoresService } from 'src/app/service/programadores.service';
import { ModalProgramadorComponent } from '../modal-programador/modal-programador.component';

@Component({
  selector: 'app-programadores-maestro',
  templateUrl: './programadores-maestro.component.html',
  styleUrls: ['./programadores-maestro.component.css']
})
export class ProgramadoresMaestroComponent implements OnInit {
  programadores: CursoProgramado[] = [];

  mensaje?: string;
  mensajeError?: string;

  constructor(private programadorService : ProgramadoresService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getProgramadores();

    this.mensajeError = '';
  }

  getProgramadores(): void {
    this.programadorService.getCursoProgramados()
    .subscribe(
      res => {
        this.programadores = res;
      },
      err => {this.mensajeError = err.message}
    );
  }

  programar() {
  this.modalService.open(ModalProgramadorComponent).result
  .then(() => {
    this.getProgramadores();
  });
  }

  abrir(programador: CursoProgramado) {
    if(!programador.inscripcion) {
      this.modalService.open(ModalBajaComponent)
      .result.then(() => {
        this.programadorService.abrirCursoProgramado(programador.id)
        .subscribe(() => 
        programador.inscripcion = true);
        this.mensajeError = '';
        this.mensaje = 'Se han abierto las inscripciones.';
      },
      err => {this.mensajeError = err.message});
    }
  }

  cerrar(programador: CursoProgramado) {
    if(programador.inscripcion) {
      this.modalService.open(ModalAltaComponent)
      .result.then(() => {
        this.programadorService.cerrarCursoProgramado(programador.id)
        .subscribe(() => 
        programador.inscripcion = false);
        this.mensajeError = '';
        this.mensaje = 'Se han cerrado las inscripciones.';
      },
      err => {this.mensajeError = err.message});
    }
  }

  borrar(programador: CursoProgramado) {
    this.modalService.open(ModalAltaComponent)
      .result.then(() => {
        this.programadorService.deleteCursoProgramado(programador.id)
        .subscribe(() => 
        this.getProgramadores());
        this.mensajeError = '';
        this.mensaje = 'Se ha borrado la programación del curso.';
      },
      err => {this.mensajeError = err.message});
  }

  modificar(programador: CursoProgramado) {
    let modal = this.modalService.open(ModalProgramadorComponent);
    modal.componentInstance.programadorInput = programador;

    modal.result
    .then(() => {
      this.getProgramadores();
    });
  }

}
