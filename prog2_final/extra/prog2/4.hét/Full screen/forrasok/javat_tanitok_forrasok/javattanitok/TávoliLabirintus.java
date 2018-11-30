/*
 * TávoliLabirintus.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * Java RMI-s hálózati, szerver oldali életre keltésére ad példát 
 * ez az osztály: a hõsök távolról történõ jelentkezését és 
 * mozgatását biztosítja.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.KorbásLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see TávoliKliens
 * @see TávoliHõsíthetõ
 */
public class TávoliLabirintus extends HálózatiLabirintus
        implements TávoliHõsíthetõ {
    /**
     * A <code>TávoliLabirintus</code> objektum elkészítése.
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelõ 
     * szerkezetû szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány 
     * nem a megfelelõ szerkezetû
     */
    public TávoliLabirintus(String labirintusFájlNév) throws 
            RosszLabirintusKivétel {
        // A labirintus elkészítése állományból
        labirintus = new TöbbHõsösLabirintus(labirintusFájlNév);
        // A hõs elkészítése és a kezdõ pozíciójának beállítása
        hõsök = new java.util.Hashtable();
        // A játékbeli idõ folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
        // Az RMI szerver indítása
        try {
            // A távoli objektum 
            TávoliHõsíthetõ távoliHõsíthetõ = (TávoliHõsíthetõ)
            java.rmi.server.UnicastRemoteObject.exportObject(this, 0);
            // bejegyzése a névszolgáltatóba        
            java.rmi.registry.Registry registry =
                    java.rmi.registry.LocateRegistry.getRegistry();
            registry.bind("TavoliLabirintus", távoliHõsíthetõ);
            
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
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve. 
     * @return String a labirintus állapotát bemutató string. 
     */
    public String lépLe(String hõsNév) throws java.rmi.RemoteException {
        Hõs hõs = null;
        try {
            // a hõs neve (= "hoszt IP : név")
            hõs = hõs(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + hõsNév);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }        
        
        hõs.lépLe();
        
        return labirintus.kinyomtat(hõs);
    }
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve. 
     * @return String a labirintus állapotát bemutató string. 
     */
    public String lépFöl(String hõsNév) throws java.rmi.RemoteException {
        Hõs hõs = null;
        try {
            
            hõs = hõs(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + hõsNév);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }        
        
        hõs.lépFöl();
        
        return labirintus.kinyomtat(hõs);
        
    }
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve. 
     * @return String a labirintus állapotát bemutató string. 
     */    
    public String lépJobbra(String hõsNév) throws java.rmi.RemoteException {
        Hõs hõs = null;
        try {

            hõs = hõs(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + hõsNév);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }        
        
        hõs.lépJobbra();
        
        return labirintus.kinyomtat(hõs);
    }
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve. 
     * @return String a labirintus állapotát bemutató string. 
     */    
    public String lépBalra(String hõsNév) throws java.rmi.RemoteException {
        Hõs hõs = null;
        try {

            hõs = hõs(java.rmi.server.RemoteServer.getClientHost() +
                    " : " + hõsNév);
            
        } catch(java.rmi.server.ServerNotActiveException e) {
            e.printStackTrace();
        }   
        
        hõs.lépBalra();
        
        return labirintus.kinyomtat(hõs);        
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
                    "java javattanitok.TávoliLabirintus labirintus.txt");
            System.exit(-1);
            
        }
        
        try {
            
            new TávoliLabirintus(args[0]);
            
        } catch(RosszLabirintusKivétel rosszLabirintusKivétel) {
            
            System.out.println(rosszLabirintusKivétel);
            
        }
    }
}                
