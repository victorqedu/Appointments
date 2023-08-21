import {Injectable} from "@angular/core";
import {Contact} from "./contact.model";
import {Subject} from "rxjs";

@Injectable()
export class ContactService {
  contactChanged = new Subject<Contact>();
  private contact!: Contact;

  getContact() {
    console.log("ContactService.getContact");
    return this.contact;
  }

  setContact(contact: Contact) {
    console.log("ContactService.setContact");
    this.contact = contact;
    this.contactChanged.next(this.contact);
  }
}
