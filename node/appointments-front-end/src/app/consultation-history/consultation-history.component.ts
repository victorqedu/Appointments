import {Component, OnInit} from '@angular/core';
import {HttpService} from "../services/http-service";
import {AccountService} from "../services/accountService";
import {Consultation} from "../models/consultation.model";
import {ModalMessageService} from "../modal-message/modal-message-service";
import {ModalMessage} from "../modal-message/modal-message-model";

@Component({
  selector: 'app-consultation-history',
  templateUrl: './consultation-history.component.html',
  styleUrls: ['./consultation-history.component.css']
})
export class ConsultationHistoryComponent implements OnInit {
  isFetching:boolean=false; // if true data is currently retrieved from the API
  consultations: Consultation[] = [];
  currentPage: number = 1;
  recPerPage: number = 5;
  totalRecords: number = 0;
  totalPages: number = 0;
  displayStartPage: number = 0;
  displayStopPage: number = 0;
  pages: number[] = [];

  constructor(
    private httpService: HttpService,
    public accountService: AccountService,
    private modalMessageService: ModalMessageService,) {}

  ngOnInit(): void {
    console.log("ConsultationHistoryComponent.ngOnInit");
    this.fetchData();
  }

  fetchData() {
    console.log("Fetch this.recPerPage "+this.recPerPage+" offset "+((this.currentPage-1)*this.recPerPage));
    if(this.accountService.getAccount().id!==null && this.accountService.getAccount().cnp!==null && this.accountService.getAccount().cnp!=="0000000000000") {
      this.isFetching = true;
      this.httpService.getConsultationHistory(this.accountService.getAccount().id!, this.recPerPage, (this.currentPage-1)*this.recPerPage).subscribe(consultatii => {
        console.log(consultatii);
        this.consultations = consultatii as Consultation[];
        this.isFetching = false;
      });
      this.httpService.countPatientConsultations(this.accountService.getAccount().id!).subscribe(total => {
        this.totalRecords = total;
        this.totalPages = Math.ceil(total/this.recPerPage);
        //this.totalPages = 10;
        this.setStartAndStop();
        console.log("Total records "+total);
        //this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
      });
    }
  }

  setStartAndStop() {
    let shiftLeft = 0;
    let shiftRight = 0;
    let lr = (this.recPerPage-1)/2;
    if(this.currentPage-lr-1<=0) {
      shiftLeft = this.currentPage-lr-1;
    }
    if(this.totalPages-(this.currentPage+lr)<0) {
      shiftRight = this.totalPages-(this.currentPage+lr);
    }
    this.displayStartPage=this.currentPage-lr-shiftLeft+shiftRight;
    if(this.displayStartPage<1) {
      this.displayStartPage = 1;
    }
    this.displayStopPage=this.currentPage+lr-shiftLeft+shiftRight;
    if(this.displayStopPage>this.totalPages) {
      this.displayStopPage = this.totalPages;
    }
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i + this.displayStartPage);
    console.log("pages:"+this.pages+" shiftLeft "+shiftLeft+" shiftRight "+shiftRight+" this.totalPages "+this.totalPages+" this.currentPage "+this.currentPage+" lr "+lr
      +" this.displayStartPage "+this.displayStartPage+" this.displayStopPage "+this.displayStopPage);
  }

  nextPage() {
    if(this.currentPage<this.totalPages) {
      this.currentPage++;
      this.fetchData();
    }
  }

  previousPage() {
    if(this.currentPage>1) {
      this.currentPage--;
      this.fetchData();
    }
  }

  setPage(page: number) {
    if(page!==this.currentPage) {
      this.currentPage = page;
      this.fetchData();
    }
  }

  showSM(idScrisoareMedicala: number) {
    this.modalMessageService.modalMessageAnswer.subscribe(answer => {
      this.modalMessageService.modalMessageAnswer.unsubscribe();
      this.modalMessageService.reinitializeModalMessageAnswerSubject();
    });
    this.modalMessageService.setModalMessage(
      new ModalMessage(
        "Scrisoare medicala",
        "",
        true,
        false,
        true,
        false,
        "ScrisoareMedicalaComponent",
        idScrisoareMedicala,
        99,
        true,
        ));
  }
}
