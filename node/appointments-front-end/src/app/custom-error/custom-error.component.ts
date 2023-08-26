import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {CustomErrorService} from "./custom-error.service";
import {Subscription} from "rxjs";
import {CustomError} from "./custom-error.model";

@Component({
  selector: 'app-custom-error',
  templateUrl: './custom-error.component.html',
  styleUrls: ['./custom-error.component.css']
})
export class CustomErrorComponent  implements OnInit, OnDestroy {
  customError!: CustomError;
  subscription!: Subscription;

  constructor(private customErrorService: CustomErrorService) {}

  ngOnInit(): void {
    console.log("ContactComponent.ngOnInit");
    this.subscription = this.customErrorService.customErrorChanged.subscribe(
      (customError: CustomError) => {
        this.customError = customError;
      }
    );
    this.customError = this.customErrorService.getCustomError();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}




