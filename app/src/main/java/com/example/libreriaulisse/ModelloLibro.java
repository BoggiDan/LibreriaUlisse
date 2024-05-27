package com.example.libreriaulisse;

public class ModelloLibro {
    private int id;
    private String titolo;
    private String autore;
    private String genere;
    private String descrizione;
    private double prezzo;
    private int quantita;
    private String url;

    public ModelloLibro(int id, String titolo, String autore, String genere, String descrizione, double prezzo, int quantita, String url) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getGenere() {
        return genere;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public String getUrl() {
        return url;
    }

    public String getAdapterUrl(){
        return "https://francescoarzilli3g.altervista.org/libbbreria/api/tools/img/index.php?url=" + this.url;
    }

    @Override
    public String toString() {
        return "ModelloLibro{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", quantita=" + quantita +
                ", url='" + url + '\'' +
                '}';
    }


}
