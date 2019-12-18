package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class Komplikacije {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_trudnoce() {
        return id_trudnoce;
    }

    public void setId_trudnoce(int id_trudnoce) {
        this.id_trudnoce = id_trudnoce;
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

    public String getUzrok() {
        return uzrok;
    }

    public void setUzrok(String uzrok) {
        this.uzrok = uzrok;
    }

    private int id;
    private int id_trudnoce;
    private String naziv;
    private String opis;
    private String uzrok;

    public Komplikacije() {
    }

    public Komplikacije(int id, int id_trudnoce, String naziv, String opis, String uzrok) {
        this.id = id;
        this.id_trudnoce = id_trudnoce;
        this.naziv = naziv;
        this.opis = opis;
        this.uzrok = uzrok;

    }

}
