package tbp.antpofuk.podaci;

/**
 * Klasa za rad s korisničkim podacima
 *
 * @author Antonija Pofuk
 */
public class Korisnica {

    private String OIB;
    private String adresa;
    private String prezime;
    private String ime;
    private String email;
    private String telefon;
    private String datumRod;

    public Korisnica() {
    }

    public Korisnica(String OIB, String adresa, String prezime, String ime, String email, String telefon, String datumRod) {
        this.OIB = OIB;
        this.adresa = adresa;
        this.email = email;
        this.prezime = prezime;
        this.ime = ime;
        this.telefon = telefon;
        this.datumRod = datumRod;
    }

    public String getOIB() {
        return OIB;
    }

    public void setOIB(String OIB) {
        this.OIB = OIB;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getDatumRod() {
        return datumRod;
    }

    public void setDatumRod(String datumRod) {
        this.datumRod = datumRod;
    }

}
