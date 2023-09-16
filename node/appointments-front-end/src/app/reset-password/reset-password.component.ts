import {Component, OnInit} from '@angular/core';
import {AccountService} from "../services/accountService";
import {HttpService} from "../services/http-service";
import {ActivatedRoute} from "@angular/router";
import {FormControl, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {CustomValidator, passwordValidator} from "../customValidators/customValidator";
import {ResetPassword} from "../models/resetPassword.model";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit  {
  jwtToken!: string;

  loading: boolean = false;
  success: boolean = false;

  resetPassword = new FormGroup({
      password : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
      passwordCheck : new FormControl('', [Validators.required, CustomValidator.checkPassword as ValidatorFn]),
    }, { validators: passwordValidator }
  );
  constructor(public accountService: AccountService, public httpService: HttpService, public route: ActivatedRoute) {}

  ngOnInit(): void {
    this.jwtToken = this.route.snapshot.paramMap.get('jwtToken')!;
  }

  onSubmit() {
    if(this.resetPassword.valid && this.resetPassword.get('password')!.valid) {
      this.loading = true;
      let rp: ResetPassword = new ResetPassword(this.jwtToken, this.resetPassword.get('password')!.value!);
      this.httpService.resetPassword(rp).subscribe({
        next: (response) => {
            this.success = response;
        },
        complete: () => {
          this.loading = false;
        }
      });

    }
  }
}
