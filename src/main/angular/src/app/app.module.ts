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
    TematicaDetalleComponent
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
