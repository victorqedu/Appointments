import {Physician} from "./physician.model";

export class Consultation {
  constructor(
    public id: number ,
    public consultationDate: string ,
    public physician: Physician ,
    public idScrisoareMedicala: number ,
    public serviceName: string,
    public speciality: string,) {}
}
