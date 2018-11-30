/*
 * SzörnyKiszolgáló.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
/**
 * Az <code>elosztott.idl</code> leírta Szörny CORBA objektumot
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
 * @see SzereplõKiszolgáló
 * @see KincsKiszolgáló
 */
public class SzörnyKiszolgáló
        extends SzereplõKiszolgáló
        implements javattanitok.elosztott.objektumok.SzornyOperations {
    /** A <code>SzörnyKiszolgáló</code> objektum elkészítése. */
    public SzörnyKiszolgáló() {}
    /**
     * Lépek a hõs felé.
     *
     * @param hõs aki felé mozgok.
     * @return boolean elkaptam ez a hõst?
     */
    public boolean lep(javattanitok.elosztott.objektumok.Hos hos) {
        // ... a hõs felé lépést nem implementáltuk ebben a példában.
        System.out.println("SZÖRNY> Lépek egy hõs felé.");
        // ... soha nem eszem meg ebben az implementációban.
        return false;
    }
}					
