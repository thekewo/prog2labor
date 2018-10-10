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
 * "tiszta" életre keltésére ad példát ez az osztály. Ennek megfelelően
 * csak egyszerű karakteres megjelenítést biztosít. Fő feladata a
 * kialakított labirintus OO mikrovilágunk API interfésze használatának
 * bemutatása. Továbbá az egyszerűség megtartása miatt ebben a példában
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
    /** A hős. */
    protected Hős hős;
    /** A játékbeli idő mérésére.*/
    private long idő = 0;
    public LabirintusVilág() {
    }
    /**
     * A <code>LabirintusVilág</code> objektum elkészítése.
     *
     * @param labirintusFájlNév a labirintust definiáló, megfelelő
     * szerkezetű szöveges állomány neve.
     * @exception RosszLabirintusKivétel ha a labirintust definiáló állomány nem
     * a megfelelő szerkezetű
     */
    public LabirintusVilág(String labirintusFájlNév)
    throws RosszLabirintusKivétel {
        
        // A labirintus elkészítése állományból
        labirintus = new Labirintus(labirintusFájlNév);
        
        // A hős elkészítése és a kezdő pozíciójának beállítása
        hős = new Hős(labirintus);
        hős.sor(9);
        hős.oszlop(0);
        
        // A játékbeli idő folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
        
    }
    /**
     * A játék időbeli fejlődésének vezérlése. A labirintus mikrovilágának
     * jelen osztálybeli életre keltésében max. 10 időpillanatig játszunk,
     * mialatt a hős igyekszik mindig jobbra lépni.
     */
    public void run() {
        
        labirintus.nyomtat();
        
        boolean játékVége = false;
        
        while(!játékVége) {
            
            idoegyseg();
            
            if(idő<10)
                hős.lépJobbra();
            else
                break;
            
            switch(labirintus.bolyong(hős)) {
                
                case Labirintus.JÁTÉK_MEGY_HŐS_RENDBEN:
                    break;
                case Labirintus.JÁTÉK_MEGY_MEGHALT_HŐS:
                    hős.sor(9);
                    hős.oszlop(0);
                    System.out.println("Megettek a(z) " + idő + ". lépésben!");
                    break;
                case Labirintus.JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN:
                    System.out.println("Megvan minden kincs a(z) " + idő
                            + ". lépésben!");
                    játékVége = true;
                    break;
                case Labirintus.JÁTÉK_VÉGE_MEGHALT_HŐS:
                    System.out.println("Minden életem elfogyott a(z) " + idő
                            + ". lépésben!");
                    játékVége = true;
                    break;
                    
            }
            
            System.out.println("A labirintus a(z) " + idő + ". lépésben:");
            labirintus.nyomtat(hős);
        }
        
        System.out.println("Megtalált értékek: " + hős.pontszám());
        System.out.println("Játékidő: " + idő + " lépés");
        System.out.println("Hányszor ettek meg: "
                + (Hős.ÉLETEK_SZÁMA - hős.életek()));
        
    }
    /** Megadja, hogy milyen gyorsan telik az idő a játékban. */
    private void idoegyseg() {
        
        ++idő;
        
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
    /**
     * Átveszi a játék indításához szükséges paramétereket, majd
     * elindítja a játék világának működését.
     *
     * @param args a labirintus tervét tartalmazó állomány neve az első
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