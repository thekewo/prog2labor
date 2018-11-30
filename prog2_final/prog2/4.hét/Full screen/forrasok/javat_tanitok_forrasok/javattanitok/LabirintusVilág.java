/*
 * LabirintusVil�g.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * "tiszta" �letre kelt�s�re ad p�ld�t ez az oszt�ly. Ennek megfelel�en
 * csak egyszer� karakteres megjelen�t�st biztos�t. F� feladata a
 * kialak�tott labirintus OO mikrovil�gunk API interf�sze haszn�lat�nak
 * bemutat�sa. Tov�bb� az egyszer�s�g megtart�sa miatt ebben a p�ld�ban
 * m�g nem vesz �t adatokat a j�t�kost�l a program.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.Korb�sLabirintus
 * @see javattanitok.ElosztottLabirintus
 */
public class LabirintusVil�g implements Runnable {
    /** A labirintus. */
    protected Labirintus labirintus;
    /** A h�s. */
    protected H�s h�s;
    /** A j�t�kbeli id� m�r�s�re.*/
    private long id� = 0;
    public LabirintusVil�g() {
    }
    /**
     * A <code>LabirintusVil�g</code> objektum elk�sz�t�se.
     *
     * @param labirintusF�jlN�v a labirintust defini�l�, megfelel�
     * szerkezet� sz�veges �llom�ny neve.
     * @exception RosszLabirintusKiv�tel ha a labirintust defini�l� �llom�ny nem
     * a megfelel� szerkezet�
     */
    public LabirintusVil�g(String labirintusF�jlN�v)
    throws RosszLabirintusKiv�tel {
        
        // A labirintus elk�sz�t�se �llom�nyb�l
        labirintus = new Labirintus(labirintusF�jlN�v);
        
        // A h�s elk�sz�t�se �s a kezd� poz�ci�j�nak be�ll�t�sa
        h�s = new H�s(labirintus);
        h�s.sor(9);
        h�s.oszlop(0);
        
        // A j�t�kbeli id� foly�s�t biztos�t� sz�l elk�sz�t�se �s ind�t�sa
        new Thread(this).start();
        
    }
    /**
     * A j�t�k id�beli fejl�d�s�nek vez�rl�se. A labirintus mikrovil�g�nak
     * jelen oszt�lybeli �letre kelt�s�ben max. 10 id�pillanatig j�tszunk,
     * mialatt a h�s igyekszik mindig jobbra l�pni.
     */
    public void run() {
        
        labirintus.nyomtat();
        
        boolean j�t�kV�ge = false;
        
        while(!j�t�kV�ge) {
            
            idoegyseg();
            
            if(id�<10)
                h�s.l�pJobbra();
            else
                break;
            
            switch(labirintus.bolyong(h�s)) {
                
                case Labirintus.J�T�K_MEGY_H�S_RENDBEN:
                    break;
                case Labirintus.J�T�K_MEGY_MEGHALT_H�S:
                    h�s.sor(9);
                    h�s.oszlop(0);
                    System.out.println("Megettek a(z) " + id� + ". l�p�sben!");
                    break;
                case Labirintus.J�T�K_V�GE_MINDEN_KINCS_MEGVAN:
                    System.out.println("Megvan minden kincs a(z) " + id�
                            + ". l�p�sben!");
                    j�t�kV�ge = true;
                    break;
                case Labirintus.J�T�K_V�GE_MEGHALT_H�S:
                    System.out.println("Minden �letem elfogyott a(z) " + id�
                            + ". l�p�sben!");
                    j�t�kV�ge = true;
                    break;
                    
            }
            
            System.out.println("A labirintus a(z) " + id� + ". l�p�sben:");
            labirintus.nyomtat(h�s);
        }
        
        System.out.println("Megtal�lt �rt�kek: " + h�s.pontsz�m());
        System.out.println("J�t�kid�: " + id� + " l�p�s");
        System.out.println("H�nyszor ettek meg: "
                + (H�s.�LETEK_SZ�MA - h�s.�letek()));
        
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
            
            System.out.println("Ind�t�s: java LabirintusVil�g labirintus.txt");
            System.exit(-1);
        }
        
        try {
            
            new LabirintusVil�g(args[0]);
            
        } catch(RosszLabirintusKiv�tel rosszLabirintusKiv�tel) {
            
            System.out.println(rosszLabirintusKiv�tel);
            
        }
    }
}
