import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { CursoProgramado } from '../model/programador';

@Injectable({
  providedIn: 'root'
})
export class ProgramadoresService {

  url = `${environment.host}/api/programacion`;

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  getCursoProgramados(): Observable<CursoProgramado[]> {
    return this.http.get<CursoProgramado[]>(this.url);
  }

  getCursoProgramado(id: number): Observable<CursoProgramado> {
    const urlId = `${this.url}/${id}`;

    return this.http.get<CursoProgramado>(urlId).pipe(
      catchError(err => {throw new Error(err.error)})
    );
  }

  createCursoProgramado(tematica: CursoProgramado): Observable<number> {
    return this.http.post<number>(this.url, tematica, this.options).pipe(
      catchError(err => {throw new Error(err.error)})
    );
  }

  updateCursoProgramado(tematica: CursoProgramado): Observable<any> {
    return this.http.put<any>(this.url, tematica, this.options).pipe(
      catchError(err => {throw new Error(err.error)})
    );
  }

  deleteCursoProgramado(id: number): Observable<any> {
    const idUrl = `${this.url}/${id}`;

    return this.http.delete<any>(idUrl).pipe(
      catchError(err => {throw new Error(err.error)})
    );
  }

  abrirCursoProgramado(id:number): Observable<any> {
    const abrirUrl = `${this.url}/abrir/${id}`;

    return this.http.post<any>(abrirUrl, null).pipe(
      catchError(err => {throw new Error(err.error)})
    );
  }

  cerrarCursoProgramado(id:number): Observable<any> {
    const cerrarUrl = `${this.url}/cerrar/${id}`;

    return this.http.post<any>(cerrarUrl, null).pipe(
      catchError(err => {throw new Error(err.error)})
    );
  }
}
