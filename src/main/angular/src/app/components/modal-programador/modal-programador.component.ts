import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Curso } from 'src/app/model/curso';

import { CursoProgramado } from 'src/app/model/programador';
import { CursoService } from 'src/app/service/curso.service';
import { ProgramadoresService } from 'src/app/service/programadores.service';
import { ToastService } from 'src/app/service/toast.service';

@Component({
  selector: 'app-modal-programador',
  templateUrl: './modal-programador.component.html',
  styleUrls: ['./modal-programador.component.css']
})
export class ModalProgramadorComponent implements OnInit {

  cursos: Curso[] = [];

  nombreCurso = '';

  @Input() programadorInput?: CursoProgramado;

  programador = <CursoProgramado>{};


  programadorForm = this.fb.group({
    nombreCurso: [undefined, this.cursos],
    fechaInicio: ['', Validators.required],
    fechaFin: [''],
    inscripcion: [false]
  });

  constructor(public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private cursoService: CursoService,
    private programadorService: ProgramadoresService,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.getCursos();
    this.getProgramador();
    if(this.programador.id) {
      this.setValue();
    }
    
  }

  getCursos(): void {
    this.cursoService.getCursosActivos()
    .subscribe(
      res =>  {
        this.cursos = res;
      },
      err => {
        let mensajeError = err.status == 500? "Ha ocurrido un error al cargar los datos" : err.message;

        this.toastService.show(mensajeError, { classname: 'bg-danger text-light', delay: 10000 })}
    )
  }

  get nombre() { return this.programadorForm.get('nombreCurso')}
  get inscripcion() { return this.programadorForm.get('inscripcion')}
  get fechaInicio() { return this.programadorForm.get('fechaInicio')}

  guardar() :void {
  if(this.programador.id) {
    let idCurso = this.programador.idCurso
    let id = this.programador.id;
    this.programador = this.programadorForm.value;
    this.programador.idCurso = idCurso;
    this.programador.id = id;
    this.modificar();
  } else {
    this.programador = this.programadorForm.value;
    this.programador.idCurso = this.nombre?.value;
    this.crear();
  }
  
  }

  setValue(): void {
    this.programadorForm.patchValue({
      id: this.programador.id,
      nombreCurso: this.programador.nombreCurso,
      fechaInicio: this.programador.fechaInicio,
      fechaFin: this.programador.fechaFin,
      inscripcion: this.programador.inscripcion
    });
  }

  getProgramador():void {
    this.programador = this.programadorInput ? this.programadorInput : <CursoProgramado>{};
  }

  crear() : void {
    this.programadorService.createCursoProgramado(this.programador)
    .subscribe(
      res => {
        this.programador.id = res;
          this.toastService.show('Se ha programado el curso.', { classname: 'bg-success text-light', delay: 10000 });
        
      },
      err => {let mensajeError = this.getMensajeError(err);
         this.toastService.show(mensajeError, { classname: 'bg-danger text-light', delay: 10000 })}
    )
  }

  modificar() : void {
    this.programadorService.updateCursoProgramado(this.programador)
    .subscribe(
      res => {
        
        this.toastService.show('Se ha modificado la programación del curso.', { classname: 'bg-success text-light', delay: 10000 });
      },
      err => { let mensajeError = this.getMensajeError(err);
        this.toastService.show(mensajeError, { classname: 'bg-danger text-light', delay: 10000 })}
    );
  }


  getMensajeError(err: any): string {
    if(this.programadorForm.invalid) {
      if(this.nombre?.errors) {
        return "Seleccione el curso a programar";
      }
      if(this.fechaInicio?.errors) {
        return "Debe introducir una fecha de inicio";
      }
      return err.status == 500? "Ha ocurrido un error al cargar los datos" : err.message;
    }
    return err.status == 500? "Ha ocurrido un error al cargar los datos" : err.message;
  }
}
