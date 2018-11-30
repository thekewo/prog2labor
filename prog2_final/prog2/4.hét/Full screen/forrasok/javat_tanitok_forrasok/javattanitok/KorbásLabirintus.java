/*
 * KorbásLabirintus.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * CORBA-s, szerver oldali életre keltésére ad példát ez az osztály:
 * a hõsök távolról történõ jelentkezését és mozgatását biztosítja.
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
 * @see javattanitok.ElosztottLabirintus
 * @see TávoliHõsKiszolgáló
 * @see KorbásKliens
 * @see tavolihos.idl
 */
public class KorbásLabirintus extends HálózatiLabirintus {
    /**
     * A <code>TávoliLabirintus</code> objektum elkészítése.
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelõ
     * szerkezetû szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány nem
     * a megfelelõ szerkezetû
     */
    public KorbásLabirintus(String labirintusFájlNév) throws
            RosszLabirintusKivétel {
        // A labirintus elkészítése állományból
        labirintus = new TöbbHõsösLabirintus(labirintusFájlNév);
        // A hõsöket tartalmazó adatszerkezet elkészítése
        hõsök = new java.util.Hashtable();
        // A játék valóságának (világának) indítása:
        new Thread(this).start();
        // A CORBA szerver indítása
        try{
            // Az ORB tulajdonságainak megadása
            java.util.Properties orbTulajdonságok = new java.util.Properties();
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialHost", "localhost");
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialPort", "2006");
            // Az ORB (a metódushívások közvetítõjének) inicializálása
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String [])null,
                    orbTulajdonságok);
            // A gyökér POA CORBA objektum referenciájának megszerzése
            org.omg.CORBA.Object corbaObjektum =
                    orb.resolve_initial_references("RootPOA");
            // CORBA-s típuskényszerítéssel a megfelelõre
            org.omg.PortableServer.POA gyökérPoa =
                    org.omg.PortableServer.POAHelper.narrow(corbaObjektum);
            // A POA kiszolgáló állapotba kapcsolása:
            gyökérPoa.the_POAManager().activate();
            // A kiszolgáló objektum létrehozása
            TávoliHõsKiszolgáló távoliHõsKiszolgáló =
                    new TávoliHõsKiszolgáló(this);
            // CORBA objektum referencia elkészítése
            corbaObjektum =
                    gyökérPoa.servant_to_reference(távoliHõsKiszolgáló);
            // CORBA-s típuskényszerítéssel a megfelelõre
            javattanitok.korbas.TavoliHos távoliHõs =
                    javattanitok.korbas.TavoliHosHelper.narrow(corbaObjektum);
            // A névszolgáltató gyökerének, mint CORBA objektum
            // referenciájának megszerzése
            corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt névszolgáltatóGyökere
                    = org.omg.CosNaming.NamingContextExtHelper
                    .narrow(corbaObjektum);
            // A kiszolgáló objektum bejegyzése a névszolgáltatóba
            org.omg.CosNaming.NameComponent név[] =
                    névszolgáltatóGyökere.to_name("TavoliHos");
            névszolgáltatóGyökere.rebind(név, távoliHõs);
            // Várakozás a játékosok jelentkezésére
            orb.run();
        } catch (Exception e) {
            
            e.printStackTrace();
            System.exit(-1);
            
        }        
    }
    /**
     * Átveszi a játék indításához szükséges paramétereket, majd
     * elindítja a játék világának mûködését.
     *
     * @param args a labirintus tervét tartalmazó állomány neve az elsõ 
     * parancssor-argumentum.
     */
    public static void main(String[] args) {
        
        if(args.length != 1) {
            
            System.out.println("Indítás: " +
                    "java javattanitok.KorbásLabirintus labirintus.txt");
            System.exit(-1);
            
        }
        
        try {
            
            new KorbásLabirintus(args[0]);
            
        } catch(RosszLabirintusKivétel rosszLabirintusKivétel) {            
            System.out.println(rosszLabirintusKivétel);            
        }
    }
}					
