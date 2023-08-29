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
import { SpecialitiesComponent } from './appointments/specialities/specialities.component';
import { PhysiciansComponent } from './appointments/physicians/physicians.component';
import {SpecialitiesService} from "./appointments/specialities/specialities.service";

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
    SpecialitiesComponent,
    PhysiciansComponent
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
  ],
  providers: [{
    provide : HTTP_INTERCEPTORS,
    useClass : GenericHttpInterceptor,
    multi : true
  },ContactService, CustomErrorService, MatDialog,ModalMessageService,AccountService,SpecialitiesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
