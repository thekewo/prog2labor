/*
 * Sz�rny.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus sz�rnyeit megad� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class Sz�rny extends Szerepl� {
    /** A megevett h�s�k sz�ma. */
    int megevettH�s�kSz�ma;
    /** A megevett kincsek sz�ma. */
    int megevettKincsekSz�ma;
    /**
     * L�trehoz egy <code>Sz�rny</code> objektumot.
     *
     * @param      labirintus       amibe a sz�rnyet helyezz�k.
     */
    public Sz�rny(Labirintus labirintus) {
        super(labirintus);
    }
    /**
     * A sz�rnyek mozg�s�nak vez�rl�se, ami szerint sz�rnyek
     * a h�s fel�s igyekeznek.
     *
     * @param h�s aki fel� a sz�rny igyekszik
     */
    public void l�p(H�s h�s) {
        
        int t�vols�g = t�vols�g(h�s);
        // Abba az ir�nyba l�v� poz�ci�ra l�p, amelyben k�zelebb ker�l a h�s.
        if(!labirintus.fal(oszlop, sor-1))
            if(t�vols�g(oszlop, sor-1, h�s) < t�vols�g) {
            l�pF�l();
            return;
            
            }
        
        if(!labirintus.fal(oszlop, sor+1))
            if(t�vols�g(oszlop, sor+1, h�s) < t�vols�g) {
            l�pLe();
            return;
            
            }
        
        if(!labirintus.fal(oszlop-1, sor))
            if(t�vols�g(oszlop-1, sor, h�s) < t�vols�g) {
            l�pBalra();
            return;
            
            }
        
        if(!labirintus.fal(oszlop+1, sor))
            if(t�vols�g(oszlop+1, sor, h�s) < t�vols�g) {
            l�pJobbra();
            return;
            
            }
        
    }
    /**
     * A sz�rny megette a h�st?
     *
     * @param      h�s       aki bolyong a labirintusban.
     */
    public boolean megesz(H�s h�s) {
        
        if(oszlop == h�s.oszlop()
        && sor == h�s.sor()) {
            
            ++megevettH�s�kSz�ma;
            return true;
            
        } else
            return false;
    }    
    /**
     * Sz�molgatja a megevett kincseket.
     *
     * @param      kincs       amit �ppen megettem.
     */
    public void megtal�ltam(Kincs kincs) {
        
        ++megevettKincsekSz�ma;
        
    }
        /**
     * A {@code Sz�rny} objektum sztring reprezent�ci�j�t adja
     * meg.
     *
     * @return String az objektum sztring reprezent�ci�ja.
     */
    public String toString() {
        
        return "SZ�RNY megevett h�s�k = "
                + megevettH�s�kSz�ma
                + "megevett kincsek = "
                + megevettKincsekSz�ma;
    }    
}
