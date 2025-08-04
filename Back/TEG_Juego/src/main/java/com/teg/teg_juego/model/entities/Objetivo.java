package com.teg.teg_juego.model.entities;


import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.TipoObjetivo;
import com.teg.teg_juego.model.interfaces.IObjetivo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Entity
@Table(name = "Objetivos")
public class Objetivo implements IObjetivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo")
    private Integer id;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 15)
    private TipoObjetivo tipo;

    public Objetivo() {}

    public Objetivo(Integer id, String descripcion, Boolean estado, TipoObjetivo tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipo = tipo;
    }


    public Color obtenerColor(String descripcion) {
        switch (descripcion) {
            case "Destruir Totalmente al Ejercito Negro":
                return Color.NEGRO;
            case "Destruir Totalmente al Ejercito Magenta":
                return Color.MAGENTA;
            case "Destruir Totalmente al Ejercito Amarillo":
                return Color.AMARILLO;
            case "Destruir Totalmente al Ejercito Rojo":
                return Color.ROJO;
            case "Destruir Totalmente al Ejercito Verde":
                return Color.VERDE;
            case"Destruir Totalmente al Ejercito Azul":
                return Color.AZUL;
        }
        return null;
    }

    public Boolean verificarObjetivoEliminacion(Jugador jugador, List<Jugador> jugadores) {

        //-----------------------------------------------------------------------------------------------------------

        String objetivo = jugador.getObjetivo().getDescripcion();
        Color colorO = obtenerColor(objetivo);

        Optional<Jugador> jugadorObjetivoO = jugadores.stream().filter(j -> j.getColor() == colorO ).findFirst();

        if (jugadorObjetivoO.isEmpty()) {
            return false; // puede ser que no está en partida
        }

        Jugador jugadorObjetivo = jugadorObjetivoO.get();
        //verificamos si tiene paises
        if(!jugadorObjetivo.getPaises().isEmpty())
        {

            return false;
        }

        //validar que haya sido el jugador quien lo elimino
        if(jugador.getColoresEliminados().contains(colorO))
        {
            esCumplido();
            return true;
        }
        else {
            jugador.getObjetivo().setDescripcion("Conquistar 30 paises");
            return false;
        }
    }
    public Boolean verificarPaisesObjetivos(Jugador jugador) {

        String descripcion = jugador.getObjetivo().getDescripcion();

        boolean cumple = false;

        switch (descripcion) {
            case "Ocupar Africa, 5 Paises de America del Norte, 4 Paises de Europa":
                cumple = jugador.tieneContinenteConquistado("Africa") &&
                        jugador.tienePaisesPorContinente("AmericaNorte", 5) &&
                        jugador.tienePaisesPorContinente("Europa", 4);
                break;

            case "Ocupar Asia, 2 Paises de America del Sur":
                cumple = jugador.tieneContinenteConquistado("Asia") &&
                        jugador.tienePaisesPorContinente("AmericaSur", 2);
                break;

            case "Ocupar Europa, 4 Paises de Asia, 2 Paises de America del Sur":
                cumple = jugador.tieneContinenteConquistado("Europa") &&
                        jugador.tienePaisesPorContinente("Asia", 4) &&
                        jugador.tienePaisesPorContinente("AmericaSur", 2);
                break;

            case "Ocupar America del Norte, 2 Paises de Oceania y 4 Paises de Asia":
                cumple = jugador.tieneContinenteConquistado("AmericaNorte") &&
                        jugador.tienePaisesPorContinente("Oceania", 2) &&
                        jugador.tienePaisesPorContinente("Asia", 4);
                break;

            case "Ocupar 2 Paises de Oceania, 2 Paises de Africa, 2 Paises de America del Sur, 4 Paises de America del Norte, 3 Paises de Europa y 3 Paises de Asia":
                cumple = jugador.tienePaisesPorContinente("Oceania", 2) &&
                        jugador.tienePaisesPorContinente("Africa", 2) &&
                        jugador.tienePaisesPorContinente("AmericaSur", 2) &&
                        jugador.tienePaisesPorContinente("Europa", 3) &&
                        jugador.tienePaisesPorContinente("AmericaNorte", 4) &&
                        jugador.tienePaisesPorContinente("Asia", 3);
                break;

            case "Ocupar Oceania, America del Norte, 2 Paises de Africa":
                cumple = jugador.tieneContinenteConquistado("Oceania") &&
                        jugador.tieneContinenteConquistado("AmericaNorte") &&
                        jugador.tienePaisesPorContinente("Europa", 2);
                break;

            case "Ocupar America del Sur, Ocupar Africa, 4 Paises de Asia":
                cumple = jugador.tieneContinenteConquistado("AmericaSur") &&
                        jugador.tieneContinenteConquistado("Africa") &&
                        jugador.tienePaisesPorContinente("Asia", 4);
                break;

            case "Ocupar Oceania, Ocupar Africa, 5 Paises de America del Norte":
                cumple = jugador.tieneContinenteConquistado("Oceania") &&
                        jugador.tieneContinenteConquistado("Africa") &&
                        jugador.tienePaisesPorContinente("AmericaNorte", 5);
                break;

            default:
                System.out.println("Objetivo no reconocido: " + descripcion);
                break;
        }

        return cumple;
    }

    public boolean verificarObjetivo(Jugador jugador, List<Jugador> jugadores) {
        // No verificar antes de ronda 3
        if (jugador.getPartida().getRonda() < 3) {
            return false;
        }

        boolean cumplido = jugador.getPaises().size() >= 30;

        if (tipo == TipoObjetivo.CONQUISTA) {
            if (verificarPaisesObjetivos(jugador)) {
                cumplido = true;
            }
        } else if (tipo == TipoObjetivo.ELIMINAR) {
            if (verificarObjetivoEliminacion(jugador, jugadores)) {
                cumplido = true;
            }
        }

        if (cumplido) {
            esCumplido();
        }

        return cumplido;
    }


    @Override
    public boolean esCumplido() {
        this.estado = true;
        System.out.println("Objetivo cumplido");
        return estado;
    }
}
