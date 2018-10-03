/*
 * LabirintusMIDlet.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

/**
 * A labirintus csomag absztrah�lta labirintus mikrovil�g�nak egy
 * mobiltelefonos �letre kelt�s�re ad p�ld�t ez az oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.LabirintusVil�g
 * @see javattanitok.LabirintusApplet
 * @see javattanitok.LabirintusJ�t�k
 * @see javattanitok.LabirintusServlet
 * @see javattanitok.H�l�zatiLabirintus
 * @see javattanitok.T�voliLabirintus
 * @see javattanitok.Korb�sLabirintus
 * @see javattanitok.ElosztottLabirintus
 * @see javattanitok.LabirintusVaszon
 */
public class LabirintusMIDlet extends javax.microedition.midlet.MIDlet
        implements javax.microedition.lcdui.CommandListener {
    /** A MIDlethez tartoz� kijelz�. */
    private javax.microedition.lcdui.Display kijelz�;
    /** Parancs a kil�p�shez. */
    private javax.microedition.lcdui.Command kil�p�sParancs;
    /** A labirintus �letre kelt�se �s megjelen�t�se. */
    private LabirintusVaszon labirintusV�szon;
    /**
     * A <code>LabirintusMIDlet</code> objektum elk�sz�t�se.
     */
    public LabirintusMIDlet() {
        // A MIDlethez tartoz� kijelz� elk�r�se
        kijelz� = javax.microedition.lcdui.Display.getDisplay(this);
        // A labirintus elk�sz�t�se
        labirintusV�szon = new LabirintusVaszon();
        // A kil�p�s parancs elk�sz�t�se
        kil�p�sParancs = new javax.microedition.lcdui.Command("Kil�p",
                javax.microedition.lcdui.Command.EXIT, 1);
        // �s a labirintus v�szonra helyez�se
        labirintusV�szon.addCommand(kil�p�sParancs);
        // az esem�nyeket (most kil�p�s parancs) itt dolgozzuk fel
        labirintusV�szon.setCommandListener(this);
    }
    /** A MIDletet ind�t� �letciklus met�dus. */
    public void startApp() {
        // A kijelz�n a labirintus v�szon legyen l�that�
        kijelz�.setCurrent(labirintusV�szon);
    }
    /**
     * A MIDletet felf�ggeszt� �letciklus met�dus, azaz mit tegy�nk,
     * ha egy bej�v� h�v�s vagy SMS megzavarja a programunk fut�s�t?
     * (Most semmit, mert csup�n �res testes implement�ci�j�t adtuk a
     * f�ggv�nynek.)
     */
    public void pauseApp() {
    }
    /**
     * A MIDletet befejez� �letciklus met�dus, azaz mit tegy�nk,
     * ha programunk befejezi fut�s�t? (Most semmit, mert csup�n
     * �res testes implement�ci�j�t adtuk a f�ggv�nynek.)
     */
    public void destroyApp(boolean unconditional) {
        // Le�ll�tjuk a labirintus j�t�k sz�l�t
        if(labirintusV�szon != null)
            labirintusV�szon.j�t�kKil�p();
    }
    /**
     * A labirintus j�t�k parancsainak (jelen esetben egy ilyen van,
     * a kil�p�s) kezel�se.
     *
     * @param command parancs, ami keletkezett
     * @param displayable valamelyik k�perny�n
     */
    public void commandAction(javax.microedition.lcdui.Command parancs,
            javax.microedition.lcdui.Displayable k�perny�) {
        
        if (k�perny� == labirintusV�szon) {
            if (parancs == kil�p�sParancs) {
                // Le�ll�tjuk a labirintus j�t�k sz�l�t
                labirintusV�szon.j�t�kKil�p();
                // Le�ll�tjuk a programot
                kijelz�.setCurrent(null);
                destroyApp(true);
                notifyDestroyed();
                
            }
        }
    }
}                
