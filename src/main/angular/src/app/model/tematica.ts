import { Categoria } from './categoria';

export interface Tematica {
    id?: number;
    nombre: string;
    descripcion?: string;
    referencia: string;
    subtematicas?: string;
    listaSubtematicas?: string[];
    activo: boolean;
    eliminable: boolean;
    categoria: Categoria;
}