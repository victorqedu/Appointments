import {CustomReturnType} from "../models/customReturnType";

export class CommonFunctions {
  public static checkCNP(cnp: string):CustomReturnType {
    //console.log("start checkCNP for cnp "+cnp);
    if(cnp==="" || cnp===null) {
      return new CustomReturnType(true, '', '');
    }
    let has13digits = /^\d{13}$/.test(cnp);
    if(!has13digits) {
      return new CustomReturnType(false, 'CNP-ul trebuie sa aiba 13 cifre', '');
    }
    let numbers:number[] = cnp.split('').map((nr) => Number(nr));
    //console.log("numbers "+numbers);
    let keyTest: number[] = [2,7,9,1,4,6,3,5,8,2,7,9];

    let month:number | null = CommonFunctions.getMonthFromCNP(cnp);
    if(month!==null) {
      if(month<1 || month>12) {
        return new CustomReturnType(false, "Luna trebuie sa se incadreze in intervalul 1-12 "+month, '');
      }
    } else {
      return new CustomReturnType(false, "Luna teste nula ", '');
    }

    let day:number | null = CommonFunctions.getDayFromCNP(cnp);
    if(day!==null) {
      if(day<1 || day>31) {
        return new CustomReturnType(false, "Ziua trebuie sa se incdrese in intervalul 1-31 "+day, '');
      }
    } else {
      return new CustomReturnType(false, "Ziua teste nula ", '');
    }
    let sumaControl:number = 0;
    for (let i = 0;i < 12; i++){
      if(i==0 && numbers[i]==0) {
        return new CustomReturnType(false, "Prima cifra a cnp-ului nu poate fi 0", '');
      }
      sumaControl+=numbers[i]*keyTest[i];
      //console.log("sumaControl "+sumaControl);
    }

    let rest = sumaControl % 11;
    if(rest==10){ rest = 1; }

    if(rest===numbers[12]) {
      return new CustomReturnType(true, '', '');
    } else {
      return new CustomReturnType(false, "Cifra de control nu este corespunzatoare "+rest+" != "+numbers[12], '');
    }
  }
  public static getDayFromCNP (cnp: string | null):number | null {
    return cnp!==null ? Number(cnp.substring(5, 7)): null;
  }
  public static get2DigitsDayFromCNP (cnp: string | null):string | null {
    return cnp!==null ? cnp.substring(5, 7): null;
  }

  public static getMonthFromCNP (cnp: string | null):number | null {
    return cnp!==null ? Number(cnp.substring(3, 5)) : null;
  }

  public static get2DigitsMonthFromCNP (cnp: string | null):string | null {
    return cnp!==null ? cnp.substring(3, 5) : null;
  }
  public static getYearFromCNP (cnp: string | null):number|null  {
    if(cnp!==null) {
      let rawYear:string = cnp.substring(1, 3);
      let intVal:number = Number(cnp.substring(0, 1));
      switch (intVal) {
        case 1 :
        case 2 :
          return Number("19"+rawYear);
        case 3:
        case 4 :
          return Number("18"+rawYear);
        case 5:
        case 6 :
          return Number("20"+rawYear);
        case 7 :
        case 8 :
        case 9 :
          return Number("19"+rawYear);
      }
    }
    return null;
  }

  public static checkPasswordComplexity(password: string):CustomReturnType {
    let hasNumber = /\d/.test(password);
    let hasUpper = /[A-Z]/.test(password);
    let hasLower = /[a-z]/.test(password);
    let hasSigns = /[\!\@\#\$\%\^\&\*\(\)\_\+\=\-\/\.\,\<\>\?\'\;\:\"\[\]\{\}\\\|]/.test(password);
    if(hasNumber && hasUpper && hasLower && hasSigns && password.length>=7) {
      return new CustomReturnType(true, "", '');
    } else if(!hasNumber) {
      return new CustomReturnType(false, "Parola trebuie sa conțină minim o cifră", '');
    } else if(!hasUpper) {
      return new CustomReturnType(false, "Parola trebuie sa conțină minim o literă mare", '');
    } else if(!hasLower) {
      return new CustomReturnType(false, "Parola trebuie sa conțină minim o literă mică", '');
    } else if(!hasNumber) {
      return new CustomReturnType(false, "Parola trebuie sa conțină minim un caracter special: !@#$...", '');
    } else if(password.length<7) {
      return new CustomReturnType(false, "Parola trebuie sa fie de minim 7 caractere", '');
    } else {
      return new CustomReturnType(false, "Parola invalidă", '');
    }

  }
  public static getSex(cnp: string | null): number | null {
    if(cnp!==null) {
      switch (Number(cnp.substring(0, 1))) {
        case 1:
        case 3:
        case 5:
        case 7:
          return 1;
        case 2:
        case 4:
        case 6:
        case 8:
          return 2;
      }
    }
    return null;
  }

  public static formatDateToYYYYMMDD(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
}
