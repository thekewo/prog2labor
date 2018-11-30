/*
 * Hõs.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus hõsét leíró osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class Hõs extends Szereplõ {
    /** A labirintusban megtalált kincsek értékei. */
    protected int megtaláltÉrtékek;
    /** A hõs életeinek maximális száma. */
    public static final int ÉLETEK_SZÁMA = 5;
    /** A hõs életeinek száma. */
    protected int életekSzáma = ÉLETEK_SZÁMA;
    /**
     * Létrehoz egy <code>Hõs</code> objektumot.
     *
     * @param      labirintus       amelyben a hõs bolyongani fog.
     */
    public Hõs(Labirintus labirintus) {
        super(labirintus);
        megtaláltÉrtékek = 0;
    }
    /**
     * Gyüjtögeti a megtalált kincseket.
     *
     * @param      kincs       amit éppen magtalált a hõs.
     */
    public void megtaláltam(Kincs kincs) {
        
        megtaláltÉrtékek += kincs.érték();
        
    }
    /**
     * Jelzi, hogy éppen megettek.
     *
     * @return true ha a hõsnek még van élete, ellenkezõ esetben, 
     * azaz ha az összes élete elfogyott már, akkor false.
     */
    public boolean megettek() {
        
        if(életekSzáma > 0) {
            --életekSzáma;
            return false;
        } else
            return true;
        
    }
    /**
     * megmondja, hogy élek-e még?
     *
     * @return true ha a hõsnek még van élete, ellenkezõ esetben, azaz 
     * ha az összes élete elfogyott már, akkor false.
     */
    public boolean él() {
        
        return életekSzáma > 0;
        
    }
    /**
     * Megadja az életek számát.
     *
     * @return int az életek száma.
     */
    public int életek() {
        
        return életekSzáma;
        
    }
    /**
     * Megadja a megtalált kincsek összegyüjtögetett értékeit.
     *
     * @return int a megtalált kincsek összegyüjtögetett értékei.
     */
    public int pontszám() {
        
        return megtaláltÉrtékek;
        
    }
   /**
     * A labirintus, amiben a hõs mozog.
     *
     * @return Labirintus a labirintus.
     */
    public Labirintus labirintus() {
        
        return labirintus;
        
    }      
}
