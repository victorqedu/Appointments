import {inject, NgModule} from '@angular/core';
import {ActivatedRouteSnapshot, ResolveFn, RouterModule, RouterStateSnapshot, Routes} from '@angular/router';
import {FirstPageComponent} from "./first-page/first-page.component";
import {ContactComponent} from "./contact/contact.component";
import {HttpService} from "./services/http-service";
import {ContactService} from "./contact/contact.service";
import {CustomErrorComponent} from "./custom-error/custom-error.component";
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {AppointmentsComponent} from "./appointments/appointments.component";
import {ConsultationHistoryComponent} from "./consultation-history/consultation-history.component";
import {AppointmentsHistoryComponent} from "./appointments-history/appointments-history.component";
import {ScrisoareMedicalaComponent} from "./scrisoare-medicala/scrisoare-medicala.component";
import {ConfirmEmailComponent} from "./confirm-email/confirm-email.component";

/*const getContact: ResolveFn<any> =
  (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    if(inject(ContactService).getContact() === undefined) {
      console.log("contact is undefined");
      return inject(HttpService).getContact();
    } else {
      console.log("contact is defined");
      return;
    }
  };*/

const routes: Routes = [
  { path: '', redirectTo: '/firstPage', pathMatch: 'full'  },
  { path: 'firstPage', component: FirstPageComponent },
//  { path: 'contact', component: ContactComponent, resolve: {data: getContact} },
  { path: 'contact', component: ContactComponent },
  { path: 'error', component: CustomErrorComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'appointments', component: AppointmentsComponent },
  { path: 'consultationHistory', component: ConsultationHistoryComponent },
  { path: 'appointmentsHistory', component: AppointmentsHistoryComponent },
  { path: 'confirmEmail/:jwtToken', component: ConfirmEmailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

