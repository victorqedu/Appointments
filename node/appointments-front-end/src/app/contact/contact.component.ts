import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ContactService} from "./contact.service";
import {Contact} from "./contact.model";
import {HttpService} from "../services/http-service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent  implements OnInit, OnDestroy {
  subscriptionData!: Subscription;
  subscriptionIsFetching!: Subscription;
  contact!: Contact;
  isFetching:boolean=false;

  constructor(private contactService: ContactService) {
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("ContactComponent.ngOnInit");
    this.subscriptionData = this.contactService.contactChanged.subscribe(
      (contact: Contact) => {
        this.contact = contact;
      }
    );
    this.subscriptionIsFetching = this.contactService.isFetchingChanged.subscribe(
      (isFetchingChanged: boolean) => {
        this.isFetching = isFetchingChanged;
      }
    );
    this.contactService.fetchContact();
  }

  ngOnDestroy(): void {
    console.log("ContactComponent.ngOnDestroy");
    this.subscriptionData.unsubscribe();
    this.subscriptionIsFetching.unsubscribe();
  }

}
