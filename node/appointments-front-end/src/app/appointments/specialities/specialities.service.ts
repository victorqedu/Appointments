import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {HttpService} from "../../services/http-service";
import {Speciality} from "../../models/speciality.model";

@Injectable()
export class SpecialitiesService {
  constructor(private httpService: HttpService) {}

  isFetchingChanged = new Subject<boolean>();
  changed = new Subject<Speciality[]>();
  private specialities!: Speciality[];

  fetch() {
    this.isFetchingChanged.next(true);
    this.httpService.getAllAvailableSpecialities().subscribe(s => {
      console.log(s);
      this.specialities = s as Speciality[];
      this.changed.next(this.specialities);
      this.isFetchingChanged.next(false);
    });
  }

}
