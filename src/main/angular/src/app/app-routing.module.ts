import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './components/inicio/inicio.component';
import { TematicaDetalleComponent } from './components/tematica-detalle/tematica-detalle.component';
import { TematicaMaestroComponent } from './components/tematica-maestro/tematica-maestro.component';

const routes: Routes = [
  {path: 'index', component: InicioComponent},
  {path: '', redirectTo:'index', pathMatch: 'full'},
  {path: 'tematica', children: [
    {path: 'list', component: TematicaMaestroComponent},
    {path: ':id', component: TematicaDetalleComponent},
    {path: '', component: TematicaDetalleComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
