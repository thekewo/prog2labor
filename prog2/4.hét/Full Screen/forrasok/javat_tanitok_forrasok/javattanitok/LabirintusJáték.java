/*
 * LabirintusJáték.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * "teljes képernyõs" (Full Screen Exclusive Mode API-s) életre
 * keltésére ad példát ez az osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.LabirintusMIDlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.TávoliLabirintus
 * @see javattanitok.KorbásLabirintus
 * @see javattanitok.ElosztottLabirintus
 */
public class LabirintusJáték extends java.awt.Frame
        implements Runnable {
    /** A labirintus. */
    Labirintus labirintus;
    /** A hõs. */
    Hõs hõs;
    /** A játékbeli idõ mérésére.*/
    private long idõ = 0;
    /** Jelzi a játék végét, ezután a játék álapota már nem változik. */
    private boolean játékVége = false;
    /** A játék végén a játékost tájékoztató üzenet. */
    String végeÜzenet = "Vége a játéknak!";
    /** Jelzi, hogy a program terminálhat. */
    private boolean játékKilép = false;
    /** A labirintus szereplõihez rendelt képek. Ebben a példában már
     * BufferedImage képeket használunk, mert majd a teljesítmény javitás
     * apropóján ezeket a grafikus konfigurációnkhoz igazítjuk. */
    java.awt.image.BufferedImage falKép;
    java.awt.image.BufferedImage járatKép;
    java.awt.image.BufferedImage hõsKép;
    java.awt.image.BufferedImage szörnyKép;
    java.awt.image.BufferedImage kincsKép;
    // A fullscreenbe kapcsoláshoz
    java.awt.GraphicsDevice graphicsDevice;
    // A megjelenítéshez
    java.awt.image.BufferStrategy bufferStrategy;
    /**
     * A <code>LabirintusJáték</code> objektum elkészítése.
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelõ
     * szerkezetû szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány nem 
     * a megfelelõ szerkezetû
     */
    public LabirintusJáték(String labirintusFájlNév) 
    throws RosszLabirintusKivétel {
        /* A labirintus felépítése. */
        // A labirintus elkészítése állományból
        labirintus = new Labirintus(labirintusFájlNév);
        // A hõs elkészítése és a kezdõ pozíciójának beállítása
        hõs = new Hõs(labirintus);
        // A hõs kezdõ pozíciója
        hõs.sor(9);
        hõs.oszlop(0);
        /* Teljes képernyõs módba próbálunk váltani. */
        // A lokális grafikus környezet elkérése
        java.awt.GraphicsEnvironment graphicsEnvironment
                = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        // A grafikus környzetbõl a képernyõvel dolgozunk
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        // Próbálunk teljes képernyõs, most speciálisan 1024x768-ba váltani
        teljesKépernyõsMód(graphicsDevice);
        // Átadjuk a grafikus konfigurációt a kompatibilis képek elkészítéséhez
        képErõforrásokBetöltése(graphicsDevice.getDefaultConfiguration());
        // A hõs mozgatása a KURZOR billenytûkkel, ESC kilép
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent billentyûEsemény) {
                
                int billentyû = billentyûEsemény.getKeyCode();
                
                if(!játékVége)
                    switch(billentyû) { // hõs mozgatása
                        
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
                    // Kilépés a játékból
                    if(billentyû == java.awt.event.KeyEvent.VK_ESCAPE)
                        játékKilép = true;
                    
                    // A játékban történt változások a képernyõn 
                    // is jelenjenek meg
                    rajzolniKell();
                    
            };
        });
        
        // A játékbeli idõ folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
    }
    /** A játék idõbeli fejlõdésének vezérlése. */
    synchronized public void run() {
        
        while(!játékKilép) {
            
            // Aktív renderelés
            rajzol();
            
            idoegyseg();
            
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
            
        }
        // Kilépés a játékból
        setVisible(false);
        graphicsDevice.setFullScreenWindow(null);
        System.exit(0);
        
    }
    /**
     * Ébresztõ az várakozó rajzolást végzõ szálnak, ki kell rajzolni a játék 
     * grafikus felületét.
     */
    synchronized public void rajzolniKell() {
        
        notify();
        
    }
    /**
     * Megadja, hogy milyen gyorsan telik az idõ a játékban.
     */
    private void idoegyseg() {
        
        ++ idõ;
        
        try {
            
            wait(1000);
            
        } catch (InterruptedException e) {}
        
    }
    /**
     * Kép erõforrások betöltése.
     *
     * @param   graphicsConfiguration   a grafikus komfigurációval kompatibilis
     * képek készítéséhez.
     */
    public void képErõforrásokBetöltése(java.awt.GraphicsConfiguration 
            graphicsConfiguration) {
        
        falKép = kompatibilisKép("fal.png", graphicsConfiguration);
        járatKép = kompatibilisKép("járat.png", graphicsConfiguration);
        hõsKép = kompatibilisKép("hõs.png", graphicsConfiguration);
        szörnyKép = kompatibilisKép("szörny.png", graphicsConfiguration);
        kincsKép = kompatibilisKép("kincs.png", graphicsConfiguration);
        
    }
    /**
     * A grafikus konfigurációhoz igazítot kép.
     *
     * @param   képNév   a kép állomány neve
     * @param   graphicsConfiguration   a grafikus komfigurációval kompatibilis
     * képek készítéséhez.
     */
    public java.awt.image.BufferedImage kompatibilisKép(String képNév,
            java.awt.GraphicsConfiguration graphicsConfiguration) {
        // Képet legegyszerûben a Swing-beli ImageIcon-al tölthetünk be:
        java.awt.Image kép = new javax.swing.ImageIcon
                (képNév).getImage();
        // ebbõl BufferedImage-et készítünk, hogy hozzáférjünk a transzparencia
        // értékhez (pl. a hõs, a kincs és a szörny transzparens nálunk)
        java.awt.image.BufferedImage bufferedImage =
                new java.awt.image.BufferedImage(kép.getWidth(null), 
                kép.getHeight(null),
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        
        java.awt.Graphics2D g0 = bufferedImage.createGraphics();
        g0.drawImage(kép, 0, 0, null);
        g0.dispose();
        // Az elõzõ lépéshez hasonló lépésben most egy olyan BufferedImage-et,
        // készítünk, ami kompatibilis a grafikus konfigurációnkkal
        java.awt.image.BufferedImage kompatibilisKép
                = graphicsConfiguration.createCompatibleImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(),
                bufferedImage.getColorModel().getTransparency());
        
        java.awt.Graphics2D g = kompatibilisKép.createGraphics();
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
        
        return kompatibilisKép;
    }
    /**
     * A játék grafikus felületének aktív renderelése.
     */
    public void rajzol() {
        
        java.awt.Graphics g = bufferStrategy.getDrawGraphics();
        
        // A labirintus kirajzolása
        for(int i=0; i<labirintus.szélesség(); ++i) {
            for(int j=0; j<labirintus.magasság(); ++j) {
                
                if(labirintus.szerkezet()[j][i])
                    g.drawImage(falKép, i*falKép.getWidth(),
                            j*falKép.getHeight(), null);
                else
                    g.drawImage(járatKép, i*járatKép.getWidth(),
                            j*járatKép.getHeight(), null);
                
            }
        }
        
        // A kincsek kirajzolása
        Kincs[] kincsek = labirintus.kincsek();
        for(int i=0; i<kincsek.length; ++i) {
            g.drawImage(kincsKép,
                    kincsek[i].oszlop()*kincsKép.getWidth(),
                    kincsek[i].sor()*kincsKép.getHeight(), null);
            // Ha már megvan a kics, akkor áthúzzuk
            if(kincsek[i].megtalálva()) {
                g.setColor(java.awt.Color.red);
                g.drawLine(kincsek[i].oszlop()*kincsKép.getWidth(),
                        kincsek[i].sor()*kincsKép.getHeight(),
                        kincsek[i].oszlop()*kincsKép.getWidth()
                        + kincsKép.getWidth(),
                        kincsek[i].sor()*kincsKép.getHeight()
                        + kincsKép.getHeight());
                g.drawLine(kincsek[i].oszlop()*kincsKép.getWidth()
                +kincsKép.getWidth(),
                        kincsek[i].sor()*kincsKép.getHeight(),
                        kincsek[i].oszlop()*kincsKép.getWidth(),
                        kincsek[i].sor()*kincsKép.getHeight()
                        + kincsKép.getHeight());
            } else {
                // ellenkezõ esetben kiírjuk az értékét
                g.setColor(java.awt.Color.yellow);
                g.drawString(""+kincsek[i].érték(),
                        kincsek[i].oszlop()*kincsKép.getWidth()
                        + kincsKép.getWidth()/2,
                        kincsek[i].sor()*kincsKép.getHeight()
                        + kincsKép.getHeight()/2);
            }
        }
        
        // A szörnyek kirajzolása
        Szörny[] szörnyek = labirintus.szörnyek();
        for(int i=0; i<szörnyek.length; ++i)
            g.drawImage(szörnyKép,
                    szörnyek[i].oszlop()*szörnyKép.getWidth(),
                    szörnyek[i].sor()*szörnyKép.getHeight(), null);
        
        // A hõs kirajzolása
        g.drawImage(hõsKép,
                hõs.oszlop()*hõsKép.getWidth(),
                hõs.sor()*hõsKép.getHeight(), null);
        
        // A játék aktuális adataiból néhány kiíratása
        g.setColor(java.awt.Color.black);
        
        g.drawString("Életek száma: "+hõs.életek(), 10, 40);
        g.drawString("Gyûjtött érték: "+hõs.pontszám(), 10, 60);
        g.drawString("Idõ: "+idõ, 10, 80);
        
        if(játékVége)
            g.drawString(végeÜzenet, 420, 350);
        
        g.dispose();
        if (!bufferStrategy.contentsLost())
            bufferStrategy.show();
        
    }
    /**
     * Teljes képernyõs módba (Full Screen Exclusive Mode) kapcsolás.
     * Ha nem támogatott, akkor sima ablak fejléc és keret nélkül.
     */
    public void teljesKépernyõsMód(java.awt.GraphicsDevice graphicsDevice) {
        
        int szélesség = 0;
        int magasság = 0;
        // Nincs ablak fejléc, keret.
        setUndecorated(true);
        // Mi magunk fogunk rajzolni.
        setIgnoreRepaint(true);
        // Nincs átméretezés
        setResizable(false);
        // Át tudunk kapcsolni fullscreenbe?
        boolean fullScreenTamogatott = graphicsDevice.isFullScreenSupported();
        // Ha tudunk, akkor Full-Screen exkluzív módba váltunk
        if(fullScreenTamogatott) {
            graphicsDevice.setFullScreenWindow(this);
            // az aktuális képernyõ jellemzök (szélesség, magasság, színmélység,
            // frissítési frekvencia) becsomagolt elkérése
            java.awt.DisplayMode displayMode
                    = graphicsDevice.getDisplayMode();
            // és kiíratása
            szélesség = displayMode.getWidth();
            magasság = displayMode.getHeight();
            int színMélység = displayMode.getBitDepth();
            int frissítésiFrekvencia = displayMode.getRefreshRate();
            System.out.println(szélesség
                    + "x"  + magasság
                    + ", " + színMélység
                    + ", " + frissítésiFrekvencia);
            // A lehetséges képernyõ beállítások elkérése
            java.awt.DisplayMode[] displayModes
                    = graphicsDevice.getDisplayModes();
            // Megnézzük, hogy támogatja-e az 1024x768-at, mert a
            // példa játékunkhoz ehhez a felbontáshoz készítettük a képeket
            boolean dm1024x768 = false;
            for(int i=0; i<displayModes.length; ++i) {
                if(displayModes[i].getWidth() == 1024
                        && displayModes[i].getHeight() == 768
                        && displayModes[i].getBitDepth() == színMélység
                        && displayModes[i].getRefreshRate() 
                        == frissítésiFrekvencia) {
                    graphicsDevice.setDisplayMode(displayModes[i]);
                    dm1024x768 = true;
                    break;
                }
                
            }
            
            if(!dm1024x768)
                System.out.println("Nem megy az 1024x768, de a példa képméretei ehhez a felbontáshoz vannak állítva.");
            
        } else {
            setSize(szélesség, magasság);
            validate();
            setVisible(true);
        }
        
        createBufferStrategy(2);
        
        bufferStrategy = getBufferStrategy();
        
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
            
            System.out.println("Indítás: java LabirintusJáték labirintus.txt");
            System.exit(-1);
        }
        
        try {
            
            new LabirintusJáték(args[0]);
            
        } catch(RosszLabirintusKivétel rosszLabirintusKivétel) {
            
            System.out.println(rosszLabirintusKivétel);
            
        }
    }    
}
