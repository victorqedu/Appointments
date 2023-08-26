import {Component, OnInit} from '@angular/core';
import {Account} from "../models/account.model";
import {CommonService} from "../services/commonService";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  account!:Account;
  subscription!: Subscription;
  constructor(public commonService:CommonService) {}


  ngOnInit(): void {
    console.log("ContactComponent.ngOnInit");
    this.subscription = this.commonService.connectedChanged.subscribe(
      (account: Account) => {
        this.account = account;
      }
    );
    this.account = this.commonService.getAccount();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
