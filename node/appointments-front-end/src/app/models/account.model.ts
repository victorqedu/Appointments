export class Account {
  public id: number;
  public name: string;
  public surname: string;
  public cnp: string;
  public birthDate: string;
  public sex: number;
  public email: string;
  public password: string;

  constructor(id: number, name: string, surname: string, cnp: string, birthDate: string, sex: number, email: string, password: string) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.cnp = cnp;
    this.birthDate = birthDate;
    this.sex = sex;
    this.email = email;
    this.password = password;
  }
}

