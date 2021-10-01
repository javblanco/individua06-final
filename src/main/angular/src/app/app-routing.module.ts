import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CursoDetalleComponent } from './components/curso-detalle/curso-detalle.component';
import { CursoMaestroComponent } from './components/curso-maestro/curso-maestro.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { ProgramadoresMaestroComponent } from './components/programadores-maestro/programadores-maestro.component';
import { TematicaDetalleComponent } from './components/tematica-detalle/tematica-detalle.component';
import { TematicaMaestroComponent } from './components/tematica-maestro/tematica-maestro.component';

const routes: Routes = [
  {path: 'index', component: InicioComponent},
  {path: '', redirectTo:'index', pathMatch: 'full'},
  {path: 'tematica', children: [
    {path: 'list', component: TematicaMaestroComponent},
    {path: ':id', component: TematicaDetalleComponent},
    {path: '', component: TematicaDetalleComponent}
  ]},
  {path: 'curso', children: [
    {path: 'list', component: CursoMaestroComponent},
    {path: 'programados', component: ProgramadoresMaestroComponent},
    {path: ':id', component: CursoDetalleComponent},
    {path: '', component: CursoDetalleComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
