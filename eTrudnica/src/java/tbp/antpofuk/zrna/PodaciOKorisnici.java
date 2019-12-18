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
import tbp.antpofuk.podaci.Komplikacije;
import tbp.antpofuk.podaci.Korisnica;
import tbp.antpofuk.podaci.Novorodence;
import tbp.antpofuk.podaci.Trudnoca;

/**
 * Klasa koja služi za pregled podataka o korisnici
 *
 * @author Antonija Pofuk
 */
@ManagedBean
@SessionScoped
public class PodaciOKorisnici implements Serializable {

    private List<Korisnica> lista = new ArrayList<>();
    private List<Trudnoca> lista2 = new ArrayList<>();
    private List<Novorodence> lista3 = new ArrayList<>();
    private List<Komplikacije> lista4 = new ArrayList<>();
    private int limit = 0;
    String baza = "jdbc:postgresql://127.0.0.1:5432/tbp_baza";
    String korisnik = "postgres";
    String lozinka = "admin123";
    String driver = "org.postgresql.Driver";
    Konfiguracija konf;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    String prijavljen;
    String lozinkaSesija;
    String opis;
    String kraj;
    String pocetak;
    private String imeN;
    private String tezina;
    private String spol;
    String pocetakR;
    String krajR;
    String opisR;
    String OIB;
    String ime;
    String prezime;
    String adresa;
    String email;
    String telefon;
    String datum_rodjenja;
    String korisnicko_ime;
    String lozinkaK;
    int id_trudnoce;
    int odabranaTrudnoca;
    int odabranaBeba;
    String OIBSesija;
    String imeR;
    String spolR;
    String tezinaR;
    String nazivKR;
    String opisKR;
    String uzrokKR;
    int odabranaKomplikacija;
    String nazivKA;
    String opisKA;
    String uzrokKA;
    String rezultat;

    /**
     * Creates a new instance of PregledKorisnika
     */
    public PodaciOKorisnici() {
        ucitajKonfiguraciju();
        dajSesiju();
        dajPodatke();
        dajTrudnocu();
    }

    public void dajSesiju() {
        prijavljen = (String) session.getAttribute("korime");
        lozinkaSesija = (String) session.getAttribute("lozinka");
        OIBSesija = (String) session.getAttribute("OIB");
        System.out.println("Sesija za: " + prijavljen);
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
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            String upit = "SELECT COUNT(*) AS broj FROM korisnica";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(upit);
            rs.next();
            rs.close();
            stmt.close();
            String upit2 = "SELECT * FROM korisnica WHERE korisnicko_ime = '" + prijavljen + "'";
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
                System.out.println("korisnica je " + k.getIme() + " " + k.getPrezime());
                this.OIB = k.getOIB();
                this.ime = k.getIme();
                this.prezime = k.getPrezime();
                this.adresa = k.getAdresa();
                this.datum_rodjenja = k.getDatumRod();
                this.telefon = k.getTelefon();
                this.email = k.getEmail();
                session.setAttribute("OIB", OIB);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return "dajKorisnike";
    }

