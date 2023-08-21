import {AbstractControl, FormControl, ValidationErrors, ValidatorFn} from "@angular/forms";
import {CommonFunctions} from "../commonFunctions/commonFunctions";
import {CustomReturnType} from "../models/customReturnType";

export const passwordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const passwordCheck = control.get('passwordCheck');
  console.log("start passwordValidator");
  if(!password || !passwordCheck || password.value===null || passwordCheck.value===null) {
    console.log("passwordValidator - pass match");
    return null;
  } else if(password && passwordCheck && password.value!==null && passwordCheck.value!==null && password.value === passwordCheck.value) {
    console.log("passwordValidator - pass match");
    return null;
  } else {
    console.log("passwordValidator - pass don't match");
    return { passwordsDoNotMatch: true };
  }
};

export class CustomValidator {
  public static checkCNP(control: FormControl): {[s:string]:string} | null {
    console.log("start check CNP validator");
    let c:CustomReturnType = CommonFunctions.checkCNP(control.value);
    if(c.success) {
      console.log("checkCNP - valid");
      return null;
    } else {
      console.log("checkCNP - invalid "+c.title);
      return {"invalidCNP": c.title};
    }
  }

  public static checkPassword(control: FormControl): {[s:string]:string} | null {
    let c:CustomReturnType = CommonFunctions.checkPasswordComplexity(control.value);
    if(c.success) {
      return null;
    } else {
      return {"invalidPassword": c.title};
    }
  }
}
