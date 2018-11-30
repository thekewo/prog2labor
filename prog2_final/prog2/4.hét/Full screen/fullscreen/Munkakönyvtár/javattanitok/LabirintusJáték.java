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
 * "teljes képernyős" (Full Screen Exclusive Mode API-s) életre
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
    /** A hős. */
    Hős hős;
    /** A játékbeli idő mérésére.*/
    private long idő = 0;
    /** Jelzi a játék végét, ezután a játék álapota már nem változik. */
    private boolean játékVége = false;
    /** A játék végén a játékost tájékoztató üzenet. */
    String végeÜzenet = "Vége a játéknak!";
    /** Jelzi, hogy a program terminálhat. */
    private boolean játékKilép = false;
    /** A labirintus szereplőihez rendelt képek. Ebben a példában már
     * BufferedImage képeket használunk, mert majd a teljesítmény javitás
     * apropóján ezeket a grafikus konfigurációnkhoz igazítjuk. */
    java.awt.image.BufferedImage falKép;
    java.awt.image.BufferedImage járatKép;
    java.awt.image.BufferedImage hősKép;
    java.awt.image.BufferedImage szörnyKép;
    java.awt.image.BufferedImage kincsKép;
    // A fullscreenbe kapcsoláshoz
    java.awt.GraphicsDevice graphicsDevice;
    // A megjelenítéshez
    java.awt.image.BufferStrategy bufferStrategy;
    /**
     * A <code>LabirintusJáték</code> objektum elkészítése.
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelő
     * szerkezetű szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány nem 
     * a megfelelő szerkezetű
     */
    public LabirintusJáték(String labirintusFájlNév) 
    throws RosszLabirintusKivétel {
        /* A labirintus felépítése. */
        // A labirintus elkészítése állományból
        labirintus = new Labirintus(labirintusFájlNév);
        // A hős elkészítése és a kezdő pozíciójának beállítása
        hős = new Hős(labirintus);
        // A hős kezdő pozíciója
        hős.sor(9);
        hős.oszlop(0);
        /* Teljes képernyős módba próbálunk váltani. */
        // A lokális grafikus környezet elkérése
        java.awt.GraphicsEnvironment graphicsEnvironment
                = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        // A grafikus környzetből a képernyővel dolgozunk
        graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        // Próbálunk teljes képernyős, most speciálisan 1024x768-ba váltani
        teljesKépernyősMód(graphicsDevice);
        // Átadjuk a grafikus konfigurációt a kompatibilis képek elkészítéséhez
        képErőforrásokBetöltése(graphicsDevice.getDefaultConfiguration());
        // A hős mozgatása a KURZOR billenytűkkel, ESC kilép
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent billentyűEsemény) {
                
                int billentyű = billentyűEsemény.getKeyCode();
                
                if(!játékVége)
                    switch(billentyű) { // hős mozgatása
                        
                        case java.awt.event.KeyEvent.VK_UP:
                            hős.lépFöl();
                            break;
                        case java.awt.event.KeyEvent.VK_DOWN:
                            hős.lépLe();
                            break;
                        case java.awt.event.KeyEvent.VK_RIGHT:
                            hős.lépJobbra();
                            break;
                        case java.awt.event.KeyEvent.VK_LEFT:
                            hős.lépBalra();
                            break;
                            
                    }
                    // Kilépés a játékból
                    if(billentyű == java.awt.event.KeyEvent.VK_ESCAPE)
                        játékKilép = true;
                    
                    // A játékban történt változások a képernyőn 
                    // is jelenjenek meg
                    rajzolniKell();
                    
            };
        });
        
        // A játékbeli idő folyását biztosító szál elkészítése és indítása
        new Thread(this).start();
    }
    /** A játék időbeli fejlődésének vezérlése. */
    synchronized public void run() {
        
        while(!játékKilép) {
            
            // Aktív renderelés
            rajzol();
            
            idoegyseg();
            
            switch(labirintus.bolyong(hős)) {
                
                case Labirintus.JÁTÉK_MEGY_HŐS_RENDBEN:
                    break;
                case Labirintus.JÁTÉK_MEGY_MEGHALT_HŐS:
                    // Még van élete, visszatesszük a kezdő pozícióra
                    hős.sor(9);
                    hős.oszlop(0);
                    break;
                case Labirintus.JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN:
                    végeÜzenet = "Győztél, vége a játéknak!";
                    játékVége = true;
                    break;
                case Labirintus.JÁTÉK_VÉGE_MEGHALT_HŐS:
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
     * Ébresztő az várakozó rajzolást végző szálnak, ki kell rajzolni a játék 
     * grafikus felületét.
     */
    synchronized public void rajzolniKell() {
        
        notify();
        
    }
    /**
     * Megadja, hogy milyen gyorsan telik az idő a játékban.
     */
    private void idoegyseg() {
        
        ++ idő;
        
        try {
            
            wait(1000);
            
        } catch (InterruptedException e) {}
        
    }
    /**
     * Kép erőforrások betöltése.
     *
     * @param   graphicsConfiguration   a grafikus komfigurációval kompatibilis
     * képek készítéséhez.
     */
    public void képErőforrásokBetöltése(java.awt.GraphicsConfiguration 
            graphicsConfiguration) {
        
        falKép = kompatibilisKép("fal.png", graphicsConfiguration);
        járatKép = kompatibilisKép("járat.png", graphicsConfiguration);
        hősKép = kompatibilisKép("hős.png", graphicsConfiguration);
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
        // Képet legegyszerűben a Swing-beli ImageIcon-al tölthetünk be:
        java.awt.Image kép = new javax.swing.ImageIcon
                (képNév).getImage();
        // ebből BufferedImage-et készítünk, hogy hozzáférjünk a transzparencia
        // értékhez (pl. a hős, a kincs és a szörny transzparens nálunk)
        java.awt.image.BufferedImage bufferedImage =
                new java.awt.image.BufferedImage(kép.getWidth(null), 
                kép.getHeight(null),
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        
        java.awt.Graphics2D g0 = bufferedImage.createGraphics();
        g0.drawImage(kép, 0, 0, null);
        g0.dispose();
        // Az előző lépéshez hasonló lépésben most egy olyan BufferedImage-et,
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
                // ellenkező esetben kiírjuk az értékét
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
        
        // A hős kirajzolása
        g.drawImage(hősKép,
                hős.oszlop()*hősKép.getWidth(),
                hős.sor()*hősKép.getHeight(), null);
        
        // A játék aktuális adataiból néhány kiíratása
        g.setColor(java.awt.Color.black);
        
        g.drawString("Életek száma: "+hős.életek(), 10, 40);
        g.drawString("Gyűjtött érték: "+hős.pontszám(), 10, 60);
        g.drawString("Idő: "+idő, 10, 80);
        
        if(játékVége)
            g.drawString(végeÜzenet, 420, 350);
        
        g.dispose();
        if (!bufferStrategy.contentsLost())
            bufferStrategy.show();
        
    }
    /**
     * Teljes képernyős módba (Full Screen Exclusive Mode) kapcsolás.
     * Ha nem támogatott, akkor sima ablak fejléc és keret nélkül.
     */
    public void teljesKépernyősMód(java.awt.GraphicsDevice graphicsDevice) {
        
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
            // az aktuális képernyő jellemzök (szélesség, magasság, színmélység,
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
            // A lehetséges képernyő beállítások elkérése
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
     * elindítja a játék világának működését.
     *
     * @param args a labirintus tervét tartalmazó állomány neve az első 
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