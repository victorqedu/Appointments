import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from "rxjs";
import {Speciality} from "../../models/speciality.model";
import {SpecialitiesService} from "./specialities.service";
import {FormGroup, FormGroupDirective} from "@angular/forms";
import {HttpService} from "../../services/http-service";

@Component({
  selector: 'app-specialities-selector',
  templateUrl: './specialities-selector.component.html',
  styleUrls: ['./specialities-selector.component.css']
})
export class SpecialitiesSelectorComponent implements OnInit {
  @Output() stepThreeRequiredByUser = new EventEmitter<boolean>(); // I use stepTwoRequiredByUser to inform the parent that I want to go to step 3 of the appointment
  form!: FormGroup; // The parent form will be read here on NgInit
  public specialities!: Speciality[]; // list of all specialities obtained from the API
  public specialitiesSearched!: Speciality[]; // list of specialities filtered using the search criteria
  isFetching:boolean=false; // if true data is currently retrieved from the API
  placehoderSearch:string = "Scrieți pentru a căuta"; // text to be displayed in the search box while no search criteria is inputed by the user
  searchText!: string; // the search criteria
  speciality!: Speciality; // the specialty that is currently selected

  /**
   * I inject the FormGroupDirective because I need to access the parent form(the FormGroup), I will do this in ngInit
   * @param rootFormGroup
   */
  constructor(private httpService: HttpService, private rootFormGroup: FormGroupDirective) {
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("SpecialitiesComponent.ngOnInit");
    this.form = this.rootFormGroup.control;
    this.isFetching = true;
    this.httpService.getAllAvailableSpecialities().subscribe(s => {
      console.log(s);
      this.specialities = s as Speciality[];
      this.specialitiesSearched = s as Speciality[];
      this.isFetching = false;
    });
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


  setCurrentSpeciality(s:Speciality) {
    this.form.get('speciality')!.patchValue(JSON.stringify(s));
    this.goToStep3();
  }


  /**
   * When the user inserts a date and presses "Go to step 3" this function will be executed and the result the parent component will be informed
   */
  goToStep3() {
    console.log(this.form);
    if (this.form.controls['speciality'].valid) {
      this.stepThreeRequiredByUser.emit(true);
    }
  }
}
