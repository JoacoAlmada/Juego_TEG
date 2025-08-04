package com.teg.teg_juego.model.entities;


import com.teg.teg_juego.model.DTO.CanjearDTO;
import com.teg.teg_juego.model.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@DiscriminatorValue("BOT")
public class Bot extends Jugador {

    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad", length = 20)
    private DificultadBot dificultad;

    public Bot() {}

    public Bot(String nombre, Color color, DificultadBot dificultad) {
        super(nombre);
        this.setTipoJ(TipoJugador.BOT);
        this.dificultad = dificultad;
        this.setColor(color);
    }

    public DificultadBot getDificultad() {
        return dificultad;
    }

    public void setDificultad(DificultadBot dificultad) {
        this.dificultad = dificultad;
    }

    public void tomarDecision() {
        Partida partida = this.getPartida();
        Fase faseActual = partida.getFase();
        int ronda = partida.getRonda();

        System.out.println("\n=== [Bot] " + getNombreJ() + " comienza turno ===");
        System.out.println("Fase actual: " + faseActual + ", Ronda: " + ronda);

        try {
            if (faseActual == Fase.COLOCACION) {
                intentarCanjearTarjetas();
                if (ronda <= 2 || ronda >= 4) {
                    int fichas = getFichasJ();
                    System.out.println("[Bot] Coloca " + fichas + " tropas.");
                    colocarSegunDificultad();
                } else {
                    System.out.println("[Bot] En ronda 3 no se colocan tropas.");
                }
            }

            else if (faseActual == Fase.ATAQUE) {
                if (ronda >= 3) {
                    boolean pudoAtacar = atacarSegunDificultad();
                    if (!pudoAtacar) {
                        System.out.println("[Bot] No pudo atacar. Avanzará de fase.");
                    }
                } else {
                    System.out.println("[Bot] Aún no se ataca en esta ronda.");
                }
            }

            else if (faseActual == Fase.REAGRUPACION) {
                if (ronda >= 3) {
                    System.out.println("[Bot] Reagrupa tropas.");
                    reagruparSegunDificultad();
                } else {
                    System.out.println("[Bot] Aún no se reagrupa en esta ronda.");
                }
            }

            else {
                System.out.println("[Bot] Fase desconocida o inválida.");
            }
        } catch (Exception e) {
            System.out.println("[Bot ERROR] Fallo en tomarDecision: " + e.getMessage());
            e.printStackTrace();
        } finally {
            partida.pasarFase();
            System.out.println("[Bot] " + getNombreJ() + " termina fase y avanza a: " + partida.getFase());
        }
    }

    private void colocarSegunDificultad() {
        switch (this.dificultad) {
            case NOVATO, INTERMEDIO -> colocarFichasBasico();
            case EXPERTO -> colocarFichasEstrategico();
        }

        DificultadBot Dificultad;
        if (this.getTarjetas().size() >= (this.dificultad == DificultadBot.NOVATO ? 5 : 3)) {
            intentarCanjearTarjetas();
        }

    }

    private boolean atacarSegunDificultad() {
        boolean hizoAtaque = false;

        switch (this.dificultad) {
            case NOVATO -> hizoAtaque = atacarComoNovato();
            case INTERMEDIO -> hizoAtaque = atacarComoIntermedio();
            case EXPERTO -> hizoAtaque = atacarObjetivoOContinente();
        }

        return hizoAtaque;
    }

    private void reagruparSegunDificultad() {
        switch (this.dificultad) {
            case NOVATO, INTERMEDIO -> reubicarTropasSimple();
            case EXPERTO -> reagruparInteligente();
        }

    }

