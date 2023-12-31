import {Component, HostListener, OnInit} from '@angular/core';
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
  isNavbarContentVisible: boolean = false;
  size:number = 575;
  constructor(public accountService:AccountService, public contactService:ContactService) {
    this.checkViewportSize();
  }

  smallWidth():boolean {
    return window.innerWidth < this.size;
  }

  toggleNavbarContent() {
    if (window.innerWidth < this.size) {
      this.isNavbarContentVisible = !this.isNavbarContentVisible;
      console.log("this.isNavbarContentVisible "+this.isNavbarContentVisible)
    }
  }

  // Listen for changes in viewport size
  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkViewportSize();
  }

  // Function to check the viewport size and update isNavbarContentVisible
  checkViewportSize() {
    if (window.innerWidth >= this.size) {
      // Display the navigation content on larger screens
      this.isNavbarContentVisible = true;
    } else {
      // Hide the navigation content on smaller screens
      this.isNavbarContentVisible = false;
    }
  }

}
