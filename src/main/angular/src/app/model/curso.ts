export interface Curso {
    id: number;
    nombre: string;
    idTematica: number;
    nombreTematica: string;
    descripcion: string;
    duracion: number;
    cantidadAlumnos: number;
    numeroTemas: number;
    certificacion: boolean;
    precio: number;
    activo: boolean;
}