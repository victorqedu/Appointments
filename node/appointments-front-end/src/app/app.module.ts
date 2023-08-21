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

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BodyComponent,
    FirstPageComponent,
    ContactComponent,
    CustomErrorComponent,
    LoginComponent,
    SignupComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [{
    provide : HTTP_INTERCEPTORS,
    useClass : GenericHttpInterceptor,
    multi : true
  },ContactService, CustomErrorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
