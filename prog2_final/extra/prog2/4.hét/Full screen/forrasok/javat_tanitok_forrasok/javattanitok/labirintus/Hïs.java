/*
 * H�s.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus h�s�t le�r� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class H�s extends Szerepl� {
    /** A labirintusban megtal�lt kincsek �rt�kei. */
    protected int megtal�lt�rt�kek;
    /** A h�s �leteinek maxim�lis sz�ma. */
    public static final int �LETEK_SZ�MA = 5;
    /** A h�s �leteinek sz�ma. */
    protected int �letekSz�ma = �LETEK_SZ�MA;
    /**
     * L�trehoz egy <code>H�s</code> objektumot.
     *
     * @param      labirintus       amelyben a h�s bolyongani fog.
     */
    public H�s(Labirintus labirintus) {
        super(labirintus);
        megtal�lt�rt�kek = 0;
    }
    /**
     * Gy�jt�geti a megtal�lt kincseket.
     *
     * @param      kincs       amit �ppen magtal�lt a h�s.
     */
    public void megtal�ltam(Kincs kincs) {
        
        megtal�lt�rt�kek += kincs.�rt�k();
        
    }
    /**
     * Jelzi, hogy �ppen megettek.
     *
     * @return true ha a h�snek m�g van �lete, ellenkez� esetben, 
     * azaz ha az �sszes �lete elfogyott m�r, akkor false.
     */
    public boolean megettek() {
        
        if(�letekSz�ma > 0) {
            --�letekSz�ma;
            return false;
        } else
            return true;
        
    }
    /**
     * megmondja, hogy �lek-e m�g?
     *
     * @return true ha a h�snek m�g van �lete, ellenkez� esetben, azaz 
     * ha az �sszes �lete elfogyott m�r, akkor false.
     */
    public boolean �l() {
        
        return �letekSz�ma > 0;
        
    }
    /**
     * Megadja az �letek sz�m�t.
     *
     * @return int az �letek sz�ma.
     */
    public int �letek() {
        
        return �letekSz�ma;
        
    }
    /**
     * Megadja a megtal�lt kincsek �sszegy�jt�getett �rt�keit.
     *
     * @return int a megtal�lt kincsek �sszegy�jt�getett �rt�kei.
     */
    public int pontsz�m() {
        
        return megtal�lt�rt�kek;
        
    }
   /**
     * A labirintus, amiben a h�s mozog.
     *
     * @return Labirintus a labirintus.
     */
    public Labirintus labirintus() {
        
        return labirintus;
        
    }      
}
