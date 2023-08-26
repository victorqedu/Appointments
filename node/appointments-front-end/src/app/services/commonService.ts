import {Injectable, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Subject} from "rxjs";
import {Account} from "../models/account.model";
import {HttpService} from "./http-service";

@Injectable()
export class CommonService {
  private account!:Account;
  connectedChanged = new Subject<Account>();

  constructor(private router: Router, public httpService: HttpService) {
    let accountDetails = sessionStorage.getItem("accountDetails");
    console.log("CommonService.init "+accountDetails)
    if (accountDetails) {
      this.account = JSON.parse(accountDetails) as Account;
      this.connectedChanged.next(this.account);
    } else {
      this.account = new Account(null, null, null, null, null, null, null, null);
      this.connectedChanged.next(this.account);
    }
  }

  disconnect() {
    console.log("start disconnect");
    sessionStorage.removeItem("accountDetails");
    sessionStorage.removeItem("Authorization");
    this.account = new Account(null, null, null, null, null, null, null, null);
    this.connectedChanged.next(this.account);
    this.router.navigate(['/firstPage']);
  }

  login(account: Account) {
    this.httpService.login(account).subscribe(response => {
      console.log("Login response ");
      console.log(response);
      if(response.status===200) {
        sessionStorage.setItem("Authorization",response.headers.get('Authorization')!);
        sessionStorage.setItem("accountDetails",JSON.stringify(response.body!));

        console.log(response.body);
        this.account = response.body as Account;
        this.connectedChanged.next(this.account);
        console.log("Account name "+this.account.name);

        this.router.navigate(['/firstPage']);
      }
    });
  }

  getAccount() {
    console.log("CommonService.getAccount "+JSON.stringify(this.account)+ " accountDetails "+sessionStorage.getItem("accountDetails"));
    return this.account;
  }

  isConnected():boolean {
    if(this.account && this.account.id && this.account.id!==null) {
      console.log("isConnected: true");
      return true;
    } else {
      console.log("isConnected: false");
      return false;
    }
  }
}
