/*
 * T�voliKliens.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * Java RMI-s h�l�zati, kliens oldali �letre kelt�s�re ad p�ld�t 
 * ez az oszt�ly: a h�s t�volr�l t�rt�n� mozgat�s�t biztos�tja.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see T�voliLabirintus
 * @see T�voliH�s�thet�
 */
public class T�voliKliens {
    /**
     * Elind�tja a t�volr�l jelentkez� h�s klienst.
     *
     * @param args a h�s neve.
     */
    public static void main(String[] args) {
        
        String h�sN�v = null;
        // ha nem adtuk meg a parancssor-argumentumot, 
        // akkor ez az alap�rtelmez�s:
        if(args.length != 1)
            h�sN�v = "Matyi";
        else
            h�sN�v = args[0];
        // Megszerezz�k a t�voli labirintus (T�voliH�s�thet�) referenci�j�t
        try {
            java.rmi.registry.Registry registry =
                    java.rmi.registry.LocateRegistry.getRegistry();
            T�voliH�s�thet� t�voliH�s�thet� = 
                    (T�voliH�s�thet�) registry.lookup("TavoliLabirintus");
            // �s jobbra mozgatjuk a labirintusban a h�s�nket 
            System.out.println(t�voliH�s�thet�.l�pJobbra(h�sN�v));
            
        } catch (java.rmi.NotBoundException be) {
            be.printStackTrace();
        } catch (java.rmi.RemoteException re) {
            re.printStackTrace();
        }
    }    
}               
