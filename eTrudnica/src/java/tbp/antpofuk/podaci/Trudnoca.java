package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class Trudnoca {

    private int id;
    private String id_korisnice;
    private String opis;
    private String kraj;
    private String pocetak;
    
    public Trudnoca() {
    }

    public Trudnoca(int id, String id_korisnice, String opis, String kraj, String pocetak) {
        this.id = id;
        this.id_korisnice = id_korisnice;
        this.opis = opis;
        this.kraj = kraj;
        this.pocetak = pocetak;
       
    }

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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getPocetak() {
        return pocetak;
    }

    public void setPocetak(String pocetak) {
        this.pocetak = pocetak;
    }

   }
