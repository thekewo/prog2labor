/*
 * T�voliH�sKiszolg�l�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok;

import javattanitok.labirintus.*;
/**
 * A <code>tavolihos.idl</code> le�rta T�voliH�s CORBA
 * kiszolg�l� objektum megval�s�t�sa.
 *
 * A <code>tavolihos.idl</code> interf�sz:
 * <pre>
 * module javattanitok
 * {
 *   module korbas
 *   {
 *   interface TavoliHos
 *     {
 *       string l�pLe(in string hosNev);
 *       string l�pF�l(in string hosNev);
 *       string l�pJobbra(in string hosNev);
 *       string l�pBalra(in string hosNev);
 *     };
 *   };
 * };
 * </pre>
 * A <code>javattanitok.korbas.*</code> csomag legener�l�sa:
 * <pre>
 * idlj -fall tavolihos.idl
 * </pre>
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 * @see Korb�sLabirintus
 * @see Korb�sKliens
 */
public class T�voliH�sKiszolg�l�
        extends javattanitok.korbas.TavoliHosPOA {
    /** A labirintus j�t�kot futtat� oszt�ly. */
    Korb�sLabirintus korb�sLabirintus;
    /**
     * A <code>T�voliH�sKiszolg�l�</code> objektum elk�sz�t�se.
     *
     * @param      korb�sLabirintus       A labirintus j�t�kot futtat� oszt�ly.
     */
    public T�voliH�sKiszolg�l�(Korb�sLabirintus korb�sLabirintus) {
        
        this.korb�sLabirintus = korb�sLabirintus;
        
    }    
    /**
     * Az ORB-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     * @return String a labirintus �llapot�t bemutat� string. 
     */
    public String l�pLe(String h�sN�v) {
        H�s h�s = null;
        
        h�s = korb�sLabirintus.h�s(h�sN�v);
        
        h�s.l�pLe();
        
        return korb�sLabirintus.labirintus().kinyomtat(h�s);
    }
    /**
     * Az ORB-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     * @return String a labirintus �llapot�t bemutat� string. 
     */
    public String l�pF�l(String h�sN�v) {
        H�s h�s = null;
        h�s = korb�sLabirintus.h�s(h�sN�v);
        
        h�s.l�pF�l();
        
        return korb�sLabirintus.labirintus().kinyomtat(h�s);
        
    }
    /**
     * Az ORB-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     * @return String a labirintus �llapot�t bemutat� string. 
     */
    public String l�pJobbra(String h�sN�v) {
        H�s h�s = null;
        h�s = korb�sLabirintus.h�s(h�sN�v);
        
        h�s.l�pJobbra();
        
        return korb�sLabirintus.labirintus().kinyomtat(h�s);
    }
    /**
     * Az ORB-n kereszt�l jelenetkez� h�s l�p�se.
     *
     * @param h�sN�v a h�s neve.
     * @return String a labirintus �llapot�t bemutat� string. 
     */
    public String l�pBalra(String h�sN�v) {
        H�s h�s = null;
        h�s = korb�sLabirintus.h�s(h�sN�v);
        h�s.l�pBalra();
        
        return korb�sLabirintus.labirintus().kinyomtat(h�s);
    }
}					
