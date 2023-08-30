import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {FormArray, FormControl, FormGroup, FormGroupDirective, Validators} from "@angular/forms";
import {Subscription} from "rxjs";
import {Speciality} from "../../models/speciality.model";
import {Service} from "../../models/service.model";
import {ServicesService} from "./services.service";


@Component({
  selector: 'app-service-selector',
  templateUrl: './service-selector.component.html',
  styleUrls: ['./service-selector.component.css']
})
export class ServiceSelectorComponent  implements OnInit, OnDestroy {
  @Output() stepFourRequiredByUser = new EventEmitter<boolean>(); // I use stepTwoRequiredByUser to inform the parent that I want to go to step 3 of the appointment
  form!: FormGroup; // The parent form will be read here on NgInit
  subscriptionData!: Subscription; // the subscription I use in every component to communicate with the service.model.ts of the same component
  subscriptionIsFetching!: Subscription; // the subscription I use in every component to communicate with the service.model.ts to check if the data is currently loading from the API
  public services!: Service[]; // list of all specialities obtained from the API
  public servicesSearched!: Service[]; // list of specialities filtered using the search criteria
  isFetching:boolean=false; // if true data is currently retrieved from the API
  placehoderSearch:string = "Scrieți pentru a căuta"; // text to be displayed in the search box while no search criteria is inputted by the user
  searchText!: string; // the search criteria

  /**
   * I inject the FormGroupDirective because I need to access the parent form(the FormGroup), I will do this in ngInit
   * @param rootFormGroup
   */
  constructor(private servicesService: ServicesService, private rootFormGroup: FormGroupDirective) {
    console.log("ServiceSelectorComponent.constructor");
  }

  ngOnInit(): void {
    console.log("ServiceSelectorComponent.ngOnInit");
    this.form = this.rootFormGroup.control;
    console.log(this.form);
    this.subscriptionData = this.servicesService.changed.subscribe(
      (s: Service[]) => {
        console.log("ServiceSelectorComponent.ngOnInit changes to "+s);
        this.services = s;
        this.servicesSearched = s;
        console.log("SpecialitiesComponent.ngOnInit size "+this.services.length);
      }
    );
    this.subscriptionIsFetching = this.servicesService.isFetchingChanged.subscribe(
      (isFetchingChanged: boolean) => {
        this.isFetching = isFetchingChanged;
      }
    );
    this.servicesService.fetch(this.form.get('specialityId')!.value);
    console.log("ServiceSelectorComponent.ngOnInit end");
  }

  ngOnDestroy(): void {
    console.log("ServiceSelectorComponent.ngOnDestroy");
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
      this.servicesSearched = this.services;
    } else {
      this.servicesSearched = this.services.filter( (speciality) => speciality.name?.toUpperCase().includes(this.searchText.toUpperCase()));
    }
  }

  /**
   * When the user inserts a date and presses "Go to step 4" this function will be executed and the result the parent component will be informed
   */
  goToStep4() {
    console.log(this.form);
    if (this.form.controls['selectedServices'].valid) {
      this.stepFourRequiredByUser.emit(true);
    }
  }

  checkServiceIsSelected(s: Service): number {
    let fa = this.form.get('selectedServices') as FormArray
    return fa.controls.findIndex(control => {
      let tmp: Service = JSON.parse(control.value);
      return s.id === tmp.id;
    });
  }

  addOrRemoveService(s: Service) {
    let index = this.checkServiceIsSelected(s);
    let fa = this.form.get('selectedServices') as FormArray
    if(index===-1) {
      let serviceId: FormControl  = new FormControl(JSON.stringify(s), [Validators.required]);
      fa.push(serviceId);
    } else {
      fa.removeAt(index);
    }
  }

}
