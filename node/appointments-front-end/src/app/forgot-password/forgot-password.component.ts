import { Component } from '@angular/core';
import {AccountService} from "../services/accountService";
import {HttpService} from "../services/http-service";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CustomValidator} from "../customValidators/customValidator";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  loading: boolean = false;
  success: boolean = false;
  loginForm = new FormGroup({
      email : new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
    }
  );

  constructor(public httpService: HttpService) {}

  onSubmit() {
    console.log(this.loginForm);
    this.loading = true;
    if(this.loginForm.valid && this.loginForm.get('email')!.valid) {
      this.httpService.sendMailWithPasswordResetLink(this.loginForm.get("email")!.value!).subscribe({
        next:answer => {
          this.success = answer as boolean;
        },
        complete: () => {
          this.loading = false;
        }
      });
    }
  }
}
