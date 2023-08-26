import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {CustomError} from "./custom-error.model";

@Injectable()
export class CustomErrorService {
  customErrorChanged = new Subject<CustomError>();
  private customError: CustomError = new CustomError("", "", "", "",);

  getCustomError() {
    console.log("CustomErrorService.getCustomError");
    return this.customError;
  }

  setCustomError(customError: CustomError) {
    console.log("CustomErrorService.setCustomError");
    this.customError = customError;
    this.customErrorChanged.next(this.customError);
  }

}
