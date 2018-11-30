/*
 * TávoliHõsKiszolgáló.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A <code>tavolihos.idl</code> leírta TávoliHõs CORBA
 * kiszolgáló objektum megvalósítása.
 *
 * A <code>tavolihos.idl</code> interfész:
 * <pre>
 * module javattanitok
 * {
 *   module korbas
 *   {
 *   interface TavoliHos
 *     {
 *       string lépLe(in string hosNev);
 *       string lépFöl(in string hosNev);
 *       string lépJobbra(in string hosNev);
 *       string lépBalra(in string hosNev);
 *     };
 *   };
 * };
 * </pre>
 * A <code>javattanitok.korbas.*</code> csomag legenerálása:
 * <pre>
 * idlj -fall tavolihos.idl
 * </pre>
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see KorbásLabirintus
 * @see KorbásKliens
 */
public class TávoliHõsKiszolgáló
        extends javattanitok.korbas.TavoliHosPOA {
    /** A labirintus játékot futtató osztály. */
    KorbásLabirintus korbásLabirintus;
    /**
     * A <code>TávoliHõsKiszolgáló</code> objektum elkészítése.
     *
     * @param      korbásLabirintus       A labirintus játékot futtató osztály.
     */
    public TávoliHõsKiszolgáló(KorbásLabirintus korbásLabirintus) {
        
        this.korbásLabirintus = korbásLabirintus;
        
    }    
    /**
     * Az ORB-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     * @return String a labirintus állapotát bemutató string. 
     */
    public String lépLe(String hõsNév) {
        Hõs hõs = null;
        
        hõs = korbásLabirintus.hõs(hõsNév);
        
        hõs.lépLe();
        
        return korbásLabirintus.labirintus().kinyomtat(hõs);
    }
    /**
     * Az ORB-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     * @return String a labirintus állapotát bemutató string. 
     */
    public String lépFöl(String hõsNév) {
        Hõs hõs = null;
        hõs = korbásLabirintus.hõs(hõsNév);
        
        hõs.lépFöl();
        
        return korbásLabirintus.labirintus().kinyomtat(hõs);
        
    }
    /**
     * Az ORB-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     * @return String a labirintus állapotát bemutató string. 
     */
    public String lépJobbra(String hõsNév) {
        Hõs hõs = null;
        hõs = korbásLabirintus.hõs(hõsNév);
        
        hõs.lépJobbra();
        
        return korbásLabirintus.labirintus().kinyomtat(hõs);
    }
    /**
     * Az ORB-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     * @return String a labirintus állapotát bemutató string. 
     */
    public String lépBalra(String hõsNév) {
        Hõs hõs = null;
        hõs = korbásLabirintus.hõs(hõsNév);
        hõs.lépBalra();
        
        return korbásLabirintus.labirintus().kinyomtat(hõs);
    }
}					
