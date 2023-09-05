import {Injectable} from "@angular/core";
import {Subject} from "rxjs";

@Injectable()
export class ScrisoareMedicalaService {
  changedIdScrisoareMedicala = new Subject<number>();
  idScrisoareMedicala: number = 0;

  setChanged(idScrisoareMedicala:number) {
    console.log("New sm = "+idScrisoareMedicala);
    if(this.idScrisoareMedicala!==idScrisoareMedicala) {
      this.changedIdScrisoareMedicala.next(idScrisoareMedicala);
      this.idScrisoareMedicala=idScrisoareMedicala;
    }
  }
}
