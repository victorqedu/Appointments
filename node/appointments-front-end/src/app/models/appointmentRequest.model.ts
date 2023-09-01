import {Speciality} from "./speciality.model";
import {Physician} from "./physician.model";
import {Service} from "./service.model";

export class AppointmentRequest {
  constructor(
    public localDateStartIV: string,
    public localDateStopIV: string,
    public idSpeciality: Speciality,
    public idPhysician: Physician,
    public labTestsGroups: Service[] = []
  ) {}
}
