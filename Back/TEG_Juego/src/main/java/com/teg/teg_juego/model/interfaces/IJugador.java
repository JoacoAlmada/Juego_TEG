package com.teg.teg_juego.model.interfaces;


import com.teg.teg_juego.model.entities.Pais;
import com.teg.teg_juego.model.entities.ResultadoAtaque;

public interface IJugador {
   void colocar(Pais pais, Integer fichas);
   ResultadoAtaque atacar(Pais origen, Pais destino);
   void mover(Pais origen, Pais destino, int tropas);
}
