/*
 * LabirintusKiszolgálóSzál.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának
 * TCP/IP-s hálózati életre keltését bemutató
 * <code>javattanitok.HálózatiLabirintus</code> osztály hálózati
 * kiszolgálását végzõ szál. A kommunikációs socket kapcsolattól
 * elkéri a kimeneti és bemeneti csatornát, majd a játékos inputját
 * átvéve végtehajtja a hõs mozgatását.
 *
 * Egy pillanatfelvétel a kiszolgálásról:
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
 * @see javattanitok.HálózatiLabirintus
 */
public class LabirintusKiszolgálóSzál implements Runnable {
    /** TCP-s kommunikációs kapcsolat a játékossal. */
    java.net.Socket socket;
    HálózatiLabirintus hálózatiLabirintus;
    /**
     * A <code>LabirintusKiszolgálóSzál</code> objektum elkészítése.
     *
     * @param   socket              TCP socket kapcsolat a játékossal.
     * @param   hálózatiLabirintus  A labirintust tartalmazó TCP szerver
     * <code>javattanitok.HálózatiLabirintus</code> osztály
     */
    public LabirintusKiszolgálóSzál(java.net.Socket socket, 
            HálózatiLabirintus hálózatiLabirintus) {
        this.socket = socket;
        this.hálózatiLabirintus = hálózatiLabirintus;
        new Thread(this).start();
    }
    /** A jelentkezõ játékosokat párhuzamosan kiszolgáló szál. */
    public void run() {
        try {
            // A socket kapcsolat feletti bejövõ csatorna elkérése
            java.io.BufferedReader bejövõCsatorna =
                    new java.io.BufferedReader(
                    new java.io.InputStreamReader(socket.
                    getInputStream()));
            // A socket kapcsolat feletti kimenõ csatorna elkérése
            java.io.PrintWriter kimenõCsatorna =
                    new java.io.PrintWriter(socket.getOutputStream());
            // A hõs nevének beolvasása
            kimenõCsatorna.println("A hõs neve?");
            kimenõCsatorna.flush();
            String játékostól = bejövõCsatorna.readLine();
            // Vagy új vagy régi a hõs, a hõs neve = "hoszt IP : név"
            String hõsNév = socket.getInetAddress().getHostAddress() +
                    " : " + játékostól;
            Hõs hõs = hálózatiLabirintus.hõs(hõsNév);
            // Informáljuk a játékost a játék használatáról
            kimenõCsatorna.println("Parancsok: l = le, f = föl, " +
                    "j = jobbra, b = balra k = kilép");
            kimenõCsatorna.println("           sima ENTER = " +
                    "megmutatja a labirintust");
            kimenõCsatorna.flush();
            
            játékostól = bejövõCsatorna.readLine();
            
            while(játékostól != null) {
                // A játékostól érkezõ parancsok feldolgozása
                if("l".equals(játékostól))
                    hõs.lépLe();
                else if("f".equals(játékostól))
                    hõs.lépFöl();
                else if("j".equals(játékostól))
                    hõs.lépJobbra();
                else if("b".equals(játékostól))
                    hõs.lépBalra();
                else if("k".equals(játékostól))
                    break;
                
                kimenõCsatorna.println("--- Labirintusszerinti idõ: "
                        + hálózatiLabirintus.idõ() + ". pillanat -------");
                kimenõCsatorna.println("--- Összes hõsök száma: " 
                        + hálózatiLabirintus.hõsökSzáma());
                kimenõCsatorna.println("--- Életek száma: " + hõs.életek());
                kimenõCsatorna.println("--- Gyûjtött érték: " + hõs.pontszám());
                kimenõCsatorna.println("--- A labirintus: (" 
                        + hálózatiLabirintus.idõ()+". pillanat) -------");
                // Megmutatjuk a labirintus aktuális állapotát
                hálózatiLabirintus.labirintus().nyomtat(hõs, kimenõCsatorna);
                kimenõCsatorna.flush();
                if(hõs.életek() <= 0) {
                    hálózatiLabirintus.hõsMeghalt(hõsNév);
                    break;
                }
                játékostól = bejövõCsatorna.readLine();                
            }
            
            socket.close();
            
        } catch(java.io.IOException e) {
            
            e.printStackTrace();
            
        } finally {
            
            if(socket != null) {
                
                try{
                    socket.close();
                } catch(Exception e) {}                
            }
            
        }        
    }    
}               
