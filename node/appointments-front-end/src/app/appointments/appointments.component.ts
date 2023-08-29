import { Component } from '@angular/core';
import {AccountService} from "../services/accountService";

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent {
  constructor(public accountService: AccountService) {}
}
