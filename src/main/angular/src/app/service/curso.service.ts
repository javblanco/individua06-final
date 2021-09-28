import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Curso } from '../model/curso';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  url = `${environment.host}/api/curso`;

  options = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  }

  constructor(private http: HttpClient) { }

  getCursos(): Observable<Curso[]> {
    return this.http.get<Curso[]>(this.url);
  }

  getCursosActivos(): Observable<Curso[]> {
    const urlActivas = `${this.url}/activos`;

    return this.http.get<Curso[]>(urlActivas);
  }

  getCurso(id: number): Observable<Curso> {
    const urlId = `${this.url}/${id}`;

    return this.http.get<Curso>(urlId);
  }

  createCurso(tematica: Curso): Observable<number> {
    return this.http.post<number>(this.url, tematica, this.options)
    .pipe(
      catchError(err => {throw new Error(err.message)})
    );
  }

  updateCurso(tematica: Curso): Observable<any> {
    return this.http.put<any>(this.url, tematica, this.options);
  }

  deleteCurso(id: number): Observable<any> {
    const idUrl = `${this.url}/${id}`;

    return this.http.delete<any>(idUrl);
  }

  bajaCurso(id:number): Observable<any> {
    const baja = `${this.url}/baja/${id}`;

    return this.http.post<any>(baja, null);
  }

  altaCurso(id:number): Observable<any> {
    const alta = `${this.url}/alta/${id}`;

    return this.http.post<any>(alta, null);
  }

}
