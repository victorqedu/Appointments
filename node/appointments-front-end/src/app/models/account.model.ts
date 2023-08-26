export class Account {
  constructor(
    public id: number | null,
    public name: string | null,
    public surname: string | null,
    public cnp: string | null,
    public birthDate: string | null,
    public idSex: number | null,
    public authEmail: string | null,
    public onlinePassword: string | null) {}
}
