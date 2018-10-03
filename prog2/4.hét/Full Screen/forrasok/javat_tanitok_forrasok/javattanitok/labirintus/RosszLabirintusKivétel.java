/*
 * RosszLabirintusKivétel.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * Ha állomány alapján készítjük a labirintust, akkor az állomány szerkezetének
 * hibáit jelzi ez az osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class RosszLabirintusKivétel extends java.lang.Exception {    
    /**
     * Elkészít egy <code>RosszLabirintusKivétel</code> kivétel objektumot.
     *
     * @param hiba a hiba leírása
     */
    public RosszLabirintusKivétel(String hiba) {
        super(hiba);
    }
}
