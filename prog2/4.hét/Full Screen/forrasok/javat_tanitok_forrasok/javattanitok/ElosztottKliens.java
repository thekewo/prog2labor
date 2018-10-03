/*
 * ElosztottKliens.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.elosztott.*;
/**
 * Az elosztott CORBA-s labirintus csomag absztrah�lta elosztott labirintus
 * mikrovil�g�nak egy kliens oldali �letre kelt�s�re ad p�ld�t ez az oszt�ly,
 * mely egyben a szerepl� objektumok visszah�vhat� szervere is.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see ElosztottLabirintus
 * @see Szerepl�Kiszolg�l�
 * @see H�sKiszolg�l�
 * @see Sz�rnyKiszolg�l�
 * @see KincsKiszolg�l�
 */
public class ElosztottKliens implements Runnable {
    org.omg.CORBA.ORB orb; // hogy a run()-b�l el�rj�k �ket
    javattanitok.elosztott.objektumok.Labirintus labirintus;
    /**
     * Elk�sz�ti a t�vol g�pre bejelentkez� h�s, sz�rny vagy
     * kincs visszah�vhat� kliens CORBA objektumot. H�sk�nt
     * val� ind�t�sakor feldolgozza a j�t�kost�l j�v� billenty�
     * parancsokat.
     *
     * @param args els� tagja "h�s" - Hos objektum, "kincs" -
     * Kincs objektum, "sz�rny" - Szorny objektum lesz a kliens,
     * alap�rtelmez�s a "kincs".
     */
    public ElosztottKliens(String[] args) {
        
        String ki = null;
        // ha nem adtuk meg a parancssor-argumentumot,
        // akkor ez az alap�rtelmez�s:
        if(args.length != 1)
            ki = "Kincs";
        else
            ki = args[0];
        
        try {
            // Az ORB tulajdons�gainak megad�sa
            java.util.Properties orbTulajdons�gok = new java.util.Properties();
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialHost",
                    "172.21.0.152");
            orbTulajdons�gok.put("org.omg.CORBA.ORBInitialPort", "2006");
            // Az ORB (a met�dush�v�sok k�zvet�t�j�nek) inicializ�l�sa
            orb = org.omg.CORBA.ORB.init((String [])null,
                    orbTulajdons�gok);
            // A n�vszolg�ltat� gy�ker�nek, mint CORBA objektum
            // referenci�j�nak megszerz�se
            org.omg.CORBA.Object corbaObjektum =
                    orb.resolve_initial_references("NameService");
            org.omg.CosNaming.NamingContextExt n�vszolg�ltat�Gy�kere
                    = org.omg.CosNaming.
                    NamingContextExtHelper.narrow(corbaObjektum);
            // A labirintus CORBA objektum referenci�j�nak megszerz�se
            labirintus =
                    javattanitok.elosztott.objektumok.
                    LabirintusHelper.narrow(n�vszolg�ltat�Gy�kere.
                    resolve_str("ElosztottLabirintus"));
            // A gy�k�r POA CORBA objektum referenci�j�nak megszerz�se
            corbaObjektum = orb.resolve_initial_references("RootPOA");
            // CORBA-s t�pusk�nyszer�t�ssel a megfelel�re
            org.omg.PortableServer.POA gy�k�rPoa =
                    org.omg.PortableServer.POAHelper.narrow(corbaObjektum);
            // A POA kiszolg�l� �llapotba kapcsol�sa:
            gy�k�rPoa.the_POAManager().activate();
            // Mik�nt ind�tott�k ezt a klienst?
            if("h�s".equals(ki)) {
                // Ha a kliens h�sk�nt m�k�dik
                
                // A Hos kiszolg�l� objektum elk�sz�t�se,
                // a deleg�ci�s/POA/Tie modell szerinti lek�pez�ssel
                javattanitok.elosztott.objektumok.HosPOATie tie =
                        new javattanitok.elosztott.objektumok.HosPOATie(
                        new H�sKiszolg�l�(), gy�k�rPoa);
                // A Hos CORBA objektum
                javattanitok.elosztott.objektumok.Hos h�s = tie._this(orb);
                
                System.out.println("H�S> ELk�sz�ltem: " + h�s);
                
                // T�voli met�dus h�v�sa, a t�voli CORBA labirintus
                // objektumnak �tadkuk a Hos CORBA objektum referenci�j�t
                System.out.println(labirintus.belepHos(h�s));
                
                new Thread(this).start();
                
                // CORBA szerverk�nt is �zemel�nk, mert a Hos
                // objektumot a Labirintus objektum majd visszah�vogatja
                orb.run();
                
            } else if("sz�rny".equals(ki)) {
                // Ha a kliens sz�rnyk�nt m�k�dik
                javattanitok.elosztott.objektumok.SzornyPOATie tie =
                        new javattanitok.elosztott.objektumok.SzornyPOATie(
                        new Sz�rnyKiszolg�l�(), gy�k�rPoa);
                javattanitok.elosztott.objektumok.Szorny sz�rny =
                        tie._this(orb);
                
                System.out.println(labirintus.belepSzorny(sz�rny));
                
                orb.run();
                
            } else  {
                // Ha a kliens kincsk�nt m�k�dik
                javattanitok.elosztott.objektumok.KincsPOATie tie =
                        new javattanitok.elosztott.objektumok.KincsPOATie(
                        new KincsKiszolg�l�(), gy�k�rPoa);
                javattanitok.elosztott.objektumok.Kincs kincs =
                        tie._this(orb);
                
                System.out.println(labirintus.belepKincs(kincs));
                
                orb.run();
                
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
    }
    /** A h�s j�t�kost�l j�v� billenty�zet input feldolgoz�sa. */
    public void run() {
        
        try {
            // Feldolgozzuk a j�t�kost�l j�v� billenty� parancsokat
            java.io.BufferedReader konzol =
                    new java.io.BufferedReader(
                    new java.io.InputStreamReader(System.in));
            
            String parancs = null;
            while((parancs = konzol.readLine()) != null) {
                System.out.println(parancs);
                // A Hos CORBA objektum kil�p a labirintusb�l
                if("k".equals(parancs))
                    break;
                // Tov�bbi l�p�s parancsok itt implemet�lva: f�l, le stb. ...
            }
        } catch(java.io.IOException e) {
            System.out.println(e);
        }
        orb.shutdown(false);
        System.exit(0);
    }
    /** Az elosztott labirintus klienseinek ind�t�sa.
     *
     * @param args els� tagja "h�s" - Hos objektum, "kincs" -
     * Kincs objektum, "sz�rny" - Szorny objektum lesz a kliens,
     * alap�rtelmez�s a "kincs".
     */
    public static void main(String[] args) {
        
        new ElosztottKliens(args);
        
    }
}					
