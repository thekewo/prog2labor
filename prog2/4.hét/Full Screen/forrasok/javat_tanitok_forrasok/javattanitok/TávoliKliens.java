/*
 * TávoliKliens.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * Java RMI-s hálózati, kliens oldali életre keltésére ad példát 
 * ez az osztály: a hõs távolról történõ mozgatását biztosítja.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see TávoliLabirintus
 * @see TávoliHõsíthetõ
 */
public class TávoliKliens {
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
        // Megszerezzük a távoli labirintus (TávoliHõsíthetõ) referenciáját
        try {
            java.rmi.registry.Registry registry =
                    java.rmi.registry.LocateRegistry.getRegistry();
            TávoliHõsíthetõ távoliHõsíthetõ = 
                    (TávoliHõsíthetõ) registry.lookup("TavoliLabirintus");
            // és jobbra mozgatjuk a labirintusban a hõsünket 
            System.out.println(távoliHõsíthetõ.lépJobbra(hõsNév));
            
        } catch (java.rmi.NotBoundException be) {
            be.printStackTrace();
        } catch (java.rmi.RemoteException re) {
            re.printStackTrace();
        }
    }    
}               
