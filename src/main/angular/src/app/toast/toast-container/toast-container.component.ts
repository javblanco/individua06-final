import { Component, OnInit, TemplateRef } from '@angular/core';
import { ToastService } from 'src/app/service/toast.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast-container.component.html',
  styleUrls: ['./toast-container.component.css'],
  host: {'[class.ngb-toasts]': 'true'}
})
export class ToastContainerComponent implements OnInit {

  
  constructor(public toastService: ToastService) {}

  ngOnInit(): void {
  }


  isTemplate(toast: any) { return toast.textOrTpl instanceof TemplateRef; }
}
