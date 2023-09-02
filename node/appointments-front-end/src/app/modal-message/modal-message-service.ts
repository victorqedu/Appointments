import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {ModalMessage} from "./modal-message-model";

@Injectable()
export class ModalMessageService {
  modalMessageChanged = new Subject<ModalMessage>();
  modalMessageAnswer = new Subject<boolean>();

  /**
   * This function will be executed only when the ModalMessage has areyousure property = true and the scope is to provide feedback regarding
   * the button pressed in the areyousure winwod
   * @param status = true means a Confirm opperation and false Cancel
   */
  modalFeedback(status: boolean) {
    this.modalMessageAnswer.next(status);
  }

  /**
   * I reinitialize the modalMessageAnswer after every next because in the subscription of this modal I also included the "unscribe" and if
   * I unscribe from a subject than the subject is marked as dead and I can no longer resubscribe until the subject is reinitialized.
   * This may look bad as many Components could subscribe to this subject and the subject gets reset while others listen to it but in fact
   * this modal will be displayed a single time per application.
   */
  reinitializeModalMessageAnswerSubject() {
    this.modalMessageAnswer = new Subject<boolean>();
  }

  setModalMessage(modalMessage: ModalMessage) {
    console.log("setModalMessage "+modalMessage.title);
    this.modalMessageChanged.next(modalMessage);
  }

}
