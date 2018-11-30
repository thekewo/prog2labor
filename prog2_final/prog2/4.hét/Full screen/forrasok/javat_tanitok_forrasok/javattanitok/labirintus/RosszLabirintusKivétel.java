/*
 * RosszLabirintusKiv�tel.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * Ha �llom�ny alapj�n k�sz�tj�k a labirintust, akkor az �llom�ny szerkezet�nek
 * hib�it jelzi ez az oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see javattanitok.labirintus.Labirintus
 */
public class RosszLabirintusKiv�tel extends java.lang.Exception {    
    /**
     * Elk�sz�t egy <code>RosszLabirintusKiv�tel</code> kiv�tel objektumot.
     *
     * @param hiba a hiba le�r�sa
     */
    public RosszLabirintusKiv�tel(String hiba) {
        super(hiba);
    }
}
