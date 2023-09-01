import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {ModalMessage} from "./modal-message-model";

@Injectable()
export class ModalMessageService {
  modalMessageChanged = new Subject<ModalMessage>();
  private modalMessage: ModalMessage = new ModalMessage('', '', false, false, false, false);

  getModalMessage() {
    console.log("getModalMessage "+this.modalMessage);
    return this.modalMessage;
  }

  setModalMessage(modalMessage: ModalMessage) {
    console.log("setModalMessage "+modalMessage.title);
    this.modalMessage = modalMessage;
    this.modalMessageChanged.next(this.modalMessage);
  }

}
