import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { tap, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

import { Tematica } from '../model/tematica';

@Injectable({
  providedIn: 'root'
})
export class TematicaService {

  url = `${environment.host}/api/tematica`;

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  getTematicas(): Observable<Tematica[]> {
    return this.http.get<Tematica[]>(this.url);
  }

  getTematicasActivas(): Observable<Tematica[]> {
    const urlActivas = `${this.url}/activos`;

    return this.http.get<Tematica[]>(urlActivas);
  }

  getTematica(id: number): Observable<Tematica> {
    const urlId = `${this.url}/${id}`;

    return this.http.get<Tematica>(urlId);
  }

  createTematica(tematica: Tematica): Observable<number> {
    return this.http.post<number>(this.url, tematica, this.options)
    .pipe(
      catchError(err => {throw new Error(err.message)})
    );
  }

  updateTematica(tematica: Tematica): Observable<any> {
    return this.http.put<any>(this.url, tematica, this.options);
  }

  deleteTematica(id: number): Observable<any> {
    const idUrl = `${this.url}/${id}`;

    return this.http.delete<any>(idUrl);
  }

  bajaTematica(id:number): Observable<any> {
    const baja = `${this.url}/baja/${id}`;

    return this.http.post<any>(baja, null);
  }

  altaTematica(id:number): Observable<any> {
    const alta = `${this.url}/alta/${id}`;

    return this.http.post<any>(alta, null);
  }

  
}
