import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpService} from "../services/http-service";
import {ContactService} from "../contact/contact.service";
import {Contact} from "../models/contact.model";
import {Subject, Subscription} from "rxjs";
import {Config} from "../models/config";
import {ModalMessage} from "../modal-message/modal-message-model";

@Component({
  selector: 'app-scrisoare-medicala',
  templateUrl: './scrisoare-medicala.component.html',
  styleUrls: ['./scrisoare-medicala.component.css']
})
export class ScrisoareMedicalaComponent implements OnInit, OnDestroy {
  contactSubscription!: Subscription;
  public contact: Contact = new Contact(0, "", "", "", "", "", "", "", "", "");
  public bamCodImagineDreapta: string = "";
  public imagineInCursDeAcreditare: string = "";

  constructor(
    private contactService: ContactService,
    private httpService: HttpService) {}

  ngOnInit(): void {
    this.contactSubscription = this.contactService.contactChanged.subscribe(
      (contact: Contact) => {
        this.contact = contact;
      }
    );
    this.contactService.fetchContact();

    this.httpService.getBamCodImagineDreapta().subscribe(config => {
      console.log(config);
      let c: Config = config as Config;
      this.bamCodImagineDreapta = c.valoare;
    });

    this.httpService.getImagineInCursDeAcreditare().subscribe(config => {
      console.log(config);
      let c: Config = config as Config;
      this.imagineInCursDeAcreditare = c.valoare;
    });

  }

  ngOnDestroy(): void {
  }

}
