import {Injectable} from "@angular/core";
import {Subject} from "rxjs";
import {HttpService} from "../../services/http-service";
import {Service} from "../../models/service.model";

@Injectable()
export class ServicesService {
  constructor(private httpService: HttpService) {}

  isFetchingChanged = new Subject<boolean>();
  changed = new Subject<Service[]>();
  private services!: Service[];

  fetch(idSpeciality: number) {
    console.log("Start fetch findLabTestsGroupsBySpeciality");
    this.isFetchingChanged.next(true);
    this.httpService.findLabTestsGroupsBySpeciality(idSpeciality).subscribe(s => {
      console.log("findLabTestsGroupsBySpeciality ");
      console.log(s);
      this.services = s as Service[];
      this.changed.next(this.services);
      this.isFetchingChanged.next(false);
    });
  }

}
