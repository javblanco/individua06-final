import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalGuardarComponent } from 'src/app/modal/modal-guardar/modal-guardar.component';
import { ModalSalirComponent } from 'src/app/modal/modal-salir/modal-salir.component';
import { Curso } from 'src/app/model/curso';
import { Tematica } from 'src/app/model/tematica';
import { CursoService } from 'src/app/service/curso.service';
import { TematicaService } from 'src/app/service/tematica.service';

@Component({
  selector: 'app-curso-detalle',
  templateUrl: './curso-detalle.component.html',
  styleUrls: ['./curso-detalle.component.css']
})
export class CursoDetalleComponent implements OnInit {

  curso = <Curso> {};
  tematicas: Tematica[] = [];

  cursoForm = this.fb.group({
    id: [''],
    nombre: [''],
    tematicas: [undefined, this.tematicas],
    descripcion: [''],
    duracion: [''],
    cantidadAlumnos: [''],
    numeroTemas: [''],
    certificacion: false,
    precio: ['']
  });

  mensaje?: string;

  mensajeError?: string;


  constructor(
    private cursoService: CursoService,
    private tematicaService: TematicaService,
    private location: Location,
    private routes: ActivatedRoute,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.getTematicas();
    this.getCurso();
  }

  getTematicas(): void {
    this.tematicaService.getTematicasActivas()
    .subscribe(res => this.tematicas = res);
  }

  getCurso(): void {
    const id = Number(this.routes.snapshot.paramMap.get('id'));

    if(id) {
      this.cursoService.getCurso(id)
      .subscribe(res =>        
        {
          this.curso = res;
          this.setValue();          
        })
    }
  }


  setValue(): void {
    if(this.curso.id) {
    this.cursoForm.patchValue(
      {id: this.curso.id,
      nombre: this.curso.nombre,
      tematicas: this.curso.idTematica,
      descripcion: this.curso.descripcion,
      duracion: this.curso.duracion,
      cantidadAlumnos: this.curso.cantidadAlumnos,
      numeroTemas: this.curso.numeroTemas,
      certificacion: this.curso.certificacion,
      precio: this.curso.precio.toFixed(2)
    });
    }
  }


  guardar(): void {
    this.modalService.open(ModalGuardarComponent)
    .result.then(
      () => {
        this.curso = this.cursoForm.value;
        if(this.curso.id) {
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
    this.cursoService.updateCurso(this.curso)
    .subscribe(() => this.mensaje = "Se ha actualizado la entrada.");
  }

  crear(): void {
    this.cursoService.createCurso(this.curso)
    .subscribe(
      res =>{
        try {
          this.curso.id = res;
          this.mensaje = "Se ha creado la entrada";
        } catch (error) {
          console.log(error)
        }
         
      }
    );
  }

}
