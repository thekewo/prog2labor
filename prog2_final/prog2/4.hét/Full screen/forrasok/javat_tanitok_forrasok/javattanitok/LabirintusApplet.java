/*
 * LabirintusApplet.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * appletbeli �letre kelt�s�re ad p�ld�t ez az oszt�ly. Ennek megfelel�en
 * appletk�nt a b�ng�sz�ben, alkalmaz�sk�nt k�l�n ablakban t�rt�n�
 * megjelen�t�st biztos�t.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.Korb�sLabirintus
 * @see javattanitok.ElosztottLabirintus
 */
public class LabirintusApplet extends java.applet.Applet
        implements java.awt.event.KeyListener {
    /** A labirintus. */
    Labirintus labirintus;
    /** A h�s. */
    H�s h�s;
    /** A j�t�k v�ge ut�n m�r nem vesz�nk �t inputot a j�t�kost�l,
     * illetve a j�t�k vil�g�nak �llapota sem v�ltozik. */
    boolean j�t�kV�ge = false;
    /** A j�t�k v�ge ut�n megjelen� �zenet. */
    String v�ge�zenet = "V�ge a j�t�knak!";
    // Ha nam appletk�nt ind�tjuk a programot, hanem alkalmaz�sk�nt, akkor
    // ez lesz az alkalmaz�s ablaka
    java.awt.Frame ablak;
    // A labirintus szerepl�ihez rendelt k�pek
    java.awt.Image falK�p;
    java.awt.Image j�ratK�p;
    java.awt.Image h�sK�p;
    java.awt.Image sz�rnyK�p;
    java.awt.Image kincsK�p;
    /**
     * Az applet �letciklus�nak ind�t�sa, csak akkor fut le, ha appletk�nt
     * ind�totuk a programot.
     */
    public void init() {
        
        addKeyListener(this);
        indul(true);
        
    }
    /**
     * Ak�r appletk�nt, ak�r alkalmaz�sk�nt ind�tjuk a programot, itt
     * v�gezz�k el az inicializ�l�s jav�t.
     */
    public void indul(boolean appletk�nt) {
        
        ClassLoader classLoader = this.getClass().getClassLoader();
         
        falK�p = new javax.swing.ImageIcon
                (classLoader.getResource("fal.png")).getImage();
        j�ratK�p = new javax.swing.ImageIcon
                (classLoader.getResource("j�rat.png")).getImage();
        h�sK�p = new javax.swing.ImageIcon
                (classLoader.getResource("h�s.png")).getImage();
        sz�rnyK�p = new javax.swing.ImageIcon
                (classLoader.getResource("sz�rny.png")).getImage();
        kincsK�p = new javax.swing.ImageIcon
                (classLoader.getResource("kincs.png")).getImage();
                
        labirintus = new Labirintus(6, 3);
        h�s = new H�s(labirintus);
        h�s.sor(9);
        h�s.oszlop(0);
        // ha nem appletk�nt ind�tottuk a programot
        if(!appletk�nt) {
            // akkor nyitunk neki egy ablakot
            ablak = new java.awt.Frame("Labirintus applet alkalmaz�sk�nt");
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
     * A j�t�kost�l (aki a j�t�k vil�g�ban a h�s) j�v� input feldolgoz�sa:
     * a h�s mozgat�sa a KURZOR billenyt�kkel, illetve a j�t�k vil�g�nak
     * �llapot v�ltoz�sait is innen ir�ny�tjuk.
     */
    public void keyPressed(java.awt.event.KeyEvent billenty�Esem�ny) {
        // Mit nyomott le?
        int billenty� = billenty�Esem�ny.getKeyCode();
        
        if(!j�t�kV�ge) {
            // Merre l�p a h�s?
            switch(billenty�) {
                // A KURZOR billenty�kkel foglalkozunk, a megfelel� ir�nyba
                // l�p�nk
                case java.awt.event.KeyEvent.VK_UP:
                    h�s.l�pF�l();
                    break;
                case java.awt.event.KeyEvent.VK_DOWN:
                    h�s.l�pLe();
                    break;
                case java.awt.event.KeyEvent.VK_RIGHT:
                    h�s.l�pJobbra();
                    break;
                case java.awt.event.KeyEvent.VK_LEFT:
                    h�s.l�pBalra();
                    break;
                    
            }
            // A j�t�k vil�g�nak �llapot v�ltoz�sa: azaz a j�t�k t�bbi
            // szerepl�je is l�p. Ha ezzel a l�p�ssel a j�t�k vil�g�ban
            // t�rt�nt valami l�nyeges: pl. v�ge a j�t�knak vagy egy sz�rny
            // elkapta a h�st, akkor reag�lunk:
            switch(labirintus.bolyong(h�s)) {
                
                case Labirintus.J�T�K_MEGY_H�S_RENDBEN:
                    break;
                case Labirintus.J�T�K_MEGY_MEGHALT_H�S:
                    // M�g van �lete, visszatessz�k a kezd� poz�ci�ra
                    h�s.sor(9);
                    h�s.oszlop(0);
                    break;
                case Labirintus.J�T�K_V�GE_MINDEN_KINCS_MEGVAN:
                    v�ge�zenet = "Gy�zt�l, v�ge a j�t�knak!";
                    j�t�kV�ge = true;
                    break;
                case Labirintus.J�T�K_V�GE_MEGHALT_H�S:
                    v�ge�zenet = "Vesztett�l, v�ge a j�t�knak!";
                    j�t�kV�ge = true;
                    break;
                    
            }
            // Am�g nincs v�ge a j�t�knak, �jra rajzoljuk a
            // j�t�k fel�let�t, hogy l�tsz�djanak a j�t�k �llapot�ban
            // bek�vetkezett v�ltoz�sok
            repaint();
        }
    }
    /**
     * A KeyListener sz�munkra most �rdektelen tov�bbi met�dusait �res
     * testtel defini�ljuk fel�l.
     */
    public void keyTyped(java.awt.event.KeyEvent billenty�Esem�ny) {}
    public void keyReleased(java.awt.event.KeyEvent billenty�Esem�ny) {}
    /**
     * Kajzolja a j�t�k fel�let�t, azaz a labirintust �s a benne szerepl�ket:
     * a h�st, a kincseket �s a sz�rnyeket.
     */
    public void paint(java.awt.Graphics g) {
        
        // A labirintus kirajzol�sa
        for(int i=0; i<labirintus.sz�less�g(); ++i) {
            for(int j=0; j<labirintus.magass�g(); ++j) {
                
                if(labirintus.szerkezet()[j][i])
                    g.drawImage(falK�p, i*falK�p.getWidth(this),
                            j*falK�p.getHeight(this), null);
                else
                    g.drawImage(j�ratK�p, i*j�ratK�p.getWidth(this),
                            j*j�ratK�p.getHeight(this), null);
                
            }
        }
        // A kincsek kirajzol�sa
        Kincs[] kincsek = labirintus.kincsek();
        for(int i=0; i<kincsek.length; ++i) {
            g.drawImage(kincsK�p,
                    kincsek[i].oszlop()*kincsK�p.getWidth(this),
                    kincsek[i].sor()*kincsK�p.getHeight(this), null);
            
            // Ha m�r megvan a kics, akkor �th�zzuk
            if(kincsek[i].megtal�lva()) {
                g.setColor(java.awt.Color.red);
                g.drawLine(kincsek[i].oszlop()*kincsK�p.getWidth(this),
                        kincsek[i].sor()*kincsK�p.getHeight(this),
                        kincsek[i].oszlop()*kincsK�p.getWidth(this)
                        + kincsK�p.getWidth(this),
                        kincsek[i].sor()*kincsK�p.getHeight(this)
                        + kincsK�p.getHeight(this));
                g.drawLine(kincsek[i].oszlop()*kincsK�p.getWidth(this)
                          + kincsK�p.getWidth(this),
                        kincsek[i].sor()*kincsK�p.getHeight(this),
                        kincsek[i].oszlop()*kincsK�p.getWidth(this),
                        kincsek[i].sor()*kincsK�p.getHeight(this)
                        + kincsK�p.getHeight(this));
            } else {
                // ellenkez� esetben ki�rjuk az �rt�k�t
                g.setColor(java.awt.Color.yellow);
                g.drawString(""+kincsek[i].�rt�k(),
                        kincsek[i].oszlop()*kincsK�p.getWidth(this)
                        + kincsK�p.getWidth(this)/2,
                        kincsek[i].sor()*kincsK�p.getHeight(this)
                        + kincsK�p.getHeight(this)/2);
            }
            
        }
        // A sz�rnyek kirajzol�sa
        Sz�rny[] sz�rnyek = labirintus.sz�rnyek();
        for(int i=0; i<sz�rnyek.length; ++i)
            g.drawImage(sz�rnyK�p,
                    sz�rnyek[i].oszlop()*sz�rnyK�p.getWidth(this),
                    sz�rnyek[i].sor()*sz�rnyK�p.getHeight(this), null);
        // A h�s kirajzol�sa
        g.drawImage(h�sK�p,
                h�s.oszlop()*h�sK�p.getWidth(this),
                h�s.sor()*h�sK�p.getHeight(this), null);
        
        // A j�t�k aktu�lis adataib�l n�h�ny ki�rat�sa
        g.setColor(java.awt.Color.black);
        
        g.drawString("�letek sz�ma: "+h�s.�letek(), 10, 40);
        g.drawString("Gy�jt�tt �rt�k: "+h�s.pontsz�m(), 10, 60);
        
        if(j�t�kV�ge) {
            
            g.setColor(java.awt.Color.black);
            g.drawString(v�ge�zenet, 420, 350);
            
        }
    }    
    /**
     * A j�t�k fel�let�nek kirajzol�sakor ne legyen villog�s, ez�rt
     * az eredeti, a fel�let t�rl�s�t elv�gz� update met�dust fel�ldefini�ljuk.
     */
    public void update(java.awt.Graphics g) {
        paint(g);
    }    
    /**
     * A program alkalmaz�sk�nt val� ind�that�s�g�t szolg�lja.
     */
    public static void main(String[] args) {
        
        // Ha itt van a vez�rl�s, akkor nem igaz az, hogy appletk�nt ind�tottuk
        new LabirintusApplet().indul(false);
        
    }    
}                
