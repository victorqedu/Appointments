import {Account} from "./account.model";

export class Personnel {
  constructor(
    public id: number | null,
    public idperson: Account | null,
    public validfrom: string | null,
    public validto: string | null,
  ) {}
}
