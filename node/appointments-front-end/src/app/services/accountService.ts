import {Injectable, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Subject} from "rxjs";
import {Account} from "../models/account.model";
import {HttpService} from "./http-service";
import {Config} from "../models/config";
import {ModalMessage} from "../modal-message/modal-message-model";
import {ModalMessageService} from "../modal-message/modal-message-service";

@Injectable()
export class AccountService {
  private account!:Account;
  connectedChanged = new Subject<Account>();

  /**
   * Every time I instantiate this service.model.ts I will read the current connected account from the sessionStorage
   * @param router
   * @param modalMessageService
   * @param httpService
   */
  constructor(private router: Router,
              private modalMessageService: ModalMessageService,
              public httpService: HttpService) {
    let accountDetails = sessionStorage.getItem("accountDetails");
    console.log("AccountService.init "+accountDetails)
    if (accountDetails) {
      this.account = JSON.parse(accountDetails) as Account;
      this.connectedChanged.next(this.account);
    } else {
      this.account = new Account(null, null, null, null, null, null, null, null, null);
      this.connectedChanged.next(this.account);
    }
  }

  /**
   * Logout
   */
  disconnect() {
    console.log("start disconnect");
    sessionStorage.removeItem("accountDetails");
    sessionStorage.removeItem("Authorization");
    this.account = new Account(null, null, null, null, null, null, null, null, null);
    this.connectedChanged.next(this.account);
    this.router.navigate(['/firstPage']);
  }

  /**
   * Login with the account specified in the parameters
   * @param account
   */
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

  /**
   * Get the current connected user
   */
  getAccount() {
    console.log("AccountService.getAccount "+JSON.stringify(this.account)+ " accountDetails "+sessionStorage.getItem("accountDetails"));
    return this.account;
  }

  /**
   * Check if someone is connected
   */
  isConnected():boolean {
    if(this.account && this.account.id && this.account.id!==null) {
      //console.log("isConnected: true");
      return true;
    } else {
      //console.log("isConnected: false");
      return false;
    }
  }

  /**
   * This functions can be called only in components that contain a <app-modal-message></app-modal-message> because the scope is to show the terms and conditions
   * in a popup modal that must be defined in the component that calls this service.model.ts and this function
   */
  showTermsAndConditions() {
    this.modalMessageService.setModalMessage(
      new ModalMessage(
        "Termeni si conditii de utilizare",
        '',
        true,
        true,
        false,
        false));
    this.httpService.getTermsAndConditions().subscribe(termsAndConditions => {
      console.log(termsAndConditions);
      let tac: Config = termsAndConditions as Config;
      this.modalMessageService.setModalMessage(
        new ModalMessage(
          "Termeni si conditii de utilizare",
          tac.valoare,
          true,
          false,
          true,
          false));
    });
  }

  /**
   * This functions can be called only in components that contain a <app-modal-message></app-modal-message> because the scope is to show the policy of confidentiality
   * in a popup modal that must be defined in the component that calls this service.model.ts and this function
   */
  showPolicyOfConfidentiality() {
    this.modalMessageService.setModalMessage(
      new ModalMessage(
        "Termeni si conditii de utilizare",
        '',
        true,
        true,
        false,
        false));
    this.httpService.getPolicyOfConfidentiality().subscribe(poc => {
      console.log(poc);
      let pol: Config = poc as Config;
      this.modalMessageService.setModalMessage(
        new ModalMessage(
          "Politica de confidentialitate",
          pol.valoare,
          true,
          false,
          true,
          false));
    });
  }

}
