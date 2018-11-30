/*
 * H�l�zatiLabirintus.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * TCP/IP-s h�l�zati �letre kelt�s�re ad p�ld�t ez az oszt�ly.
 * Tesztel�se p�ld�ul a telnet TCP klienssel:
 * <pre>
 * telnet niobe 2006
 * A h�s neve?
 * Matyi
 * Parancsok: l = le, f = f�l, j = jobbra, b = balra k = kil�p
 *            sima ENTER = megmutatja a labirintust
 *
 * --- Labirintusszerinti id�: 13. pillanat -------
 * --- �sszes h�s�k sz�ma: 1
 * --- �letek sz�ma: 5
 * --- Gy�jt�tt �rt�k: 0
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
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.Korb�sLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see LabirintusKiszolg�l�Sz�l
 */
public class H�l�zatiLabirintus implements Runnable {
    /** A j�t�k aktu�lis labirintusa, minden h�l�zati h�s ebben mozog. */
    T�bbH�s�sLabirintus labirintus;
    /** A h�s�k. */
    java.util.Hashtable h�s�k;
    /** Melyik porton megy a j�t�k. */
    private static final int LABIRINTUS_PORT = 2006;
    /** A j�t�kbeli id� m�r�s�re.*/
    private long id� = 0;
    /** Jelzi a j�t�k v�g�t, ezut�n a j�t�k �lapota m�r nem v�ltozik. */
    private boolean j�t�kV�ge = false;
    /**
     * Argumentum n�lk�li konstruktor, gyerekek implicit super()-�hez.
     */
    public H�l�zatiLabirintus(){}
    /**
     * A <code>H�l�zatiLabirintus</code> objektum elk�sz�t�se.
     *
     * @param      labirintusF�jlN�v       a labirintust defini�l�, megfelel� 
     * szerkezet� sz�veges �llom�ny neve.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� �llom�ny 
     * nem a megfelel� szerkezet�
     */
    public H�l�zatiLabirintus(String labirintusF�jlN�v) throws 
            RosszLabirintusKiv�tel {
        // A labirintus elk�sz�t�se �llom�nyb�l
        labirintus = new T�bbH�s�sLabirintus(labirintusF�jlN�v);
        // A h�s elk�sz�t�se �s a kezd� poz�ci�j�nak be�ll�t�sa
        h�s�k = new java.util.Hashtable();
        // A j�t�kbeli id� foly�s�t biztos�t� sz�l elk�sz�t�se �s ind�t�sa
        new Thread(this).start();
        // A TCP szerver ind�t�sa
        try {
            java.net.ServerSocket serverSocket =
                    new java.net.ServerSocket(LABIRINTUS_PORT);
            while(true) {
                // V�rakoz�s a j�t�kosok jelentkez�s�re
                java.net.Socket socket = serverSocket.accept();
                // akiket k�l�n sz�lban szolg�lunk ki
                new LabirintusKiszolg�l�Sz�l(socket, this);
            }
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * A h�l�zaton kereszt�l jelentkez� h�s elk�sz�t�se.
     *
     * @param   n�v     a h�s neve (= "hoszt IP : n�v").
     * @return H�s      a n�vhez tartoz�, esetleg �jonan l�trehozott h�s.
     */
    public H�s h�s(String n�v) {
        
        // Ha m�r l�tez� h�s jelentkezett be �jra a j�t�kba
        if(h�s�k.containsKey(n�v))
            return (H�s)h�s�k.get(n�v);
        // Vagy �j j�t�kos j�n
        else {
            // aki m�g nincs a h�s�k k�z�tt
            // akkor �j h�sk�nt l�trehozzuk
            H�s h�s = new H�s(labirintus);
            // A h�s kezd� poz�ci�ja
            h�s.sor(9);
            h�s.oszlop(0);
            // Felv�tele a h�s�k k�z�
            h�s�k.put(n�v, h�s);
            
            return h�s;
        }        
    }
    /**
     * A valamikor h�l�zaton kereszt�l jelentkez� h�s t�rl�se.
     *
     * @param   n�v     a h�s neve (= "hoszt IP : n�v").
     */
    public void h�sMeghalt(String n�v) {        
            // T�rl�s a h�s�k k�z�l
            h�s�k.remove(n�v);
    }
    /**
     * A h�s�k sz�ma.
     *
     * @return int a h�s�k sz�ma.
     */
    public int h�s�kSz�ma() {
        
        return h�s�k.size();
        
    }
    /**
     * A labirintus j�t�k vil�g�nak ideje.
     *
     * @return long labirintus j�t�k vil�g�nak ideje.
     */
    public long id�() {
        
        return id�;
        
    }
    /**
     * A j�t�k aktu�lis labirintusa, minden h�l�zati h�s ebben mozog.
     *
     * @return Labirintus a labirintus.
     */
    public Labirintus labirintus() {
        
        return labirintus;
        
    }
    /** A j�t�k id�beli fejl�d�s�nek vez�rl�se. */
    public void run() {
        
        while(!j�t�kV�ge) {
            
            idoegyseg();
            
            System.out.println("H�s�k sz�ma: " + h�s�k.size());
            java.util.Enumeration e = h�s�k.elements();
            while(e.hasMoreElements()) {
                
                H�s h�s = (H�s)e.nextElement();
                
                    switch(labirintus.bolyong(h�s)) {
                        
                        case T�bbH�s�sLabirintus.J�T�K_MEGY_H�S_RENDBEN:
                            break;
                        case Labirintus.J�T�K_MEGY_MEGHALT_H�S:
                            h�s.sor(9);
                            h�s.oszlop(0);
                            System.out.println("Megettek a(z) " + id� 
                                    + ". l�p�sben!");
                            break;
                        case Labirintus.J�T�K_V�GE_MINDEN_KINCS_MEGVAN:
                            System.out.println("Megvan minden kincs a(z) " 
                                    + id� + ". l�p�sben!");
                            j�t�kV�ge = true;
                            break;
                        case Labirintus.J�T�K_V�GE_MEGHALT_H�S:
                            System.out.println("Minden �letem elfogyott a(z) " 
                                    + id� + ". l�p�sben!");
                            // Ebben a v�ltozatban t�bb h�s bolyong, 
                            // �gy imm�r egyik�k hal�la nem jelenti a 
                            // j�t�k v�g�t is:
                            // j�t�kV�ge = true; 
                            break;                            
                    }
            }                        
        }                
    }
    /** Megadja, hogy milyen gyorsan telik az id� a j�t�kban. */
    private void idoegyseg() {
        
        ++id�;        
        try {            
            Thread.sleep(1000);            
        } catch(InterruptedException e) {}        
    }
    /**
     * �tveszi a j�t�k ind�t�s�hoz sz�ks�ges param�tereket, majd
     * elind�tja a j�t�k vil�g�nak m�k�d�s�t.
     *
     * @param args a labirintus terv�t tartalmaz� �llom�ny neve az els� 
     * parancssor-argumentum.
     */
    public static void main(String[] args) {
        
        if(args.length != 1) {
            
            System.out.println("Ind�t�s: " +
                    "java javattanitok.H�l�zatiLabirintus labirintus.txt");
            System.exit(-1);
        }
        
        try {
            
            new H�l�zatiLabirintus(args[0]);
            
        } catch(RosszLabirintusKiv�tel rosszLabirintusKiv�tel) {
            
            System.out.println(rosszLabirintusKiv�tel);
            
        }
    }
}                
