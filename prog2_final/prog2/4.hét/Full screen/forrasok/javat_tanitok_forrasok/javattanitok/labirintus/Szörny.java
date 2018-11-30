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
public class Szörny extends Szereplõ {
    /** A megevett hõsök száma. */
    int megevettHõsökSzáma;
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
     * a hõs felés igyekeznek.
     *
     * @param hõs aki felé a szörny igyekszik
     */
    public void lép(Hõs hõs) {
        
        int távolság = távolság(hõs);
        // Abba az irányba lévõ pozícióra lép, amelyben közelebb kerül a hõs.
        if(!labirintus.fal(oszlop, sor-1))
            if(távolság(oszlop, sor-1, hõs) < távolság) {
            lépFöl();
            return;
            
            }
        
        if(!labirintus.fal(oszlop, sor+1))
            if(távolság(oszlop, sor+1, hõs) < távolság) {
            lépLe();
            return;
            
            }
        
        if(!labirintus.fal(oszlop-1, sor))
            if(távolság(oszlop-1, sor, hõs) < távolság) {
            lépBalra();
            return;
            
            }
        
        if(!labirintus.fal(oszlop+1, sor))
            if(távolság(oszlop+1, sor, hõs) < távolság) {
            lépJobbra();
            return;
            
            }
        
    }
    /**
     * A szörny megette a hõst?
     *
     * @param      hõs       aki bolyong a labirintusban.
     */
    public boolean megesz(Hõs hõs) {
        
        if(oszlop == hõs.oszlop()
        && sor == hõs.sor()) {
            
            ++megevettHõsökSzáma;
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
        
        return "SZÖRNY megevett hõsök = "
                + megevettHõsökSzáma
                + "megevett kincsek = "
                + megevettKincsekSzáma;
    }    
}
