/*
 * LabirintusVaszon.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * mobiltelefonos életre keltésére ad példát ez az osztály: elkészíti,
 * vezérli és megjeleníti a labirintust.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusMIDlet
 */
public class LabirintusVaszon extends javax.microedition.lcdui.game.GameCanvas
        implements Runnable {
    /** A vászon szélessége. */
    private int szélesség;
    /** A vászon magassága. */
    private int magasság;
    /** A labirintus. */
    Labirintus labirintus;
    /** A hõs. */
    Hos hõs;
    /** A játékbeli idõ folyását biztosító szál. */
    private Thread játékSzál;
    /** A játékbeli idõ mérésére.*/
    private long idõ = 0;
    /** Jelzi a játék végét, ezután a játék álapota már nem változik. */
    private boolean játékVége = false;
    /** A játék végén a játékost tájékoztató üzenet. */
    String végeÜzenet = "Vége a játéknak!";
    /** Jelzi, hogy a program terminálhat. */
    private boolean játékKilép = false;
    /** A labirintus egy fal vagy járat cellájának szélessége. */
    private int téglaSzélesség;
    /** A labirintus egy fal vagy járat cellájának magassága. */
    private int téglaMagasság;
    /** A szereplõkhöz rendelt képek. */
    javax.microedition.lcdui.Image hõsKép, szörnyKép, kincsKép;
    /**
     * A <code>LabirintusVászon</code> objektum elkészítése.
     */
    public LabirintusVaszon() {
        
        super(false);
        // A mobil kijelzõje teljes képernyõs módba
        setFullScreenMode(true);
        // Milyenek ekkor a méretek?
        szélesség = getWidth();
        magasság = getHeight();
        // A labirintus elkészítése
        labirintus = new Labirintus(6, 3);
        hõs = new Hos(labirintus);
        hõs.sor(9);
        hõs.oszlop(0);
        // A labirintusnak a telefon kijelzõ méretéhez igazítása
        téglaSzélesség = szélesség/labirintus.szélesség();
        téglaMagasság = magasság/labirintus.magasság();
        try {
            // A szereplõkhöz rendelt képek betöltése
            hõsKép = 
                    javax.microedition.lcdui.Image.createImage("/hos.png");
            kincsKép = 
                    javax.microedition.lcdui.Image.createImage("/kincs.png");
            szörnyKép = 
                    javax.microedition.lcdui.Image.createImage("/szorny.png");
            
        } catch(Exception e) {
            
            hõsKép = null;
            kincsKép = null;
            szörnyKép = null;
            
        }
        // A játékbeli idõ folyását biztosító szál elkészítése
        játékSzál = new Thread(this);
        // és indítása
        játékSzál.start();
        
    }
    /** A játék idõbeli fejlõdésének vezérlése. */
    public void run() {
        
        javax.microedition.lcdui.Graphics g = getGraphics();
        
        while(!játékKilép) {
            
            if(!játékVége) { // Ha még nincs vége, akkor érdemben
                // reagálunk a billentyûzet lenyomásokra
                int billentyû = getKeyStates();
                // A kurzor gomboknak megfelelõ irányba lépéssel
                if ((billentyû & LEFT_PRESSED) != 0) {
                    hõs.lépBalra();
                } else if ((billentyû & RIGHT_PRESSED) != 0) {
                    hõs.lépJobbra();
                } else if ((billentyû & UP_PRESSED) != 0) {
                    hõs.lépFöl();
                } else if ((billentyû & DOWN_PRESSED) != 0) {
                    hõs.lépLe();
                }
            }
            
            switch(labirintus.bolyong(hõs)) {
                
                case Labirintus.JÁTÉK_MEGY_HÕS_RENDBEN:
                    break;                    
                case Labirintus.JÁTÉK_MEGY_MEGHALT_HÕS:
                    // Még van élete, visszatesszük a kezdõ pozícióra
                    hõs.sor(9);
                    hõs.oszlop(0);
                    break;
                case Labirintus.JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN:
                    végeÜzenet = "Gyõztél, vége a játéknak!";
                    játékVége = true;
                    break;
                case Labirintus.JÁTÉK_VÉGE_MEGHALT_HÕS:
                    végeÜzenet = "Vesztettél, vége a játéknak!";
                    játékVége = true;
                    break;
                    
            }
            // A kijelzõ törlése
            g.setColor(0x00FFFFFF);
            g.fillRect(0, 0, getWidth(), getHeight());
            // A labirintus kirajzolása
            g.setColor(0x00ed7703);
            for(int i=0; i<labirintus.szélesség(); ++i)
                for(int j=0; j<labirintus.magasság(); ++j)
                    if(labirintus.szerkezet()[j][i])
                        g.fillRect(i*téglaSzélesség, j*téglaMagasság,
                                téglaSzélesség, téglaMagasság);
            // A kincsek kirajzolása
            Kincs[] kincsek = labirintus.kincsek();
            for(int i=0; i<kincsek.length; ++i) {
                
                if(kincsKép != null) {
                    if(!kincsek[i].megtalálva())
                        g.drawImage(kincsKép,
                                kincsek[i].oszlop()*téglaSzélesség,
                                kincsek[i].sor()*téglaMagasság,
                                javax.microedition.lcdui.Graphics.LEFT
                                |javax.microedition.lcdui.Graphics.TOP);
                } else {
                    // Ha már megvan a kics, akkor szürkébbel rajzoljuk
                    if(kincsek[i].megtalálva())
                        g.setColor(0x00d2cfb7);
                    else // Különben sárgábbal
                        g.setColor(0x00fbe101);
                    
                    g.fillRect(kincsek[i].oszlop()*téglaSzélesség,
                            kincsek[i].sor()*téglaMagasság,
                            téglaSzélesség/2, téglaMagasság);
                }
            }
            // A szörnyek kirajzolása
            g.setColor(0x00ff0000);
            Szorny[] szörnyek = labirintus.szörnyek();
            for(int i=0; i<szörnyek.length; ++i)
                if(szörnyKép != null)
                    g.drawImage(szörnyKép,
                            szörnyek[i].oszlop()*téglaSzélesség,
                            szörnyek[i].sor()*téglaMagasság,
                            javax.microedition.lcdui.Graphics.LEFT
                            |javax.microedition.lcdui.Graphics.TOP);
                else
                    g.fillRect(szörnyek[i].oszlop()*téglaSzélesség 
                            + téglaSzélesség/2,
                            szörnyek[i].sor()*téglaMagasság,
                            téglaSzélesség/2, téglaMagasság);
            // A hõs kirajzolása
            if(hõsKép != null)
                g.drawImage(hõsKép,
                        hõs.oszlop()*téglaSzélesség,
                        hõs.sor()*téglaMagasság,
                        javax.microedition.lcdui.Graphics.LEFT
                        |javax.microedition.lcdui.Graphics.TOP);
            else {
                g.setColor(0x0000ff00);
                g.drawRect(hõs.oszlop()*téglaSzélesség,
                        hõs.sor()*téglaMagasság,
                        téglaSzélesség, téglaMagasság);
            }
            // A játék aktuális adataiból néhány kiíratása
            g.setColor(0x000000ff);
            g.drawString("Életek száma: "+hõs.életek(), 10, 15,
                    javax.microedition.lcdui.Graphics.LEFT
                    |javax.microedition.lcdui.Graphics.BOTTOM);
            g.drawString("Gyûjtött érték: "+hõs.pontszám(), 10, 30,
                    javax.microedition.lcdui.Graphics.LEFT
                    |javax.microedition.lcdui.Graphics.BOTTOM);
            g.drawString("Idõ: "+idõ/5, 10, 45,
                    javax.microedition.lcdui.Graphics.LEFT
                    |javax.microedition.lcdui.Graphics.BOTTOM);
            
            if(játékVége)
                g.drawString(végeÜzenet, 10, magasság-20,
                        javax.microedition.lcdui.Graphics.LEFT
                        |javax.microedition.lcdui.Graphics.BOTTOM);
            
            flushGraphics();
            
            idoegyseg();
        }
    }
    /** A játék szál leállítása. */
    public void játékKilép() {
        
        játékKilép = true;
        játékSzál = null;
        
    }
    /** Megadja, hogy milyen gyorsan telik az idõ a játékban. */
    private void idoegyseg() {
        
        ++ idõ;        
        try {            
            Thread.sleep(200);            
        } catch(InterruptedException e) {}        
    }
}               
