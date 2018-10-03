/*
 * HálózatiLabirintus.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * TCP/IP-s hálózati életre keltésére ad példát ez az osztály.
 * Tesztelése például a telnet TCP klienssel:
 * <pre>
 * telnet niobe 2006
 * A hõs neve?
 * Matyi
 * Parancsok: l = le, f = föl, j = jobbra, b = balra k = kilép
 *            sima ENTER = megmutatja a labirintust
 *
 * --- Labirintusszerinti idõ: 13. pillanat -------
 * --- Összes hõsök száma: 1
 * --- Életek száma: 5
 * --- Gyûjtött érték: 0
 * --- A labirintus: (13. pillanat) -------
 * |   |   |   |FAL|   |FAL|   |FAL|FAL|FAL
 * |   |   |   |   |K  |   |   |   |   |
 * |FAL|   |FAL|   |FAL|   |FAL|   |FAL|
 * |   |   |   |K  |FAL|   |FAL|   |   |S
 * |   |FAL|FAL|S  |   |   |FAL|FAL|   |FAL
 * |   |   |   |   |FAL|K  |   |   |   |
 * |   |FAL|   |   |   |FAL|K  |FAL|FAL|
 * |   |   |K  |FAL|   |FAL|S  |FAL|   |
 * |   |FAL|   |   |   |   |   |   |   |FAL
 * |H  |   |   |   |FAL|   |   |   |FAL|FAL
 * </pre>
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.TávoliLabirintus
 * @see javattanitok.KorbásLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see LabirintusKiszolgálóSzál
 */
public class HálózatiLabirintus implements Runnable {
    /** A játék aktuális labirintusa, minden hálózati hõs ebben mozog. */
    TöbbHõsösLabirintus labirintus;
    /** A hõsök. */
    java.util.Hashtable hõsök;
    /** Melyik porton megy a játék. */
    private static final int LABIRINTUS_PORT = 2006;
    /** A játékbeli idõ mérésére.*/
    private long idõ = 0;
    /** Jelzi a játék végét, ezután a játék álapota már nem változik. */
    private boolean játékVége = false;
    /**
     * Argumentum nélküli konstruktor, gyerekek implicit super()-éhez.
     */
    public HálózatiLabirintus(){}
    /**
     * A <code>HálózatiLabirintus</code> objektum elkészítése.
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelõ 
     * szerkezetû szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány 
     * nem a megfelelõ szerkezetû
     */
    public HálózatiLabirintus(String labirintusFájlNév) throws 
            RosszLabirintusKivétel {
        // A labirintus elkészítése állományból
        labirintus = new TöbbHõsösLabirintus(labirintusFájlNév);
        // A hõs elkészítése és a kezdõ pozíciójának beállítása
        hõsök = new java.util.Hashtable();
        // A játékbeli idõ folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
        // A TCP szerver indítása
        try {
            java.net.ServerSocket serverSocket =
                    new java.net.ServerSocket(LABIRINTUS_PORT);
            while(true) {
                // Várakozás a játékosok jelentkezésére
                java.net.Socket socket = serverSocket.accept();
                // akiket külön szálban szolgálunk ki
                new LabirintusKiszolgálóSzál(socket, this);
            }
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * A hálózaton keresztül jelentkezõ hõs elkészítése.
     *
     * @param   név     a hõs neve (= "hoszt IP : név").
     * @return Hõs      a névhez tartozó, esetleg újonan létrehozott hõs.
     */
    public Hõs hõs(String név) {
        
        // Ha már létezõ hõs jelentkezett be újra a játékba
        if(hõsök.containsKey(név))
            return (Hõs)hõsök.get(név);
        // Vagy új játékos jön
        else {
            // aki még nincs a hõsök között
            // akkor új hõsként létrehozzuk
            Hõs hõs = new Hõs(labirintus);
            // A hõs kezdõ pozíciója
            hõs.sor(9);
            hõs.oszlop(0);
            // Felvétele a hõsök közé
            hõsök.put(név, hõs);
            
            return hõs;
        }        
    }
    /**
     * A valamikor hálózaton keresztül jelentkezõ hõs törlése.
     *
     * @param   név     a hõs neve (= "hoszt IP : név").
     */
    public void hõsMeghalt(String név) {        
            // Törlés a hõsök közül
            hõsök.remove(név);
    }
    /**
     * A hõsök száma.
     *
     * @return int a hõsök száma.
     */
    public int hõsökSzáma() {
        
        return hõsök.size();
        
    }
    /**
     * A labirintus játék világának ideje.
     *
     * @return long labirintus játék világának ideje.
     */
    public long idõ() {
        
        return idõ;
        
    }
    /**
     * A játék aktuális labirintusa, minden hálózati hõs ebben mozog.
     *
     * @return Labirintus a labirintus.
     */
    public Labirintus labirintus() {
        
        return labirintus;
        
    }
    /** A játék idõbeli fejlõdésének vezérlése. */
    public void run() {
        
        while(!játékVége) {
            
            idoegyseg();
            
            System.out.println("Hõsök száma: " + hõsök.size());
            java.util.Enumeration e = hõsök.elements();
            while(e.hasMoreElements()) {
                
                Hõs hõs = (Hõs)e.nextElement();
                
                    switch(labirintus.bolyong(hõs)) {
                        
                        case TöbbHõsösLabirintus.JÁTÉK_MEGY_HÕS_RENDBEN:
                            break;
                        case Labirintus.JÁTÉK_MEGY_MEGHALT_HÕS:
                            hõs.sor(9);
                            hõs.oszlop(0);
                            System.out.println("Megettek a(z) " + idõ 
                                    + ". lépésben!");
                            break;
                        case Labirintus.JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN:
                            System.out.println("Megvan minden kincs a(z) " 
                                    + idõ + ". lépésben!");
                            játékVége = true;
                            break;
                        case Labirintus.JÁTÉK_VÉGE_MEGHALT_HÕS:
                            System.out.println("Minden életem elfogyott a(z) " 
                                    + idõ + ". lépésben!");
                            // Ebben a változatban több hõs bolyong, 
                            // így immár egyikük halála nem jelenti a 
                            // játék végét is:
                            // játékVége = true; 
                            break;                            
                    }
            }                        
        }                
    }
    /** Megadja, hogy milyen gyorsan telik az idõ a játékban. */
    private void idoegyseg() {
        
        ++idõ;        
        try {            
            Thread.sleep(1000);            
        } catch(InterruptedException e) {}        
    }
    /**
     * Átveszi a játék indításához szükséges paramétereket, majd
     * elindítja a játék világának mûködését.
     *
     * @param args a labirintus tervét tartalmazó állomány neve az elsõ 
     * parancssor-argumentum.
     */
    public static void main(String[] args) {
        
        if(args.length != 1) {
            
            System.out.println("Indítás: " +
                    "java javattanitok.HálózatiLabirintus labirintus.txt");
            System.exit(-1);
        }
        
        try {
            
            new HálózatiLabirintus(args[0]);
            
        } catch(RosszLabirintusKivétel rosszLabirintusKivétel) {
            
            System.out.println(rosszLabirintusKivétel);
            
        }
    }
}                
