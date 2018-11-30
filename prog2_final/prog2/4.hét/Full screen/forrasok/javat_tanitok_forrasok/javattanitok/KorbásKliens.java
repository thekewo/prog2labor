/*
 * KorbásKliens.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * CORBA-s hálózati, kliens oldali életre keltésére ad példát ez az osztály:
 * a hõs távolról történõ mozgatását biztosítja.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see KorbásLabirintus
 * @see TávoliHõsKiszolgáló
 */
public class KorbásKliens {
    /**
     * Elindítja a távolról jelentkezõ hõs klienst.
     *
     * @param args a hõs neve.
     */
    public static void main(String[] args) {
        
        String hõsNév = null;
        // ha nem adtuk meg a parancssor-argumentumot, 
        // akkor ez az alapértelmezés:
        if(args.length != 1)
            hõsNév = "Matyi";
        else
            hõsNév = args[0];
        
        try {
            // Az ORB tulajdonságainak megadása
            java.util.Properties orbTulajdonságok = new java.util.Properties();
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialHost", "localhost");
            orbTulajdonságok.put("org.omg.CORBA.ORBInitialPort", "2006");
            // Az ORB (a metódushívások közvetítõjének) inicializálása
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String [])null, 
                    orbTulajdonságok);
            // A névszolgáltató gyökerének, mint CORBA objektum
            // referenciájának megszerzése
            org.omg.CORBA.Object corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt névszolgáltatóGyökere
                    = org.omg.CosNaming.
                    NamingContextExtHelper.narrow(corbaObjektum);
            // A TavoliHos szolgáltatást nyújtó CORBA objektum
            // referenciájának megszerzése
            javattanitok.korbas.TavoliHos távoliHõs =
                    javattanitok.korbas.TavoliHosHelper.narrow(
                    névszolgáltatóGyökere.resolve_str("TavoliHos"));
            
            System.out.println(távoliHõs.lépJobbra(hõsNév));
            
        } catch (Exception e) {            
            e.printStackTrace();            
        }        
    }    
}					
