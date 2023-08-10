export class Contact {
  public id: number;
  public code: string;
  public name: string;
  public fiscalCode: string;
  public address: string;
  public phone: string;
  public mail: string;
  public localUrgUnit: string

  constructor(id: number, code: string, name: string, fiscalCode: string, address: string, phone: string, mail: string, localUrgUnit: string) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.name = name;
    this.fiscalCode = fiscalCode;
    this.address = address;
    this.phone = phone;
    this.mail = mail;
    this.localUrgUnit = localUrgUnit;
  }
}

