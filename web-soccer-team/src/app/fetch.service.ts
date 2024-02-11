import { Injectable } from '@angular/core';
import {Configuration, ConfigurePercentage, SoccerResponseDto, TrainingRequest} from "./models/models";

@Injectable({
  providedIn: 'root'
})
export class FetchService {
  private URL_BASE = 'https://stoccer-team-79e4c71cb910.herokuapp.com/';
  private API_INIT_WEEK = 'initWeek';
  private API_CONFIGURE_PERCENTAGE = 'configurePercentage';
  private API_TRAINING = 'training';
  private API_TEAM = 'team';


  constructor() { }

  postInitWeek():Promise<SoccerResponseDto>{
    return  fetch(`${this.URL_BASE}${this.API_INIT_WEEK}`,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(response => {
      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }
      return response.json();
    }).then(data => {
      return data;
    })
      .catch(error => {
        console.error(error);
      });
  }

  postConfigurePercentage(percentages: ConfigurePercentage):Promise<SoccerResponseDto> {
    return  fetch(`${this.URL_BASE}${this.API_CONFIGURE_PERCENTAGE}`,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body:JSON.stringify(percentages)
    }).then(response => {
      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }
      return response.json();
    }).then(data => {
      return data;
    })
      .catch(error => {
        console.error(error);
      });
  }

  postTraining(trainings: TrainingRequest):Promise<SoccerResponseDto> {
    return  fetch(`${this.URL_BASE}${this.API_TRAINING}`,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body:JSON.stringify(trainings)
    }).then(response => {
      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }
      return response.json();
    }).then(data => {
      return data;
    })
      .catch(error => {
        console.error(error);
      });
  }

  getTeam(idWeek:number):Promise<SoccerResponseDto> {
    return  fetch(`${this.URL_BASE}${this.API_TEAM}?week=${idWeek}`).then(response => {
      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }
      return response.json();
    }).then(data => {
      return data;
    })
      .catch(error => {
        console.error(error);
      });
  }
}
