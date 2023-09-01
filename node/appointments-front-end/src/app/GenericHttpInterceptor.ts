import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, EMPTY, Observable} from "rxjs";
import {Router} from "@angular/router";
import {CustomErrorService} from "./custom-error/custom-error.service";
import {CustomError} from "./custom-error/custom-error.model";
import {MatDialog} from "@angular/material/dialog";
import {CustomErrorComponent} from "./custom-error/custom-error.component";
import {Account} from "./models/account.model";
import {AccountService} from "./services/accountService";

@Injectable()
export class GenericHttpInterceptor implements HttpInterceptor {
  account!:Account;
  constructor(private router: Router, private customErrorService: CustomErrorService, private dialog: MatDialog, private accountService: AccountService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("GenericHttpInterceptor.intercept");
    let httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
    if(sessionStorage.getItem('accountDetails')) {
      this.account = JSON.parse(sessionStorage.getItem('accountDetails')!);
    }
    if(this.account && this.account.authEmail && this.account.onlinePassword) {
      httpHeaders = httpHeaders.append('Authorization', 'Basic ' + window.btoa(this.account.authEmail + ':' + this.account.onlinePassword));
      // I'm removing the user account clear text data as soon as possible, they will be stored as a JWT token
      sessionStorage.removeItem('accountDetails');
    } else {
      let authorization = sessionStorage.getItem('Authorization');
      if(authorization) {
        httpHeaders = httpHeaders.append('Authorization', authorization);
      }
    }
    const alteredRequest = req.clone({
      headers: httpHeaders
    });

    return next.handle(alteredRequest).pipe(
      catchError((error) => {
        console.log("GenericHttpInterceptor.intercept "+JSON.stringify(error));
        let errorSmall = "";
        if(error!=null && error.hasOwnProperty("error") && error.error!==null) {
          console.log("GenericHttpInterceptor.intercept error has the required structure");
          if(error.error.hasOwnProperty("message")) {
            errorSmall = error.error.message;
            console.log("GenericHttpInterceptor.intercept error has message "+errorSmall);
          }
          errorSmall = errorSmall.split("[java.lang.RuntimeException:")[0];
          console.log("GenericHttpInterceptor.intercept errsmall "+errorSmall);
        }
        if(error.status===401 && errorSmall==="") {
          // In the rest API I cover all cases and when I cereive 401 it can be only the missed password
          errorSmall = "Parolă incorectă sau sesiune expirată!";
          this.accountService.disconnect();
        }
        this.customErrorService.setCustomError(new CustomError(error.name+"(textStatus : "+error.statusText+")", error.status, errorSmall, JSON.stringify(error, undefined, 2)));
        this.dialog.open(CustomErrorComponent);
        /*this.router.navigate(['/error']);*/
        return EMPTY;
      })
    );
  }

}
