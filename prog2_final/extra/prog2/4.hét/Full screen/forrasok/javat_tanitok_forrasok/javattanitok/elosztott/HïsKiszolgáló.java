/*
 * HõsKiszolgáló.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
// Itt vannak a CORBA objektumaink, bár nem szokásunk,
// most importálunk, mert a teljes csomagnévvel
// való osztálynév minõsítést, a hossza miatt nehézkes
// lenne kezelni a könyv kapcsán a szövegszerkesztõnkben.
import javattanitok.elosztott.objektumok.*;
/**
 * Az <code>elosztott.idl</code> leírta Hõs CORBA objektumot
 * kiszolgáló objektum megvalósítása.
 *
 * Az <code>elosztott.idl</code> interfész:
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
 * A <code>javattanitok.elosztott.objektumok.*</code> csomag legenerálása:
 * <pre>
 * idlj -fall -falltie elosztott.idl
 * </pre>
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see ElosztottLabirintus
 * @see ElosztottKliens
 * @see LabirintusKiszolgáló
 * @see SzereplõKiszolgáló
 * @see HõsKiszolgáló
 * @see SzörnyKiszolgáló
 * @see KincsKiszolgáló
 */
public class HõsKiszolgáló
        extends SzereplõKiszolgáló
        implements HosOperations {
    
    /** A labirintusban megtalált kincsek értékei. */
    protected int megtaláltÉrtékek;
    /** A hõs életeinek maximális száma. */
    public static final int ÉLETEK_SZÁMA = 5;
    /** A hõs életeinek száma. */
    protected int életekSzáma = ÉLETEK_SZÁMA;
    /** A <code>HõsKiszolgáló</code> objektum elkészítése. */
    public HõsKiszolgáló() {}
    /**
     * Életek számának lekérdezése.
     *
     * @return int az életek száma.
     */
    public int eletek() {
        
        return életekSzáma;
    }
    /**
     * A megtalált értékek beállítása.
     *
     * @param megtaláltÉrtékek a megtalált értékek.
     */
    public void megtalaltErtekek(int megtaláltÉrtékek) {
        
        this.megtaláltÉrtékek = megtaláltÉrtékek;
    }
    /**
     * A megtalált értékek lekérdezése.
     *
     * @return int a megtalált értékek.
     */
    public int megtalaltErtekek() {
        
        return megtaláltÉrtékek;
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
     * Gyüjtögeti a megtalált kincseket.
     *
     * @param kincs amit éppen magtalált a hõs.
     */
    public void megtalaltam(Kincs kincs) {
        
        megtaláltÉrtékek += kincs.ertek();
        System.out.println("HÕS> Kincset találtam.");
        
    }
}					
