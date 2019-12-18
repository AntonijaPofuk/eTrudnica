package tbp.antpofuk.slusaci;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.antpofuk.konfiguracije.Konfiguracija;
import org.foi.nwtis.antpofuk.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.antpofuk.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.antpofuk.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.antpofuk.konfiguracije.bp.BP_Konfiguracija;


/**
 * Klasa koja djeluje kao slusac konteksta servleta
 *
 * @author Antonija Pofuk
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {

    private static ServletContext sc;  

    public static ServletContext getServletContext() {
        return sc;
    }

    /**
     * Metoda koja služi za inicijalizaciju konteksta servleta
     * 
     * @param sce označava kontekst servleta
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sc = sce.getServletContext();
      
 
       
    }

      
    /**
     * Metoda koja služi za uništavanje konteksta servleta
     * 
     * @param sce označava kontekst servleta
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {     
        sc = sce.getServletContext();
        
    }
}
