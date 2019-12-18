package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class CIjepljenje {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_novorodenceta() {
        return id_novorodenceta;
    }

    public void setId_novorodenceta(int id_novorodenceta) {
        this.id_novorodenceta = id_novorodenceta;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(String simptomi) {
        this.simptomi = simptomi;
    }

    public int getId_bolnickih_podataka() {
        return id_bolnickih_podataka;
    }

    public void setId_bolnickih_podataka(int id_bolnickih_podataka) {
        this.id_bolnickih_podataka = id_bolnickih_podataka;
    }
    private int id;
    private int id_novorodenceta;
    private String datum;
    private String opis;
    private String simptomi;
    private int id_bolnickih_podataka;

    public CIjepljenje() {
    }

    public CIjepljenje(int id, int id_novorodenceta, String datum, String opis, String simptomi, int id_bolnickih_podataka) {
        this.id = id;
        this.id_novorodenceta = id_novorodenceta;
        this.datum = datum;
        this.opis = opis;
        this.simptomi = simptomi;
        this.id_bolnickih_podataka= id_bolnickih_podataka;

    }

}
