/*
 * T�voliH�s�thet�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

/**
 * A labirintusba t�volr�l t�rt�n� jelentkez�st biztos�t a h�s�k sz�m�ra.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see T�voliKliens
 * @see T�voliLabirintus
 */
public interface T�voliH�s�thet� extends java.rmi.Remote {
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     */
    public String l�pLe(String h�sN�v) throws java.rmi.RemoteException;
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     */
    public String l�pF�l(String h�sN�v) throws java.rmi.RemoteException;
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     */
    public String l�pJobbra(String h�sN�v) throws java.rmi.RemoteException;
    /**
     * Az RMI-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     */
    public String l�pBalra(String h�sN�v) throws java.rmi.RemoteException;    
}               
