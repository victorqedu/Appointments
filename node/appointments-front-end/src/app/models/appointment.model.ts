import {Account} from "./account.model";
import {Physician} from "./physician.model";
import {Speciality} from "./speciality.model";
import {Service} from "./service.model";
import {AppointmentTypes} from "./appointmentsTypes.model";
import {Personnel} from "./personnel.model";

export class Appointment {
  constructor(
    public id: number | null,
    public oraProgramare: string | null,
    public minuteEstimate: number | null,
    public oraConfirmare: string | null,
    public comments: string | null,
    public idAppointmentsTypes: AppointmentTypes,
    public idPerson: Account | null,
    public idPersonnel: Personnel | null,
    public idPhysicians: Physician | null,
    public idSpeciality: Speciality | null,
    public labTestsGroups: Service[] = [],
    public idDepartment: number | null
  ) {}

}
