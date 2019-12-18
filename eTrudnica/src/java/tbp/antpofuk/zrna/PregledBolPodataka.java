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
import tbp.antpofuk.podaci.Pregledi;

/**
 * Klasa koja služi za pregled korisnika
 *
 * @author Antonija Pofuk
 */
@ManagedBean
@SessionScoped
public class PregledBolPodataka implements Serializable {

    private List<BolnickiPodaci> lista = new ArrayList<>();
    private List<Pregledi> lista2 = new ArrayList<>();
    private int limit = 0;
    String baza = "jdbc:postgresql://127.0.0.1:5432/tbp_baza";
    String korisnik = "postgres";
    String lozinka = "admin123";
    String driver = "org.postgresql.Driver";
    Konfiguracija konf;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    String naziv_bolnice;
    String podaci;
    String doktor;
    int odabraniBPodaci;
    String naziv_bolniceR;
    String doktorR;
    String detaljiR;
    String prijavljen;
    String lozinkaSesija;
    String OIBSesija;  
    String naziv;
    String opis;
    String opisUltrazvuka;
    String otkucaji;
    String tezina;
    int tjedan;
    String datum;
    int odabraniPregled;
    String nazivP;
    String opisP;
    int tjedanP;
    String tezinaP;
    String otkucajiP;
    String opisPA;
    String datumP;
    String opisUltrazvukaP;


    /**
     * Creates a new instance of PregledKorisnika
     */
    public PregledBolPodataka() {
        ucitajKonfiguraciju();
        dajBolPodatke();
    }

    public void dajBolPodatke() {
        ucitajKonfiguraciju();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit2 = "SELECT * FROM bolnicki_podaci";
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(upit2);
            while (rs2.next()) {
                BolnickiPodaci k = new BolnickiPodaci();
                k.setId(rs2.getInt("id"));
                k.setDoktor(rs2.getString("id_doktor"));
                k.setNazivBolnice(rs2.getString("naziv_bolnice"));
                k.setPodaci(rs2.getString("podaci"));
                lista.add(k);
                System.out.println("Bolnica je" + k.getNazivBolnice() + " " + k.getDoktor());
            }
            rs2.close();
            stmt2.close();
        } catch (SQLException ex) {
            Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String vidiBPodatke(int id) {
        ucitajKonfiguraciju();
        odabraniBPodaci = id;
        System.out.println("Dohvaceni id bolnickih podataka je 1 " + id);
        System.out.println("Dohvaceni id bolnickih podataka je 2 " + odabraniBPodaci);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit3 = "SELECT * FROM bolnicki_podaci WHERE id = " + odabraniBPodaci;
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(upit3);
            while (rs3.next()) {
                BolnickiPodaci k = new BolnickiPodaci();
                k.setId(rs3.getInt("id"));
                k.setDoktor(rs3.getString("id_doktor"));
                k.setNazivBolnice(rs3.getString("naziv_bolnice"));
                k.setPodaci(rs3.getString("podaci"));
                lista.add(k);
                System.out.println("Bolnica je" + k.getNazivBolnice() + " " + k.getDoktor());
                this.naziv_bolnice = k.getNazivBolnice();
                this.doktor = k.getDoktor();
                this.podaci = k.getPodaci();
            }
            rs3.close();
            stmt3.close();
        } catch (SQLException ex) {
            Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "uredivanje_bol_podataka";
    }

    public void urediBolPodatke() {
        System.out.println("Dohvaceni id bolnickih podataka je " + odabraniBPodaci);
        String upit = "UPDATE bolnicki_podaci SET naziv_bolnice ='" + naziv_bolnice + "', id_doktor = '" + doktor + "', podaci ='" + podaci + "' WHERE id = " + odabraniBPodaci;
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate(upit);
            System.out.println("Azurirani su podaci ");
            prikaziPoruku("Podaci su uspješno ažurirani!");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nisu ažurirani podaci, provjerite podatke! " + ex);
            prikaziPoruku("Podaci nisu uspješno ažurirani!" + ex);
        }
        this.naziv_bolnice = naziv_bolnice;
        this.doktor = doktor;
        this.podaci = podaci;
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

    public String dodajBPodatke() {
        if (!naziv_bolniceR.isEmpty() && !detaljiR.isEmpty() && !doktorR.isEmpty()) {
            System.out.println("upisuje se" + naziv_bolniceR);
            String upit = "INSERT INTO bolnicki_podaci VALUES "
                    + "(default,'" + doktorR + "','" + naziv_bolniceR + "','" + detaljiR + "')";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                    Statement stmt = con.createStatement();) {
                stmt.executeUpdate(upit);
                System.out.println("Dodani novi podaci");
                prikaziPoruku("Dodali ste podatke uspješno!");
            } catch (SQLException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nisu dodani novi podaci, provjerite podatke! " + ex);
                prikaziPoruku("Niste uspješno unjeli nove podatke. Provjerite podatke!");
            }
            return "index_2";
        }
        return "index_2";
    }

