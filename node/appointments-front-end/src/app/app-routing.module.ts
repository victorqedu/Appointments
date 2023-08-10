import {inject, NgModule} from '@angular/core';
import {ActivatedRouteSnapshot, ResolveFn, RouterModule, RouterStateSnapshot, Routes} from '@angular/router';
import {FirstPageComponent} from "./first-page/first-page.component";
import {ContactComponent} from "./contact/contact.component";
import {HttpService} from "./http-service";
import {ContactService} from "./contact/contact.service";

const getContact: ResolveFn<any> =
  (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    if(inject(ContactService).getContact() === undefined) {
      console.log("contact is undefined");
      return inject(HttpService).getContact();
    } else {
      console.log("contact is defined");
      return;
    }
  };

const routes: Routes = [
  { path: '', redirectTo: '/firstPage', pathMatch: 'full'  },
  { path: 'firstPage', component: FirstPageComponent },
  { path: 'contact', component: ContactComponent, resolve: {data: getContact} },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}

