import { Directive } from '@angular/core';
import { AbstractControl, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';

@Directive({
  selector: '[appValidarEnteros]'
})
export class ValidarNumerosDirective implements Validator{

  constructor() { }


  validate(control: AbstractControl): ValidationErrors | null {
    return this.isEnteroValidator();
  }

  isEnteroValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      let error;
      

      let numero = Number(control.value);
      if(Number.isNaN(numero)) {
        return error = {noNumero: {value: control.value}}
       }

      if(!Number.isInteger(numero)) {
        return error = {noEntero: {value: control.value}}
      }

      if(numero < 0 ) {
        return error = {noPositivo: {value: control.value}}
      }

      
      return null;
    };
  }

  isMayor0(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      let numero = Number(control.value);

      if(numero <= 0 ) {
        return {mayor0: {value: control.value}}
      }
      return null;
    }
  }

  isDecimal2Decimales(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      let error;
      

      let numero = Number(control.value);
      if(Number.isNaN(numero)) {
        return error = {noNumero: {value: control.value}}
       }

       
      let decimales = null;

      if(String(control.value).includes(',')) {
        decimales = String(control.value).split(',')[1];
      }

      if(String(control.value).includes('.')) {
        decimales = String(control.value).split('.')[1];
      }

      if(decimales != null && decimales.length > 2) {
        return error = {no2Decimales: {value: control.value}}
      }
      return null;
    };
  }
}
