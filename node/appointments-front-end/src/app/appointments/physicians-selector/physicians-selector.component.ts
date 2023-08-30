import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {Speciality} from "../../models/speciality.model";
import {PhysiciansService} from "./physicians.service";
import {FormGroup, FormGroupDirective} from "@angular/forms";
import {Physician} from "../../models/physician.model";

@Component({
  selector: 'app-physicians-selector',
  templateUrl: './physicians-selector.component.html',
  styleUrls: ['./physicians-selector.component.css']
})
export class PhysiciansSelectorComponent implements OnInit, OnDestroy {
  @Output() stepFiveRequiredByUser = new EventEmitter<boolean>(); // I use stepTwoRequiredByUser to inform the parent that I want to go to step 3 of the appointment
  form!: FormGroup; // The parent form will be read here on NgInit
  subscriptionData!: Subscription; // the subscription I use in every component to communicate with the service.model.ts of the same component
  subscriptionIsFetching!: Subscription; // the subscription I use in every component to communicate with the service.model.ts to check if the data is currently loading from the API
  public physicians!: Physician[]; // list of all specialities obtained from the API
  public physiciansSearched!: Physician[]; // list of specialities filtered using the search criteria
  isFetching:boolean=false; // if true data is currently retrieved from the API
  placehoderSearch:string = "Scrieți pentru a căuta"; // text to be displayed in the search box while no search criteria is inputed by the user
  searchText!: string; // the search criteria

  /**
   * I inject the FormGroupDirective because I need to access the parent form(the FormGroup), I will do this in ngInit
   * @param rootFormGroup
   */
  constructor(private physiciansService: PhysiciansService, private rootFormGroup: FormGroupDirective) {
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("PhysiciansSelectorComponent.ngOnInit");
    this.form = this.rootFormGroup.control;
    this.subscriptionData = this.physiciansService.changed.subscribe(
      (p: Physician[]) => {
        console.log("PhysiciansSelectorComponent.ngOnInit changes to "+p);
        this.physicians = p;
        this.physiciansSearched = p;
        console.log("PhysiciansSelectorComponent.ngOnInit size "+this.physicians.length);
      }
    );
    this.subscriptionIsFetching = this.physiciansService.isFetchingChanged.subscribe(
      (isFetchingChanged: boolean) => {
        this.isFetching = isFetchingChanged;
      }
    );
    this.physiciansService.fetch(this.form.get('specialityId')!.value, this.form.get('appointmentSearchDateStart')!.value);
  }

  ngOnDestroy(): void {
    console.log("PhysiciansSelectorComponent.ngOnDestroy");
    this.subscriptionData.unsubscribe();
    this.subscriptionIsFetching.unsubscribe();
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

  /**
   * When the user inserts a date and presses "Go to step 5" this function will be executed and the result the parent component will be informed
   */
  setPhysician(p: Physician) {
    console.log(this.form);
    this.form.get('physician')!.patchValue(JSON.stringify(p));

  }

  submit() {
    this.stepFiveRequiredByUser.emit(true);
  }
}
