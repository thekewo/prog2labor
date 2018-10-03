/*
 * LabirintusKiszolgáló.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.elosztott;

import java.util.List;
import java.util.Iterator;
// Itt vannak a CORBA objektumaink, bár nem szokásunk,
// most importálunk, mert a teljes csomagnévvel
// való osztálynév minõsítést, a hossza miatt nehézkes
// lenne kezelni a könyv kapcsán a szövegszerkesztõnkben.
import javattanitok.elosztott.objektumok.*;
/**
 * Az <code>elosztott.idl</code> leírta Labirintus CORBA objektumot
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
 * @see SzereplõKiszolgáló
 * @see HõsKiszolgáló
 * @see SzörnyKiszolgáló
 * @see KincsKiszolgáló
 */
public class LabirintusKiszolgáló
        extends javattanitok.labirintus.TöbbHõsösLabirintus
        implements LabirintusOperations, Runnable {
    /** A labirintus szörnyei, elemei távoli CORBA objektumok. */
    protected List<Szorny> szörnyek;
    /** A labirintus kincsei, elemei távoli CORBA objektumok. */
    protected List<Kincs> kincsek;
    /** A labirintus hõsei, elemei távoli CORBA objektumok. */
    protected List<Hos> hõsök;
    /** A játékbeli idõ mérésére.*/
    private long idõ = 0;
    /** Véletlenszám generátor a szereplõk mozgatásához. */
    protected static java.util.Random véletlenGenerátor =
            new java.util.Random();
    /** A <code>LabirintusKiszolgáló</code> objektum elkészítése. */
    public LabirintusKiszolgáló() {
        // Az õs megfelelõ konstruktorának hívása
        super();
        // A kincseket tartalmazó adatszerkezet létrehozása,
        // elemei majd távoli CORBA objektumok lesznek
        kincsek = new java.util.ArrayList<Kincs>();
        // A kincseket tartalmazó adatszerkezet létrehozása,
        // elemei majd távoli CORBA objektumok lesznek
        szörnyek = new java.util.ArrayList<Szorny>();
        // A kincseket tartalmazó adatszerkezet létrehozása,
        // elemei majd távoli CORBA objektumok lesznek
        hõsök = java.util.Collections.synchronizedList(
                new java.util.ArrayList<Hos>());
        
        // A játékbeli idõ folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
    }
    /**
     * A Hos CORBA objektum az ORB-n keresztül belép a labirintusba.
     *
     * @param hõs a Hos CORBA objektum referenciája.
     * @return String a labirintus állapotát bemutató string.
     */
    public String belepHos(Hos hõs) {
        System.out.println("LABIRINTUS> Belépett egy hõs: " + hõs);
        hõsök.add(hõs);
        szereplõHelyeKezdetben(hõs);
        
        return toString();
        
    }
    /**
     * A Kincs CORBA objektum az ORB-n keresztül belép a labirintusba.
     *
     * @param hõs a Kincs CORBA objektum referenciája.
     * @return String a labirintus állapotát bemutató string.
     */
    public String belepKincs(Kincs kincs) {
        
        System.out.println("LABIRINTUS> Belépett egy kincs.");
        kincsek.add(kincs);
        szereplõHelyeKezdetben(kincs);
        
        return toString();
        
    }
    /**
     * A Szorny CORBA objektum az ORB-n keresztül belép a labirintusba.
     *
     * @param hõs a Szorny CORBA objektum referenciája.
     * @return String a labirintus állapotát bemutató string.
     */
    public String belepSzorny(Szorny szörny) {
        
        System.out.println("LABIRINTUS> Belépett egy szörny.");
        szörnyek.add(szörny);
        szereplõHelyeKezdetben(szörny);
        
        return toString();
        
    }
    /**
     * A szereplõ labirintusbeli kezdõ pozíciójának meghatározása.
     */
    void szereplõHelyeKezdetben(Szereplo szereplõ) {
        // Többször próbálkozunk elhelyezni a szereplõt a labirintusban,
        // számolja, hol tartunk ezekkel a próbálkozásokkal:
        int számláló = 0, oszlop = 0, sor = 0;
        do {
            // itt +2,-2-k, hogy a bal alsó saroktól távol tartsuk
            // a szereplõket, mert majd ezt akarjuk a hõs kezdõ pozíciójának
            oszlop = 2+véletlenGenerátor.nextInt(szélesség-2);
            sor = véletlenGenerátor.nextInt(magasság-2);
            // max. 10-szer próbálkozunk, de ha sikerül nem "falba tenni" a
            // szereplõt, akkor máris kilépünk:
        } while(++számláló<10 && fal(oszlop, sor));
        
        szereplõ.oszlop(oszlop);
        szereplõ.sor(sor);
        
    }
    /** A játék idõbeli fejlõdésének vezérlése. */
    public void run() {
        
        while(true) {
            
            idoegyseg();
            
            System.out.println(this);
            
            synchronized(hõsök) {
                Iterator i = hõsök.iterator();
                
                while(i.hasNext()) {
                    Hos hõs = (Hos)i.next();
                    try {
                        switch(bolyong(hõs)) {
                            
                            case JÁTÉK_MEGY_HÕS_RENDBEN:
                                break;
                            case JÁTÉK_MEGY_MEGHALT_HÕS:
                                break;
                            case JÁTÉK_VÉGE_MEGHALT_HÕS:
                                i.remove();
                                break;
                                
                        }
                        
                    } catch (org.omg.CORBA.SystemException e) {
                        
                        i.remove();
                        System.out.println("LABIRINTUS> " +
                                "Egy hõst eltávolítottam.");
                        
                    }
                }
            }
        }
    }
    /**
     * Az õs megfelelõ metódusának elfedése, az új CORBA szereplõket
     * használva.
     *
     * @see TöbbHõsösLabirintus#bolyong(Hõs hõs)
     * @param hõs aki a labirintusban bolyong.
     * @return int a játék állapotát leíró kód.
     */
    public int bolyong(Hos hõs) {
        
        for(Kincs kincs : kincsek) {
            
            if(kincs.megtalalt(hõs))
                hõs.megtalaltam(kincs);
            
        }
        
        for(Szorny szörny : szörnyek) {
            
            if(szörny.lep(hõs))  {
                
                if(hõs.megettek())
                    return JÁTÉK_VÉGE_MEGHALT_HÕS;
                else
                    return JÁTÉK_MEGY_MEGHALT_HÕS;
            }
            
        }
        
        return JÁTÉK_MEGY_HÕS_RENDBEN;
    }
    /**
     * Megadja, hogy milyen gyorsan telik az idõ a játékban.
     */
    private void idoegyseg() {
        
        ++idõ;
        
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
    /**
     * Madadja, hogy van-e szörny a labirintus adott oszlop,
     * sor pozíciója.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha van.
     */
    boolean vanSzörny(int sor, int oszlop) {
        
        boolean van = false;
        
        for(Szorny szörny: szörnyek)
            if(sor == szörny.sor()
            && oszlop == szörny.oszlop()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * Madadja, hogy van-e megtalálható kincs a labirintus
     * adott oszlop, sor pozíciója.
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
     * Kinyomtatja a labirintus szerkezetét és szereplõit a System.out-ra.
     *
     * @param hõs akit szintén belenyomtat a labirintusba.
     */
    public void nyomtat(Hos hõs) {
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHõs = (i == hõs.sor() && j == hõs.oszlop());
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else if(vanSzörny && vanKincs && vanHõs)
                    System.out.print("|SKH");
                else if(vanSzörny && vanKincs)
                    System.out.print("|SK ");
                else if(vanKincs && vanHõs)
                    System.out.print("|KH ");
                else if(vanSzörny && vanHõs)
                    System.out.print("|SH ");
                else if(vanKincs)
                    System.out.print("|K  ");
                else if(vanHõs)
                    System.out.print("|H  ");
                else if(vanSzörny)
                    System.out.print("|S  ");
                else
                    System.out.print("|   ");
                
            }            
            System.out.println();            
        }
    }
    /**
     * A labirintus sztring reprezentációja.
     *
     * @return String labirintus sztring reprezentációja.
     */
    public String toString() {
        
        return " Idõ:" + idõ
                + " hõs:" + hõsök.size()
                + " kincs:" + kincsek.size()
                + " szörny:" + szörnyek.size();        
    }
}					
