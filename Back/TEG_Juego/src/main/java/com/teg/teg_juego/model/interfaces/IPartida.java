package com.teg.teg_juego.model.interfaces;



import com.teg.teg_juego.model.entities.Jugador;
import com.teg.teg_juego.model.entities.Objetivo;
import com.teg.teg_juego.model.entities.Pais;

import java.util.List;

public interface IPartida {
   void iniciarPartida();
   void guardarPartida();
   Jugador terminarPartida(Jugador jugador);
    void repartirObjetivos(List<Objetivo> todosLosObjetivos);
    void pasarTurno();
    void repartirPaises(List<Pais> todosLosPaises);
    void repartirEjercitos();
   void iniciarRonda();

}
