package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class BolnickiPodaci {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoktor() {
        return doktor;
    }

    public void setDoktor(String doktor) {
        this.doktor = doktor;
    }

    public String getNazivBolnice() {
        return nazivBolnice;
    }

    public void setNazivBolnice(String nazivBolnice) {
        this.nazivBolnice = nazivBolnice;
    }

    public String getPodaci() {
        return podaci;
    }

    public void setPodaci(String podaci) {
        this.podaci = podaci;
    }

    private int id;
    private String doktor;
    private String nazivBolnice;
    private String podaci;
    
    public BolnickiPodaci() {
    }

    public BolnickiPodaci(int id, String doktor, String nazivBolnice,String podaci) {
        this.id = id;
        this.doktor = doktor;
        this.nazivBolnice = nazivBolnice;
        this.podaci = podaci;
            
    }

   
   }
