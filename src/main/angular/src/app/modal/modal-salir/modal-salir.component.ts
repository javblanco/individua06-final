import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-modal-salir',
  templateUrl: './modal-salir.component.html',
  styleUrls: ['./modal-salir.component.css']
})
export class ModalSalirComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }

}
