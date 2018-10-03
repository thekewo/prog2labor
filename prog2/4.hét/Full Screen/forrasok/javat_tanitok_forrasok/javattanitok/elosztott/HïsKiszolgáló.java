/*
 * H�sKiszolg�l�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
// Itt vannak a CORBA objektumaink, b�r nem szok�sunk,
// most import�lunk, mert a teljes csomagn�vvel
// val� oszt�lyn�v min�s�t�st, a hossza miatt neh�zkes
// lenne kezelni a k�nyv kapcs�n a sz�vegszerkeszt�nkben.
import javattanitok.elosztott.objektumok.*;
/**
 * Az <code>elosztott.idl</code> le�rta H�s CORBA objektumot
 * kiszolg�l� objektum megval�s�t�sa.
 *
 * Az <code>elosztott.idl</code> interf�sz:
 * <pre>
 * module javattanitok
 * {
 *   module elosztott
 *   {
 *     module objektumok
 *     {
 *       interface Szereplo
 *       {
 *         attribute long oszlop;
 *         attribute long sor;
 *       };
 *       interface Kincs;
 *       interface Hos : Szereplo
 *       {
 *         attribute long megtalaltErtekek;
 *         readonly attribute long eletek;
 *         void megtalaltam(in Kincs kincs);
 *         boolean megettek();
 *       };
 *       interface Kincs : Szereplo
 *       {
 *         attribute long ertek;
 *         readonly attribute boolean megtalalva;
 *         boolean megtalalt(in Hos hos);
 *       };
 *       interface Szorny : Szereplo
 *       {
 *         boolean lep(in Hos hos);
 *       };
 *       interface Labirintus
 *       {
 *         wstring belepHos(in Hos hos);
 *         wstring belepKincs(in Kincs kincs);
 *         wstring belepSzorny(in Szorny szorny);
 *       };
 *     };
 *   };
 * };
 * </pre>
 * A <code>javattanitok.elosztott.objektumok.*</code> csomag legener�l�sa:
 * <pre>
 * idlj -fall -falltie elosztott.idl
 * </pre>
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see ElosztottLabirintus
 * @see ElosztottKliens
 * @see LabirintusKiszolg�l�
 * @see Szerepl�Kiszolg�l�
 * @see H�sKiszolg�l�
 * @see Sz�rnyKiszolg�l�
 * @see KincsKiszolg�l�
 */
public class H�sKiszolg�l�
        extends Szerepl�Kiszolg�l�
        implements HosOperations {
    
    /** A labirintusban megtal�lt kincsek �rt�kei. */
    protected int megtal�lt�rt�kek;
    /** A h�s �leteinek maxim�lis sz�ma. */
    public static final int �LETEK_SZ�MA = 5;
    /** A h�s �leteinek sz�ma. */
    protected int �letekSz�ma = �LETEK_SZ�MA;
    /** A <code>H�sKiszolg�l�</code> objektum elk�sz�t�se. */
    public H�sKiszolg�l�() {}
    /**
     * �letek sz�m�nak lek�rdez�se.
     *
     * @return int az �letek sz�ma.
     */
    public int eletek() {
        
        return �letekSz�ma;
    }
    /**
     * A megtal�lt �rt�kek be�ll�t�sa.
     *
     * @param megtal�lt�rt�kek a megtal�lt �rt�kek.
     */
    public void megtalaltErtekek(int megtal�lt�rt�kek) {
        
        this.megtal�lt�rt�kek = megtal�lt�rt�kek;
    }
    /**
     * A megtal�lt �rt�kek lek�rdez�se.
     *
     * @return int a megtal�lt �rt�kek.
     */
    public int megtalaltErtekek() {
        
        return megtal�lt�rt�kek;
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
     * Gy�jt�geti a megtal�lt kincseket.
     *
     * @param kincs amit �ppen magtal�lt a h�s.
     */
    public void megtalaltam(Kincs kincs) {
        
        megtal�lt�rt�kek += kincs.ertek();
        System.out.println("H�S> Kincset tal�ltam.");
        
    }
}					
