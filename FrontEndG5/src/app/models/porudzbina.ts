import { Dobavljac } from 'src/app/models/dobavljac';
export class Porudzbina{
    id!:number;
    datum!:Date;
    isporuceno!:Date;
    iznos!:number;
    placeno!:boolean;
    dobavljac!:Dobavljac;
}