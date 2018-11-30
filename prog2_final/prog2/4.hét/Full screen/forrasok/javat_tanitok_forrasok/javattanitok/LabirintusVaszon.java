/*
 * LabirintusVaszon.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * mobiltelefonos �letre kelt�s�re ad p�ld�t ez az oszt�ly: elk�sz�ti,
 * vez�rli �s megjelen�ti a labirintust.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusMIDlet
 */
public class LabirintusVaszon extends javax.microedition.lcdui.game.GameCanvas
        implements Runnable {
    /** A v�szon sz�less�ge. */
    private int sz�less�g;
    /** A v�szon magass�ga. */
    private int magass�g;
    /** A labirintus. */
    Labirintus labirintus;
    /** A h�s. */
    Hos h�s;
    /** A j�t�kbeli id� foly�s�t biztos�t� sz�l. */
    private Thread j�t�kSz�l;
    /** A j�t�kbeli id� m�r�s�re.*/
    private long id� = 0;
    /** Jelzi a j�t�k v�g�t, ezut�n a j�t�k �lapota m�r nem v�ltozik. */
    private boolean j�t�kV�ge = false;
    /** A j�t�k v�g�n a j�t�kost t�j�koztat� �zenet. */
    String v�ge�zenet = "V�ge a j�t�knak!";
    /** Jelzi, hogy a program termin�lhat. */
    private boolean j�t�kKil�p = false;
    /** A labirintus egy fal vagy j�rat cell�j�nak sz�less�ge. */
    private int t�glaSz�less�g;
    /** A labirintus egy fal vagy j�rat cell�j�nak magass�ga. */
    private int t�glaMagass�g;
    /** A szerepl�kh�z rendelt k�pek. */
    javax.microedition.lcdui.Image h�sK�p, sz�rnyK�p, kincsK�p;
    /**
     * A <code>LabirintusV�szon</code> objektum elk�sz�t�se.
     */
    public LabirintusVaszon() {
        
        super(false);
        // A mobil kijelz�je teljes k�perny�s m�dba
        setFullScreenMode(true);
        // Milyenek ekkor a m�retek?
        sz�less�g = getWidth();
        magass�g = getHeight();
        // A labirintus elk�sz�t�se
        labirintus = new Labirintus(6, 3);
        h�s = new Hos(labirintus);
        h�s.sor(9);
        h�s.oszlop(0);
        // A labirintusnak a telefon kijelz� m�ret�hez igaz�t�sa
        t�glaSz�less�g = sz�less�g/labirintus.sz�less�g();
        t�glaMagass�g = magass�g/labirintus.magass�g();
        try {
            // A szerepl�kh�z rendelt k�pek bet�lt�se
            h�sK�p = 
                    javax.microedition.lcdui.Image.createImage("/hos.png");
            kincsK�p = 
                    javax.microedition.lcdui.Image.createImage("/kincs.png");
            sz�rnyK�p = 
                    javax.microedition.lcdui.Image.createImage("/szorny.png");
            
        } catch(Exception e) {
            
            h�sK�p = null;
            kincsK�p = null;
            sz�rnyK�p = null;
            
        }
        // A j�t�kbeli id� foly�s�t biztos�t� sz�l elk�sz�t�se
        j�t�kSz�l = new Thread(this);
        // �s ind�t�sa
        j�t�kSz�l.start();
        
    }
    /** A j�t�k id�beli fejl�d�s�nek vez�rl�se. */
    public void run() {
        
        javax.microedition.lcdui.Graphics g = getGraphics();
        
        while(!j�t�kKil�p) {
            
            if(!j�t�kV�ge) { // Ha m�g nincs v�ge, akkor �rdemben
                // reag�lunk a billenty�zet lenyom�sokra
                int billenty� = getKeyStates();
                // A kurzor gomboknak megfelel� ir�nyba l�p�ssel
                if ((billenty� & LEFT_PRESSED) != 0) {
                    h�s.l�pBalra();
                } else if ((billenty� & RIGHT_PRESSED) != 0) {
                    h�s.l�pJobbra();
                } else if ((billenty� & UP_PRESSED) != 0) {
                    h�s.l�pF�l();
                } else if ((billenty� & DOWN_PRESSED) != 0) {
                    h�s.l�pLe();
                }
            }
            
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
            // A kijelz� t�rl�se
            g.setColor(0x00FFFFFF);
            g.fillRect(0, 0, getWidth(), getHeight());
            // A labirintus kirajzol�sa
            g.setColor(0x00ed7703);
            for(int i=0; i<labirintus.sz�less�g(); ++i)
                for(int j=0; j<labirintus.magass�g(); ++j)
                    if(labirintus.szerkezet()[j][i])
                        g.fillRect(i*t�glaSz�less�g, j*t�glaMagass�g,
                                t�glaSz�less�g, t�glaMagass�g);
            // A kincsek kirajzol�sa
            Kincs[] kincsek = labirintus.kincsek();
            for(int i=0; i<kincsek.length; ++i) {
                
                if(kincsK�p != null) {
                    if(!kincsek[i].megtal�lva())
                        g.drawImage(kincsK�p,
                                kincsek[i].oszlop()*t�glaSz�less�g,
                                kincsek[i].sor()*t�glaMagass�g,
                                javax.microedition.lcdui.Graphics.LEFT
                                |javax.microedition.lcdui.Graphics.TOP);
                } else {
                    // Ha m�r megvan a kics, akkor sz�rk�bbel rajzoljuk
                    if(kincsek[i].megtal�lva())
                        g.setColor(0x00d2cfb7);
                    else // K�l�nben s�rg�bbal
                        g.setColor(0x00fbe101);
                    
                    g.fillRect(kincsek[i].oszlop()*t�glaSz�less�g,
                            kincsek[i].sor()*t�glaMagass�g,
                            t�glaSz�less�g/2, t�glaMagass�g);
                }
            }
            // A sz�rnyek kirajzol�sa
            g.setColor(0x00ff0000);
            Szorny[] sz�rnyek = labirintus.sz�rnyek();
            for(int i=0; i<sz�rnyek.length; ++i)
                if(sz�rnyK�p != null)
                    g.drawImage(sz�rnyK�p,
                            sz�rnyek[i].oszlop()*t�glaSz�less�g,
                            sz�rnyek[i].sor()*t�glaMagass�g,
                            javax.microedition.lcdui.Graphics.LEFT
                            |javax.microedition.lcdui.Graphics.TOP);
                else
                    g.fillRect(sz�rnyek[i].oszlop()*t�glaSz�less�g 
                            + t�glaSz�less�g/2,
                            sz�rnyek[i].sor()*t�glaMagass�g,
                            t�glaSz�less�g/2, t�glaMagass�g);
            // A h�s kirajzol�sa
            if(h�sK�p != null)
                g.drawImage(h�sK�p,
                        h�s.oszlop()*t�glaSz�less�g,
                        h�s.sor()*t�glaMagass�g,
                        javax.microedition.lcdui.Graphics.LEFT
                        |javax.microedition.lcdui.Graphics.TOP);
            else {
                g.setColor(0x0000ff00);
                g.drawRect(h�s.oszlop()*t�glaSz�less�g,
                        h�s.sor()*t�glaMagass�g,
                        t�glaSz�less�g, t�glaMagass�g);
            }
            // A j�t�k aktu�lis adataib�l n�h�ny ki�rat�sa
            g.setColor(0x000000ff);
            g.drawString("�letek sz�ma: "+h�s.�letek(), 10, 15,
                    javax.microedition.lcdui.Graphics.LEFT
                    |javax.microedition.lcdui.Graphics.BOTTOM);
            g.drawString("Gy�jt�tt �rt�k: "+h�s.pontsz�m(), 10, 30,
                    javax.microedition.lcdui.Graphics.LEFT
                    |javax.microedition.lcdui.Graphics.BOTTOM);
            g.drawString("Id�: "+id�/5, 10, 45,
                    javax.microedition.lcdui.Graphics.LEFT
                    |javax.microedition.lcdui.Graphics.BOTTOM);
            
            if(j�t�kV�ge)
                g.drawString(v�ge�zenet, 10, magass�g-20,
                        javax.microedition.lcdui.Graphics.LEFT
                        |javax.microedition.lcdui.Graphics.BOTTOM);
            
            flushGraphics();
            
            idoegyseg();
        }
    }
    /** A j�t�k sz�l le�ll�t�sa. */
    public void j�t�kKil�p() {
        
        j�t�kKil�p = true;
        j�t�kSz�l = null;
        
    }
    /** Megadja, hogy milyen gyorsan telik az id� a j�t�kban. */
    private void idoegyseg() {
        
        ++ id�;        
        try {            
            Thread.sleep(200);            
        } catch(InterruptedException e) {}        
    }
}               
