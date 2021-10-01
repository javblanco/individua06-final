import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'inscripcion'
})
export class InscripcionPipe implements PipeTransform {

  transform(value: boolean): string {
    return value ? 'Abiertas' : 'Cerradas';
  }

}
