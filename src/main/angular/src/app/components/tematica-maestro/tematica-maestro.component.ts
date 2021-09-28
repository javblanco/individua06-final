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

  constructor(private tematicaService: TematicaService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getTematicas();
  }

  getTematicas(): void {
    this.tematicaService.getTematicas()
      .subscribe(
        res => this.tematicas = res
      );
  }

  ver(): void {

  }

  eliminar(tematica: Tematica): void {

    this.modalService.open(ModalBorrarComponent)
      .result.then(
        () => {
          if (tematica.id) {
            this.tematicaService.deleteTematica(tematica.id)
            .subscribe(() => this.tematicas = this.tematicas.filter(h => h.id !== tematica.id))
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
            .subscribe(() => tematica.activo = false)
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
            .subscribe(() => tematica.activo = true)
          }
        }
      );
  }
}
