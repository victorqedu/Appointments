export class CustomError {
  constructor(public title: string, public code: string, public description: string, public technicalData: string) {
    if(code == "0") {
      this.description = "Conexiunea catre serviciile REST nu este posibila "+description;
    }
  }
}

