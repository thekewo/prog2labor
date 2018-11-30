/*
 * ElosztottLabirintus.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.elosztott.*;
/**
 * Az elosztott CORBA-s labirintus csomag absztrahálta elosztott labirintus
 * mikrovilágának egy szerver oldali (delegációs POA/Tie leképezéses) életre
 * keltésére ad példát ez az osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.TávoliLabirintus
 * @see ElosztottKliens
 * @see LabirintusKiszolgáló
 * @see elosztott.idl
 */
public class ElosztottLabirintus {
    /** A <code>ElosztottLabirintus</code> objektum elkészítése. */
    public ElosztottLabirintus() {
        // A CORBA szerver indítása
        try{
            // Az ORB tulajdonságainak megadása
            java.util.Properties orbTulajdonságok =
                    new java.util.Properties();
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialPort", "2006");
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialHost", "localhost");
            // Az ORB (a metódushívások közvetítõjének) inicializálása
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String [])null,
                    orbTulajdonságok);
            // A gyökér POA CORBA objektum referenciájának megszerzése
            org.omg.CORBA.Object corbaObjektum = orb.
                    resolve_initial_references("RootPOA");
            // CORBA-s típuskényszerítéssel a megfelelõre
            org.omg.PortableServer.POA gyökérPoa =
                    org.omg.PortableServer.POAHelper.narrow(corbaObjektum);
            // A POA kiszolgáló állapotba kapcsolása:
            gyökérPoa.the_POAManager().activate();
            // A Labirintus kiszolgáló objektum elkészítése,
            // a delegációs/POA/Tie modell szerinti leképezéssel
            javattanitok.elosztott.objektumok.LabirintusPOATie tie =
                    new javattanitok.elosztott.objektumok.LabirintusPOATie(
                    new LabirintusKiszolgáló(), gyökérPoa);
            // A Labirintus CORBA objektum
            javattanitok.elosztott.objektumok.Labirintus labirintus =
                    tie._this(orb);
            // A névszolgáltató gyökerének, mint CORBA objektum
            // referenciájának megszerzése
            corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt névszolgáltatóGyökere
                    = org.omg.CosNaming.
                    NamingContextExtHelper.narrow(corbaObjektum);
            // A kiszolgáló objektum bejegyzése a névszolgáltatóba
            org.omg.CosNaming.NameComponent név[] =
                    névszolgáltatóGyökere.to_name("ElosztottLabirintus");
            névszolgáltatóGyökere.rebind(név, labirintus);
            // Várakozás a szereplõk (hõsök, kincsek, szörnyek) jelentkezésére
            orb.run();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.exit(-1);
            
        }
    }
    /**
     * Elindítja az elosztott labirintust világának labirintus részét.
     *
     * @param args nincsenek parancssor-argumentumok.
     */
    public static void main(String[] args) {
        
        new ElosztottLabirintus();
        
    }
}					
