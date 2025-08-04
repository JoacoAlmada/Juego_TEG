package com.teg.teg_juego.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "Continentes")
public class Continente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_continente")
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "cantidad_paises")
    private Integer cantidad_paises;

    @Column(name = "conquistado")
    private Boolean conquistado;

    @OneToMany(mappedBy = "continente")
    private List<Pais> paises = new ArrayList<>();

    public Continente() {}

    public Continente(Integer id, String nombre, Integer cantidad_paises, boolean conquistado) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad_paises = cantidad_paises;
        this.conquistado = conquistado;
    }

    public static Map<String, List<String>> paises_contiente = Map.of(
            "AmericaSur", List.of("Argentina", "Brasil", "Uruguay", "Chile", "Colombia", "Peru"),
            "AmericaNorte", List.of("NuevaYork", "Yukon", "Oregon", "California", "Terranova", "Labrador", "Groenlandia", "Mexico", "Canada", "Alaska"),
            "Africa", List.of("Sahara", "Zaire", "Egipto", "Etiopia", "Madagascar", "sudAfrica"),
            "Europa", List.of("Alemania", "Espana", "Francia", "GranBretana", "Rusia", "Polonia", "Italia", "Islandia", "Suecia"),
            "Oceania", List.of("Sumatra", "Java", "Borneo", "Australia"),
            "Asia", List.of("Japon", "Iran", "Gobi", "Malasia", "India", "China", "Arabia", "Israel", "Siberia", "Turquia", "Aral", "Mongolia", "Kamchatka", "Tamir", "Tartaria")
    );


    public boolean isConquistado() {
        return conquistado;
    }
    public void setConquistado(boolean conquistado) {
        this.conquistado = conquistado;
    }

}
