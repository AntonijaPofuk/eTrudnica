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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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
public class PregledKorisnica implements Serializable {

    private List<Korisnica> lista = new ArrayList<>();
    private int limit = 0;
    private String ime;
    private String prezime;
    String baza = "jdbc:postgresql://127.0.0.1:5432/tbp_baza";
    String korisnik = "postgres";
    String lozinka = "admin123";
    String driver = "org.postgresql.Driver";
    Konfiguracija konf;
    int ciklus;
    int trajanje;
    String token;
    private String korime;
    private String pass;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    String prijavljen;
    String lozinkaSesija;

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
     /**
     * Creates a new instance of PregledKorisnika
     */
    public PregledKorisnica() {
        ucitajKonfiguraciju();
        dajPodatke();
    }

    public String login() {
        ucitajKonfiguraciju();
        System.out.println("Klik!");   
            System.out.println(korime + pass);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledKorisnica.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit2 = "SELECT * FROM korisnica WHERE korisnicko_ime = '" + korime + "' AND lozinka = '" + pass + "'";
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(upit2);
            while (rs2.next()) {               
                Korisnica k = new Korisnica();
                k.setAdresa(rs2.getString("adresa"));
                k.setDatumRod(rs2.getString("datum_rodjenja"));
                k.setEmail(rs2.getString("email"));
                k.setIme(rs2.getString("ime"));
                k.setOIB(rs2.getString("OIB"));
                k.setPrezime(rs2.getString("prezime"));
                k.setTelefon(rs2.getString("telefon"));
                lista.add(k);
                System.out.println("korisnica je" + k.getIme() + " " + k.getPrezime());
                prikaziPoruku("Prijavili ste se pod korisnickim imenom: " + korime);
                staviUSesiju(korime, pass);
                session.setAttribute("OIBSesija", rs2.getString("OIB"));
            }
            rs2.close();
            stmt2.close();
        } catch (SQLException ex) {
            Logger.getLogger(PregledKorisnica.class.getName()).log(Level.SEVERE, null, ex);
        prikaziPoruku("Neuspješna prijava, pokušajte ponovno!");
         return "index_1";
        }
        return "index_2";
    }

     public void staviUSesiju(String user, String pass) {
        session.setAttribute("korime", user);
        session.setAttribute("lozinka", pass);
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
    
    public String dajPodatke() {
        ucitajKonfiguraciju();
        lista.clear();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledKorisnica.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit = "SELECT COUNT(*) AS broj FROM korisnica";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(upit);
            rs.next();
           
            rs.close();
            stmt.close();
            String upit2 = "SELECT * FROM korisnica";
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(upit2);
            while (rs2.next()) {
                Korisnica k = new Korisnica();
                k.setAdresa(rs2.getString("adresa"));
                k.setDatumRod(rs2.getString("datum_rodjenja"));
                k.setEmail(rs2.getString("email"));
                k.setIme(rs2.getString("ime"));
                k.setOIB(rs2.getString("OIB"));
                k.setPrezime(rs2.getString("prezime"));
                k.setTelefon(rs2.getString("telefon"));
                lista.add(k);
                System.out.println("korisnica je" + k.getIme() + " " + k.getPrezime());
            }
            rs2.close();
            stmt2.close();
        } catch (SQLException ex) {
            Logger.getLogger(PregledKorisnica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "dajKorisnike";
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
