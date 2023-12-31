import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CustomValidator} from "../customValidators/customValidator";
import {HttpService} from "../services/http-service";
import {Account} from "../models/account.model";
import {ModalMessageService} from "../modal-message/modal-message-service";
import {ModalMessage} from "../modal-message/modal-message-model";
import {Router} from "@angular/router";
import {AccountService} from "../services/accountService";
import {Contact} from "../models/contact.model";
import {Subscription} from "rxjs";
import {Config} from "../models/config";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  loading: boolean = false;
  loadingSubscription!: Subscription;
  subscription!: Subscription;
  account!:Account;
  loginForm = new FormGroup({
      email : new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
    }
  );

  constructor(private httpService: HttpService,
              private modalMessageService: ModalMessageService,
              public accountService: AccountService) {}

  /**
   * Check if the for is valid, if the data is ready to be sent to the backend
   */
  checkAllFieldsAreValid():boolean {
    if(this.loginForm.valid
      && this.loginForm.get('email')!.valid
      && this.loginForm.get('password')!.valid
      ) {
      return true;
    }
    return false;
  }

  /**
   * Send the data to the backend
   */
  onSubmit() {
    console.warn(this.loginForm);
    console.warn("[1] Loading is now "+this.loading);
    if(this.checkAllFieldsAreValid()) {
      console.log("All fields are valid");
      this.accountService.login(new Account(
          null,
          null,
          null,
          null,
          null,
          null,
          this.loginForm.get('email')!.value,
          null,
          this.loginForm.get('password')!.value, null, null, null)
      );
      console.warn("[2] Loading is now "+this.loading);
    } else {
      console.log("Some error");
      this.modalMessageService.setModalMessage(
        new ModalMessage(
          "Date incorecte",
          "Completati emailul si parola, corectati campurile greșite.",
          true,
          false,
          false,
          false,
          "",
          null,
          null,
          false,));
      this.loading = false;
      console.warn("[3] Loading is now "+this.loading);
    }
  }

  ngOnInit(): void {
    console.log("LoginComponent.ngOnInit");
    this.subscription = this.accountService.connectedChanged.subscribe(
      (account: Account) => {
        this.account = account;
      }
    );
    this.loadingSubscription = this.accountService.loginChangedSubject.subscribe(loading => {
      this.loading = loading;
      console.log("Loafing subject is now "+loading)
    })
    this.account = this.accountService.getAccount();
  }

  ngOnDestroy(): void {
    console.log("LoginComponent.ngOnDestroy");
    this.subscription.unsubscribe();
    this.loadingSubscription.unsubscribe();
  }

}
