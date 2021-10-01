import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InicioComponent } from './components/inicio/inicio.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ModalGuardarComponent } from './modal/modal-guardar/modal-guardar.component';
import { ModalBorrarComponent } from './modal/modal-borrar/modal-borrar.component';
import { ModalBajaComponent } from './modal/modal-baja/modal-baja.component';
import { ModalAltaComponent } from './modal/modal-alta/modal-alta.component';
import { ModalSalirComponent } from './modal/modal-salir/modal-salir.component';
import { TematicaMaestroComponent } from './components/tematica-maestro/tematica-maestro.component';
import { TematicaDetalleComponent } from './components/tematica-detalle/tematica-detalle.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CursoMaestroComponent } from './components/curso-maestro/curso-maestro.component';
import { CursoDetalleComponent } from './components/curso-detalle/curso-detalle.component';
import { ValidarNumerosDirective } from './validation/validar-numeros.directive';
import { ProgramadoresMaestroComponent } from './components/programadores-maestro/programadores-maestro.component';
import { ModalProgramadorComponent } from './components/modal-programador/modal-programador.component';
import { ToastContainerComponent } from './toast/toast-container/toast-container.component';
import { InscripcionPipe } from './inscripcion.pipe';

@NgModule({
  declarations: [
    AppComponent,
    InicioComponent,
    NavbarComponent,
    ModalGuardarComponent,
    ModalBorrarComponent,
    ModalBajaComponent,
    ModalAltaComponent,
    ModalSalirComponent,
    TematicaMaestroComponent,
    TematicaDetalleComponent,
    CursoMaestroComponent,
    CursoDetalleComponent,
    ValidarNumerosDirective,
    ProgramadoresMaestroComponent,
    ModalProgramadorComponent,
    ToastContainerComponent,
    InscripcionPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
