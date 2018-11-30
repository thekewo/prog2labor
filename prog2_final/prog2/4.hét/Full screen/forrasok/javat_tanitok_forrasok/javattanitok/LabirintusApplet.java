/*
 * LabirintusApplet.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * appletbeli életre keltésére ad példát ez az osztály. Ennek megfelelõen
 * appletként a böngészõben, alkalmazásként külön ablakban történõ
 * megjelenítést biztosít.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.TávoliLabirintus
 * @see javattanitok.KorbásLabirintus
 * @see javattanitok.ElosztottLabirintus
 */
public class LabirintusApplet extends java.applet.Applet
        implements java.awt.event.KeyListener {
    /** A labirintus. */
    Labirintus labirintus;
    /** A hõs. */
    Hõs hõs;
    /** A játék vége után már nem veszünk át inputot a játékostól,
     * illetve a játék világának állapota sem változik. */
    boolean játékVége = false;
    /** A játék vége után megjelenõ üzenet. */
    String végeÜzenet = "Vége a játéknak!";
    // Ha nam appletként indítjuk a programot, hanem alkalmazásként, akkor
    // ez lesz az alkalmazás ablaka
    java.awt.Frame ablak;
    // A labirintus szereplõihez rendelt képek
    java.awt.Image falKép;
    java.awt.Image járatKép;
    java.awt.Image hõsKép;
    java.awt.Image szörnyKép;
    java.awt.Image kincsKép;
    /**
     * Az applet életciklusának indítása, csak akkor fut le, ha appletként
     * indítotuk a programot.
     */
    public void init() {
        
        addKeyListener(this);
        indul(true);
        
    }
    /**
     * Akár appletként, akár alkalmazásként indítjuk a programot, itt
     * végezzük el az inicializálás javát.
     */
    public void indul(boolean appletként) {
        
        ClassLoader classLoader = this.getClass().getClassLoader();
         
        falKép = new javax.swing.ImageIcon
                (classLoader.getResource("fal.png")).getImage();
        járatKép = new javax.swing.ImageIcon
                (classLoader.getResource("járat.png")).getImage();
        hõsKép = new javax.swing.ImageIcon
                (classLoader.getResource("hõs.png")).getImage();
        szörnyKép = new javax.swing.ImageIcon
                (classLoader.getResource("szörny.png")).getImage();
        kincsKép = new javax.swing.ImageIcon
                (classLoader.getResource("kincs.png")).getImage();
                
        labirintus = new Labirintus(6, 3);
        hõs = new Hõs(labirintus);
        hõs.sor(9);
        hõs.oszlop(0);
        // ha nem appletként indítottuk a programot
        if(!appletként) {
            // akkor nyitunk neki egy ablakot
            ablak = new java.awt.Frame("Labirintus applet alkalmazásként");
            // amit be is lehet csukni
            ablak.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    ablak.setVisible(false);
                    System.exit(0);
                }
            });
            
            ablak.add(this);
            ablak.addKeyListener(this);
            ablak.setSize(1024, 768);
            ablak.setVisible(true);
            
        }
    }
    /**
     * A játékostól (aki a játék világában a hõs) jövõ input feldolgozása:
     * a hõs mozgatása a KURZOR billenytûkkel, illetve a játék világának
     * állapot változásait is innen irányítjuk.
     */
    public void keyPressed(java.awt.event.KeyEvent billentyûEsemény) {
        // Mit nyomott le?
        int billentyû = billentyûEsemény.getKeyCode();
        
        if(!játékVége) {
            // Merre lép a hõs?
            switch(billentyû) {
                // A KURZOR billentyûkkel foglalkozunk, a megfelelõ irányba
                // lépünk
                case java.awt.event.KeyEvent.VK_UP:
                    hõs.lépFöl();
                    break;
                case java.awt.event.KeyEvent.VK_DOWN:
                    hõs.lépLe();
                    break;
                case java.awt.event.KeyEvent.VK_RIGHT:
                    hõs.lépJobbra();
                    break;
                case java.awt.event.KeyEvent.VK_LEFT:
                    hõs.lépBalra();
                    break;
                    
            }
            // A játék világának állapot változása: azaz a játék többi
            // szereplõje is lép. Ha ezzel a lépéssel a játék világában
            // történt valami lényeges: pl. vége a játéknak vagy egy szörny
            // elkapta a hõst, akkor reagálunk:
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
            // Amíg nincs vége a játéknak, újra rajzoljuk a
            // játék felületét, hogy látszódjanak a játék állapotában
            // bekövetkezett változások
            repaint();
        }
    }
    /**
     * A KeyListener számunkra most érdektelen további metódusait üres
     * testtel definiáljuk felül.
     */
    public void keyTyped(java.awt.event.KeyEvent billentyûEsemény) {}
    public void keyReleased(java.awt.event.KeyEvent billentyûEsemény) {}
    /**
     * Kajzolja a játék felületét, azaz a labirintust és a benne szereplõket:
     * a hõst, a kincseket és a szörnyeket.
     */
    public void paint(java.awt.Graphics g) {
        
        // A labirintus kirajzolása
        for(int i=0; i<labirintus.szélesség(); ++i) {
            for(int j=0; j<labirintus.magasság(); ++j) {
                
                if(labirintus.szerkezet()[j][i])
                    g.drawImage(falKép, i*falKép.getWidth(this),
                            j*falKép.getHeight(this), null);
                else
                    g.drawImage(járatKép, i*járatKép.getWidth(this),
                            j*járatKép.getHeight(this), null);
                
            }
        }
        // A kincsek kirajzolása
        Kincs[] kincsek = labirintus.kincsek();
        for(int i=0; i<kincsek.length; ++i) {
            g.drawImage(kincsKép,
                    kincsek[i].oszlop()*kincsKép.getWidth(this),
                    kincsek[i].sor()*kincsKép.getHeight(this), null);
            
            // Ha már megvan a kics, akkor áthúzzuk
            if(kincsek[i].megtalálva()) {
                g.setColor(java.awt.Color.red);
                g.drawLine(kincsek[i].oszlop()*kincsKép.getWidth(this),
                        kincsek[i].sor()*kincsKép.getHeight(this),
                        kincsek[i].oszlop()*kincsKép.getWidth(this)
                        + kincsKép.getWidth(this),
                        kincsek[i].sor()*kincsKép.getHeight(this)
                        + kincsKép.getHeight(this));
                g.drawLine(kincsek[i].oszlop()*kincsKép.getWidth(this)
                          + kincsKép.getWidth(this),
                        kincsek[i].sor()*kincsKép.getHeight(this),
                        kincsek[i].oszlop()*kincsKép.getWidth(this),
                        kincsek[i].sor()*kincsKép.getHeight(this)
                        + kincsKép.getHeight(this));
            } else {
                // ellenkezõ esetben kiírjuk az értékét
                g.setColor(java.awt.Color.yellow);
                g.drawString(""+kincsek[i].érték(),
                        kincsek[i].oszlop()*kincsKép.getWidth(this)
                        + kincsKép.getWidth(this)/2,
                        kincsek[i].sor()*kincsKép.getHeight(this)
                        + kincsKép.getHeight(this)/2);
            }
            
        }
        // A szörnyek kirajzolása
        Szörny[] szörnyek = labirintus.szörnyek();
        for(int i=0; i<szörnyek.length; ++i)
            g.drawImage(szörnyKép,
                    szörnyek[i].oszlop()*szörnyKép.getWidth(this),
                    szörnyek[i].sor()*szörnyKép.getHeight(this), null);
        // A hõs kirajzolása
        g.drawImage(hõsKép,
                hõs.oszlop()*hõsKép.getWidth(this),
                hõs.sor()*hõsKép.getHeight(this), null);
        
        // A játék aktuális adataiból néhány kiíratása
        g.setColor(java.awt.Color.black);
        
        g.drawString("Életek száma: "+hõs.életek(), 10, 40);
        g.drawString("Gyûjtött érték: "+hõs.pontszám(), 10, 60);
        
        if(játékVége) {
            
            g.setColor(java.awt.Color.black);
            g.drawString(végeÜzenet, 420, 350);
            
        }
    }    
    /**
     * A játék felületének kirajzolásakor ne legyen villogás, ezért
     * az eredeti, a felület törlését elvégzõ update metódust felüldefiniáljuk.
     */
    public void update(java.awt.Graphics g) {
        paint(g);
    }    
    /**
     * A program alkalmazásként való indíthatóságát szolgálja.
     */
    public static void main(String[] args) {
        
        // Ha itt van a vezérlés, akkor nem igaz az, hogy appletként indítottuk
        new LabirintusApplet().indul(false);
        
    }    
}                
