/*
 * Szerepl�Kiszolg�l�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;
/**
 * Az <code>elosztott.idl</code> le�rta Szerepl� CORBA objektumot
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
 * @see H�sKiszolg�l�
 * @see Sz�rnyKiszolg�l�
 * @see KincsKiszolg�l�
 */
public class Szerepl�Kiszolg�l�
        extends javattanitok.elosztott.objektumok.SzereploPOA {
    /** A szerepl� oszlop poz�ci�ja. */
    int oszlop;
    /** A szerepl� sor poz�ci�ja. */
    int sor;
    /** A <code>Szerepl�Kiszolg�l�</code> objektum elk�sz�t�se. */
    public Szerepl�Kiszolg�l�() {}
    /**
     * Be�ll�tja a szerepl� labirintusbeli poz�ci�j�nak oszlop
     * koordin�t�j�t.
     *
     * @param oszlop a szerepl� labirintusbeli poz�ci�j�nak oszlop
     * koordin�t�ja.
     */
    public void oszlop(int oszlop) {
        
        this.oszlop = oszlop;
        
        System.out.println("SZEREPL�> Be�ll�tott�k a poz�ci�mat.");
    }
    /**
     * Megadja a szerepl� labirintusbeli poz�ci�j�nak oszlop koordin�t�j�t.
     *
     * @return int a szerepl� labirintusbeli poz�ci�j�nak oszlop koordin�t�ja.
     */
    public int oszlop() {
        
        System.out.println("SZEREPL�> Lek�rdezt�k a poz�ci�mat.");
        
        return oszlop;
    }
    /**
     * Be�ll�tja a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�j�t.
     *
     * @param sor a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�ja.
     */
    public void sor(int sor) {
        
        this.sor = sor;
        
    }
    /**
     * Megadja a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�j�t.
     *
     * @return int a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�ja.
     */
    public int sor() {
        
        return sor;
        
    }
}					
