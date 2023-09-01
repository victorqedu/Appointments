import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {Speciality} from "../../models/speciality.model";
import {FormArray, FormGroup, FormGroupDirective} from "@angular/forms";
import {Physician} from "../../models/physician.model";
import {AppointmentRequest} from "../../models/appointmentRequest.model";
import {Service} from "../../models/service.model";
import {HttpService} from "../../services/http-service";

@Component({
  selector: 'app-physicians-selector',
  templateUrl: './physicians-selector.component.html',
  styleUrls: ['./physicians-selector.component.css']
})
export class PhysiciansSelectorComponent implements OnInit {
  @Output() stepFiveRequiredByUser = new EventEmitter<boolean>(); // I use stepTwoRequiredByUser to inform the parent that I want to go to step 3 of the appointment
  form!: FormGroup; // The parent form will be read here on NgInit
  public physicians!: Physician[]; // list of all specialities obtained from the API
  public physiciansSearched!: Physician[]; // list of specialities filtered using the search criteria
  isFetching:boolean=false; // if true data is currently retrieved from the API
  placehoderSearch:string = "Scrieți pentru a căuta"; // text to be displayed in the search box while no search criteria is inputed by the user
  searchText!: string; // the search criteria

  /**
   * I inject the FormGroupDirective because I need to access the parent form(the FormGroup), I will do this in ngInit
   * @param rootFormGroup
   */
  constructor(private httpService: HttpService, private rootFormGroup: FormGroupDirective) {
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("PhysiciansSelectorComponent.ngOnInit");
    this.form = this.rootFormGroup.control;
    this.isFetching = true;
    this.httpService.getPhysiciansBySpecialityAndDate(
          JSON.parse(this.form.get('speciality')!.value).id,
          this.form.get('appointmentSearchDateStart')!.value).subscribe(p => {
      console.log("getPhysiciansBySpecialityAndDate ");
      console.log(p);
      this.physicians = p as Physician[];
      this.physiciansSearched = p as Physician[];
      this.isFetching = false;
    });
  }

  clearSearchText() {
    if (this.searchText === this.placehoderSearch) {
      this.searchText = "";
    }
  }

  performSearch() {
    console.log("Start performSearch for "+this.searchText);
    if(this.searchText==="") {
      this.physiciansSearched = this.physicians;
    } else {
      this.physiciansSearched = this.physicians.filter( (physician) => {
        //console.log("physician.name?.toUpperCase()+\" \"+physician.surname?.toUpperCase().includes(this.searchText.toUpperCase()) "+physician.name?.toUpperCase()+" "+physician.surname?.toUpperCase().includes(this.searchText.toUpperCase()));
        return (physician.name?.toUpperCase()+" "+physician.surname?.toUpperCase()).includes(this.searchText.toUpperCase());
      }
      );
    }
    console.log("Stop performSearch for "+this.physiciansSearched);
  }

  getAppointmentRequest(physician: Physician): AppointmentRequest {
    let speciality: Speciality = JSON.parse(this.form.get('speciality')!.value);
    let labTestsGroups: Service[] = [];
    let fa = this.form.get('selectedServices') as FormArray
    fa.controls.map(control => {
      let tmp: Service = JSON.parse(control.value);
      labTestsGroups.push(tmp);
    });
    return new AppointmentRequest(
      this.form.get('appointmentSearchDateStart')!.value,
      this.form.get('appointmentSearchDateStop')!.value,
      speciality,
      physician,
      labTestsGroups
    );
  }
}
