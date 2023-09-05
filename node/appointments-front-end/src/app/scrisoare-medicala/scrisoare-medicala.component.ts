import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {HttpService} from "../services/http-service";
import {ContactService} from "../contact/contact.service";
import {Contact} from "../models/contact.model";
import {Subject, Subscription} from "rxjs";
import {Config} from "../models/config";
import {ScrisoareMedicala} from "../models/scrisoareMedicala.model";

@Component({
  selector: 'app-scrisoare-medicala',
  templateUrl: './scrisoare-medicala.component.html',
  styleUrls: ['./scrisoare-medicala.component.css']
})
export class ScrisoareMedicalaComponent implements OnInit, OnDestroy, OnChanges {
  contactSubscription!: Subscription;
  public contact: Contact = new Contact(0, "", "", "", "", "", "", "", "", "");
  public bamCodImagineDreapta: string = "";
  public imagineInCursDeAcreditare: string = "";
  public scrisoareMedicala: ScrisoareMedicala = new ScrisoareMedicala(0,"","","", null, null,"","",
    "","","","","","","","",
    "","","","","","","");

  @Input()
  public idScrisoareMedicala!: number;

  constructor(
    private contactService: ContactService,
    private httpService: HttpService,
    ) {}

  ngOnInit(): void {
    console.log("ngOnInit in ScrisoareMedicalaComponent "+this.idScrisoareMedicala);
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

  ngOnChanges(changes: SimpleChanges): void {
    console.log("Something changed "+this.idScrisoareMedicala);
    console.log(changes);
    if(changes.hasOwnProperty("idScrisoareMedicala")) {
      console.log("has");
      if(changes['idScrisoareMedicala'].currentValue!==changes['idScrisoareMedicala'].previousValue) {
        console.log("changed");
        this.scrisoareMedicala.id = 0;
        this.httpService.getScrisoareMedicala(this.idScrisoareMedicala).subscribe(sm => {
          this.scrisoareMedicala = sm;
        });
      }
    }
  }

}
