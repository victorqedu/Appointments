import { Component } from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CustomValidator, passwordValidator} from "../customValidators/customValidator";
import {CommonFunctions} from "../commonFunctions/commonFunctions";
import {HttpService} from "../services/http-service";
import {Account} from "../models/account.model";
import {AccountService} from "../services/accountService";
import {ModalMessage} from "../modal-message/modal-message-model";
import {ModalMessageService} from "../modal-message/modal-message-service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm = new FormGroup({
      name : new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z -]{1,}$")]),
      surname : new FormControl('', [Validators.required, Validators.pattern("^[a-zA-Z -]{1,}$")]),
      cnp : new FormControl('', [CustomValidator.checkCNP as ValidatorFn]),
      birthDate : new FormControl('', [Validators.required]),
      sex : new FormControl('', [Validators.required]),
      phone : new FormControl('', [Validators.required,Validators.pattern("^[0-9]*$"), Validators.minLength(10), Validators.maxLength(12)]),
      email : new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
      passwordCheck : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
    }, { validators: passwordValidator }
  );

  constructor(private httpService: HttpService,
              public accountService: AccountService,
              private modalMessageService: ModalMessageService,
              private router: Router) {
    if(accountService.getAccount().id!=null) {
      this.signupForm.setValue({
        name: accountService.getAccount().name,
        surname: accountService.getAccount().surname,
        cnp: accountService.getAccount().cnp==="0000000000000"?"":accountService.getAccount().cnp,
        birthDate: accountService.getAccount().birthDate,
        sex: accountService.getAccount().idSex+"",
        phone: accountService.getAccount().phone,
        email: accountService.getAccount().authEmail,
        password: "",
        passwordCheck: "",
      });
    } else {
      console.log("Accout is null");
    }

  }

  /**
   * Check if the for is valid, if the data is ready to be sent to the backend
   */
  checkAllFieldsAreValid():boolean {
    if(this.signupForm.valid
        && this.signupForm.get('name')!.valid
        && this.signupForm.get('surname')!.valid
        && this.signupForm.get('cnp')!.valid
        && (this.signupForm.get('birthDate')!.valid || this.signupForm.get('birthDate')!.disabled)
        && (this.signupForm.get('sex')!.valid || this.signupForm.get('sex')!.disabled)
        && this.signupForm.get('email')!.valid
        && this.signupForm.get('password')!.valid
        && this.signupForm.get('passwordCheck')!.valid) {
      return true;
    }
    return false;
  }

  /**
   * Send the data to the backend
   */
  onSubmit() {
    console.warn(this.signupForm);
    if(this.checkAllFieldsAreValid()) {
      if(this.accountService.getAccount().id!=null) {
        let newAccount: Account = new Account(
          this.accountService.getAccount().id,
          this.signupForm.get('name')!.value,
          this.signupForm.get('surname')!.value,
          this.signupForm.get('cnp')!.value,
          this.signupForm.get('birthDate')!.value,
          Number(this.signupForm.get('sex')!.value),
          this.signupForm.get('email')!.value,
          this.signupForm.get('phone')!.value,
          this.signupForm.get('password')!.value
        );
        this.httpService.updateAccount(newAccount).subscribe(response => {
          console.log(response);
          this.accountService.setAccount(newAccount);
          this.modalMessageService.setModalMessage(
            new ModalMessage(
              "Cont modificat cu succes",
              "Cont modificat cu succes",
              true,
              false,
              false,
              false,
              "",
              null,
              null,
              false,
            ));
          this.modalMessageService.modalMessageAnswer.subscribe(answer => {
            this.router.navigate(['/firstPage']);
          });
        });
      } else {
        this.httpService.storeSignup(new Account(
          null,
          this.signupForm.get('name')!.value,
          this.signupForm.get('surname')!.value,
          this.signupForm.get('cnp')!.value,
          this.signupForm.get('birthDate')!.value,
          Number(this.signupForm.get('sex')!.value),
          this.signupForm.get('email')!.value,
          this.signupForm.get('phone')!.value,
          this.signupForm.get('password')!.value
        )).subscribe(response => {
          console.log(response);
          this.modalMessageService.setModalMessage(
            new ModalMessage(
              "Cont creat cu succes",
              "Accesați seciunea de login pentru a vă conecta.",
              true,
              false,
              false,
              false,
              "",
              null,
              null,
              false,
              ));
          this.modalMessageService.modalMessageAnswer.subscribe(answer => {
            this.router.navigate(['/login']);
          });
        });
      }
    } else {
      this.modalMessageService.setModalMessage(
        new ModalMessage(
          "Date incorecte",
          "Completati câmpurile marcate ca incorecte / câmpurile obligatorii.",
          true,
          false,
          false,
          false,
          "",
          null,
          null,
          false,
        ));
    }
  }

  /**
   * When the CNP is changed this function is used to set the sex and birthDate as extracted from the CNP
   * @param $event - not used
   */
  setBirthDateAndSex($event: any) {
    console.log("Start setBirthDateAndSex");
    if(this.signupForm.get('cnp')!==null && this.signupForm.get('cnp')?.value!=="" && this.signupForm.get('cnp')!.valid) {
      let cnp = this.signupForm.get('cnp')!.value;
      console.log("setBirthDateAndSex cnp: "+cnp);
      let birthDate:string = CommonFunctions.getYearFromCNP(cnp)+"-"+CommonFunctions.get2DigitsMonthFromCNP(cnp)+"-"+CommonFunctions.get2DigitsDayFromCNP(cnp);
      console.log("setBirthDateAndSex birthDate: "+birthDate);
      this.signupForm!.get("birthDate")!.patchValue(birthDate);
      this.signupForm!.get("birthDate")!.disable();

      let sex = CommonFunctions.getSex(cnp);
      if(sex!==null) {
        this.signupForm!.get("sex")!.patchValue(sex+"");
        this.signupForm!.get("sex")!.disable();
      }
    } else {
      this.signupForm!.get("birthDate")!.patchValue('');
      this.signupForm!.get("sex")!.patchValue('');
      this.signupForm!.get("birthDate")!.enable();
      this.signupForm!.get("sex")!.enable();
    }
  }

}
