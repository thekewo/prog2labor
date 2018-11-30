/*
 * LabirintusKiszolg�l�Sz�l.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak
 * TCP/IP-s h�l�zati �letre kelt�s�t bemutat�
 * <code>javattanitok.H�l�zatiLabirintus</code> oszt�ly h�l�zati
 * kiszolg�l�s�t v�gz� sz�l. A kommunik�ci�s socket kapcsolatt�l
 * elk�ri a kimeneti �s bemeneti csatorn�t, majd a j�t�kos inputj�t
 * �tv�ve v�gtehajtja a h�s mozgat�s�t.
 *
 * Egy pillanatfelv�tel a kiszolg�l�sr�l:
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
 * @see javattanitok.H�l�zatiLabirintus
 */
public class LabirintusKiszolg�l�Sz�l implements Runnable {
    /** TCP-s kommunik�ci�s kapcsolat a j�t�kossal. */
    java.net.Socket socket;
    H�l�zatiLabirintus h�l�zatiLabirintus;
    /**
     * A <code>LabirintusKiszolg�l�Sz�l</code> objektum elk�sz�t�se.
     *
     * @param   socket              TCP socket kapcsolat a j�t�kossal.
     * @param   h�l�zatiLabirintus  A labirintust tartalmaz� TCP szerver
     * <code>javattanitok.H�l�zatiLabirintus</code> oszt�ly
     */
    public LabirintusKiszolg�l�Sz�l(java.net.Socket socket, 
            H�l�zatiLabirintus h�l�zatiLabirintus) {
        this.socket = socket;
        this.h�l�zatiLabirintus = h�l�zatiLabirintus;
        new Thread(this).start();
    }
    /** A jelentkez� j�t�kosokat p�rhuzamosan kiszolg�l� sz�l. */
    public void run() {
        try {
            // A socket kapcsolat feletti bej�v� csatorna elk�r�se
            java.io.BufferedReader bej�v�Csatorna =
                    new java.io.BufferedReader(
                    new java.io.InputStreamReader(socket.
                    getInputStream()));
            // A socket kapcsolat feletti kimen� csatorna elk�r�se
            java.io.PrintWriter kimen�Csatorna =
                    new java.io.PrintWriter(socket.getOutputStream());
            // A h�s nev�nek beolvas�sa
            kimen�Csatorna.println("A h�s neve?");
            kimen�Csatorna.flush();
            String j�t�kost�l = bej�v�Csatorna.readLine();
            // Vagy �j vagy r�gi a h�s, a h�s neve = "hoszt IP : n�v"
            String h�sN�v = socket.getInetAddress().getHostAddress() +
                    " : " + j�t�kost�l;
            H�s h�s = h�l�zatiLabirintus.h�s(h�sN�v);
            // Inform�ljuk a j�t�kost a j�t�k haszn�lat�r�l
            kimen�Csatorna.println("Parancsok: l = le, f = f�l, " +
                    "j = jobbra, b = balra k = kil�p");
            kimen�Csatorna.println("           sima ENTER = " +
                    "megmutatja a labirintust");
            kimen�Csatorna.flush();
            
            j�t�kost�l = bej�v�Csatorna.readLine();
            
            while(j�t�kost�l != null) {
                // A j�t�kost�l �rkez� parancsok feldolgoz�sa
                if("l".equals(j�t�kost�l))
                    h�s.l�pLe();
                else if("f".equals(j�t�kost�l))
                    h�s.l�pF�l();
                else if("j".equals(j�t�kost�l))
                    h�s.l�pJobbra();
                else if("b".equals(j�t�kost�l))
                    h�s.l�pBalra();
                else if("k".equals(j�t�kost�l))
                    break;
                
                kimen�Csatorna.println("--- Labirintusszerinti id�: "
                        + h�l�zatiLabirintus.id�() + ". pillanat -------");
                kimen�Csatorna.println("--- �sszes h�s�k sz�ma: " 
                        + h�l�zatiLabirintus.h�s�kSz�ma());
                kimen�Csatorna.println("--- �letek sz�ma: " + h�s.�letek());
                kimen�Csatorna.println("--- Gy�jt�tt �rt�k: " + h�s.pontsz�m());
                kimen�Csatorna.println("--- A labirintus: (" 
                        + h�l�zatiLabirintus.id�()+". pillanat) -------");
                // Megmutatjuk a labirintus aktu�lis �llapot�t
                h�l�zatiLabirintus.labirintus().nyomtat(h�s, kimen�Csatorna);
                kimen�Csatorna.flush();
                if(h�s.�letek() <= 0) {
                    h�l�zatiLabirintus.h�sMeghalt(h�sN�v);
                    break;
                }
                j�t�kost�l = bej�v�Csatorna.readLine();                
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
