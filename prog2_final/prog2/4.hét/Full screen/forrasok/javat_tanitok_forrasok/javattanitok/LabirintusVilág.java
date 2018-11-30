/*
 * LabirintusVilág.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * "tiszta" életre keltésére ad példát ez az osztály. Ennek megfelelõen
 * csak egyszerû karakteres megjelenítést biztosít. Fõ feladata a
 * kialakított labirintus OO mikrovilágunk API interfésze használatának
 * bemutatása. Továbbá az egyszerûség megtartása miatt ebben a példában
 * még nem vesz át adatokat a játékostól a program.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.TávoliLabirintus
 * @see javattanitok.KorbásLabirintus
 * @see javattanitok.ElosztottLabirintus
 */
public class LabirintusVilág implements Runnable {
    /** A labirintus. */
    protected Labirintus labirintus;
    /** A hõs. */
    protected Hõs hõs;
    /** A játékbeli idõ mérésére.*/
    private long idõ = 0;
    public LabirintusVilág() {
    }
    /**
     * A <code>LabirintusVilág</code> objektum elkészítése.
     *
     * @param labirintusFájlNév a labirintust definiáló, megfelelõ
     * szerkezetû szöveges állomány neve.
     * @exception RosszLabirintusKivétel ha a labirintust definiáló állomány nem
     * a megfelelõ szerkezetû
     */
    public LabirintusVilág(String labirintusFájlNév)
    throws RosszLabirintusKivétel {
        
        // A labirintus elkészítése állományból
        labirintus = new Labirintus(labirintusFájlNév);
        
        // A hõs elkészítése és a kezdõ pozíciójának beállítása
        hõs = new Hõs(labirintus);
        hõs.sor(9);
        hõs.oszlop(0);
        
        // A játékbeli idõ folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
        
    }
    /**
     * A játék idõbeli fejlõdésének vezérlése. A labirintus mikrovilágának
     * jelen osztálybeli életre keltésében max. 10 idõpillanatig játszunk,
     * mialatt a hõs igyekszik mindig jobbra lépni.
     */
    public void run() {
        
        labirintus.nyomtat();
        
        boolean játékVége = false;
        
        while(!játékVége) {
            
            idoegyseg();
            
            if(idõ<10)
                hõs.lépJobbra();
            else
                break;
            
            switch(labirintus.bolyong(hõs)) {
                
                case Labirintus.JÁTÉK_MEGY_HÕS_RENDBEN:
                    break;
                case Labirintus.JÁTÉK_MEGY_MEGHALT_HÕS:
                    hõs.sor(9);
                    hõs.oszlop(0);
                    System.out.println("Megettek a(z) " + idõ + ". lépésben!");
                    break;
                case Labirintus.JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN:
                    System.out.println("Megvan minden kincs a(z) " + idõ
                            + ". lépésben!");
                    játékVége = true;
                    break;
                case Labirintus.JÁTÉK_VÉGE_MEGHALT_HÕS:
                    System.out.println("Minden életem elfogyott a(z) " + idõ
                            + ". lépésben!");
                    játékVége = true;
                    break;
                    
            }
            
            System.out.println("A labirintus a(z) " + idõ + ". lépésben:");
            labirintus.nyomtat(hõs);
        }
        
        System.out.println("Megtalált értékek: " + hõs.pontszám());
        System.out.println("Játékidõ: " + idõ + " lépés");
        System.out.println("Hányszor ettek meg: "
                + (Hõs.ÉLETEK_SZÁMA - hõs.életek()));
        
    }
    /** Megadja, hogy milyen gyorsan telik az idõ a játékban. */
    private void idoegyseg() {
        
        ++idõ;
        
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
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
            
            System.out.println("Indítás: java LabirintusVilág labirintus.txt");
            System.exit(-1);
        }
        
        try {
            
            new LabirintusVilág(args[0]);
            
        } catch(RosszLabirintusKivétel rosszLabirintusKivétel) {
            
            System.out.println(rosszLabirintusKivétel);
            
        }
    }
}
