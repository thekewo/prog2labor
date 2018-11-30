/*
 * ElosztottLabirintus.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.elosztott.*;
/**
 * Az elosztott CORBA-s labirintus csomag absztrah�lta elosztott labirintus
 * mikrovil�g�nak egy szerver oldali (deleg�ci�s POA/Tie lek�pez�ses) �letre
 * kelt�s�re ad p�ld�t ez az oszt�ly.
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
 * @see ElosztottKliens
 * @see LabirintusKiszolg�l�
 * @see elosztott.idl
 */
public class ElosztottLabirintus {
    /** A <code>ElosztottLabirintus</code> objektum elk�sz�t�se. */
    public ElosztottLabirintus() {
        // A CORBA szerver ind�t�sa
        try{
            // Az ORB tulajdons�gainak megad�sa
            java.util.Properties orbTulajdons�gok =
                    new java.util.Properties();
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialPort", "2006");
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialHost", "localhost");
            // Az ORB (a met�dush�v�sok k�zvet�t�j�nek) inicializ�l�sa
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init((String [])null,
                    orbTulajdons�gok);
            // A gy�k�r POA CORBA objektum referenci�j�nak megszerz�se
            org.omg.CORBA.Object corbaObjektum = orb.
                    resolve_initial_references("RootPOA");
            // CORBA-s t�pusk�nyszer�t�ssel a megfelel�re
            org.omg.PortableServer.POA gy�k�rPoa =
                    org.omg.PortableServer.POAHelper.narrow(corbaObjektum);
            // A POA kiszolg�l� �llapotba kapcsol�sa:
            gy�k�rPoa.the_POAManager().activate();
            // A Labirintus kiszolg�l� objektum elk�sz�t�se,
            // a deleg�ci�s/POA/Tie modell szerinti lek�pez�ssel
            javattanitok.elosztott.objektumok.LabirintusPOATie tie =
                    new javattanitok.elosztott.objektumok.LabirintusPOATie(
                    new LabirintusKiszolg�l�(), gy�k�rPoa);
            // A Labirintus CORBA objektum
            javattanitok.elosztott.objektumok.Labirintus labirintus =
                    tie._this(orb);
            // A n�vszolg�ltat� gy�ker�nek, mint CORBA objektum
            // referenci�j�nak megszerz�se
            corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt n�vszolg�ltat�Gy�kere
                    = org.omg.CosNaming.
                    NamingContextExtHelper.narrow(corbaObjektum);
            // A kiszolg�l� objektum bejegyz�se a n�vszolg�ltat�ba
            org.omg.CosNaming.NameComponent n�v[] =
                    n�vszolg�ltat�Gy�kere.to_name("ElosztottLabirintus");
            n�vszolg�ltat�Gy�kere.rebind(n�v, labirintus);
            // V�rakoz�s a szerepl�k (h�s�k, kincsek, sz�rnyek) jelentkez�s�re
            orb.run();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.exit(-1);
            
        }
    }
    /**
     * Elind�tja az elosztott labirintust vil�g�nak labirintus r�sz�t.
     *
     * @param args nincsenek parancssor-argumentumok.
     */
    public static void main(String[] args) {
        
        new ElosztottLabirintus();
        
    }
}					
