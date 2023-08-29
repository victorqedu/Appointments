import { Component } from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CustomValidator, passwordValidator} from "../customValidators/customValidator";
import {CommonFunctions} from "../commonFunctions/commonFunctions";
import {HttpService} from "../services/http-service";
import {Account} from "../models/account.model";
import {AccountService} from "../services/accountService";

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

  showModal: boolean = false;
  modalTitle: string = "";
  modalDescription: string = "";

  constructor(private httpService: HttpService,
              public accountService: AccountService) {}

  /**
   * Check if the for is valid, if the data is ready to be sent to the backend
   */
  checkAllFieldsAreValid():boolean {
    if(this.signupForm.valid
        && this.signupForm.get('name')!.valid
        && this.signupForm.get('surname')!.valid
        && this.signupForm.get('cnp')!.valid
        && this.signupForm.get('birthDate')!.valid
        && this.signupForm.get('sex')!.valid
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
      let name = this.signupForm.get('name')!.value;
      this.httpService.storeSignup(new Account(
        null,
        this.signupForm.get('name')!.value,
        this.signupForm.get('surname')!.value,
        this.signupForm.get('cnp')!.value,
        this.signupForm.get('birthDate')!.value,
        Number(this.signupForm.get('sex')!.value),
        this.signupForm.get('email')!.value,
        this.signupForm.get('password')!.value
      ));
    } else {
      this.openModal("Date incorecte", "Completati câmpurile marcate ca incorecte / câmpurile obligatorii.");
    }
  }

  /**
   * Opens the modal message containing the component ModalMessageComponent
   * @param title - title for the modal
   * @param description - description for the modal
   */
  openModal(title: string, description: string) {
    this.modalTitle = title;
    this.modalDescription = description;
    this.showModal = true;
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

  /**
   * function created only for communication with the @Output of the component app-modal-message,
   * it synchronizes the show attribute of app-modal-message with the showModal attribute of the signup component
   * I should use a service, but I want to test the @Output functionality
   * @param showModal
   */
  setShowModal(showModal: boolean) {
    this.showModal = showModal;
  }
}