    public void dajTrudnocu() {
        ucitajKonfiguraciju();
        lista2.clear();
        String upit3 = "SELECT * FROM trudnoca WHERE id_korisnice = '" + OIB + "'";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(upit3);
            while (rs3.next()) {
                Trudnoca t = new Trudnoca();
                t.setId(rs3.getInt("id"));
                t.setId_korisnice(rs3.getString("id_korisnice"));
                t.setOpis(rs3.getString("opis"));
                t.setPocetak(rs3.getString("pocetak"));
                t.setKraj(rs3.getString("kraj"));
                lista2.add(t);
                System.out.println("trudnoca je " + t.getOpis());                                
                this.opis = t.getOpis();
                this.pocetak = t.getPocetak();
                this.kraj = t.getKraj();
            }
            rs3.close();
            stmt3.close();
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String izracunajTrudnocu(int id) {
        String upit3 = "select pocetak + integer '280' from trudnoca where id = " + id;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(upit3);
            while (rs3.next()) {
                rezultat = rs3.getString(1);
                System.out.println(rezultat);
            }
            rs3.close();
            stmt3.close();
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rezultat;
    }

    public String dajNovorodence(int id) {
        System.out.println("Dohvaceni id trudnoce za dohvat bebe je " + id);
        dajSesiju();
        String upit5 = "SELECT * FROM trudnoca WHERE id_korisnice = '" + OIB + "' AND id = " + id;
        String upit4 = "SELECT * FROM novorodence WHERE id_trudnoce = " + id;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            Statement stmt4 = con.createStatement();
            Statement stmt5 = con.createStatement();
            ResultSet rs4 = stmt4.executeQuery(upit4);
            ResultSet rs5 = stmt5.executeQuery(upit5);
            while (rs4.next()) {
                Novorodence n = new Novorodence();
                n.setId(rs4.getInt("id"));
                n.setId_trudnoce(rs4.getInt("id_trudnoce"));
                n.setIme(rs4.getString("ime"));
                n.setSpol(rs4.getString("spol"));
                n.setTezina(rs4.getString("tezina"));
                lista3.add(n);
                System.out.println("novorodence je " + n.getIme());
                this.imeN = n.getIme();
                this.spol = n.getSpol();
                this.tezina = n.getTezina();
            }
            while (rs5.next()) {
                Trudnoca n = new Trudnoca();
                n.setOpis(rs5.getString("opis"));
                n.setPocetak(rs5.getString("pocetak"));
                n.setKraj(rs5.getString("kraj"));
                n.setId(rs5.getInt("id"));
                lista2.add(n);
                System.out.println("trudnoca za update  je " + n.getOpis());
                odabranaTrudnoca = n.getId();
                this.opis = n.getOpis();
                this.pocetak = n.getPocetak();
                this.kraj = n.getKraj();
            }
            System.out.println("Sve smo ispisali");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "podaci_o_novorodencetu";
    }

    public String urediNovo(int id) {
        odabranaBeba = id;
        System.out.println("Dohvaceni id bebe je " + id);
        String upit4 = "SELECT * FROM novorodence WHERE id_trudnoce = " + odabranaTrudnoca
                + "AND id = " + id;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);) {
            Statement stmt4 = con.createStatement();
            ResultSet rs4 = stmt4.executeQuery(upit4);
            while (rs4.next()) {
                Novorodence n = new Novorodence();
                n.setId(rs4.getInt("id"));
                n.setId_trudnoce(rs4.getInt("id_trudnoce"));
                n.setIme(rs4.getString("ime"));
                n.setSpol(rs4.getString("spol"));
                n.setTezina(rs4.getString("tezina"));
                lista3.add(n);
                System.out.println("novorodence je " + n.getIme());

            }
            this.imeN = imeN;
            this.spol = spol;
            this.tezina = tezina;
            System.out.println("Sve smo ispisali");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "uredivanje_novorodenceta";
    }

