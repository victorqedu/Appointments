import {Component, OnInit} from '@angular/core';
import {Account} from "../models/account.model";
import {AccountService} from "../services/accountService";
import {Subscription} from "rxjs";
import {ContactService} from "../contact/contact.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(public accountService:AccountService, public contactService:ContactService) {}
}
