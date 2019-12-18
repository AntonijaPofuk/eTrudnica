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
import tbp.antpofuk.podaci.BolnickiPodaci;
import tbp.antpofuk.podaci.CIjepljenje;
import tbp.antpofuk.podaci.Komplikacije;
import tbp.antpofuk.podaci.Korisnica;
import tbp.antpofuk.podaci.Podsjetnici;
import tbp.antpofuk.podaci.Pregledi;

/**
 * Klasa koja služi za pregled cijepljenja, komplikacija, podsjetnika i pregleda
 *
 * @author Antonija Pofuk
 */
@ManagedBean
@SessionScoped
public class PregledTretmana implements Serializable {

    private List<Pregledi> lista1 = new ArrayList<>();

    public List<Pregledi> getLista1() {
        return lista1;
    }

    public void setLista1(List<Pregledi> lista1) {
        this.lista1 = lista1;
    }

    public List<CIjepljenje> getLista2() {
        return lista2;
    }

    public void setLista2(List<CIjepljenje> lista2) {
        this.lista2 = lista2;
    }

    public List<Komplikacije> getLista3() {
        return lista3;
    }

    public void setLista3(List<Komplikacije> lista3) {
        this.lista3 = lista3;
    }

    public List<Podsjetnici> getLista4() {
        return lista4;
    }

    public void setLista4(List<Podsjetnici> lista4) {
        this.lista4 = lista4;
    }
    private List<CIjepljenje> lista2 = new ArrayList<>();
    private List<Komplikacije> lista3 = new ArrayList<>();
    private List<Podsjetnici> lista4 = new ArrayList<>();

    private int limit = 0;
    String baza = "jdbc:postgresql://127.0.0.1:5432/tbp_baza";
    String korisnik = "postgres";
    String lozinka = "admin123";
    String driver = "org.postgresql.Driver";
    Konfiguracija konf;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
   

    /**
     * Creates a new instance of PregledKorisnika
     */
    public PregledTretmana() {
        ucitajKonfiguraciju();
       
    }

   
    
    

//    public String vidiBPodatke(int id) {
//        ucitajKonfiguraciju();
//        odabraniBPodaci = id;
//        System.out.println("Dohvaceni id bolnickih podataka je 1 " + id);
//        System.out.println("Dohvaceni id bolnickih podataka je 2 " + odabraniBPodaci);
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(PregledTretmana.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
//            String upit3 = "SELECT * FROM bolnicki_podaci WHERE id = " + odabraniBPodaci;
//            Statement stmt3 = con.createStatement();
//            ResultSet rs3 = stmt3.executeQuery(upit3);
//            while (rs3.next()) {
//                BolnickiPodaci k = new BolnickiPodaci();
//                k.setId(rs3.getInt("id"));
//                k.setDoktor(rs3.getString("id_doktor"));
//                k.setNazivBolnice(rs3.getString("naziv_bolnice"));
//                k.setPodaci(rs3.getString("podaci"));
//                lista.add(k);
//                System.out.println("Bolnica je" + k.getNazivBolnice() + " " + k.getDoktor());
//                this.naziv_bolnice = k.getNazivBolnice();
//                this.doktor = k.getDoktor();
//                this.podaci = k.getPodaci();
//            }
//            rs3.close();
//            stmt3.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(PregledTretmana.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "uredivanje_bol_podataka";
//    }
//
//    public void urediBolPodatke() {
//        System.out.println("Dohvaceni id bolnickih podataka je " + odabraniBPodaci);
//        String upit = "UPDATE bolnicki_podaci SET naziv_bolnice ='" + naziv_bolnice + "', id_doktor = '" + doktor + "', podaci ='" + podaci + "' WHERE id = " + odabraniBPodaci;
//        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
//                Statement stmt = con.createStatement();) {
//            stmt.executeUpdate(upit);
//            System.out.println("Azurirani su podaci ");
//            prikaziPoruku("Podaci su uspješno ažurirani!");
//        } catch (SQLException ex) {
//            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Nisu ažurirani podaci, provjerite podatke! " + ex);
//            prikaziPoruku("Podaci nisu uspješno ažurirani!" + ex);
//        }
//        this.naziv_bolnice = naziv_bolnice;
//        this.doktor = doktor;
//        this.podaci = podaci;
//    }

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

//    public String dodajBPodatke() {
//        if (!naziv_bolniceR.isEmpty() && !detaljiR.isEmpty() && !doktorR.isEmpty()) {
//            System.out.println("upisuje se" + naziv_bolniceR);
//            String upit = "INSERT INTO bolnicki_podaci VALUES "
//                    + "(default,'" + doktorR + "','" + naziv_bolniceR + "','" + detaljiR + "')";
//            try {
//                Class.forName(driver);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
//                    Statement stmt = con.createStatement();) {
//                stmt.executeUpdate(upit);
//                System.out.println("Dodani novi podaci");
//                prikaziPoruku("Dodali ste podatke uspješno!");
//            } catch (SQLException ex) {
//                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
//                System.out.println("Nisu dodani novi podaci, provjerite podatke! " + ex);
//                prikaziPoruku("Niste uspješno unjeli nove podatke. Provjerite podatke!");
//            }           
//            return "index_2";
//        }
//        return "index_2";
//    }

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
