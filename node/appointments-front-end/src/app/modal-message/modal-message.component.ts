import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-modal-message',
  templateUrl: './modal-message.component.html',
  styleUrls: ['./modal-message.component.css']
})

export class ModalMessageComponent {
  @Input() title:string = "";
  @Input() description: string = "";
  @Input() show : boolean = false;
  @Output() visibilityChanged = new EventEmitter<boolean>();

  openModal(open : boolean) : void {
    console.log("openModal "+open);
    this.show = open;
    this.visibilityChanged.emit(open);
  }

}
