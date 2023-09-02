import {Component, Input, OnInit} from '@angular/core';
import {Appointment} from "../../models/appointment.model";
import {AppointmentRequest} from "../../models/appointmentRequest.model";
import {HttpService} from "../../services/http-service";
import {Physician} from "../../models/physician.model";
import {FormArray, FormGroup, FormGroupDirective} from "@angular/forms";
import {Account} from "../../models/account.model";
import {Speciality} from "../../models/speciality.model";
import {AccountService} from "../../services/accountService";
import {AppointmentTypes} from "../../models/appointmentsTypes.model";
import {Service} from "../../models/service.model";
import {ModalMessageService} from "../../modal-message/modal-message-service";
import {ModalMessage} from "../../modal-message/modal-message-model";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";

@Component({
  selector: 'app-physician-appointments',
  templateUrl: './physician-appointments.component.html',
  styleUrls: ['./physician-appointments.component.css']
})
export class PhysicianAppointmentsComponent implements OnInit {
  @Input() appointmentRequest!: AppointmentRequest;
  form!: FormGroup; // The parent form will be read here on NgInit
  appointments: Appointment[] = [];
  isFetching:boolean=false;
  daysAvailableSorted: string[] = [];
  daysAvailable: Map<string, string[]> = new Map<string, string[]>();

  constructor(
    private httpService: HttpService,
    private rootFormGroup: FormGroupDirective,
    private modalMessageService: ModalMessageService,
    private accountService: AccountService,
    private router: Router,
    ) {}

  ngOnInit(): void {
    console.log("PhysicianAppointmentsComponent.ngOnInit");
    this.form = this.rootFormGroup.control;
    this.isFetching = true;
    this.httpService.getPhysiciansAvailableAppointments(this.appointmentRequest).subscribe(availableAppointments => {
      this.appointments = availableAppointments;
      this.calculateAllAvailableDays(this.appointments);
      this.isFetching = false;
    });
  }

  public calculateAllAvailableDays(appointments: Appointment[]) {
    appointments.map(appointment => {
      let splitDateFromTime = appointment.oraProgramare!.split("T");
      let date = splitDateFromTime[0];
      let time = splitDateFromTime[1];
      let timeSplit = time.split(":");
      let appointmentStart = timeSplit[0]+":"+timeSplit[1];
      if(!this.daysAvailable.has(date)) {
        this.daysAvailable.set(date, [appointmentStart]);
      } else {
        this.daysAvailable.get(date)!.push(appointmentStart);
      }
      this.daysAvailableSorted = Array.from(this.daysAvailable.keys()).sort();
    });
  }
  /**
   * Check if the for is valid, if the data is ready to be sent to the backend
   */
  checkAllFieldsAreValid():boolean {
    if(this.form.valid
      && this.form.get('appointmentSearchDateStart')!.valid
      && this.form.get('appointmentSearchDateStop')!.valid
      && this.form.get('speciality')!.valid
      && this.form.get('selectedServices')!.valid
      && this.form.get('physician')!.valid
      && this.form.get('appointmentHour')!.valid) {
      return true;
    }
    return false;
  }

  submit(day:string, hour: string) {
    //console.log(this.form);
    console.log("Start submit");
    this.form.get('physician')!.patchValue(JSON.stringify(this.appointmentRequest.idPhysician));
    this.form.get('appointmentHour')!.patchValue(day+"T"+hour+":00");
    let ss = this.form.get('selectedServices') as FormArray;
    let labTestsGroups: Service[] = [];
    ss.controls.map(ctrl => {
      labTestsGroups.push(JSON.parse(ctrl.value));
    });
    if(this.checkAllFieldsAreValid()) {
      let appointment = new Appointment(null, this.form.get('appointmentHour')?.value, null, null, null, new AppointmentTypes(1),
      this.accountService.getAccount(), this.appointmentRequest.idPhysician.idPersonnel, this.appointmentRequest.idPhysician, this.appointmentRequest.idSpeciality,
        labTestsGroups, this.appointmentRequest.idPhysician.idDepartment);
      //console.log(JSON.stringify(appointment));
      //console.log(appointment);

      console.log("Start submit - subscribe to modalMessageAnswer");
      this.modalMessageService.modalMessageAnswer.subscribe(answer => {
          console.log("Modal answer: "+answer);
          this.modalMessageService.modalMessageAnswer.unsubscribe();
          this.modalMessageService.reinitializeModalMessageAnswerSubject();
          if(answer) { //Confirm has been hit
            this.httpService.saveAppointment(appointment).subscribe(savedAppointment => {
              console.log("Saved");
              console.log(savedAppointment);
              if(savedAppointment.id!=null) {
                this.modalMessageService.modalMessageAnswer.subscribe(answer => {
                  this.modalMessageService.modalMessageAnswer.unsubscribe();
                  this.modalMessageService.reinitializeModalMessageAnswerSubject();
                  this.router.navigate(['/firstPage']);
                });
                this.modalMessageService.setModalMessage(
                  new ModalMessage(
                    "Programare salvată cu succes",
                    "Programare salvată cu succes, puteți accesa 'Contul meu/Date cont' pentru a vizualiza istoricul programarilor si pentru a anula programarile",
                    true,
                    false,
                    false,
                    false));

              }
            });
          } else { //cancel hit
            //nothing to do
          }
        }
      );
      this.modalMessageService.setModalMessage(
        new ModalMessage(
          "Cofirmați salvarea programării cu datele de mai jos?",
          "Programare pentru specialitatea "+this.appointmentRequest.idSpeciality.name+" la medicul "+this.appointmentRequest.idPhysician.name+
              " "+this.appointmentRequest.idPhysician.surname+" pentru data și ora "+day+" "+hour,
          true,
          false,
          false,
          true));

    }
  }
}
