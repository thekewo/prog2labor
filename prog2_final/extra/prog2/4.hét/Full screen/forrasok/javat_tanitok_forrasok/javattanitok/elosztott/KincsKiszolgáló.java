/*
 * KincsKiszolgáló.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
/**
 * Az <code>elosztott.idl</code> leírta Kincs CORBA objektumot
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
 */
public class KincsKiszolgáló
        extends SzereplõKiszolgáló
        implements javattanitok.elosztott.objektumok.KincsOperations {
    /** A kincs értéke. */
    protected int érték;
    /** Megtaláltak már? */
    protected boolean megtalálva;
    /** A <code>KincsKiszolgáló</code> objektum elkészítése. */
    public KincsKiszolgáló() {}
    /**
     * Az érték beállítása.
     *
     * @param érték az a kincs értéke.
     */
    public void ertek(int érték) {
        
        this.érték = érték;
    }
    /**
     * Az érték lekérdezése.
     *
     * @return int az érték.
     */
    public int ertek() {
        
        return érték;
    }
    /**
     * Megtalált ez a hõs?
     *
     * @param hõs aki rámbukkant éppen.
     * @return boolean megtalál?
     */
    public boolean megtalalt(javattanitok.elosztott.objektumok.Hos hõs) {
        
        if(megtalálva) {
            System.out.println("KINCS> Már korábban rámtalált egy hõs.");
            // mert egy kicset csak egyszer lehet megtalálni
            return false;
        }
        
        if(oszlop == hõs.oszlop() && sor == hõs.sor()) {
            
            megtalálva = true;
            System.out.println("KINCS> Rámtalált.");
            
        } else
            System.out.println("KINCS> Nem talált rám.");
        
        return megtalálva;
    }
    /**
     * Megmondja, hogy megtalálták-e már a kincset?
     *
     * @return true ha a kincset már megtalálták,
     * ha még nem akkor false.
     */
    public boolean megtalalva() {
        
        return megtalálva;
        
    }
}					
