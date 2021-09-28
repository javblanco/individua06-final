import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalGuardarComponent } from 'src/app/modal/modal-guardar/modal-guardar.component';
import { ModalSalirComponent } from 'src/app/modal/modal-salir/modal-salir.component';
import { Categoria } from 'src/app/model/categoria';

import { Tematica } from 'src/app/model/tematica';
import { TematicaService } from 'src/app/service/tematica.service';

@Component({
  selector: 'app-tematica-detalle',
  templateUrl: './tematica-detalle.component.html',
  styleUrls: ['./tematica-detalle.component.css']
})
export class TematicaDetalleComponent implements OnInit {

  tematica = <Tematica> {};
  categorias = Object.values(Categoria);

  tematicaForm = this.fb.group({
    id: [''],
    nombre: [''],
    referencia: [''],
    descripcion: [''],
    categoria: [undefined, Categoria],
    subtematica: ['']
  });

  mensaje?: string;

  mensajeError?: string;

  listaSubtematicas: string[] = [];

  constructor(
    private tematicaService: TematicaService,
    private location: Location,
    private routes: ActivatedRoute,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.getTematica();
  }

  getTematica(): void {
    const id = Number(this.routes.snapshot.paramMap.get('id'));

    if(id) {
      this.tematicaService.getTematica(id)
      .subscribe(res => 
       
        {
          this.tematica = res;
          if(res.listaSubtematicas) {
          this.listaSubtematicas = res.listaSubtematicas;
          this.setValue();
          }
        })
    }
  }


  setValue(): void {
    if(this.tematica.id) {
    this.tematicaForm.patchValue(
      {id: this.tematica.id,
      nombre: this.tematica.nombre,
      referencia: this.tematica.referencia,
      descripcion: this.tematica.descripcion,
      categoria: this.tematica.categoria,
      subtematica: ['']}
      );
    }
  }

  addSubtematica(): void {
    let subtematica = this.tematicaForm.controls['subtematica'].value;
    if(subtematica) {
      this.listaSubtematicas.push(subtematica);
      this.tematicaForm.controls['subtematica'].setValue('');
    }
    
  }

  eliminar(index: any): void {
    this.listaSubtematicas.splice(index, 1);
  }

  guardar(): void {
    this.modalService.open(ModalGuardarComponent)
    .result.then(
      () => {
        this.tematica = this.tematicaForm.value;
        this.tematica.listaSubtematicas = this.listaSubtematicas;
        if(this.tematica.id) {
          this.modificar();
        } else {
          this.crear();
        }
      }
    );
  }

  salir(): void {
    this.modalService.open(ModalSalirComponent)
    .result.then(
      () => this.location.back()
    );
  }

  modificar(): void {
    this.tematicaService.updateTematica(this.tematica)
    .subscribe(() => this.mensaje = "Se ha actualizado la entrada.");
  }

  crear(): void {
    this.tematicaService.createTematica(this.tematica)
    .subscribe(
      res =>{
        try {
          this.tematica.id = res;
          this.mensaje = "Se ha creado la entrada";
        } catch (error) {
          console.log(error)
        }
         
      }
    );
  }
}
