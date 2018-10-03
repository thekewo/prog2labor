/*
 * Korb�sKliens.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * CORBA-s h�l�zati, kliens oldali �letre kelt�s�re ad p�ld�t ez az oszt�ly:
 * a h�s t�volr�l t�rt�n� mozgat�s�t biztos�tja.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see Korb�sLabirintus
 * @see T�voliH�sKiszolg�l�
 */
public class Korb�sKliens {
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
        
        try {
            // Az ORB tulajdons�gainak megad�sa
            java.util.Properties orbTulajdons�gok = new java.util.Properties();
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialHost", "localhost");
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialPort", "2006");
            // Az ORB (a met�dush�v�sok k�zvet�t�j�nek) inicializ�l�sa
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String [])null, 
                    orbTulajdons�gok);
            // A n�vszolg�ltat� gy�ker�nek, mint CORBA objektum
            // referenci�j�nak megszerz�se
            org.omg.CORBA.Object corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt n�vszolg�ltat�Gy�kere
                    = org.omg.CosNaming.
                    NamingContextExtHelper.narrow(corbaObjektum);
            // A TavoliHos szolg�ltat�st ny�jt� CORBA objektum
            // referenci�j�nak megszerz�se
            javattanitok.korbas.TavoliHos t�voliH�s =
                    javattanitok.korbas.TavoliHosHelper.narrow(
                    n�vszolg�ltat�Gy�kere.resolve_str("TavoliHos"));
            
            System.out.println(t�voliH�s.l�pJobbra(h�sN�v));
            
        } catch (Exception e) {            
            e.printStackTrace();            
        }        
    }    
}					
