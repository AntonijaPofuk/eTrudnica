package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class Novorodence {

    private int id;
    private int id_trudnoce;
    private String ime;
    private String spol;
    private String tezina;
    
    public Novorodence() {
    }

    public Novorodence(int id, int id_trudnoce, String ime, String spol, String tezina) {
        this.id = id;
        this.id_trudnoce = id_trudnoce;
        this.ime = ime;
        this.spol = spol;
        this.tezina = tezina;
       
    }

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

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public String getTezina() {
        return tezina;
    }

    public void setTezina(String tezina) {
        this.tezina = tezina;
    }

       }
