import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ContactService} from "./contact.service";
import {Contact} from "./contact.model";
import {HttpService} from "../http-service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent  implements OnInit, OnDestroy {
  subscription!: Subscription;
  contact!: Contact;

  private dataStorageService: HttpService;

  constructor(private contactService: ContactService, dataStorageService: HttpService) {
    this.dataStorageService = dataStorageService;
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("ContactComponent.ngOnInit");
    this.subscription = this.contactService.contactChanged.subscribe(
      (contact: Contact) => {
        this.contact = contact;
      }
    );
    this.contact = this.contactService.getContact();
  }

  ngOnDestroy(): void {
    console.log("ContactComponent.ngOnDestroy");
    this.subscription.unsubscribe();
  }


}
