export class CustomError {
  public title: string;
  public code: string;
  public description: string;
  public technicalData: string;

  constructor(title: string, code: string, description: string, technicalData: string) {
    this.title = title;
    this.code = code;
    if(code == "0") {
      this.description = "Conexiunea catre serviciile REST nu este posibila "+description;
    } else {
      this.description = description;
    }
    this.technicalData = technicalData;
  }
}

