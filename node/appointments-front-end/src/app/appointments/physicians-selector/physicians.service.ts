import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {HttpService} from "../../services/http-service";
import {Physician} from "../../models/physician.model";

@Injectable()
export class PhysiciansService {
  constructor(private httpService: HttpService) {}

  isFetchingChanged = new Subject<boolean>();
  changed = new Subject<Physician[]>();
  private physicians!: Physician[];

  fetch(idSpeciality: number, date: string) {
    console.log("Start fetch getPhysiciansBySpecialityAndDate");
    this.isFetchingChanged.next(true);
    this.httpService.getPhysiciansBySpecialityAndDate(idSpeciality, date).subscribe(p => {
      console.log("getPhysiciansBySpecialityAndDate ");
      console.log(p);
      this.physicians = p as Physician[];
      this.changed.next(this.physicians);
      this.isFetchingChanged.next(false);
    });
  }

}