    public void dajSesiju() {
        prijavljen = (String) session.getAttribute("korime");
        lozinkaSesija = (String) session.getAttribute("lozinka");
        OIBSesija = (String) session.getAttribute("OIBSesija");
        System.out.println("Sesija za: " + prijavljen + OIBSesija);
    }

    public String dajPreglede(int id) {
        dajSesiju();
        ucitajKonfiguraciju();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledTretmana.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Podaci za pregled: " + OIBSesija + id);
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit2 = "SELECT * FROM pregledi WHERE id_korisnice = '" + OIBSesija + "' AND id_bolnickih_podataka = " + id;
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(upit2);
            while (rs2.next()) {
                Pregledi k = new Pregledi();
                k.setId(rs2.getInt("id"));
                k.setId_korisnice(rs2.getString("id_korisnice"));
                k.setId_bolnickih_podataka(rs2.getInt("id_bolnickih_podataka"));
                k.setNaziv(rs2.getString("naziv"));
                k.setOpis(rs2.getString("opis"));
                k.setTjedan_trudnoce(rs2.getInt("tjedan_trudnoce"));
                k.setTezina_novorodenceta(rs2.getString("tezina_novorodenceta"));
                k.setOtkucaji_srca(rs2.getString("otkucaji_srca_novorodenceta"));
                k.setOpis_ultrazvuka(rs2.getString("opis_ultrazvuka"));
                k.setDatum(rs2.getString("datum"));
                lista2.add(k);
                System.out.println("Pregled je" + k.getNaziv() + " " + k.getOpis());
            }
            rs2.close();
            stmt2.close();
        } catch (SQLException ex) {
            Logger.getLogger(PregledTretmana.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "tretmani";
    }

    public String dodajPreglede() {
            System.out.println("upisuje se" + naziv_bolniceR);
            String upit = "INSERT INTO pregledi VALUES "
                    + "(default,'" + OIBSesija + "','" + 2 + "','" + naziv + "','" + opis
                    + "'," + tjedan + ",'" + tezina + "','" + otkucaji + "','" + opisUltrazvuka + "','" + datum+ "')";
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                    Statement stmt = con.createStatement();) {
                stmt.executeUpdate(upit);
                System.out.println("Dodani novi podaci");
                prikaziPoruku("Dodali ste podatke uspješno!");
            } catch (SQLException ex) {
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Nisu dodani novi podaci, provjerite podatke! " + ex);
                prikaziPoruku("Niste uspješno unjeli nove podatke. Provjerite podatke!");
            }
        return "tretmani";
    }
    public String dajPregled(int id) {
        ucitajKonfiguraciju();
        odabraniPregled = id;
        System.out.println("Dohvaceni id pregleda " + id);
        System.out.println("Dohvaceni id pregleda " + odabraniBPodaci);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit3 = "SELECT * FROM pregledi WHERE id = " + odabraniPregled;
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(upit3);
            while (rs3.next()) {
                Pregledi k = new Pregledi();
                k.setId(rs3.getInt("id"));
                k.setNaziv(rs3.getString("naziv"));
                k.setOpis(rs3.getString("opis"));
                k.setTjedan_trudnoce(rs3.getInt("tjedan_trudnoce"));
                k.setTezina_novorodenceta(rs3.getString("tezina_novorodenceta"));
                k.setOtkucaji_srca(rs3.getString("otkucaji_srca_novorodenceta"));
                k.setOpis_ultrazvuka(rs3.getString("opis_ultrazvuka"));
                k.setDatum(rs3.getString("datum"));
                lista2.add(k);
                System.out.println("Pregled je" + k.getNaziv()+ " " + k.getOpis());
                this.nazivP = k.getNaziv();
                this.opisP = k.getOpis();
                this.tjedanP = k.getTjedan_trudnoce();
                this.tezinaP = k.getTezina_novorodenceta();
                this.otkucajiP = k.getOtkucaji_srca();
                this.opisUltrazvuka = k.getOpis_ultrazvuka();
                this.datumP = k.getDatum();
            }
            rs3.close();
            stmt3.close();
        } catch (SQLException ex) {
            Logger.getLogger(PregledBolPodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "uredivanje_pregleda";
    }

     public void urediPregled() {
        System.out.println("Dohvaceni id pregleda za azuriranje je " + odabraniPregled);
        String upit = "UPDATE pregledi SET naziv ='" + nazivP + "', opis = '" + opisP + "', tjedan_trudnoce =" + tjedanP 
                + ",otkucaji_srca_novorodenceta ='"+ otkucajiP +"',opis_ultrazvuka = '"+ opisUltrazvukaP+ "',datum ='" +datumP +
                "'WHERE id = " + odabraniPregled;
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate(upit);
            System.out.println("Azurirani su podaci ");
            prikaziPoruku("Podaci su uspješno ažurirani!");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nisu ažurirani podaci, provjerite podatke! " + ex);
            prikaziPoruku("Podaci nisu uspješno ažurirani!" + ex);
        }
        this.nazivP = nazivP;
        this.opisUltrazvukaP = opisUltrazvukaP;
        this.tjedanP = tjedanP;
        this.otkucajiP = otkucajiP;
        this.opisP = opisP;
        this.datumP = datumP;
        
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

    public int getOdabraniBPodaci() {
        return odabraniBPodaci;
    }

    public void setOdabraniBPodaci(int odabraniBPodaci) {
        this.odabraniBPodaci = odabraniBPodaci;
    }

    public String getNaziv_bolnice() {
        return naziv_bolnice;
    }

    public void setNaziv_bolnice(String naziv_bolnice) {
        this.naziv_bolnice = naziv_bolnice;
    }

    public String getPodaci() {
        return podaci;
    }

    public void setPodaci(String podaci) {
        this.podaci = podaci;
    }

    public String getDoktor() {
        return doktor;
    }

    public void setDoktor(String doktor) {
        this.doktor = doktor;
    }

    public List<BolnickiPodaci> getLista() {
        return lista;
    }

    public void setLista(List<BolnickiPodaci> lista) {
        this.lista = lista;
    }

    public String getNaziv_bolniceR() {
        return naziv_bolniceR;
    }

    public void setNaziv_bolniceR(String naziv_bolniceR) {
        this.naziv_bolniceR = naziv_bolniceR;
    }

    public String getDoktorR() {
        return doktorR;
    }

    public void setDoktorR(String doktorR) {
        this.doktorR = doktorR;
    }

    public String getDetaljiR() {
        return detaljiR;
    }

    public void setDetaljiR(String detaljiR) {
        this.detaljiR = detaljiR;
    }

    public List<Pregledi> getLista2() {
        return lista2;
    }

    public void setLista2(List<Pregledi> lista2) {
        this.lista2 = lista2;
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

    public String getOpisUltrazvuka() {
        return opisUltrazvuka;
    }

    public void setOpisUltrazvuka(String opisUltrazvuka) {
        this.opisUltrazvuka = opisUltrazvuka;
    }

    public String getOtkucaji() {
        return otkucaji;
    }

    public void setOtkucaji(String otkucaji) {
        this.otkucaji = otkucaji;
    }

    public int getTjedan() {
        return tjedan;
    }

    public void setTjedan(int tjedan) {
        this.tjedan = tjedan;
    }
    
    public String getTezina() {
        return tezina;
    }

    public void setTezina(String tezina) {
        this.tezina = tezina;
    }
     public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
    
    public String getNazivP() {
        return nazivP;
    }

    public void setNazivP(String nazivP) {
        this.nazivP = nazivP;
    }

    public String getOpisP() {
        return opisP;
    }

    public void setOpisP(String opisP) {
        this.opisP = opisP;
    }

    public int getTjedanP() {
        return tjedanP;
    }

    public void setTjedanP(int tjedanP) {
        this.tjedanP = tjedanP;
    }

    public String getTezinaP() {
        return tezinaP;
    }

    public void setTezinaP(String tezinaP) {
        this.tezinaP = tezinaP;
    }

    public String getOtkucajiP() {
        return otkucajiP;
    }

    public void setOtkucajiP(String otkucajiP) {
        this.otkucajiP = otkucajiP;
    }

    public String getOpisPA() {
        return opisPA;
    }

    public void setOpisPA(String opisPA) {
        this.opisPA = opisPA;
    }

    public String getDatumP() {
        return datumP;
    }

    public void setDatumP(String datumP) {
        this.datumP = datumP;
    }
    
    public int getOdabraniPregled() {
        return odabraniPregled;
    }

    public void setOdabraniPregled(int odabraniPregled) {
        this.odabraniPregled = odabraniPregled;
    }
    
    public String getOpisUltrazvukaP() {
        return opisUltrazvukaP;
    }

    public void setOpisUltrazvukaP(String opisUltrazvukaP) {
        this.opisUltrazvukaP = opisUltrazvukaP;
    }
}
