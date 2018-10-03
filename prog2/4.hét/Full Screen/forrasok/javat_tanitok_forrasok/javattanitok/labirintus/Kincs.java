/*
 * Kincs.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus kincseit jellemz� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class Kincs extends Szerepl� {
    /** A kincs �rt�ke. */
    protected int �rt�k;
    /** Megtal�ltak m�r? */
    protected boolean megtal�lva;
    /**
     * L�trehoz egy {@code Kincs} objektumot.
     *
     * @param   labirintus  amibe a kincset helyezz�k.
     * @param   �rt�k       a kincs �rt�ke.
     */
    public Kincs(Labirintus labirintus, int �rt�k) {
        super(labirintus);
        this.�rt�k = �rt�k;
    }
    /**
     * A szerepl� (pl. h�s, sz�rnyek) megtal�lta a kincset?
     *
     * @param   h�s aki keresi a kincset.
     * @return true ha a kincset �ppen most megtal�lta a szerepl�, 
     * ha �ppen nem, vagy m�r eleve megvan tal�lva a kincs, akkor false.
     */
    public boolean megtal�lt(Szerepl� szerepl�) {
        
        if(megtal�lva)
        // mert egy kicset csak egyszer lehet megtal�lni
            return false;
        
        if(oszlop == szerepl�.oszlop()
        && sor == szerepl�.sor())
            megtal�lva = true;
        
        return megtal�lva;
    }
    /**
     * Megadja a kincs �rt�k�t.
     *
     * @return  int a kincs �rt�ke.
     */
    public int �rt�k() {
        
        return �rt�k;
        
    }
    /**
     * Megmondja, hogy megtal�lt�k-e m�r a kincset?
     *
     * @return true ha a kincset m�r megtal�lt�k, 
     * ha m�g nem akkor false.
     */
    public boolean megtal�lva() {
        
        return megtal�lva;
        
    }
    /**
     * A {@code Kincs} objektum sztring reprezent�ci�j�t adja
     * meg.
     *
     * @return String az objektum sztring reprezent�ci�ja.
     */
    public String toString() {
    
        return "KINCS �rt�k = " 
                + �rt�k
                + " megtal�lva = "
                + megtal�lva;
    }    
}
