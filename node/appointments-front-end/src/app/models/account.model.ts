export class Account {
  constructor(
    public id: number | null,
    public name: string | null,
    public surname: string | null,
    public cnp: string | null,
    public birthDate: string | null,
    public sex: number | null,
    public email: string | null,
    public password: string | null) {}
}
