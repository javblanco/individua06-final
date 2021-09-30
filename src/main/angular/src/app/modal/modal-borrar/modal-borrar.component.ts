import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-borrar',
  templateUrl: './modal-borrar.component.html',
  styleUrls: ['./modal-borrar.component.css']
})
export class ModalBorrarComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
