import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ModalMessage} from "./modal-message-model";
import {Subscription} from "rxjs";
import {HttpService} from "../services/http-service";
import {ModalMessageService} from "./modal-message-service";

@Component({
  selector: 'app-modal-message',
  templateUrl: './modal-message.component.html',
  styleUrls: ['./modal-message.component.css']
})

export class ModalMessageComponent implements OnInit, OnDestroy {
  /**
   * the next 4 fields are optional, I should use just the modalMessage property
   */
  @Input() title:string = "";
  @Input() description: string = "";
  @Input() show : boolean = false;
  @Output() visibilityChanged = new EventEmitter<boolean>();
  modalMessage!: ModalMessage;
  subscription!: Subscription;

  constructor(private modalMessageService: ModalMessageService, private dataStorageService: HttpService) {}

  openModal(open : boolean) : void {
    //console.log("openModal "+open);
    this.show = open;
    this.visibilityChanged.emit(open);
  }

  ngOnDestroy(): void {
    //console.log("start ngOnDestroy - unsubscribe");
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    //console.log("start ngOnInit - startsubscription");
    this.subscription = this.modalMessageService.modalMessageChanged.subscribe(
      (mm: ModalMessage) => {
        console.log("New Modal Message t: "+mm.title+" d: "+mm.description);
        this.modalMessage = mm;

        this.title = mm.title;
        this.description = mm.description;
        this.show = mm.show;
      }
    );
    this.modalMessage = this.modalMessageService.getModalMessage();
  }

}
