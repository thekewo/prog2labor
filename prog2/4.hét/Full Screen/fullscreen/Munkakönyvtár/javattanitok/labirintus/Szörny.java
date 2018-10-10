/*
 * Szörny.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus szörnyeit megadó osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class Szörny extends Szereplő {
    /** A megevett hősök száma. */
    int megevettHősökSzáma;
    /** A megevett kincsek száma. */
    int megevettKincsekSzáma;
    /**
     * Létrehoz egy <code>Szörny</code> objektumot.
     *
     * @param      labirintus       amibe a szörnyet helyezzük.
     */
    public Szörny(Labirintus labirintus) {
        super(labirintus);
    }
    /**
     * A szörnyek mozgásának vezérlése, ami szerint szörnyek
     * a hős felés igyekeznek.
     *
     * @param hős aki felé a szörny igyekszik
     */
    public void lép(Hős hős) {
        
        int távolság = távolság(hős);
        // Abba az irányba lévő pozícióra lép, amelyben közelebb kerül a hős.
        if(!labirintus.fal(oszlop, sor-1))
            if(távolság(oszlop, sor-1, hős) < távolság) {
            lépFöl();
            return;
            
            }
        
        if(!labirintus.fal(oszlop, sor+1))
            if(távolság(oszlop, sor+1, hős) < távolság) {
            lépLe();
            return;
            
            }
        
        if(!labirintus.fal(oszlop-1, sor))
            if(távolság(oszlop-1, sor, hős) < távolság) {
            lépBalra();
            return;
            
            }
        
        if(!labirintus.fal(oszlop+1, sor))
            if(távolság(oszlop+1, sor, hős) < távolság) {
            lépJobbra();
            return;
            
            }
        
    }
    /**
     * A szörny megette a hőst?
     *
     * @param      hős       aki bolyong a labirintusban.
     */
    public boolean megesz(Hős hős) {
        
        if(oszlop == hős.oszlop()
        && sor == hős.sor()) {
            
            ++megevettHősökSzáma;
            return true;
            
        } else
            return false;
    }    
    /**
     * Számolgatja a megevett kincseket.
     *
     * @param      kincs       amit éppen megettem.
     */
    public void megtaláltam(Kincs kincs) {
        
        ++megevettKincsekSzáma;
        
    }
        /**
     * A {@code Szörny} objektum sztring reprezentációját adja
     * meg.
     *
     * @return String az objektum sztring reprezentációja.
     */
    public String toString() {
        
        return "SZÖRNY megevett hősök = "
                + megevettHősökSzáma
                + "megevett kincsek = "
                + megevettKincsekSzáma;
    }    
}