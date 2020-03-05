import { AbstractControl, AsyncValidatorFn } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ClientService } from '../services/client.service';

export class CustomValidator {

    static emailExistsValidator(clientService: ClientService): AsyncValidatorFn {
        return(control: AbstractControl): Observable<{ [key: string]: any } | null> => {
            return clientService.getUserByEmail(control.value)
            .pipe(
                map(response => {
                    return null;
                }),
                catchError(error => {
                    if(error.status == 409)
                        return of({ 'emailExists': true })
                })
            );
        }
    }

}
