import { Component } from '@angular/core';
import {Account} from "../models/account.model";
import {AccountService} from "../services/accountService";
import {HttpService} from "../services/http-service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-resend-confirmation-email',
  templateUrl: './resend-confirmation-email.component.html',
  styleUrls: ['./resend-confirmation-email.component.css']
})
export class ResendConfirmationEmailComponent {
  sendingEmail: boolean = false;
  emailWasSend: boolean = false;

  constructor(public accountService: AccountService, public httpService: HttpService) {}
  reSendEmailConfirmEmail() {
    this.sendingEmail = true;
    this.httpService.reSendEmailConfirmEmail(this.accountService.getAccount()).subscribe(
      {
        next: (account) => {
          this.accountService.setAccount(account as Account);
          this.emailWasSend = true;
        },
        complete: () => {
          this.sendingEmail = false;
        }
      }
    );
  }
}
