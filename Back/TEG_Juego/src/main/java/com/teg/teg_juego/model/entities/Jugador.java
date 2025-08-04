package com.teg.teg_juego.model.entities;

import com.teg.teg_juego.model.enums.Color;
import com.teg.teg_juego.model.enums.EstadoJugador;
import com.teg.teg_juego.model.enums.TipoJugador;
import com.teg.teg_juego.model.interfaces.IJugador;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

import static com.teg.teg_juego.model.entities.Continente.paises_contiente;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoJugador"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Jugador.class, name = "HUMANO"),
        @JsonSubTypes.Type(value = Bot.class, name = "BOT")
})
@Data
@Entity
@Table(name = "Jugadores")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "id_tipo_jugador", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("HUMANO")
public class Jugador implements IJugador{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombreJ;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", length = 15)
    private Color color;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_jugador", length = 15)
    private EstadoJugador estadoJ;

    @Column(name = "fichas_totales")
    private Integer fichasJ = 0;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Objetivo")
    private Objetivo objetivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_tipo_jugador", length = 10, insertable = false, updatable = false)
    private TipoJugador tipoJ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Partida")
    private Partida partida;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)//para automatizar y sincronizar las operaciones entre Jugador y sus Pais
    private List<Pais> paises = new ArrayList<>();

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TarjetaPais> tarjetas = new ArrayList<>();

    @Transient
    private Set<Color> coloresEliminados = new HashSet<>();

    @Column(name = "numeroCanje")
    private Integer numeroCanje;

    public Jugador() {}

    public Jugador(String nombre) {
        this.nombreJ = nombre;
    }


    public Jugador(Integer id , String nombreJ, Color color, EstadoJugador estadoJ, Integer fichasJ, Objetivo objetivo , TipoJugador tipoJ, List<Pais> paises, List<TarjetaPais> tarjetas, Partida partida ) {
        this.id = id;
        this.nombreJ = nombreJ;
        this.color = color;
        this.estadoJ = estadoJ;
        this.fichasJ = (fichasJ != null) ? fichasJ : 0;
        this.objetivo = objetivo;
        this.tipoJ = tipoJ;
        this.paises = paises;
        this.tarjetas = tarjetas;
        this.partida = partida;
    }

    public boolean tienePaisConNombre(String nombrePais) {
        return paises.stream().anyMatch(p -> p.getNombre().equalsIgnoreCase(nombrePais));
    }
    public boolean tieneContinenteConquistado(String nombreContinente) {
        List<String> paisesDelContinente = paises_contiente.get(nombreContinente);
        if (paisesDelContinente == null) return false;

        return paisesDelContinente.stream().allMatch(this::tienePaisConNombre);
    }
    public boolean tienePaisesPorContinente(String nombreContinente, Integer cantidad) {
        List<String> paisesDelContinente = paises_contiente.get(nombreContinente);
        if (paisesDelContinente == null) return false;

        long cantidadQueTiene = paisesDelContinente.stream()
                .filter(this::tienePaisConNombre)
                .count();

        return cantidadQueTiene >= cantidad;
    }

    @Override
    public void colocar(Pais pais, Integer tropas) {
        if (fichasJ < tropas) {
            throw new RuntimeException("Tropas insuficientes");
        }
        pais.setEjercito(pais.getEjercito() + tropas);
        fichasJ -= tropas;
    }

    @Override
    public ResultadoAtaque atacar(Pais origen, Pais destino) {
        if(!origen.getVecinos().contains(destino)) throw new RuntimeException("Los paises deben ser limitrofes");

        String resultado = "";
        int tropasAtaque = origen.getEjercito();
        int tropasDefensa = destino.getEjercito();

        if (tropasAtaque < tropasDefensa) throw new RuntimeException("Tropas insuficientes para atacar");

        List<Integer> dadosAtacantes = new ArrayList<>();
        List<Integer> dadosDefensores = new ArrayList<>();

        switch (tropasAtaque) {
            case 2:
                dadosAtacantes.add(tirarDado());
                break;
            case 3:
                dadosAtacantes.add(tirarDado());
                dadosAtacantes.add(tirarDado());
                break;
            default:
                dadosAtacantes.add(tirarDado());
                dadosAtacantes.add(tirarDado());
                dadosAtacantes.add(tirarDado());
                break;
        }
        switch (tropasDefensa) {
            case 1:
                dadosDefensores.add(tirarDado());
                break;
            case 2:
                dadosDefensores.add(tirarDado());
                dadosDefensores.add(tirarDado());
                break;
            default:
                dadosDefensores.add(tirarDado());
                dadosDefensores.add(tirarDado());
                dadosDefensores.add(tirarDado());
                break;
            }
        dadosAtacantes.sort(Collections.reverseOrder());
        dadosDefensores.sort(Collections.reverseOrder());

        for (int i = 0; i < dadosDefensores.size(); i++) {
            if(dadosDefensores.get(i) >= dadosAtacantes.get(i)) {
                origen.setEjercito(origen.getEjercito() - 1);
            }
            else {
                destino.setEjercito(destino.getEjercito() - 1);
                if (destino.getEjercito() == 0) {
                    Jugador jugadorDestino = destino.getJugador();
                    jugadorDestino.getPaises().remove(destino);
                    destino.setJugador(this);
                    paises.add(destino);

                    if (jugadorDestino.getPaises().isEmpty()) {
                        coloresEliminados.add(jugadorDestino.getColor());
                    }
                    this.getObjetivo().verificarObjetivo(this, this.getPartida().getJugadores());
                    resultado = "conquista";
                }
            }
        }


        if (resultado.isEmpty()){
            resultado = "ataque";
        }
        return new ResultadoAtaque(resultado, dadosAtacantes, dadosDefensores);
    }

    public int tirarDado() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    @Override
    public void mover(Pais origen, Pais destino, int tropas) {
        if(origen.getEjercito() <= tropas) {
            throw new RuntimeException("Tropas insuficientes");
        }
        if(!origen.getVecinos().contains(destino)) {
            throw new RuntimeException("Los paises deben ser limitrofes");
        }

        origen.setEjercito(origen.getEjercito() - tropas);
        destino.setEjercito(destino.getEjercito() + tropas);
    }
}

