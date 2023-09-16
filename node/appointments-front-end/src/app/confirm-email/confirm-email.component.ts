import {Component, OnInit} from '@angular/core';
import {AccountService} from "../services/accountService";
import {HttpService} from "../services/http-service";
import {Account} from "../models/account.model";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirm-email',
  templateUrl: './confirm-email.component.html',
  styleUrls: ['./confirm-email.component.css']
})
export class ConfirmEmailComponent implements OnInit {
  jwtToken!: string;
  isConfirming:boolean = true;
  confirmed:boolean = false;
  constructor(public accountService: AccountService, public httpService: HttpService, public route: ActivatedRoute) {}

  ngOnInit(): void {
    this.jwtToken = this.route.snapshot.paramMap.get('jwtToken')!;
    this.isConfirming = true;
    this.httpService.confirmEmail(this.jwtToken).subscribe(
      {
        next: (account: Account) => {
          if(this.accountService.isConnected()) {
            this.accountService.setAccount(account);
          }
          this.confirmed = true;
        },
        complete: () => {
          this.isConfirming = false;
        }
      }
      );
  }


}
