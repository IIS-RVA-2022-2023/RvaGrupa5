import { ArtiklService } from './../../../services/artikl.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-artikl',
  templateUrl: './artikl.component.html',
  styleUrls: ['./artikl.component.css']
})
export class ArtiklComponent implements OnInit {
  
  constructor(private artiklService: ArtiklService){

  }

  ngOnInit(): void {
    this.loadData();
  }

  public loadData(){
    this.artiklService.getAllArtikls().subscribe(
      data => {console.log(data);} 
    ),
    (error : Error) => {console.log(error.name + ' ' + error.message);}
  }


}
