import { Injectable } from '@angular/core';
import {SoccerResponseDto} from "./models/models";

@Injectable({
  providedIn: 'root'
})
export class FetchService {
  private URL_BASE = 'https://stoccer-team-79e4c71cb910.herokuapp.com/';
  private API_INIT_WEEK = 'initWeek';


  constructor() { }

  postInitWeek():Promise<SoccerResponseDto>{
    return  fetch(`${this.URL_BASE}${this.API_INIT_WEEK}`,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json' // Especificar el tipo de contenido
      }
    }).then(response => {
      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }
      return response.json();
    }).then(data => {
      debugger
      return data;
    })
      .catch(error => {
        console.error(error); // Manejar errores de la solicitud
      });
  }

}
