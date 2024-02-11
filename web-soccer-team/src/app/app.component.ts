import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {FetchService} from "./fetch.service";
import {CommonModule} from "@angular/common";
import {MatInputModule} from '@angular/material/input';
import {
  Configuration,
  ConfigurePercentage,
  IPlayer,
  Player,
  Speed,
  Stat, TitularTeam,
  TrainingRequest,
  Week
} from "./models/models";
import {FormsModule} from "@angular/forms";
import {MatTableModule} from "@angular/material/table";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MatCardModule, MatButtonModule, CommonModule, MatInputModule,FormsModule,MatTableModule ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'web-soccer-team';
  msn = '';
  idWeek = 0;
  idConfiguration=0;
  initWeekState = false;
  isConfiguration = false;
  isTraining = false;
  effectivePassesPercentage = 0;
  shootingPowerPercentage = 0;
  velocityPercentage = 0;
  trainingPlayers :IPlayer[] = [];
  titularTeam:TitularTeam[] = [];


  constructor(private fetchService: FetchService) {

  }

  initWeek(): void {
    this.fetchService.postInitWeek().then(
      value => {
        this.initWeekState = value.status;
        if (value.status) {
          this.idWeek = value.payload.id;
        } else {
          this.msn = value.message;
        }
      }
    );
  }

  saveConfigurationPercentage(): void {
    const percentages = new ConfigurePercentage(
      this.effectivePassesPercentage,
      this.shootingPowerPercentage,
      this.velocityPercentage,
      new Week(this.idWeek)
    );
debugger
    this.fetchService.postConfigurePercentage(percentages).then(value => {
      this.isConfiguration = value.status;
      if (value.status){
        this.idConfiguration = value.payload.id
      }else {
        this.msn = value.message;
      }
    })
  }

  addPlayer():void{
    debugger
    this.trainingPlayers.push({
      distance: 0, id: 0, name: "", passes: 0, power: 0, time: 0
    })
  }

  clean(): void {
    this.idWeek = 0;
    this.idConfiguration=0;
    this.isConfiguration=false;
    this.initWeekState = false;
    this.isTraining=false;
    this.effectivePassesPercentage=0;
    this.shootingPowerPercentage=0;
    this.velocityPercentage=0;
    this.trainingPlayers =[];
    this.titularTeam=[];
    this.msn = '';
  }

  cleanTraining():void{
    this.isTraining=false;
    this.trainingPlayers =[];
  }

  saveTraining() :void{

    const players = this.trainingPlayers.map(player => {
      const speed = new Speed(player.distance,player.time);
      const stats = Array.of(new Stat(player.power,speed,player.passes));
      return  new Player(player.id,player.name,stats);
    });
    const configuration =new Configuration(this.idConfiguration,new Week(this.idWeek));
    const requestTraining = new TrainingRequest(
      players,
      configuration
    );
    debugger
    this.fetchService.postTraining(requestTraining).then(value => {
      this.msn = value.message;
      this.isTraining = value.status;
    });

  }

  getTitularTeam():void{
    debugger
    this.fetchService.getTeam(this.idWeek).then(value => {
      if (value.status){
        this.titularTeam.push(value.payload);
      }else {
        this.msn = value.message;
      }
    });
  }
}
