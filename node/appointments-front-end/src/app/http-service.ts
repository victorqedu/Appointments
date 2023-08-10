import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Contact} from "./contact/contact.model";
import {ContactService} from "./contact/contact.service";
import {map, tap} from "rxjs";

@Injectable({ providedIn: 'root' })
export class HttpService {
  protected serverProtocol: string = "http";
  protected serverHost: string = "192.168.88.105";
  protected serverPort: number = 8080;
  protected serverPrefix: string = "api";

  constructor(private http: HttpClient, private contactService: ContactService) {
    console.log("HttpService.constructor");
  }

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
}
