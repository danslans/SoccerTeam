import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {FetchService} from "./fetch.service";
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,MatCardModule,MatButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'web-soccer-team';
  msn = '';
  idWeek=0;

  constructor(private fetchService: FetchService) {
  }

  initWeek():void{
    debugger
    this.fetchService.postInitWeek().then(
      value => {
        if (value.status){
          this.idWeek = value.payload.id;
        }else {
          this.msn = value.message;
        }
      }
    );
  }
}
