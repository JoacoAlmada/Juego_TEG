export interface JugadorPartidaDTO {
  jugadores: CrearJugadorDTO[];
  partidaId: number;
}

export interface CrearJugadorDTO {
  nombre: string;
  color: Color;
}

export enum Color {
  VERDE = 'VERDE',
  ROJO = 'ROJO',
  NEGRO = 'NEGRO',
  AZUL = 'AZUL',
  AMARILLO = 'AMARILLO',
  MAGENTA = 'MAGENTA'
}

export enum TipoJugador {
  BOT = 'BOT',
  HUMANO = 'HUMANO'
}

export enum DificultadBot {
  NOVATO = 'NOVATO',
  INTERMEDIO = 'INTERMEDIO',
  EXPERTO = 'EXPERTO',
}

export interface agregarBotDTO {
  dificultad: DificultadBot;
  cantidad: number;
  colores: Color[];
}
export enum Fase {
  COLOCACION = 'COLOCACION',
  ATAQUE = 'ATAQUE',
  REAGRUPACION = 'REAGRUPACION',

}
export enum EstadoJugador {
  ACTIVO = 'ACTIVO',
  GANADOR = 'GANADOR',
  ELIMINADO = 'ELIMINADO'
}
export interface ObjetivoDTO {
  id: number;
  descripcion: string;
}
export interface PaisDTO {
  id: number;
  nombre: string;
  color: Color;
  ejercito: number;
  continente: string;
}
export interface TarjetaDTO {
  id: number;
  paisNombre: string;
  simbolo: string; // ejemplo: INFANTERIA, CABALLERIA, etc.
}

export interface JugadorDTO {
  id: number;
  nombre: string;
  color: Color;
  fichas: number;
  tipoJugador: TipoJugador;
  estado: EstadoJugador;
  paises: PaisDTO[];
  objetivo: ObjetivoDTO;
  tarjetas: TarjetaDTO[];
}

export interface UsuarioDTO {
  id: number;
  nombre: string;
  contrasena: string;
  nivel: number;
}

export enum EstadoPartida {
  EN_JUEGO = 'EN_JUEGO',
  GUARDADA = 'GUARDADA',
  TERMINADA = 'TERMINADA'
}

export interface PartidaDTO {
  id: number;
  estado: EstadoPartida;
  cantidadJugadores: number;
  jugadorActualId: number;
  jugadorActualNombre: string;
  ronda: number;
  turno: number;
  fase: Fase;
  jugadores: JugadorDTO[];
}

export interface Pais {
  id: string;
  nombre: string;
  ejercitos: number;
  propietario?: string;
  color?: string;
}

export type TipoCarta = 'Infanteria' | 'Caballeria' | 'Artilleria';

export interface Carta {
  img: string;
  titulo: string;
  tipo: TipoCarta;
}

export interface ResultadoAtaque {
  resultado: string;
  dadosAtaque: number[];
  dadosDefensa: number[];
}
