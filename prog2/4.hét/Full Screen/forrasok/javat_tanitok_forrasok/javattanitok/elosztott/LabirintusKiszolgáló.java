/*
 * LabirintusKiszolg�l�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;

import java.util.List;
import java.util.Iterator;
// Itt vannak a CORBA objektumaink, b�r nem szok�sunk,
// most import�lunk, mert a teljes csomagn�vvel
// val� oszt�lyn�v min�s�t�st, a hossza miatt neh�zkes
// lenne kezelni a k�nyv kapcs�n a sz�vegszerkeszt�nkben.
import javattanitok.elosztott.objektumok.*;
/**
 * Az <code>elosztott.idl</code> le�rta Labirintus CORBA objektumot
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
 * @see Szerepl�Kiszolg�l�
 * @see H�sKiszolg�l�
 * @see Sz�rnyKiszolg�l�
 * @see KincsKiszolg�l�
 */
public class LabirintusKiszolg�l�
        extends javattanitok.labirintus.T�bbH�s�sLabirintus
        implements LabirintusOperations, Runnable {
    /** A labirintus sz�rnyei, elemei t�voli CORBA objektumok. */
    protected List<Szorny> sz�rnyek;
    /** A labirintus kincsei, elemei t�voli CORBA objektumok. */
    protected List<Kincs> kincsek;
    /** A labirintus h�sei, elemei t�voli CORBA objektumok. */
    protected List<Hos> h�s�k;
    /** A j�t�kbeli id� m�r�s�re.*/
    private long id� = 0;
    /** V�letlensz�m gener�tor a szerepl�k mozgat�s�hoz. */
    protected static java.util.Random v�letlenGener�tor =
            new java.util.Random();
    /** A <code>LabirintusKiszolg�l�</code> objektum elk�sz�t�se. */
    public LabirintusKiszolg�l�() {
        // Az �s megfelel� konstruktor�nak h�v�sa
        super();
        // A kincseket tartalmaz� adatszerkezet l�trehoz�sa,
        // elemei majd t�voli CORBA objektumok lesznek
        kincsek = new java.util.ArrayList<Kincs>();
        // A kincseket tartalmaz� adatszerkezet l�trehoz�sa,
        // elemei majd t�voli CORBA objektumok lesznek
        sz�rnyek = new java.util.ArrayList<Szorny>();
        // A kincseket tartalmaz� adatszerkezet l�trehoz�sa,
        // elemei majd t�voli CORBA objektumok lesznek
        h�s�k = java.util.Collections.synchronizedList(
                new java.util.ArrayList<Hos>());
        
        // A j�t�kbeli id� foly�s�t biztos�t� sz�l elk�sz�t�se �s ind�t�sa
        new Thread(this).start();
    }
    /**
     * A Hos CORBA objektum az ORB-n kereszt�l bel�p a labirintusba.
     *
     * @param h�s a Hos CORBA objektum referenci�ja.
     * @return String a labirintus �llapot�t bemutat� string.
     */
    public String belepHos(Hos h�s) {
        System.out.println("LABIRINTUS> Bel�pett egy h�s: " + h�s);
        h�s�k.add(h�s);
        szerepl�HelyeKezdetben(h�s);
        
        return toString();
        
    }
    /**
     * A Kincs CORBA objektum az ORB-n kereszt�l bel�p a labirintusba.
     *
     * @param h�s a Kincs CORBA objektum referenci�ja.
     * @return String a labirintus �llapot�t bemutat� string.
     */
    public String belepKincs(Kincs kincs) {
        
        System.out.println("LABIRINTUS> Bel�pett egy kincs.");
        kincsek.add(kincs);
        szerepl�HelyeKezdetben(kincs);
        
        return toString();
        
    }
    /**
     * A Szorny CORBA objektum az ORB-n kereszt�l bel�p a labirintusba.
     *
     * @param h�s a Szorny CORBA objektum referenci�ja.
     * @return String a labirintus �llapot�t bemutat� string.
     */
    public String belepSzorny(Szorny sz�rny) {
        
        System.out.println("LABIRINTUS> Bel�pett egy sz�rny.");
        sz�rnyek.add(sz�rny);
        szerepl�HelyeKezdetben(sz�rny);
        
        return toString();
        
    }
    /**
     * A szerepl� labirintusbeli kezd� poz�ci�j�nak meghat�roz�sa.
     */
    void szerepl�HelyeKezdetben(Szereplo szerepl�) {
        // T�bbsz�r pr�b�lkozunk elhelyezni a szerepl�t a labirintusban,
        // sz�molja, hol tartunk ezekkel a pr�b�lkoz�sokkal:
        int sz�ml�l� = 0, oszlop = 0, sor = 0;
        do {
            // itt +2,-2-k, hogy a bal als� sarokt�l t�vol tartsuk
            // a szerepl�ket, mert majd ezt akarjuk a h�s kezd� poz�ci�j�nak
            oszlop = 2+v�letlenGener�tor.nextInt(sz�less�g-2);
            sor = v�letlenGener�tor.nextInt(magass�g-2);
            // max. 10-szer pr�b�lkozunk, de ha siker�l nem "falba tenni" a
            // szerepl�t, akkor m�ris kil�p�nk:
        } while(++sz�ml�l�<10 && fal(oszlop, sor));
        
        szerepl�.oszlop(oszlop);
        szerepl�.sor(sor);
        
    }
    /** A j�t�k id�beli fejl�d�s�nek vez�rl�se. */
    public void run() {
        
        while(true) {
            
            idoegyseg();
            
            System.out.println(this);
            
            synchronized(h�s�k) {
                Iterator i = h�s�k.iterator();
                
                while(i.hasNext()) {
                    Hos h�s = (Hos)i.next();
                    try {
                        switch(bolyong(h�s)) {
                            
                            case J�T�K_MEGY_H�S_RENDBEN:
                                break;
                            case J�T�K_MEGY_MEGHALT_H�S:
                                break;
                            case J�T�K_V�GE_MEGHALT_H�S:
                                i.remove();
                                break;
                                
                        }
                        
                    } catch (org.omg.CORBA.SystemException e) {
                        
                        i.remove();
                        System.out.println("LABIRINTUS> " +
                                "Egy h�st elt�vol�tottam.");
                        
                    }
                }
            }
        }
    }
    /**
     * Az �s megfelel� met�dus�nak elfed�se, az �j CORBA szerepl�ket
     * haszn�lva.
     *
     * @see T�bbH�s�sLabirintus#bolyong(H�s h�s)
     * @param h�s aki a labirintusban bolyong.
     * @return int a j�t�k �llapot�t le�r� k�d.
     */
    public int bolyong(Hos h�s) {
        
        for(Kincs kincs : kincsek) {
            
            if(kincs.megtalalt(h�s))
                h�s.megtalaltam(kincs);
            
        }
        
        for(Szorny sz�rny : sz�rnyek) {
            
            if(sz�rny.lep(h�s))  {
                
                if(h�s.megettek())
                    return J�T�K_V�GE_MEGHALT_H�S;
                else
                    return J�T�K_MEGY_MEGHALT_H�S;
            }
            
        }
        
        return J�T�K_MEGY_H�S_RENDBEN;
    }
    /**
     * Megadja, hogy milyen gyorsan telik az id� a j�t�kban.
     */
    private void idoegyseg() {
        
        ++id�;
        
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
    /**
     * Madadja, hogy van-e sz�rny a labirintus adott oszlop,
     * sor poz�ci�ja.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha van.
     */
    boolean vanSz�rny(int sor, int oszlop) {
        
        boolean van = false;
        
        for(Szorny sz�rny: sz�rnyek)
            if(sor == sz�rny.sor()
            && oszlop == sz�rny.oszlop()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * Madadja, hogy van-e megtal�lhat� kincs a labirintus
     * adott oszlop, sor poz�ci�ja.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha van.
     */
    boolean vanKincs(int sor, int oszlop) {
        
        boolean van = false;
        
        for(Kincs kincs: kincsek)
            if(sor == kincs.sor()
            && oszlop == kincs.oszlop()
            && !kincs.megtalalva()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * Kinyomtatja a labirintus szerkezet�t �s szerepl�it a System.out-ra.
     *
     * @param h�s akit szint�n belenyomtat a labirintusba.
     */
    public void nyomtat(Hos h�s) {
        
        for(int i=0; i<magass�g; ++i) {
            for(int j=0; j<sz�less�g; ++j) {
                
                boolean vanSz�rny = vanSz�rny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanH�s = (i == h�s.sor() && j == h�s.oszlop());
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else if(vanSz�rny && vanKincs && vanH�s)
                    System.out.print("|SKH");
                else if(vanSz�rny && vanKincs)
                    System.out.print("|SK ");
                else if(vanKincs && vanH�s)
                    System.out.print("|KH ");
                else if(vanSz�rny && vanH�s)
                    System.out.print("|SH ");
                else if(vanKincs)
                    System.out.print("|K  ");
                else if(vanH�s)
                    System.out.print("|H  ");
                else if(vanSz�rny)
                    System.out.print("|S  ");
                else
                    System.out.print("|   ");
                
            }            
            System.out.println();            
        }
    }
    /**
     * A labirintus sztring reprezent�ci�ja.
     *
     * @return String labirintus sztring reprezent�ci�ja.
     */
    public String toString() {
        
        return " Id�:" + id�
                + " h�s:" + h�s�k.size()
                + " kincs:" + kincsek.size()
                + " sz�rny:" + sz�rnyek.size();        
    }
}					
