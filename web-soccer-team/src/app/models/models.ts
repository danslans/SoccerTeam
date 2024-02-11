
export interface SoccerResponseDto {
  status: boolean;
  message: string
  payload: any
}

export class ConfigurePercentage{
  effectivePassesPercentage:number;
  velocityPercentage:number;
  shootingPowerPercentage:number;
  week:Week

  constructor(effectivePassesPercentage: number, velocityPercentage: number, shootingPowerPercentage: number, week: Week) {
    this.effectivePassesPercentage = effectivePassesPercentage;
    this.velocityPercentage = velocityPercentage;
    this.shootingPowerPercentage = shootingPowerPercentage;
    this.week = week;
  }
}

export class Week{
  id:number;

  constructor(id: number) {
    this.id = id;
  }
}

export interface IPlayer {
  id:number;
  name:string;
  power:number;
  distance:number;
  time:number;
  passes:number;
}


export class TrainingRequest{
  players:Player[];
  configuration:Configuration;

  constructor(players: Player[], configuration: Configuration) {
    this.players = players;
    this.configuration = configuration;
  }
}

export class Configuration{
  id:number;
  week:Week;

  constructor(id: number, week: Week) {
    this.id = id;
    this.week = week;
  }
}

export class Player {
  id:number;
  name:string;
  stats:Stat[];

  constructor(id: number, name: string, stats: Stat[]) {
    this.id = id;
    this.name = name;
    this.stats = stats;
  }
}

export class Stat{
  power:number;
  speed: Speed;
  passes:number;

  constructor(power: number, speed: Speed, passes: number) {
    this.power = power;
    this.speed = speed;
    this.passes = passes;
  }
}

export class Speed{
  distance:number;
  time:number;

  constructor(distance: number, time: number) {
    this.distance = distance;
    this.time = time;
  }
}

export interface TitularTeam{
  id:number;
  name:string
  totalScore:number;
}

