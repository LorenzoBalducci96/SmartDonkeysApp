package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;
import java.util.List;

public class Spot implements Serializable {
    private String id;
    private String titolo;
    private String descrizione;
    private String immagine;
    private String domanda;
    private List<String> opzioniRisposta;
    private String video;

    public Spot(String id, String titolo, String descrizione, String immagine,
                String domanda, List<String> opzioniRisposta, String video) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.domanda = domanda;
        this.opzioniRisposta = opzioniRisposta;
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public String getDomanda() {
        return domanda;
    }

    public List<String> getOpzioniRisposta() {
        return opzioniRisposta;
    }

    public String getVideo() {
        return video;
    }
}
