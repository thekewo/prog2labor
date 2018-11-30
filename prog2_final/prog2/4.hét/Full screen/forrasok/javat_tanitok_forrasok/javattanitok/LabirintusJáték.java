/*
 * LabirintusJ�t�k.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * "teljes k�perny�s" (Full Screen Exclusive Mode API-s) �letre
 * kelt�s�re ad p�ld�t ez az oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.Korb�sLabirintus
 * @see javattanitok.ElosztottLabirintus
 */
public class LabirintusJ�t�k extends java.awt.Frame
        implements Runnable {
    /** A labirintus. */
    Labirintus labirintus;
    /** A h�s. */
    H�s h�s;
    /** A j�t�kbeli id� m�r�s�re.*/
    private long id� = 0;
    /** Jelzi a j�t�k v�g�t, ezut�n a j�t�k �lapota m�r nem v�ltozik. */
    private boolean j�t�kV�ge = false;
    /** A j�t�k v�g�n a j�t�kost t�j�koztat� �zenet. */
    String v�ge�zenet = "V�ge a j�t�knak!";
    /** Jelzi, hogy a program termin�lhat. */
    private boolean j�t�kKil�p = false;
    /** A labirintus szerepl�ihez rendelt k�pek. Ebben a p�ld�ban m�r
     * BufferedImage k�peket haszn�lunk, mert majd a teljes�tm�ny javit�s
     * aprop�j�n ezeket a grafikus konfigur�ci�nkhoz igaz�tjuk. */
    java.awt.image.BufferedImage falK�p;
    java.awt.image.BufferedImage j�ratK�p;
    java.awt.image.BufferedImage h�sK�p;
    java.awt.image.BufferedImage sz�rnyK�p;
    java.awt.image.BufferedImage kincsK�p;
    // A fullscreenbe kapcsol�shoz
    java.awt.GraphicsDevice graphicsDevice;
    // A megjelen�t�shez
    java.awt.image.BufferStrategy bufferStrategy;
    /**
     * A <code>LabirintusJ�t�k</code> objektum elk�sz�t�se.
     *
     * @param      labirintusF�jlN�v       a labirintust defini�l�, megfelel�
     * szerkezet� sz�veges �llom�ny neve.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� �llom�ny nem 
     * a megfelel� szerkezet�
     */
    public LabirintusJ�t�k(String labirintusF�jlN�v) 
    throws RosszLabirintusKiv�tel {
        /* A labirintus fel�p�t�se. */
        // A labirintus elk�sz�t�se �llom�nyb�l
        labirintus = new Labirintus(labirintusF�jlN�v);
        // A h�s elk�sz�t�se �s a kezd� poz�ci�j�nak be�ll�t�sa
        h�s = new H�s(labirintus);
        // A h�s kezd� poz�ci�ja
        h�s.sor(9);
        h�s.oszlop(0);
        /* Teljes k�perny�s m�dba pr�b�lunk v�ltani. */
        // A lok�lis grafikus k�rnyezet elk�r�se
        java.awt.GraphicsEnvironment graphicsEnvironment
                = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        // A grafikus k�rnyzetb�l a k�perny�vel dolgozunk
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        // Pr�b�lunk teljes k�perny�s, most speci�lisan 1024x768-ba v�ltani
        teljesK�perny�sM�d(graphicsDevice);
        // �tadjuk a grafikus konfigur�ci�t a kompatibilis k�pek elk�sz�t�s�hez
        k�pEr�forr�sokBet�lt�se(graphicsDevice.getDefaultConfiguration());
        // A h�s mozgat�sa a KURZOR billenyt�kkel, ESC kil�p
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent billenty�Esem�ny) {
                
                int billenty� = billenty�Esem�ny.getKeyCode();
                
                if(!j�t�kV�ge)
                    switch(billenty�) { // h�s mozgat�sa
                        
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
                    // Kil�p�s a j�t�kb�l
                    if(billenty� == java.awt.event.KeyEvent.VK_ESCAPE)
                        j�t�kKil�p = true;
                    
                    // A j�t�kban t�rt�nt v�ltoz�sok a k�perny�n 
                    // is jelenjenek meg
                    rajzolniKell();
                    
            };
        });
        
        // A j�t�kbeli id� foly�s�t biztos�t� sz�l elk�sz�t�se �s ind�t�sa
        new Thread(this).start();
    }
    /** A j�t�k id�beli fejl�d�s�nek vez�rl�se. */
    synchronized public void run() {
        
        while(!j�t�kKil�p) {
            
            // Akt�v renderel�s
            rajzol();
            
            idoegyseg();
            
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
            
        }
        // Kil�p�s a j�t�kb�l
        setVisible(false);
        graphicsDevice.setFullScreenWindow(null);
        System.exit(0);
        
    }
    /**
     * �breszt� az v�rakoz� rajzol�st v�gz� sz�lnak, ki kell rajzolni a j�t�k 
     * grafikus fel�let�t.
     */
    synchronized public void rajzolniKell() {
        
        notify();
        
    }
    /**
     * Megadja, hogy milyen gyorsan telik az id� a j�t�kban.
     */
    private void idoegyseg() {
        
        ++ id�;
        
        try {
            
            wait(1000);
            
        } catch (InterruptedException e) {}
        
    }
    /**
     * K�p er�forr�sok bet�lt�se.
     *
     * @param   graphicsConfiguration   a grafikus komfigur�ci�val kompatibilis
     * k�pek k�sz�t�s�hez.
     */
    public void k�pEr�forr�sokBet�lt�se(java.awt.GraphicsConfiguration 
            graphicsConfiguration) {
        
        falK�p = kompatibilisK�p("fal.png", graphicsConfiguration);
        j�ratK�p = kompatibilisK�p("j�rat.png", graphicsConfiguration);
        h�sK�p = kompatibilisK�p("h�s.png", graphicsConfiguration);
        sz�rnyK�p = kompatibilisK�p("sz�rny.png", graphicsConfiguration);
        kincsK�p = kompatibilisK�p("kincs.png", graphicsConfiguration);
        
    }
    /**
     * A grafikus konfigur�ci�hoz igaz�tot k�p.
     *
     * @param   k�pN�v   a k�p �llom�ny neve
     * @param   graphicsConfiguration   a grafikus komfigur�ci�val kompatibilis
     * k�pek k�sz�t�s�hez.
     */
    public java.awt.image.BufferedImage kompatibilisK�p(String k�pN�v,
            java.awt.GraphicsConfiguration graphicsConfiguration) {
        // K�pet legegyszer�ben a Swing-beli ImageIcon-al t�lthet�nk be:
        java.awt.Image k�p = new javax.swing.ImageIcon
                (k�pN�v).getImage();
        // ebb�l BufferedImage-et k�sz�t�nk, hogy hozz�f�rj�nk a transzparencia
        // �rt�khez (pl. a h�s, a kincs �s a sz�rny transzparens n�lunk)
        java.awt.image.BufferedImage bufferedImage =
                new java.awt.image.BufferedImage(k�p.getWidth(null), 
                k�p.getHeight(null),
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        
        java.awt.Graphics2D g0 = bufferedImage.createGraphics();
        g0.drawImage(k�p, 0, 0, null);
        g0.dispose();
        // Az el�z� l�p�shez hasonl� l�p�sben most egy olyan BufferedImage-et,
        // k�sz�t�nk, ami kompatibilis a grafikus konfigur�ci�nkkal
        java.awt.image.BufferedImage kompatibilisK�p
                = graphicsConfiguration.createCompatibleImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(),
                bufferedImage.getColorModel().getTransparency());
        
        java.awt.Graphics2D g = kompatibilisK�p.createGraphics();
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
        
        return kompatibilisK�p;
    }
    /**
     * A j�t�k grafikus fel�let�nek akt�v renderel�se.
     */
    public void rajzol() {
        
        java.awt.Graphics g = bufferStrategy.getDrawGraphics();
        
        // A labirintus kirajzol�sa
        for(int i=0; i<labirintus.sz�less�g(); ++i) {
            for(int j=0; j<labirintus.magass�g(); ++j) {
                
                if(labirintus.szerkezet()[j][i])
                    g.drawImage(falK�p, i*falK�p.getWidth(),
                            j*falK�p.getHeight(), null);
                else
                    g.drawImage(j�ratK�p, i*j�ratK�p.getWidth(),
                            j*j�ratK�p.getHeight(), null);
                
            }
        }
        
        // A kincsek kirajzol�sa
        Kincs[] kincsek = labirintus.kincsek();
        for(int i=0; i<kincsek.length; ++i) {
            g.drawImage(kincsK�p,
                    kincsek[i].oszlop()*kincsK�p.getWidth(),
                    kincsek[i].sor()*kincsK�p.getHeight(), null);
            // Ha m�r megvan a kics, akkor �th�zzuk
            if(kincsek[i].megtal�lva()) {
                g.setColor(java.awt.Color.red);
                g.drawLine(kincsek[i].oszlop()*kincsK�p.getWidth(),
                        kincsek[i].sor()*kincsK�p.getHeight(),
                        kincsek[i].oszlop()*kincsK�p.getWidth()
                        + kincsK�p.getWidth(),
                        kincsek[i].sor()*kincsK�p.getHeight()
                        + kincsK�p.getHeight());
                g.drawLine(kincsek[i].oszlop()*kincsK�p.getWidth()
                +kincsK�p.getWidth(),
                        kincsek[i].sor()*kincsK�p.getHeight(),
                        kincsek[i].oszlop()*kincsK�p.getWidth(),
                        kincsek[i].sor()*kincsK�p.getHeight()
                        + kincsK�p.getHeight());
            } else {
                // ellenkez� esetben ki�rjuk az �rt�k�t
                g.setColor(java.awt.Color.yellow);
                g.drawString(""+kincsek[i].�rt�k(),
                        kincsek[i].oszlop()*kincsK�p.getWidth()
                        + kincsK�p.getWidth()/2,
                        kincsek[i].sor()*kincsK�p.getHeight()
                        + kincsK�p.getHeight()/2);
            }
        }
        
        // A sz�rnyek kirajzol�sa
        Sz�rny[] sz�rnyek = labirintus.sz�rnyek();
        for(int i=0; i<sz�rnyek.length; ++i)
            g.drawImage(sz�rnyK�p,
                    sz�rnyek[i].oszlop()*sz�rnyK�p.getWidth(),
                    sz�rnyek[i].sor()*sz�rnyK�p.getHeight(), null);
        
        // A h�s kirajzol�sa
        g.drawImage(h�sK�p,
                h�s.oszlop()*h�sK�p.getWidth(),
                h�s.sor()*h�sK�p.getHeight(), null);
        
        // A j�t�k aktu�lis adataib�l n�h�ny ki�rat�sa
        g.setColor(java.awt.Color.black);
        
        g.drawString("�letek sz�ma: "+h�s.�letek(), 10, 40);
        g.drawString("Gy�jt�tt �rt�k: "+h�s.pontsz�m(), 10, 60);
        g.drawString("Id�: "+id�, 10, 80);
        
        if(j�t�kV�ge)
            g.drawString(v�ge�zenet, 420, 350);
        
        g.dispose();
        if (!bufferStrategy.contentsLost())
            bufferStrategy.show();
        
    }
    /**
     * Teljes k�perny�s m�dba (Full Screen Exclusive Mode) kapcsol�s.
     * Ha nem t�mogatott, akkor sima ablak fejl�c �s keret n�lk�l.
     */
    public void teljesK�perny�sM�d(java.awt.GraphicsDevice graphicsDevice) {
        
        int sz�less�g = 0;
        int magass�g = 0;
        // Nincs ablak fejl�c, keret.
        setUndecorated(true);
        // Mi magunk fogunk rajzolni.
        setIgnoreRepaint(true);
        // Nincs �tm�retez�s
        setResizable(false);
        // �t tudunk kapcsolni fullscreenbe?
        boolean fullScreenTamogatott = graphicsDevice.isFullScreenSupported();
        // Ha tudunk, akkor Full-Screen exkluz�v m�dba v�ltunk
        if(fullScreenTamogatott) {
            graphicsDevice.setFullScreenWindow(this);
            // az aktu�lis k�perny� jellemz�k (sz�less�g, magass�g, sz�nm�lys�g,
            // friss�t�si frekvencia) becsomagolt elk�r�se
            java.awt.DisplayMode displayMode
                    = graphicsDevice.getDisplayMode();
            // �s ki�rat�sa
            sz�less�g = displayMode.getWidth();
            magass�g = displayMode.getHeight();
            int sz�nM�lys�g = displayMode.getBitDepth();
            int friss�t�siFrekvencia = displayMode.getRefreshRate();
            System.out.println(sz�less�g
                    + "x"  + magass�g
                    + ", " + sz�nM�lys�g
                    + ", " + friss�t�siFrekvencia);
            // A lehets�ges k�perny� be�ll�t�sok elk�r�se
            java.awt.DisplayMode[] displayModes
                    = graphicsDevice.getDisplayModes();
            // Megn�zz�k, hogy t�mogatja-e az 1024x768-at, mert a
            // p�lda j�t�kunkhoz ehhez a felbont�shoz k�sz�tett�k a k�peket
            boolean dm1024x768 = false;
            for(int i=0; i<displayModes.length; ++i) {
                if(displayModes[i].getWidth() == 1024
                        && displayModes[i].getHeight() == 768
                        && displayModes[i].getBitDepth() == sz�nM�lys�g
                        && displayModes[i].getRefreshRate() 
                        == friss�t�siFrekvencia) {
                    graphicsDevice.setDisplayMode(displayModes[i]);
                    dm1024x768 = true;
                    break;
                }
                
            }
            
            if(!dm1024x768)
                System.out.println("Nem megy az 1024x768, de a p�lda k�pm�retei ehhez a felbont�shoz vannak �ll�tva.");
            
        } else {
            setSize(sz�less�g, magass�g);
            validate();
            setVisible(true);
        }
        
        createBufferStrategy(2);
        
        bufferStrategy = getBufferStrategy();
        
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
            
            System.out.println("Ind�t�s: java LabirintusJ�t�k labirintus.txt");
            System.exit(-1);
        }
        
        try {
            
            new LabirintusJ�t�k(args[0]);
            
        } catch(RosszLabirintusKiv�tel rosszLabirintusKiv�tel) {
            
            System.out.println(rosszLabirintusKiv�tel);
            
        }
    }    
}
