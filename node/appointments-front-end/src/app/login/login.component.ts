import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CustomValidator} from "../customValidators/customValidator";
import {HttpService} from "../services/http-service";
import {Account} from "../models/account.model";
import {ModalMessageService} from "../modal-message/modal-message-service";
import {ModalMessage} from "../modal-message/modal-message-model";
import {Router} from "@angular/router";
import {AccountService} from "../services/accountService";
import {Contact} from "../contact/contact.model";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  subscription!: Subscription;
  account!:Account;
  loginForm = new FormGroup({
      email : new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
    }
  );

  constructor(private httpService: HttpService,
              private modalMessageService: ModalMessageService,
              public commonService: AccountService) {}

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
    if(this.checkAllFieldsAreValid()) {
      console.log("All fields are valid");
      this.commonService.login(new Account(
          null,
          null,
          null,
          null,
          null,
          null,
          this.loginForm.get('email')!.value,
          this.loginForm.get('password')!.value)
      );
    } else {
      console.log("Some error");
      this.modalMessageService.setModalMessage(new ModalMessage("Date incorecte", "Completati emailul si parola, corectati campurile greșite.", true));
    }
  }

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
