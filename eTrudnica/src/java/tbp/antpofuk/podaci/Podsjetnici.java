package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class Podsjetnici {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pregleda() {
        return id_pregleda;
    }

    public void setId_pregleda(int id_pregleda) {
        this.id_pregleda = id_pregleda;
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
    private int id;
    private int id_pregleda;
    private String naziv;
    private String opis;
    private String datum;

    public Podsjetnici() {
    }

    public Podsjetnici(int id, int id_pregleda, String naziv, String opis, String datum) {
        this.id = id;
        this.id_pregleda = id_pregleda;
        this.naziv = naziv;
        this.opis = opis;
        this.datum = datum;

    }

}
