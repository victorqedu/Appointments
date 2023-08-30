import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";

@Component({
  selector: 'app-date-selector',
  templateUrl: './date-selector.component.html',
  styleUrls: ['./date-selector.component.css']
})

export class DateSelectorComponent implements OnInit {
  /**
   * I use stepTwoRequiredByUser to inform the parent that I want to go to step 2 of the appointment
   */
  @Output() stepTwoRequiredByUser = new EventEmitter<boolean>();
  form!: FormGroup;
  error: boolean = false;

  /**
   * I inject the FormGroupDirective because I need to access the parent form(the FormGroup), I will do this in ngInit
   * @param rootFormGroup
   */
  constructor(private rootFormGroup: FormGroupDirective) {}

  /**
   * When the user inserts a date and presses "Go to step 2" this function will be executed and the result the parent component will be informed
   */
  goToStep2() {
    console.log(this.form);
    if (this.form.controls['appointmentSearchDateStart'].valid && this.form.controls['appointmentSearchDateStop'].valid) {
      this.stepTwoRequiredByUser.emit(true);
      this.error = false;
    } else {
      this.error = true;
    }
  }

  /**
   * The only purpose if ngInit is to read the parent FormGroup in my local variable
   */
  ngOnInit(): void {
    this.form = this.rootFormGroup.control;
  }
}
