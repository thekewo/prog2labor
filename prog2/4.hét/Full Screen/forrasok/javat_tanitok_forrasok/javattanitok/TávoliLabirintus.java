/*
 * T�voliLabirintus.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * Java RMI-s h�l�zati, szerver oldali �letre kelt�s�re ad p�ld�t 
 * ez az oszt�ly: a h�s�k t�volr�l t�rt�n� jelentkez�s�t �s 
 * mozgat�s�t biztos�tja.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.Korb�sLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see T�voliKliens
 * @see T�voliH�s�thet�
 */
public class T�voliLabirintus extends H�l�zatiLabirintus
        implements T�voliH�s�thet� {
    /**
     * A <code>T�voliLabirintus</code> objektum elk�sz�t�se.
     *
     * @param      labirintusF�jlN�v       a labirintust defini�l�, megfelel� 
     * szerkezet� sz�veges �llom�ny neve.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� �llom�ny 
     * nem a megfelel� szerkezet�
     */
    public T�voliLabirintus(String labirintusF�jlN�v) throws 
            RosszLabirintusKiv�tel {
        // A labirintus elk�sz�t�se �llom�nyb�l
        labirintus = new T�bbH�s�sLabirintus(labirintusF�jlN�v);
        // A h�s elk�sz�t�se �s a kezd� poz�ci�j�nak be�ll�t�sa
        h�s�k = new java.util.Hashtable();
        // A j�t�kbeli id� foly�s�t biztos�t� sz�l elk�sz�t�se �s ind�t�sa
        new Thread(this).start();
        // Az RMI szerver ind�t�sa
        try {
            // A t�voli objektum 
            T�voliH�s�thet� t�voliH�s�thet� = (T�voliH�s�thet�)
            java.rmi.server.UnicastRemoteObject.exportObject(this, 0);
            // bejegyz�se a n�vszolg�ltat�ba        
            java.rmi.registry.Registry registry =
                    java.rmi.registry.LocateRegistry.getRegistry();
            registry.bind("TavoliLabirintus", t�voliH�s�thet�);
            
        } catch (java.rmi.AlreadyBoundException be) {
            be.printStackTrace();
            System.exit(-1);            
        } catch (java.rmi.RemoteException re) {
            re.printStackTrace();
            System.out.println("Fut az rmiregistry?");
            System.exit(-1);            
        }        
    }
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve. 
     * @return String a labirintus �llapot�t bemutat� string. 
     */
    public String l�pLe(String h�sN�v) throws java.rmi.RemoteException {
        H�s h�s = null;
        try {
            // a h�s neve (= "hoszt IP : n�v")
            h�s = h�s(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + h�sN�v);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }        
        
        h�s.l�pLe();
        
        return labirintus.kinyomtat(h�s);
    }
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve. 
     * @return String a labirintus �llapot�t bemutat� string. 
     */
    public String l�pF�l(String h�sN�v) throws java.rmi.RemoteException {
        H�s h�s = null;
        try {
            
            h�s = h�s(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + h�sN�v);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }        
        
        h�s.l�pF�l();
        
        return labirintus.kinyomtat(h�s);
        
    }
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve. 
     * @return String a labirintus �llapot�t bemutat� string. 
     */    
    public String l�pJobbra(String h�sN�v) throws java.rmi.RemoteException {
        H�s h�s = null;
        try {

            h�s = h�s(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + h�sN�v);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }        
        
        h�s.l�pJobbra();
        
        return labirintus.kinyomtat(h�s);
    }
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve. 
     * @return String a labirintus �llapot�t bemutat� string. 
     */    
    public String l�pBalra(String h�sN�v) throws java.rmi.RemoteException {
        H�s h�s = null;
        try {

            h�s = h�s(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + h�sN�v);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }   
        
        h�s.l�pBalra();
        
        return labirintus.kinyomtat(h�s);        
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
                    "java javattanitok.T�voliLabirintus labirintus.txt");
            System.exit(-1);
            
        }
        
        try {
            
            new T�voliLabirintus(args[0]);
            
        } catch(RosszLabirintusKiv�tel rosszLabirintusKiv�tel) {
            
            System.out.println(rosszLabirintusKiv�tel);
            
        }
    }
}                
