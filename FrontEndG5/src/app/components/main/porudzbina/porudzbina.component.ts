import { PorudzbinaDialogComponent } from './../../dialogs/porudzbina-dialog/porudzbina-dialog.component';
import { Dobavljac } from 'src/app/models/dobavljac';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Data } from '@angular/router';
import { Subscription } from 'rxjs';
import { Porudzbina } from 'src/app/models/porudzbina';
import { PorudzbinaService } from 'src/app/services/porudzbina.service';

@Component({
  selector: 'app-porudzbina',
  templateUrl: './porudzbina.component.html',
  styleUrls: ['./porudzbina.component.css']
})
export class PorudzbinaComponent implements OnInit,OnDestroy {

  dataSource!: MatTableDataSource<Porudzbina>;
  displayedColumns = ['id','datum','isporuceno','iznos','placeno','dobavljac','actions'];
  subscription!:Subscription;

  constructor(private porudzbinaService: PorudzbinaService,
              public dialog: MatDialog){

  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(){
    this.subscription = this.porudzbinaService.getAllPorudzbinas().subscribe(
      data => {this.dataSource = new MatTableDataSource(data);}),
      (error:Error) => {console.log(error.name + ' ' + error.message);}
  }

  public openDialog(flag:number, id?:number, datum?:Date, isporuceno?:Date, iznos?:number,
                                placeno?:boolean, dobavljac?:Dobavljac):void{
    const dialogRef = this.dialog.open(PorudzbinaDialogComponent, {data:{id,datum,isporuceno,iznos,placeno,dobavljac}});
    dialogRef.componentInstance.flag = flag;
    dialogRef.afterClosed().subscribe(
      result => {
        if(result == 1){
          this.loadData();
        }
      }
    )
  }
}

  