    public void azurirajKorisnicu() {
        System.out.println("Dohvaceni podaci za korisnicu su " + ime + prezime);
        String upit = "UPDATE KORISNICA SET ime = '" + ime + "',prezime='" + prezime + "', email ='" + email + "', telefon ='" + telefon + "', datum_rodjenja ='" + datum_rodjenja
                + "', adresa = '" + adresa + "' WHERE korisnicko_ime = '" + prijavljen + "'";
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate(upit);
            System.out.println("Azuriran je korisnik " + ime + prezime);
            prikaziPoruku("Korisnicki podaci su uspješno ažurirani!");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nije azuriran korisnik, provjerite podatke! " + ex);
            prikaziPoruku("Korisnicki podaci nisu uspješno ažurirani!" + ex);
        }
        this.korisnicko_ime = korisnicko_ime;
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.email = email;
        this.telefon = telefon;
        this.datum_rodjenja = datum_rodjenja;
        this.OIB = OIB;
    }

    public void azurirajTrudnocu() {
        System.out.println(OIBSesija);
        System.out.println("Odabrana trudnoca je :" + odabranaTrudnoca);
        String upit = "UPDATE trudnoca SET opis = '" + opis + "',kraj='" + kraj + "', pocetak ='" + pocetak
                + "'WHERE id_korisnice = '" + OIBSesija + "'";
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate(upit);
            System.out.println("Azurirana je trudnoca " + opis);
            prikaziPoruku("Podaci su uspješno ažurirani!");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nije azurirana trudnoca, provjerite podatke! " + ex);
            prikaziPoruku("Podaci nisu uspješno ažurirani!" + ex);
        }
        this.opis = opis;
        this.pocetak = pocetak;
        this.kraj = kraj;
    }

    public void azurirajNovo() {
        System.out.println(odabranaBeba);
        String upit = "UPDATE novorodence SET ime = '" + imeN + "',spol='" + spol + "', tezina ='" + tezina
                + "'WHERE id = '" + odabranaBeba + "'";
        try (Connection con = DriverManager.getConnection(baza, korisnik, lozinka);
                Statement stmt = con.createStatement();) {
            stmt.executeUpdate(upit);
            System.out.println("Azurirana je trudnoca " + opis);
            prikaziPoruku("Podaci su uspješno ažurirani!");
        } catch (SQLException ex) {
            Logger.getLogger(PodaciOKorisnici.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nije azurirana trudnoca, provjerite podatke! " + ex);
            prikaziPoruku("Podaci nisu uspješno ažurirani!" + ex);
        }
        this.opis = opis;
        this.pocetak = pocetak;
        this.kraj = kraj;
    }

    public String dodajTrudnocu() {
        dajSesiju();
        if (!pocetakR.isEmpty() && !opisR.isEmpty()) {
            System.out.println("upisuje se" + opisR);
            String upit = "INSERT INTO trudnoca VALUES "
                    + "(default,'" + OIBSesija + "','" + pocetakR + "','" + krajR + "','" + opisR + "')";
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
        }
        return "podaci_o_korisnici";
    }

    public String dodajBebu() {
        dajSesiju();
        if (!imeR.isEmpty() && !spolR.isEmpty() && !tezinaR.isEmpty()) {
            System.out.println("upisuje se" + imeR + odabranaTrudnoca);
            String upit = "INSERT INTO novorodence VALUES "
                    + "(default,'" + odabranaTrudnoca + "','" + imeR + "','" + spolR + "','" + tezinaR + "')";
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
        }
        return "podaci_o_korisnici";
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

    public String getImeN() {
        return imeN;
    }

    public void setImeN(String imeN) {
        this.imeN = imeN;
    }

    public String getTezina() {
        return tezina;
    }

    public void setTezina(String tezina) {
        this.tezina = tezina;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public String getPocetakR() {
        return pocetakR;
    }

    public void setPocetakR(String pocetakR) {
        this.pocetakR = pocetakR;
    }

    public String getKrajR() {
        return krajR;
    }

    public void setKrajR(String krajR) {
        this.krajR = krajR;
    }

    public String getOpisR() {
        return opisR;
    }

    public void setOpisR(String opisR) {
        this.opisR = opisR;
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

    public String getImeR() {
        return imeR;
    }

    public void setImeR(String imeR) {
        this.imeR = imeR;
    }

    public String getSpolR() {
        return spolR;
    }

    public void setSpolR(String spolR) {
        this.spolR = spolR;
    }

    public String getTezinaR() {
        return tezinaR;
    }

    public void setTezinaR(String tezinaR) {
        this.tezinaR = tezinaR;
    }

    public List<Komplikacije> getLista4() {
        return lista4;
    }

    public void setLista4(List<Komplikacije> lista4) {
        this.lista4 = lista4;
    }

    public int getOdabranaTrudnoca() {
        return odabranaTrudnoca;
    }

    public void setOdabranaTrudnoca(int odabranaTrudnoca) {
        this.odabranaTrudnoca = odabranaTrudnoca;
    }

    public List<Trudnoca> getLista2() {
        return lista2;
    }

    public void setLista2(List<Trudnoca> lista2) {
        this.lista2 = lista2;
    }

    public List<Novorodence> getLista3() {
        return lista3;
    }

    public void setLista3(List<Novorodence> lista3) {
        this.lista3 = lista3;
    }

    public String getNazivKR() {
        return nazivKR;
    }

    public void setNazivKR(String nazivKR) {
        this.nazivKR = nazivKR;
    }

    public String getOpisKR() {
        return opisKR;
    }

    public void setOpisKR(String opisKR) {
        this.opisKR = opisKR;
    }

    public String getUzrokKR() {
        return uzrokKR;
    }

    public void setUzrokKR(String uzrokKR) {
        this.uzrokKR = uzrokKR;
    }

    public int getOdabranaBeba() {
        return odabranaBeba;
    }

    public void setOdabranaBeba(int odabranaBeba) {
        this.odabranaBeba = odabranaBeba;
    }

    public int getOdabranaKomplikacija() {
        return odabranaKomplikacija;
    }

    public void setOdabranaKomplikacija(int odabranaKomplikacija) {
        this.odabranaKomplikacija = odabranaKomplikacija;
    }

    public String getNazivKA() {
        return nazivKA;
    }

    public void setNazivKA(String nazivKA) {
        this.nazivKA = nazivKA;
    }

    public String getOpisKA() {
        return opisKA;
    }

    public void setOpisKA(String opisKA) {
        this.opisKA = opisKA;
    }

    public String getUzrokKA() {
        return uzrokKA;
    }

    public void setUzrokKA(String uzrokKA) {
        this.uzrokKA = uzrokKA;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }
}
