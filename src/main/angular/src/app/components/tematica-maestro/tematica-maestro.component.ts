import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalAltaComponent } from 'src/app/modal/modal-alta/modal-alta.component';
import { ModalBajaComponent } from 'src/app/modal/modal-baja/modal-baja.component';
import { ModalBorrarComponent } from 'src/app/modal/modal-borrar/modal-borrar.component';
import { Tematica } from 'src/app/model/tematica';
import { TematicaService } from 'src/app/service/tematica.service';

@Component({
  selector: 'app-tematica-maestro',
  templateUrl: './tematica-maestro.component.html',
  styleUrls: ['./tematica-maestro.component.css']
})
export class TematicaMaestroComponent implements OnInit {

  tematicas: Tematica[] = [];

  mensajeError?: string;
  mensaje?: string;

  constructor(private tematicaService: TematicaService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getTematicas();
    this.tematicaService.lectura = false;
    this.tematicaService.fromVer = false;
  }

  getTematicas(): void {
    this.tematicaService.getTematicas()
      .subscribe(
        res => this.tematicas = res,
        err => this.mensajeError = err.message
      );
  }

  ver(): void {
    this.tematicaService.lectura = true;
    this.tematicaService.fromVer = true;
  }

  eliminar(tematica: Tematica): void {

    this.modalService.open(ModalBorrarComponent)
      .result.then(
        () => {
          if (tematica.id) {
            this.tematicaService.deleteTematica(tematica.id)
            .subscribe(() => {this.tematicas = this.tematicas.filter(h => h.id !== tematica.id);
              this.mensaje = `Se ha eliminado la temática: ${tematica.nombre}`;
              this.mensajeError = '';},
        err => this.mensajeError = err.message)
          }
        }
      );
  }


  baja(tematica: Tematica): void {

    this.modalService.open(ModalBorrarComponent)
      .result.then(
        () => {
          if (tematica.id) {
            this.tematicaService.bajaTematica(tematica.id)
            .subscribe(() => 
            {tematica.activo = false;
              this.mensaje = `Se ha dado de baja la temática: ${tematica.nombre}`;
              this.mensajeError = '';},
        err => this.mensajeError = err.message)
          }
        }
      );
  }

  alta(tematica: Tematica): void {

    this.modalService.open(ModalBorrarComponent)
      .result.then(
        () => {
          if (tematica.id) {
            this.tematicaService.altaTematica(tematica.id)
            .subscribe(() => {tematica.activo = true;
              this.mensaje = `Se ha dado de alta la temática: ${tematica.nombre}`;
              this.mensajeError = '';
            },
            err => this.mensajeError = err.message)
          }
        }
      );
  }
}
