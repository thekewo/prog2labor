/*
 * TávoliHõsíthetõ.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

/**
 * A labirintusba távolról történõ jelentkezést biztosít a hõsök számára.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see TávoliKliens
 * @see TávoliLabirintus
 */
public interface TávoliHõsíthetõ extends java.rmi.Remote {
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     */
    public String lépLe(String hõsNév) throws java.rmi.RemoteException;
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     */
    public String lépFöl(String hõsNév) throws java.rmi.RemoteException;
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     */
    public String lépJobbra(String hõsNév) throws java.rmi.RemoteException;
    /**
     * Az RMI-n keresztül jelenetkezõ hõs lépése.
     *
     * @param hõsNév a hõs neve.
     */
    public String lépBalra(String hõsNév) throws java.rmi.RemoteException;    
}               
