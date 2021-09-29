import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalBorrarComponent } from 'src/app/modal/modal-borrar/modal-borrar.component';
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

  lectura = false; 

  tematica = <Tematica> {};
  categorias = Object.values(Categoria);

  tematicaForm = this.fb.group({
    id: [''],
    nombre: ['', [Validators.required, Validators.maxLength(50)]],
    referencia: ['', [Validators.required, Validators.maxLength(50)]],
    descripcion: ['', [Validators.maxLength(1000)]],
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
    this.isLectura();
  }

  get nombre() {return this.tematicaForm.get('nombre');}
  get descripcion() {return this.tematicaForm.get('descripcion');}
  get referencia() {return this.tematicaForm.get('referencia');}


  isLectura(): void {
    if(this.tematicaService.fromVer) {
      this.tematicaService.fromVer = false;
      this.lectura = this.tematicaService.lectura;
      if(this.lectura) {
        this.tematicaForm.disable();
      }
    }
    
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
    this.modalService.open(ModalBorrarComponent)
    .result.then(
      () => this.listaSubtematicas.splice(index, 1)
    );
    
  }

  guardar(): void {
    if(this.tematicaForm.valid){
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
    } else {
      this.mensajeError = 'Compruebe que haya rellenado el formulario correctamente.';
    }

   
  }

  salir(): void {
    this.modalService.open(ModalSalirComponent)
    .result.then(
      () => {
        this.location.back();
        this.tematicaService.lectura = false;
      });
  }

  modificar(): void {
    this.tematicaService.updateTematica(this.tematica)
    .subscribe(() => {
      this.mensaje = 'Se ha actualizado la entrada.';
      this.mensajeError = '';
    });
  }

  crear(): void {
    this.tematicaService.createTematica(this.tematica)
    .subscribe(
      res =>{
        try {
          this.tematica.id = res;
          this.mensaje = 'Se ha creado la entrada';
          this.mensajeError = '';
        } catch (error) {
          console.log(error)
        }
         
      }
    );
  }
}
