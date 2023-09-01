import { Component } from '@angular/core';
import {AccountService} from "../services/accountService";
import {FormArray, FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {appointmentsDatesValidator, CustomValidator, passwordValidator} from "../customValidators/customValidator";
import {Service} from "../models/service.model";
import {Physician} from "../models/physician.model";
import {CommonFunctions} from "../commonFunctions/commonFunctions";
import {Speciality} from "../models/speciality.model";

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent {
  /**
   * The current step of the appointment, there are 4 steps:
   * 1. Selection of the date of the desired appointment
   * 2. Selection of the speciality
   * 3. Selection of the wanted service.model.ts(s)
   * 4. Selection of the physician, date and hour of the appointment
   * In function of the value of this variable I will display the corresponding component
   */
  currentStep: number = 1;
  appointmentForm = new FormGroup({
    appointmentSearchDateStart: new FormControl(CommonFunctions.formatDateToYYYYMMDD(new Date()), [Validators.required, CustomValidator.checkDateIsInThePast as ValidatorFn]),
    appointmentSearchDateStop : new FormControl(CommonFunctions.formatDateToYYYYMMDD(new Date((new Date()).getTime()+(1000 * 60 * 60 * 24)*7)), [Validators.required, CustomValidator.checkDateIsInThePast as ValidatorFn]),
    speciality : new FormControl('', [Validators.required]),
    selectedServices : new FormArray([], Validators.required),
    physician : new FormControl('', [Validators.required]),
    appointmentHour : new FormControl('', [Validators.required, CustomValidator.checkDateIsInThePast as ValidatorFn]),
    }, { validators: appointmentsDatesValidator }
  );

  constructor(public accountService: AccountService) {}

  get selectedServices(): FormArray {
    return this.appointmentForm.get('selectedServices') as FormArray;
  }

  serviceJSONFromText(index: number): Service {
    const controlAtIndex = this.selectedServices.at(index);
    return JSON.parse(controlAtIndex.value);
  }

  specialityJSONFromText(specilityText: string): Speciality {
    return JSON.parse(specilityText);
  }

  get physicianToJSON(): Physician {
    return JSON.parse(this.appointmentForm.get('physician')!.value!);

  }

  goToStep(step: number) {
      this.currentStep = step;
  }
}

