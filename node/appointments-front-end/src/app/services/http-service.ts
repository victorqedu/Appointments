import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Contact} from "../contact/contact.model";
import {ContactService} from "../contact/contact.service";
import {catchError, map, retry, tap, throwError} from "rxjs";
import {Account} from "../models/account.model";
import {Router} from "@angular/router";

@Injectable({ providedIn: 'root' })
export class HttpService {
  protected serverProtocol: string = "http";
  protected serverHost: string = "192.168.88.105";
  protected serverPort: number = 8081;
  protected serverPrefix: string = "api";

  constructor(private http: HttpClient, private contactService: ContactService) {
    console.log("HttpService.constructor");
  }
/*
  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(`Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }*/

  /**
   * Fetches the contact data for the current company
   */
  getContact() {
    console.log("HttpService.getContact");

    return this.http.get<Contact>(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/localcompany")
      .pipe(
        tap(contact => {
          console.log("HttpService contact is "+JSON.stringify(contact));
          this.contactService.setContact(contact);
          }
        )
      );
  }

  storeSignup(account: Account) {
    return this.http
      .post(
        this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/register",
        account
      )
      .subscribe(response => {
        console.log(response);
      });
  }

  login(account: Account) {
    sessionStorage.setItem("accountDetails",JSON.stringify(account));
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'application/json');
    return this.http
      .get(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/login", { observe: 'response', headers});
  }
}
