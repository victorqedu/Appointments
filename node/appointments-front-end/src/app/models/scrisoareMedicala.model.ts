import {Account} from "./account.model";

export class ScrisoareMedicala {
  constructor(
    public id: number | null,
    public numeMedic: string,
    public cjas: string,
    public contract: string,
    public person: Account | null,
    public nrConsultatie: number | null,
    public dataConsultatie: string,
    public motivelePrezentarii: string,
    public diagnosticSiCodificare: string,
    public anamneza: string,
    public factoriDeRisc: string,
    public examenClinicGeneral: string,
    public examenClinicLocal: string,
    public alte: string,
    public tratamentEfectuat: string,
    public alteInformatii: string,
    public tratamentRecomandat: string,
    public reteta: string,
    public retetaZile: string,
    public concediuMedical: string,
    public exameneParacliniceEkg: string,
    public exameneParacliniceEco: string,
    public exameneParacliniceRx: string,

  ) {}
}

