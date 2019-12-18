package tbp.antpofuk.zrna;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import org.foi.nwtis.antpofuk.konfiguracije.Konfiguracija;
import org.primefaces.context.RequestContext;
import tbp.antpofuk.podaci.Korisnica;

/**
 * Klasa koja služi za pregled korisnika
 *
 * @author Antonija Pofuk
 */
@ManagedBean
@SessionScoped
public class Registracija implements Serializable {

    private List<Korisnica> lista = new ArrayList<>();
    private int limit = 0;   
    String baza = "jdbc:postgresql://127.0.0.1:5432/tbp_baza";
    String korisnik = "postgres";
    String lozinka = "admin123";
    String driver = "org.postgresql.Driver";
    Konfiguracija konf;
    int ciklus;
    int trajanje;
    String token;

    public String getBaza() {
        return baza;
    }

    public void setBaza(String baza) {
        this.baza = baza;
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

    public String getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getLozinkaK() {
        return lozinkaK;
    }

    public void setLozinkaK(String lozinkaK) {
        this.lozinkaK = lozinkaK;
    }
    private String OIB;
    private String ime;
    private String prezime;
    private String adresa;
    private String email;
    private String telefon;
    private String datum_rodjenja;
    private String korisnicko_ime;
    private String lozinkaK;



     /**
     * Creates a new instance of PregledKorisnika
     */
    public Registracija() {
        ucitajKonfiguraciju();
    }
    /**
     * Metoda koja služi za registraciju korisnika
     *
     * @return "error" ako registracija nije uspjela odnosno "registracija" ako
     * je uspjela
     *
     */
    public String registriraj() {
        if (!korisnicko_ime.isEmpty() && !lozinkaK.isEmpty() && !OIB.isEmpty() && !ime.isEmpty()
                && !prezime.isEmpty()) {
                System.out.println("upisuje se" + korisnicko_ime + ime + prezime);
                String upit = "INSERT INTO KORISNICA VALUES "
                + "('"+OIB+"','"+ime+"','"+prezime+"','"+adresa+"','"+email+"','"+ telefon + "','" + datum_rodjenja+
                        "','"+korisnicko_ime+"','"+lozinkaK+"')";
                try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                    Statement stmt = con.createStatement();) {
                stmt.executeUpdate(upit);
                System.out.println("Dodan novi korisnik");
            } catch (SQLException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nije dodan novi korisnik, provjerite podatke! " + ex);
                prikaziPoruku("Niste se uspjeli registrirati. Provjerite podatke!");
            }   
                prikaziPoruku("Registrirali ste se uspješno!");
                return "registriran";
            }
        prikaziPoruku("Niste se uspjeli registrirati. Provjerite podatke!");
        return null;
    }
  
    /**
     * Metoda preuzima potrebne konfiguracijske parametre iz datoteke
     * konfiguracije.
     *
     * @author Antonija Pofuk
     */
    public void ucitajKonfiguraciju() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Nema prikladnog drivera" + e);
        }
        try (Connection conn = DriverManager.getConnection(baza, korisnik, lozinka)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.format("Nemože se uspostaviti veza!" + e, e.getSQLState(), e.getMessage());
        } catch (Exception e) {
        }
    }

    
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public List<Korisnica> getLista() {
        return lista;
    }

    public void setLista(List<Korisnica> lista) {
        this.lista = lista;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
 public void prikaziPoruku(String String) {
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Upozorenje", String));
    }
}