    private boolean atacarComoNovato() {
        List<Pais> copiaPaises = new ArrayList<>(getPaises());
        boolean hizoAlgo = false;

        for (Pais origen : copiaPaises) {
            if (origen.getEjercito() > 1) {
                for (Pais vecino : origen.getVecinos()) {
                    if (vecino.getJugador() != this && origen.getEjercito() > vecino.getEjercito()) {
                        try {
                            ResultadoAtaque conquista = atacar(origen, vecino);
                            hizoAlgo = true;
                            if (conquista.getResultado().equalsIgnoreCase("conquista")) {
                                vecino.setEjercito(1);
                                origen.setEjercito(origen.getEjercito() - 1);

                                return true;
                            }
                        } catch (RuntimeException e) {
                            System.out.println("[Bot NOVATO] Falló ataque desde " + origen.getNombre() + " a " + vecino.getNombre() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }

        if (!hizoAlgo) {
            System.out.println("[Bot NOVATO] No encontró ataques posibles.");
        }
        return hizoAlgo;
    }


    private boolean atacarComoIntermedio() {
        boolean pudoAtacar = false;

        List<Pais> copiaPaises = new ArrayList<>(getPaises());

        for (Pais origen : copiaPaises) {
            if (origen.getEjercito() > 2) {
                for (Pais vecino : origen.getVecinos()) {
                    if (vecino.getJugador() != this && origen.getEjercito() > vecino.getEjercito()) {
                        try {
                            ResultadoAtaque conquista = atacar(origen, vecino);
                            pudoAtacar = true;
                            if (conquista.getResultado().equalsIgnoreCase("conquista")) {
                                vecino.setEjercito(1);
                                origen.setEjercito(origen.getEjercito() - 1);

                                return true;
                            }
                        } catch (RuntimeException e) {
                            System.out.println("[Bot] Error atacando desde " + origen.getNombre() + " a " + vecino.getNombre() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
        return pudoAtacar;
    }

    private void colocarFichasBasico() {
        int fichasDisponibles = getFichasJ();
        List<Pais> misPaises = getPaises();

        if (misPaises.isEmpty() || fichasDisponibles <= 0) return;

        int i = 0;
        while (fichasDisponibles > 0) {
            Pais pais = misPaises.get(i % misPaises.size());
            colocar(pais, 1);
            fichasDisponibles--;
            System.out.println("[BOT BASICO] Coloca 1 ficha en " + pais.getNombre() + ". Quedan: " + fichasDisponibles);
            i++;
        }
    }


    private void reubicarTropasSimple() {
        for (Pais origen : getPaises()) {
            if (origen.getEjercito() > 3) {
                for (Pais destino : origen.getVecinos()) {
                    if (destino.getJugador() == this) {
                        mover(origen, destino, origen.getEjercito() - 1);
                        return;
                    }
                }
            }
        }
    }

    private List<List<TarjetaPais>> generarCombinacionesDeTres(List<TarjetaPais> tarjetas) {
        List<List<TarjetaPais>> combinaciones = new ArrayList<>();
        for (int i = 0; i < tarjetas.size(); i++) {
            for (int j = i + 1; j < tarjetas.size(); j++) {
                for (int k = j + 1; k < tarjetas.size(); k++) {
                    combinaciones.add(Arrays.asList(tarjetas.get(i), tarjetas.get(j), tarjetas.get(k)));
                }
            }
        }
        return combinaciones;
    }

    private CanjearDTO intentarCanjearTarjetas() {
        List<TarjetaPais> posibles = this.getTarjetas();
        if (posibles.size() < 3) return null;

        List<List<TarjetaPais>> combinaciones = generarCombinacionesDeTres(posibles);

        for (List<TarjetaPais> combinacion : combinaciones) {
            Set<Simbolo> simbolos = combinacion.stream()
                    .map(TarjetaPais::getSimbolo)
                    .collect(Collectors.toSet());
        }

        return null;
    }

    private void reagruparInteligente() {
        List<Pais> frontera = getPaises().stream()
                .filter(p -> p.getVecinos().stream().anyMatch(v -> v.getJugador() != this))
                .collect(Collectors.toList());

        List<Pais> retaguardia = getPaises().stream()
                .filter(p -> p.getVecinos().stream().allMatch(v -> v.getJugador() == this))
                .collect(Collectors.toList());

        for (Pais origen : retaguardia) {
            if (origen.getEjercito() > 1) {
                for (Pais destino : frontera) {
                    if (origen.getVecinos().contains(destino)) {
                        mover(origen, destino, origen.getEjercito() - 1);
                        return;
                    }
                }
            }
        }
    }

    private boolean atacarObjetivoOContinente() {
        Objetivo obj = this.getObjetivo();
        boolean hizoAtaque = false;

        System.out.println("[BOT EXPERTO] Evaluando ataques...");

        for (Pais origen : getPaises()) {
            if (origen.getEjercito() <= 3) continue;

            List<Pais> vecinos = origen.getVecinos();
            vecinos.sort(Comparator.comparingInt(Pais::getEjercito));

            for (Pais destino : vecinos) {
                if (destino.getJugador() == this) continue;

                boolean esDeContinenteClave = obj != null &&
                        obj.getDescripcion().toLowerCase().contains(destino.getContinente().getNombre().toLowerCase());

                if (esDeContinenteClave || true) {
                    if (origen.getEjercito() > destino.getEjercito()) {
                        System.out.println("[BOT EXPERTO] Intentando ataque de " +
                                origen.getNombre() + " (" + origen.getEjercito() + ") a " +
                                destino.getNombre() + " (" + destino.getEjercito() + ")");

                        try {
                            ResultadoAtaque resultado = atacar(origen, destino);
                            System.out.println("[BOT EXPERTO] Resultado del ataque: " + resultado.getResultado());

                            hizoAtaque = true;

                            if (resultado.getResultado().equalsIgnoreCase("conquista")) {
                                System.out.println("[BOT EXPERTO] Conquistó con éxito " + destino.getNombre());

                                destino.setEjercito(1);
                                origen.setEjercito(origen.getEjercito() - 1);

                                return true;
                            }
                        } catch (RuntimeException e) {
                            System.out.println("[BOT EXPERTO] Error al atacar: " + e.getMessage());
                        }
                    }
                }
            }
        }

        if (!hizoAtaque) {
            System.out.println("[BOT EXPERTO] No encontró ataques viables.");
        }

        return hizoAtaque;
    }
    private void colocarFichasEstrategico() {
        int fichas = getFichasJ();

        System.out.println("[BOT] Fichas disponibles: " + fichas);

        List<Pais> paisesAmenazados = getPaises().stream()
                .filter(p -> p.getVecinos().stream().anyMatch(v -> v.getJugador() != this))
                .sorted(Comparator.comparingInt(Pais::getEjercito))
                .collect(Collectors.toList());

        int i = 0;
        while (getFichasJ() > 0 && !paisesAmenazados.isEmpty()) {
            Pais pais = paisesAmenazados.get(i % paisesAmenazados.size());
            colocar(pais, 1);
            System.out.println("[BOT] Coloca 1 ficha en " + pais.getNombre() + ". Quedan: " + getFichasJ());
            i++;
        }

        i = 0;
        List<Pais> restantes = getPaises();
        while (getFichasJ() > 0 && !restantes.isEmpty()) {
            Pais pais = restantes.get(i % restantes.size());
            colocar(pais, 1);
            System.out.println("[BOT] Coloca 1 ficha extra en " + pais.getNombre() + ". Quedan: " + getFichasJ());
            i++;
        }
    }
}