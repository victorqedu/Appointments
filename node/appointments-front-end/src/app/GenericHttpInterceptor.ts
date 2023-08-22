import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, EMPTY, Observable} from "rxjs";
import {Router} from "@angular/router";
import {CustomErrorService} from "./custom-error/custom-error.service";
import {CustomError} from "./custom-error/custom-error.model";

@Injectable()
export class GenericHttpInterceptor implements HttpInterceptor {
  private customErrorService: CustomErrorService;
  constructor(private router: Router, cs: CustomErrorService) {
    this.customErrorService = cs;
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log("GenericHttpInterceptor.intercept");
    return next.handle(req).pipe(
      catchError((error) => {
        console.log("GenericHttpInterceptor.intercept "+JSON.stringify(error));
        this.customErrorService.setCustomError(new CustomError(error.name, error.status, error.statusText + JSON.stringify(error.error), JSON.stringify(error, undefined, 2)));
        this.router.navigate(['/error']);
        return EMPTY;
      })
    );
  }

}
