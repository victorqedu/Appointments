import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Contact} from "../models/contact.model";
import {ContactService} from "../contact/contact.service";
import {map, tap} from "rxjs";
import {Account} from "../models/account.model";
import {Speciality} from "../models/speciality.model";
import {AppointmentRequest} from "../models/appointmentRequest.model";
import {Appointment} from "../models/appointment.model";

/**
 * In this class I have many observers and I will not use any error handling, this will happen in a single common place, in the GenericHttpInterceptors
 */
@Injectable({ providedIn: 'root' })
export class HttpService {
  protected serverProtocol: string = "https";
  protected serverHost: string = "mail.caido.ro";
  protected serverPort: number = 8081;
  protected serverPrefix: string = "api";

  constructor(private http: HttpClient) {
    console.log("HttpService.constructor");
  }

  /**
   * Fetches the contact data for the current company
   * I have no subscribe nowhere in the code because the resolve in app-routing-module.ts is calling subscribe automatically
   * The upper line above is no longer valid because I no longer use resolve in app-routing-module.ts,
   * I switch to an approach using manual subscribe because I need to update the isFetching attribute from the contact to display a loading indicator
   */
  getContact() {
    console.log("HttpService.getContact");
    return this.http.get(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/localcompany");
  }

  getTermsAndConditions() {
    return this.http.get(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/termsAndConditions");
  }

  getPolicyOfConfidentiality() {
    return this.http.get(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/policyOfConfidentiality");
  }

  storeSignup(account: Account) {
    return this.http
      .post(
        this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/register",
        account
      )
      .subscribe(response => {
        console.log(response);
      });
  }

  login(account: Account) {
    sessionStorage.setItem("accountDetails",JSON.stringify(account));
    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'application/json');
    return this.http
      .get(this.serverProtocol+"://"+this.serverHost+":"+this.serverPort+"/"+this.serverPrefix+"/login", { observe: 'response', headers});
  }

  getAllAvailableSpecialities() {
    return this.http.get<any>(this.serverProtocol + "://" + this.serverHost + ":" + this.serverPort + "/" + this.serverPrefix + "/specialities")
      .pipe(
        map(response => response._embedded.specialitiesList), // Extract the array from the response
      );
  }

  findLabTestsGroupsBySpeciality(idSpeciality: number) {
    console.log("Start findLabTestsGroupsBySpeciality");
    return this.http.get<any>(this.serverProtocol + "://" + this.serverHost + ":" + this.serverPort + "/" + this.serverPrefix + "/specialitiesLabTestsGroups/"+idSpeciality)
      .pipe(
        map(response => {
          if(response.hasOwnProperty("_embedded") && response._embedded.hasOwnProperty("labTestsGroupsList")) {
            return response._embedded.labTestsGroupsList;
          } else {
            return [];
          }
        }), // Extract the array from the response
        tap(response => {
          console.log("findLabTestsGroupsBySpeciality "+response);
        })
      );
  }

  getPhysiciansBySpecialityAndDate(idSpeciality: number, date: string) {
    console.log("Start getAvailablePhysiciansBySpecialityAndDate");
    return this.http.get<any>(this.serverProtocol + "://" + this.serverHost + ":" + this.serverPort + "/" + this.serverPrefix + "/physicians/"+idSpeciality+"/"+date)
      .pipe(
        map(response => {
          if(response.hasOwnProperty("_embedded") && response._embedded.hasOwnProperty("simplePhysicianDTOList")) {
            return response._embedded.simplePhysicianDTOList;
          } else {
            return [];
          }
        }), // Extract the array from the response
        tap(response => {
          console.log("getAvailablePhysiciansBySpecialityAndDate "+response);
        })
      );
  }

  getPhysiciansAvailableAppointments(ar: AppointmentRequest) {
    console.log("Start getPhysiciansAvailableAppointments");
    return this.http.post<any>(this.serverProtocol + "://" + this.serverHost + ":" + this.serverPort + "/" + this.serverPrefix+"/appointments" , JSON.stringify(ar))
      .pipe(
        map(response => {
          if(response.hasOwnProperty("_embedded") && response._embedded.hasOwnProperty("appointmentsList")) {
            return response._embedded.appointmentsList;
          } else {
            return [];
          }
        }), // Extract the array from the response
        tap(response => {
          //console.log(response);
        })
      );
  }

  saveAppointment(appointment: Appointment) {
    return this.http.post<any>(this.serverProtocol + "://" + this.serverHost + ":" + this.serverPort + "/" + this.serverPrefix+"/saveAppointment" , JSON.stringify(appointment))
      .pipe(
        map(response => response), // Extract the array from the response
        tap(response => {
          //console.log(response);
        })
      );
  }

}
