import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalGuardarComponent } from 'src/app/modal/modal-guardar/modal-guardar.component';
import { ModalSalirComponent } from 'src/app/modal/modal-salir/modal-salir.component';
import { Curso } from 'src/app/model/curso';
import { Tematica } from 'src/app/model/tematica';
import { CursoService } from 'src/app/service/curso.service';
import { TematicaService } from 'src/app/service/tematica.service';
import { ValidarNumerosDirective } from 'src/app/validation/validar-numeros.directive';

@Component({
  selector: 'app-curso-detalle',
  templateUrl: './curso-detalle.component.html',
  styleUrls: ['./curso-detalle.component.css']
})
export class CursoDetalleComponent implements OnInit {

  lectura = false;

  curso = <Curso> {};
  tematicas: Tematica[] = [];

  cursoForm = this.fb.group({
    id: [''],
    nombre: ['', [Validators.required, Validators.maxLength(50)]],
    tematicas: [undefined, this.tematicas],
    descripcion: ['', [Validators.maxLength(1000)]],
    duracion: ['', [Validators.required, new ValidarNumerosDirective().isEnteroValidator(), new ValidarNumerosDirective().isMayor0()]],
    cantidadAlumnos: ['', [new ValidarNumerosDirective().isEnteroValidator()]],
    numeroTemas: ['', [new ValidarNumerosDirective().isEnteroValidator()]],
    certificacion: false,
    precio: ['', [Validators.min(0), new ValidarNumerosDirective().isDecimal2Decimales()]]
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
    this.mensaje = '';
    this.mensajeError = '';
    this.getTematicas();
    this.getCurso();
    this.isLectura();
  }

  get nombre() {return this.cursoForm.get('nombre')};
  get tematica() {return this.cursoForm.get('tematicas')};
  get descripcion() {return this.cursoForm.get('descripcion')};
  get duracion() {return this.cursoForm.get('duracion')};
  get cantidadAlumnos() {return this.cursoForm.get('cantidadAlumnos')};
  get numeroTemas() {return this.cursoForm.get('numeroTemas')};
  get precio() {return this.cursoForm.get('precio')};

  isLectura(): void {
    if(this.cursoService.fromVer) {
      this.cursoService.fromVer = false;
      this.lectura = this.cursoService.lectura;
      if(this.lectura) {
        this.cursoForm.disable();
      }
    }
    
  }


  getTematicas(): void {
    const id = Number(this.routes.snapshot.paramMap.get('id'));

    if(id) {
      this.tematicaService.getTematicasActivasConCursoId(id)
      .subscribe(res => this.tematicas = res);
    } else {
      this.tematicaService.getTematicasActivas()
      .subscribe(res => this.tematicas = res);
    }
    
  }

  getCurso(): void {
    const id = Number(this.routes.snapshot.paramMap.get('id'));

    if(id) {
      this.cursoService.getCurso(id)
      .subscribe(res =>        
        {
          this.curso = res;
          this.setValue();          
        },
        err => this.mensajeError = err.message)
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
      precio: this.curso.precio? this.curso.precio.toFixed(2) : ''
    });
    }
  }


  guardar(): void {
      this.modalService.open(ModalGuardarComponent)
      .result.then(
        () => {
          this.curso = this.cursoForm.value;
          this.curso.idTematica = this.tematica? this.tematica.value : null;
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
      () => {
        this.location.back();
        this.cursoService.lectura = false;
      }
    );
  }

  modificar(): void {
    this.cursoService.updateCurso(this.curso)
    .subscribe(() => {
    this.mensaje = 'Se ha actualizado la entrada.';
    this.mensajeError = '';
    });
  }

  crear(): void {
    this.cursoService.createCurso(this.curso)
    .subscribe(
      res =>{
          this.curso.id = res;
          this.mensaje = 'Se ha creado la entrada';
          this.mensajeError = '';
        },
        err => {
          this.mensajeError = err.message;
        }
    );
  }

}
