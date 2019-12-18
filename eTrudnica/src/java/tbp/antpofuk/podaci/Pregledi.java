package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class Pregledi {

    private int id;
    private String id_korisnice;
    private int id_bolnickih_podataka;
    private String naziv;
    private String opis;
    private int tjedan_trudnoce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_korisnice() {
        return id_korisnice;
    }

    public void setId_korisnice(String id_korisnice) {
        this.id_korisnice = id_korisnice;
    }

    public int getId_bolnickih_podataka() {
        return id_bolnickih_podataka;
    }

    public void setId_bolnickih_podataka(int id_bolnickih_podataka) {
        this.id_bolnickih_podataka = id_bolnickih_podataka;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getTjedan_trudnoce() {
        return tjedan_trudnoce;
    }

    public void setTjedan_trudnoce(int tjedan_trudnoce) {
        this.tjedan_trudnoce = tjedan_trudnoce;
    }

    public String getTezina_novorodenceta() {
        return tezina_novorodenceta;
    }

    public void setTezina_novorodenceta(String tezina_novorodenceta) {
        this.tezina_novorodenceta = tezina_novorodenceta;
    }

    public String getOtkucaji_srca() {
        return otkucaji_srca;
    }

    public void setOtkucaji_srca(String otkucaji_srca) {
        this.otkucaji_srca = otkucaji_srca;
    }

    public String getOpis_ultrazvuka() {
        return opis_ultrazvuka;
    }

    public void setOpis_ultrazvuka(String opis_ultrazvuka) {
        this.opis_ultrazvuka = opis_ultrazvuka;
    }
    private String tezina_novorodenceta;
    private String otkucaji_srca;
    private String opis_ultrazvuka;
    private String datum;

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Pregledi() {
    }

    public Pregledi(int id, String id_korisnice, int id_bolnickih_podataka, String naziv, String opis,
            int tjedan_trudnoce, String tezina_novorodenceta, String otkucaji_srca, String opis_ultrazvuka, String datum) {
        this.id = id;
        this.id_korisnice = id_korisnice;
        this.id_bolnickih_podataka = id_bolnickih_podataka;
        this.naziv = naziv;
        this.opis = opis;
        this.tjedan_trudnoce = tjedan_trudnoce;
        this.tezina_novorodenceta = tezina_novorodenceta;
        this.otkucaji_srca = otkucaji_srca;
        this.opis_ultrazvuka = opis_ultrazvuka;
        this.datum = datum;

    }

}
