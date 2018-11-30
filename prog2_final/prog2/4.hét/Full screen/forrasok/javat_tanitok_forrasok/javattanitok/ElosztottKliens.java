/*
 * ElosztottKliens.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.elosztott.*;
/**
 * Az elosztott CORBA-s labirintus csomag absztrahálta elosztott labirintus
 * mikrovilágának egy kliens oldali életre keltésére ad példát ez az osztály,
 * mely egyben a szereplõ objektumok visszahívható szervere is.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see ElosztottLabirintus
 * @see SzereplõKiszolgáló
 * @see HõsKiszolgáló
 * @see SzörnyKiszolgáló
 * @see KincsKiszolgáló
 */
public class ElosztottKliens implements Runnable {
    org.omg.CORBA.ORB orb; // hogy a run()-ból elérjük õket
    javattanitok.elosztott.objektumok.Labirintus labirintus;
    /**
     * Elkészíti a távol gépre bejelentkezõ hõs, szörny vagy
     * kincs visszahívható kliens CORBA objektumot. Hõsként
     * való indításakor feldolgozza a játékostól jövõ billentyû
     * parancsokat.
     *
     * @param args elsõ tagja "hõs" - Hos objektum, "kincs" -
     * Kincs objektum, "szörny" - Szorny objektum lesz a kliens,
     * alapértelmezés a "kincs".
     */
    public ElosztottKliens(String[] args) {
        
        String ki = null;
        // ha nem adtuk meg a parancssor-argumentumot,
        // akkor ez az alapértelmezés:
        if(args.length != 1)
            ki = "Kincs";
        else
            ki = args[0];
        
        try {
            // Az ORB tulajdonságainak megadása
            java.util.Properties orbTulajdonságok = new java.util.Properties();
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialHost",
                    "172.21.0.152");
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialPort", "2006");
            // Az ORB (a metódushívások közvetítõjének) inicializálása
            orb = org.omg.CORBA.ORB.init((String [])null,
                    orbTulajdonságok);
            // A névszolgáltató gyökerének, mint CORBA objektum
            // referenciájának megszerzése
            org.omg.CORBA.Object corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt névszolgáltatóGyökere
                    = org.omg.CosNaming.
                    NamingContextExtHelper.narrow(corbaObjektum);
            // A labirintus CORBA objektum referenciájának megszerzése
            labirintus =
                    javattanitok.elosztott.objektumok.
                    LabirintusHelper.narrow(névszolgáltatóGyökere.
                    resolve_str("ElosztottLabirintus"));
            // A gyökér POA CORBA objektum referenciájának megszerzése
            corbaObjektum = orb.resolve_initial_references("RootPOA");
            // CORBA-s típuskényszerítéssel a megfelelõre
            org.omg.PortableServer.POA gyökérPoa =
                    org.omg.PortableServer.POAHelper.narrow(corbaObjektum);
            // A POA kiszolgáló állapotba kapcsolása:
            gyökérPoa.the_POAManager().activate();
            // Miként indították ezt a klienst?
            if("hõs".equals(ki)) {
                // Ha a kliens hõsként mûködik
                
                // A Hos kiszolgáló objektum elkészítése,
                // a delegációs/POA/Tie modell szerinti leképezéssel
                javattanitok.elosztott.objektumok.HosPOATie tie =
                        new javattanitok.elosztott.objektumok.HosPOATie(
                        new HõsKiszolgáló(), gyökérPoa);
                // A Hos CORBA objektum
                javattanitok.elosztott.objektumok.Hos hõs = tie._this(orb);
                
                System.out.println("HÕS> ELkészültem: " + hõs);
                
                // Távoli metódus hívása, a távoli CORBA labirintus
                // objektumnak átadkuk a Hos CORBA objektum referenciáját
                System.out.println(labirintus.belepHos(hõs));
                
                new Thread(this).start();
                
                // CORBA szerverként is üzemelünk, mert a Hos
                // objektumot a Labirintus objektum majd visszahívogatja
                orb.run();
                
            } else if("szörny".equals(ki)) {
                // Ha a kliens szörnyként mûködik
                javattanitok.elosztott.objektumok.SzornyPOATie tie =
                        new javattanitok.elosztott.objektumok.SzornyPOATie(
                        new SzörnyKiszolgáló(), gyökérPoa);
                javattanitok.elosztott.objektumok.Szorny szörny =
                        tie._this(orb);
                
                System.out.println(labirintus.belepSzorny(szörny));
                
                orb.run();
                
            } else  {
                // Ha a kliens kincsként mûködik
                javattanitok.elosztott.objektumok.KincsPOATie tie =
                        new javattanitok.elosztott.objektumok.KincsPOATie(
                        new KincsKiszolgáló(), gyökérPoa);
                javattanitok.elosztott.objektumok.Kincs kincs =
                        tie._this(orb);
                
                System.out.println(labirintus.belepKincs(kincs));
                
                orb.run();
                
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
    }
    /** A hõs játékostól jövõ billentyûzet input feldolgozása. */
    public void run() {
        
        try {
            // Feldolgozzuk a játékostól jövõ billentyû parancsokat
            java.io.BufferedReader konzol =
                    new java.io.BufferedReader(
                    new java.io.InputStreamReader(System.in));
            
            String parancs = null;
            while((parancs = konzol.readLine()) != null) {
                System.out.println(parancs);
                // A Hos CORBA objektum kilép a labirintusból
                if("k".equals(parancs))
                    break;
                // További lépés parancsok itt implemetálva: föl, le stb. ...
            }
        } catch(java.io.IOException e) {
            System.out.println(e);
        }
        orb.shutdown(false);
        System.exit(0);
    }
    /** Az elosztott labirintus klienseinek indítása.
     *
     * @param args elsõ tagja "hõs" - Hos objektum, "kincs" -
     * Kincs objektum, "szörny" - Szorny objektum lesz a kliens,
     * alapértelmezés a "kincs".
     */
    public static void main(String[] args) {
        
        new ElosztottKliens(args);
        
    }
}					
