import { Region } from './region';
import { Bill } from './bill';

export class Client {

    id: number;
    firstName: string;
    lastName: string;
    createAt: string;
    email: string;
    photo: string;
    region: Region;
    bills: Array<Bill> = new Array();

}
