/*
 * KincsKiszolg�l�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
/**
 * Az <code>elosztott.idl</code> le�rta Kincs CORBA objektumot
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
 */
public class KincsKiszolg�l�
        extends Szerepl�Kiszolg�l�
        implements javattanitok.elosztott.objektumok.KincsOperations {
    /** A kincs �rt�ke. */
    protected int �rt�k;
    /** Megtal�ltak m�r? */
    protected boolean megtal�lva;
    /** A <code>KincsKiszolg�l�</code> objektum elk�sz�t�se. */
    public KincsKiszolg�l�() {}
    /**
     * Az �rt�k be�ll�t�sa.
     *
     * @param �rt�k az a kincs �rt�ke.
     */
    public void ertek(int �rt�k) {
        
        this.�rt�k = �rt�k;
    }
    /**
     * Az �rt�k lek�rdez�se.
     *
     * @return int az �rt�k.
     */
    public int ertek() {
        
        return �rt�k;
    }
    /**
     * Megtal�lt ez a h�s?
     *
     * @param h�s aki r�mbukkant �ppen.
     * @return boolean megtal�l?
     */
    public boolean megtalalt(javattanitok.elosztott.objektumok.Hos h�s) {
        
        if(megtal�lva) {
            System.out.println("KINCS> M�r kor�bban r�mtal�lt egy h�s.");
            // mert egy kicset csak egyszer lehet megtal�lni
            return false;
        }
        
        if(oszlop == h�s.oszlop() && sor == h�s.sor()) {
            
            megtal�lva = true;
            System.out.println("KINCS> R�mtal�lt.");
            
        } else
            System.out.println("KINCS> Nem tal�lt r�m.");
        
        return megtal�lva;
    }
    /**
     * Megmondja, hogy megtal�lt�k-e m�r a kincset?
     *
     * @return true ha a kincset m�r megtal�lt�k,
     * ha m�g nem akkor false.
     */
    public boolean megtalalva() {
        
        return megtal�lva;
        
    }
}					
