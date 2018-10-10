/*
 * Kincs.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus kincseit jellemző osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class Kincs extends Szereplő {
    /** A kincs értéke. */
    protected int érték;
    /** Megtaláltak már? */
    protected boolean megtalálva;
    /**
     * Létrehoz egy {@code Kincs} objektumot.
     *
     * @param   labirintus  amibe a kincset helyezzük.
     * @param   érték       a kincs értéke.
     */
    public Kincs(Labirintus labirintus, int érték) {
        super(labirintus);
        this.érték = érték;
    }
    /**
     * A szereplő (pl. hős, szörnyek) megtalálta a kincset?
     *
     * @param   hős aki keresi a kincset.
     * @return true ha a kincset éppen most megtalálta a szereplő, 
     * ha éppen nem, vagy már eleve megvan találva a kincs, akkor false.
     */
    public boolean megtalált(Szereplő szereplő) {
        
        if(megtalálva)
        // mert egy kicset csak egyszer lehet megtalálni
            return false;
        
        if(oszlop == szereplő.oszlop()
        && sor == szereplő.sor())
            megtalálva = true;
        
        return megtalálva;
    }
    /**
     * Megadja a kincs értékét.
     *
     * @return  int a kincs értéke.
     */
    public int érték() {
        
        return érték;
        
    }
    /**
     * Megmondja, hogy megtalálták-e már a kincset?
     *
     * @return true ha a kincset már megtalálták, 
     * ha még nem akkor false.
     */
    public boolean megtalálva() {
        
        return megtalálva;
        
    }
    /**
     * A {@code Kincs} objektum sztring reprezentációját adja
     * meg.
     *
     * @return String az objektum sztring reprezentációja.
     */
    public String toString() {
    
        return "KINCS érték = " 
                + érték
                + " megtalálva = "
                + megtalálva;
    }    
}