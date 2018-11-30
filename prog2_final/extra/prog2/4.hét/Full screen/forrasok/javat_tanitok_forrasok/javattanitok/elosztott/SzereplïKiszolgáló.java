/*
 * SzereplõKiszolgáló.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
/**
 * Az <code>elosztott.idl</code> leírta Szereplõ CORBA objektumot
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
 * @see HõsKiszolgáló
 * @see SzörnyKiszolgáló
 * @see KincsKiszolgáló
 */
public class SzereplõKiszolgáló
        extends javattanitok.elosztott.objektumok.SzereploPOA {
    /** A szereplõ oszlop pozíciója. */
    int oszlop;
    /** A szereplõ sor pozíciója. */
    int sor;
    /** A <code>SzereplõKiszolgáló</code> objektum elkészítése. */
    public SzereplõKiszolgáló() {}
    /**
     * Beállítja a szereplõ labirintusbeli pozíciójának oszlop
     * koordinátáját.
     *
     * @param oszlop a szereplõ labirintusbeli pozíciójának oszlop
     * koordinátája.
     */
    public void oszlop(int oszlop) {
        
        this.oszlop = oszlop;
        
        System.out.println("SZEREPLÕ> Beállították a pozíciómat.");
    }
    /**
     * Megadja a szereplõ labirintusbeli pozíciójának oszlop koordinátáját.
     *
     * @return int a szereplõ labirintusbeli pozíciójának oszlop koordinátája.
     */
    public int oszlop() {
        
        System.out.println("SZEREPLÕ> Lekérdezték a pozíciómat.");
        
        return oszlop;
    }
    /**
     * Beállítja a szereplõ labirintusbeli pozíciójának sor koordinátáját.
     *
     * @param sor a szereplõ labirintusbeli pozíciójának sor koordinátája.
     */
    public void sor(int sor) {
        
        this.sor = sor;
        
    }
    /**
     * Megadja a szereplõ labirintusbeli pozíciójának sor koordinátáját.
     *
     * @return int a szereplõ labirintusbeli pozíciójának sor koordinátája.
     */
    public int sor() {
        
        return sor;
        
    }
}					
