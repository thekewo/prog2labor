/*
 * Sz�rnyKiszolg�l�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
/**
 * Az <code>elosztott.idl</code> le�rta Sz�rny CORBA objektumot
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
 * @see Szerepl�Kiszolg�l�
 * @see KincsKiszolg�l�
 */
public class Sz�rnyKiszolg�l�
        extends Szerepl�Kiszolg�l�
        implements javattanitok.elosztott.objektumok.SzornyOperations {
    /** A <code>Sz�rnyKiszolg�l�</code> objektum elk�sz�t�se. */
    public Sz�rnyKiszolg�l�() {}
    /**
     * L�pek a h�s fel�.
     *
     * @param h�s aki fel� mozgok.
     * @return boolean elkaptam ez a h�st?
     */
    public boolean lep(javattanitok.elosztott.objektumok.Hos hos) {
        // ... a h�s fel� l�p�st nem implement�ltuk ebben a p�ld�ban.
        System.out.println("SZ�RNY> L�pek egy h�s fel�.");
        // ... soha nem eszem meg ebben az implement�ci�ban.
        return false;
    }
}					
