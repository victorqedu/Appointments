import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {Speciality} from "../../models/speciality.model";
import {SpecialitiesService} from "./specialities.service";

@Component({
  selector: 'app-specialities',
  templateUrl: './specialities.component.html',
  styleUrls: ['./specialities.component.css']
})
export class SpecialitiesComponent  implements OnInit, OnDestroy {
  subscriptionData!: Subscription;
  subscriptionIsFetching!: Subscription;
  public specialities!: Speciality[];
  public specialitiesSearched!: Speciality[];
  isFetching:boolean=false;
  placehoderSearch:string = "Scrieti pentru a cauta";
  searchText!: string;

  constructor(private specialitiesService: SpecialitiesService) {
    console.log("ContactComponent.constructor");
  }

  ngOnInit(): void {
    console.log("SpecialitiesComponent.ngOnInit");
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

}
