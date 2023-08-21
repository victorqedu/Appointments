import { Component } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {Account} from "../models/account.model";
import {CustomValidator, passwordValidator} from "../customValidators/customValidator";
import {CommonFunctions} from "../commonFunctions/commonFunctions";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupForm = new FormGroup({
      name : new FormControl('', [Validators.required]),
      surname : new FormControl('', [Validators.required]),
      cnp : new FormControl('', [CustomValidator.checkCNP as ValidatorFn]),
      birthDate : new FormControl('', [Validators.required]),
      sex : new FormControl('', [Validators.required]),
      email : new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      password : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
      passwordCheck : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
    }, { validators: passwordValidator }
  );

  onSubmit() {
    console.warn(this.signupForm);
  }

  setBirthDateAndSex($event: any) {
    console.log("Start setBirthDateAndSex");
    if(this.signupForm.get('cnp')!==null && this.signupForm.get('cnp')!.valid) {
      let cnp = this.signupForm.get('cnp')!.value;
      console.log("setBirthDateAndSex cnp: "+cnp);
      let birthDate:string = CommonFunctions.getYearFromCNP(cnp)+"-"+CommonFunctions.get2DigitsMonthFromCNP(cnp)+"-"+CommonFunctions.get2DigitsDayFromCNP(cnp);
      console.log("setBirthDateAndSex birthDate: "+birthDate);
      this.signupForm!.get("birthDate")!.patchValue(birthDate);

      let sex = CommonFunctions.getSex(cnp);
      this.signupForm!.get("sex")!.patchValue(sex+"");
    } else {
      this.signupForm!.get("birthDate")!.patchValue('');
      this.signupForm!.get("sex")!.patchValue('');
    }
  }

}
