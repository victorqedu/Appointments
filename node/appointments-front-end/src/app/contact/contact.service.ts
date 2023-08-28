import {Injectable} from "@angular/core";
import {Contact} from "./contact.model";
import {Subject} from "rxjs";
import {HttpService} from "../services/http-service";

@Injectable()
export class ContactService {
  constructor(private httpService: HttpService) {}

  isFetchingChanged = new Subject<boolean>();
  contactChanged = new Subject<Contact>();
  private contact!: Contact;

  fetchContact() {
    this.isFetchingChanged.next(true);
    this.httpService.getContact().subscribe(contact => {
      console.log(contact);
      this.contact = contact as Contact;
      this.contactChanged.next(this.contact);
      this.isFetchingChanged.next(false);
    });
  }

}
