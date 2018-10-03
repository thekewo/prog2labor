/*
 * Korb�sLabirintus.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * CORBA-s, szerver oldali �letre kelt�s�re ad p�ld�t ez az oszt�ly:
 * a h�s�k t�volr�l t�rt�n� jelentkez�s�t �s mozgat�s�t biztos�tja.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see T�voliH�sKiszolg�l�
 * @see Korb�sKliens
 * @see tavolihos.idl
 */
public class Korb�sLabirintus extends H�l�zatiLabirintus {
    /**
     * A <code>T�voliLabirintus</code> objektum elk�sz�t�se.
     *
     * @param      labirintusF�jlN�v       a labirintust defini�l�, megfelel�
     * szerkezet� sz�veges �llom�ny neve.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� �llom�ny nem
     * a megfelel� szerkezet�
     */
    public Korb�sLabirintus(String labirintusF�jlN�v) throws
            RosszLabirintusKiv�tel {
        // A labirintus elk�sz�t�se �llom�nyb�l
        labirintus = new T�bbH�s�sLabirintus(labirintusF�jlN�v);
        // A h�s�ket tartalmaz� adatszerkezet elk�sz�t�se
        h�s�k = new java.util.Hashtable();
        // A j�t�k val�s�g�nak (vil�g�nak) ind�t�sa:
        new Thread(this).start();
        // A CORBA szerver ind�t�sa
        try{
            // Az ORB tulajdons�gainak megad�sa
            java.util.Properties orbTulajdons�gok = new java.util.Properties();
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialHost", "localhost");
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialPort", "2006");
            // Az ORB (a met�dush�v�sok k�zvet�t�j�nek) inicializ�l�sa
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String [])null,
                    orbTulajdons�gok);
            // A gy�k�r POA CORBA objektum referenci�j�nak megszerz�se
            org.omg.CORBA.Object corbaObjektum =
                    orb.resolve_initial_references("RootPOA");
            // CORBA-s t�pusk�nyszer�t�ssel a megfelel�re
            org.omg.PortableServer.POA gy�k�rPoa =
                    org.omg.PortableServer.POAHelper.narrow(corbaObjektum);
            // A POA kiszolg�l� �llapotba kapcsol�sa:
            gy�k�rPoa.the_POAManager().activate();
            // A kiszolg�l� objektum l�trehoz�sa
            T�voliH�sKiszolg�l� t�voliH�sKiszolg�l� =
                    new T�voliH�sKiszolg�l�(this);
            // CORBA objektum referencia elk�sz�t�se
            corbaObjektum =
                    gy�k�rPoa.servant_to_reference(t�voliH�sKiszolg�l�);
            // CORBA-s t�pusk�nyszer�t�ssel a megfelel�re
            javattanitok.korbas.TavoliHos t�voliH�s =
                    javattanitok.korbas.TavoliHosHelper.narrow(corbaObjektum);
            // A n�vszolg�ltat� gy�ker�nek, mint CORBA objektum
            // referenci�j�nak megszerz�se
            corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt n�vszolg�ltat�Gy�kere
                    = org.omg.CosNaming.NamingContextExtHelper
                    .narrow(corbaObjektum);
            // A kiszolg�l� objektum bejegyz�se a n�vszolg�ltat�ba
            org.omg.CosNaming.NameComponent n�v[] =
                    n�vszolg�ltat�Gy�kere.to_name("TavoliHos");
            n�vszolg�ltat�Gy�kere.rebind(n�v, t�voliH�s);
            // V�rakoz�s a j�t�kosok jelentkez�s�re
            orb.run();
        } catch (Exception e) {
            
            e.printStackTrace();
            System.exit(-1);
            
        }        
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
                    "java javattanitok.Korb�sLabirintus labirintus.txt");
            System.exit(-1);
            
        }
        
        try {
            
            new Korb�sLabirintus(args[0]);
            
        } catch(RosszLabirintusKiv�tel rosszLabirintusKiv�tel) {            
            System.out.println(rosszLabirintusKiv�tel);            
        }
    }
}					
