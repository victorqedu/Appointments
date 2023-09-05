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
  modalMessage: ModalMessage = new ModalMessage('', '', false, false, false, false, "", null);
  subscription!: Subscription;

  constructor(private modalMessageService: ModalMessageService, private dataStorageService: HttpService) {}

  openModal(open : boolean) : void {
    this.modalMessage.show = open;
    this.modalMessageService.modalFeedback(true);
  }

  modalFeedback(status: boolean) : void {
    this.modalMessageService.modalFeedback(status);
    this.modalMessage.show = false;
  }

  ngOnDestroy(): void {
    //console.log("start ngOnDestroy - unsubscribe");
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    //console.log("start ngOnInit - start subscription");
    this.subscription = this.modalMessageService.modalMessageChanged.subscribe(
      (mm: ModalMessage) => {
        console.log("New Modal Message t: "+mm.title+" d: "+mm.description);
        this.modalMessage = mm;
      }
    );
  }

}
