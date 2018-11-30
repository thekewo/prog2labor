/*
 * LabirintusMIDlet.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

/**
 * A labirintus csomag absztrahálta labirintus mikrovilágának egy
 * mobiltelefonos életre keltésére ad példát ez az osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVilág
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJáték
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.HálózatiLabirintus
 * @see javattanitok.TávoliLabirintus
 * @see javattanitok.KorbásLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see javattanitok.LabirintusVaszon
 */
public class LabirintusMIDlet extends javax.microedition.midlet.MIDlet
        implements javax.microedition.lcdui.CommandListener {
    /** A MIDlethez tartozó kijelzõ. */
    private javax.microedition.lcdui.Display kijelzõ;
    /** Parancs a kilépéshez. */
    private javax.microedition.lcdui.Command kilépésParancs;
    /** A labirintus életre keltése és megjelenítése. */
    private LabirintusVaszon labirintusVászon;
    /**
     * A <code>LabirintusMIDlet</code> objektum elkészítése.
     */
    public LabirintusMIDlet() {
        // A MIDlethez tartozó kijelzõ elkérése
        kijelzõ = javax.microedition.lcdui.Display.getDisplay(this);
        // A labirintus elkészítése
        labirintusVászon = new LabirintusVaszon();
        // A kilépés parancs elkészítése
        kilépésParancs = new javax.microedition.lcdui.Command("Kilép",
                javax.microedition.lcdui.Command.EXIT, 1);
        // és a labirintus vászonra helyezése
        labirintusVászon.addCommand(kilépésParancs);
        // az eseményeket (most kilépés parancs) itt dolgozzuk fel
        labirintusVászon.setCommandListener(this);
    }
    /** A MIDletet indító életciklus metódus. */
    public void startApp() {
        // A kijelzõn a labirintus vászon legyen látható
        kijelzõ.setCurrent(labirintusVászon);
    }
    /**
     * A MIDletet felfüggesztõ életciklus metódus, azaz mit tegyünk,
     * ha egy bejövõ hívás vagy SMS megzavarja a programunk futását?
     * (Most semmit, mert csupán üres testes implementációját adtuk a
     * függvénynek.)
     */
    public void pauseApp() {
    }
    /**
     * A MIDletet befejezõ életciklus metódus, azaz mit tegyünk,
     * ha programunk befejezi futását? (Most semmit, mert csupán
     * üres testes implementációját adtuk a függvénynek.)
     */
    public void destroyApp(boolean unconditional) {
        // Leállítjuk a labirintus játék szálát
        if(labirintusVászon != null)
            labirintusVászon.játékKilép();
    }
    /**
     * A labirintus játék parancsainak (jelen esetben egy ilyen van,
     * a kilépés) kezelése.
     *
     * @param command parancs, ami keletkezett
     * @param displayable valamelyik képernyõn
     */
    public void commandAction(javax.microedition.lcdui.Command parancs,
            javax.microedition.lcdui.Displayable képernyõ) {
        
        if (képernyõ == labirintusVászon) {
            if (parancs == kilépésParancs) {
                // Leállítjuk a labirintus játék szálát
                labirintusVászon.játékKilép();
                // Leállítjuk a programot
                kijelzõ.setCurrent(null);
                destroyApp(true);
                notifyDestroyed();
                
            }
        }
    }
}                
