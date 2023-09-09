import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { BodyComponent } from './body/body.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FirstPageComponent } from './first-page/first-page.component';
import { ContactComponent } from './contact/contact.component';
import {ContactService} from "./contact/contact.service";
import { CustomErrorComponent } from './custom-error/custom-error.component';
import {GenericHttpInterceptor} from "./GenericHttpInterceptor";
import {CustomErrorService} from "./custom-error/custom-error.service";
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import { ModalMessageComponent } from './modal-message/modal-message.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {MatExpansionModule} from "@angular/material/expansion";
import {ModalMessageService} from "./modal-message/modal-message-service";
import {AccountService} from "./services/accountService";
import { WaitingForDataComponent } from './waiting-for-data/waiting-for-data.component';
import { AppointmentsComponent } from './appointments/appointments.component';
import { SpecialitiesSelectorComponent } from './appointments/specialities-selector/specialities-selector.component';
import { PhysiciansSelectorComponent } from './appointments/physicians-selector/physicians-selector.component';
import {SpecialitiesService} from "./appointments/specialities-selector/specialities.service";
import { DateSelectorComponent } from './appointments/date-selector/date-selector.component';
import { ServiceSelectorComponent } from './appointments/service-selector/service-selector.component';
import {ImageDisplayComponent} from "./image-display/image-display.component";
import { PhysicianAppointmentsComponent } from './appointments/physician-appointments/physician-appointments.component';
import {MatButtonModule} from "@angular/material/button";
import { ConsultationHistoryComponent } from './consultation-history/consultation-history.component';
import { AppointmentsHistoryComponent } from './appointments-history/appointments-history.component';
import { ScrisoareMedicalaComponent } from './scrisoare-medicala/scrisoare-medicala.component';
import { ConfirmEmailComponent } from './confirm-email/confirm-email.component';
import { ResendConfirmationEmailComponent } from './resend-confirmation-email/resend-confirmation-email.component';
import { SubmitButtonComponent } from './submit-button/submit-button.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BodyComponent,
    FirstPageComponent,
    ContactComponent,
    CustomErrorComponent,
    LoginComponent,
    SignupComponent,
    ModalMessageComponent,
    WaitingForDataComponent,
    AppointmentsComponent,
    SpecialitiesSelectorComponent,
    PhysiciansSelectorComponent,
    DateSelectorComponent,
    ServiceSelectorComponent,
    ImageDisplayComponent,
    PhysicianAppointmentsComponent,
    ConsultationHistoryComponent,
    AppointmentsHistoryComponent,
    ScrisoareMedicalaComponent,
    ConfirmEmailComponent,
    ResendConfirmationEmailComponent,
    SubmitButtonComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatExpansionModule,
    MatButtonModule,
  ],
  providers: [
    {
    provide : HTTP_INTERCEPTORS,
    useClass : GenericHttpInterceptor,
    multi : true
    },
    ContactService,
    CustomErrorService,
    MatDialog,
    ModalMessageService,
    AccountService,
    SpecialitiesService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
