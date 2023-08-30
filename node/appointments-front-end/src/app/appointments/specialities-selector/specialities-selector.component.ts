import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {Speciality} from "../../models/speciality.model";
import {SpecialitiesService} from "./specialities.service";
import {FormGroup, FormGroupDirective} from "@angular/forms";

@Component({
  selector: 'app-specialities-selector',
  templateUrl: './specialities-selector.component.html',
  styleUrls: ['./specialities-selector.component.css']
})
export class SpecialitiesSelectorComponent implements OnInit, OnDestroy {
  @Output() stepThreeRequiredByUser = new EventEmitter<boolean>(); // I use stepTwoRequiredByUser to inform the parent that I want to go to step 3 of the appointment
  form!: FormGroup; // The parent form will be read here on NgInit
  subscriptionData!: Subscription; // the subscription I use in every component to communicate with the service.model.ts of the same component
  subscriptionIsFetching!: Subscription; // the subscription I use in every component to communicate with the service.model.ts to check if the data is currently loading from the API
  public specialities!: Speciality[]; // list of all specialities obtained from the API
  public specialitiesSearched!: Speciality[]; // list of specialities filtered using the search criteria
  isFetching:boolean=false; // if true data is currently retrieved from the API
  placehoderSearch:string = "Scrieți pentru a căuta"; // text to be displayed in the search box while no search criteria is inputed by the user
  searchText!: string; // the search criteria
  specialityId!: number; // the id of the specialty that is currently selected
  specialityName!: string; // the name of the specialty that is currently selected

  /**
   * I inject the FormGroupDirective because I need to access the parent form(the FormGroup), I will do this in ngInit
   * @param rootFormGroup
   */
  constructor(private specialitiesService: SpecialitiesService, private rootFormGroup: FormGroupDirective) {
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("SpecialitiesComponent.ngOnInit");
    this.form = this.rootFormGroup.control;
    this.subscriptionData = this.specialitiesService.changed.subscribe(
      (s: Speciality[]) => {
        console.log("SpecialitiesComponent.ngOnInit changes to "+s);
        this.specialities = s;
        this.specialitiesSearched = s;
        console.log("SpecialitiesComponent.ngOnInit size "+this.specialities.length);
      }
    );
    this.subscriptionIsFetching = this.specialitiesService.isFetchingChanged.subscribe(
      (isFetchingChanged: boolean) => {
        this.isFetching = isFetchingChanged;
      }
    );
    this.specialitiesService.fetch();
  }

  ngOnDestroy(): void {
    console.log("SpecialitiesComponent.ngOnDestroy");
    this.subscriptionData.unsubscribe();
    this.subscriptionIsFetching.unsubscribe();
  }

  clearSearchText() {
    if (this.searchText === this.placehoderSearch) {
      this.searchText = "";
    }
  }

  performSearch() {
    if(this.searchText==="") {
      this.specialitiesSearched = this.specialities;
    } else {
      this.specialitiesSearched = this.specialities.filter( (speciality) => speciality.name?.toUpperCase().includes(this.searchText.toUpperCase()));
    }
  }


  setCurrentSpeciality(id: number | null, name: string | null) {
    this.form.get('specialityId')!.patchValue(id);
    this.form.get('specialityName')!.patchValue(name);
    this.goToStep3();
  }


  /**
   * When the user inserts a date and presses "Go to step 3" this function will be executed and the result the parent component will be informed
   */
  goToStep3() {
    console.log(this.form);
    if (this.form.controls['specialityId'].valid && this.form.controls['specialityName'].valid) {
      this.stepThreeRequiredByUser.emit(true);
    }
  }
}
