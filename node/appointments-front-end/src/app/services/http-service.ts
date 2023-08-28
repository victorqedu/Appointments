import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Contact} from "../contact/contact.model";
import {ContactService} from "../contact/contact.service";
import {tap} from "rxjs";
import {Account} from "../models/account.model";

/**
 * In this class I have many observers and I will not use any error handling, this will happen in a single common place, in the GenericHttpInterceptors
 */
@Injectable({ providedIn: 'root' })
export class HttpService {
  protected serverProtocol: string = "http";
  protected serverHost: string = "192.168.88.105";
  protected serverPort: number = 8081;
  protected serverPrefix: string = "api";

  constructor(private http: HttpClient) {
    console.log("HttpService.constructor");
  }

  /**
   * Fetches the contact data for the current company
   * I have no subscribe nowhere in the code because the resolve in app-routing-module.ts is calling subscribe automatically
   * The upper line above is no longer valid because I no longer use resolve in app-routing-module.ts,
   * I switch to an approach using manual subscribe because I need to update the isFetching attribute from the contact to display a loading indicator
   */
  getContact() {
    console.log("HttpService.getContact");
    return this.http.get(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/localcompany");
  }

  getTermsAndConditions() {

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

  getAllAvailableSpecialities() {

  }
}
