/*
 * TöbbHõsösLabirintus.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A több hõsös labirintust leíró osztály, ahol egy hõs halála
 * már nem jelenti a labirintus játék végét. A játék állapotát
 * a korábbi játékokban a labirintushoz kapcsoltuk, most, hogy
 * olyan továbbfejlesztett labirintust akarunk, amiben több hõs
 * is bolyonghat, úgy érezzük, hogy a játék vége inkább a hõshöz
 * tartozik, semmint a labirintushoz. Mindkettõ igaz: mert, ha a
 * kincsek fogynak el, akkor a labirintus oldaláról van vége a
 * játéknak.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class TöbbHõsösLabirintus extends Labirintus {
    /**
     * Argumentum nélküli konstruktor, gyerekek implicit super()-éhez.
     */
    public TöbbHõsösLabirintus() {}
    /**
     * A <code>TöbbHõsösLabirintus</code> objektum elkészítése.
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelõ 
     * szerkezetû szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány nem 
     * a megfelelõ szerkezetû
     */
    public TöbbHõsösLabirintus(String labirintusFájlNév) throws 
            RosszLabirintusKivétel {
        
        super(labirintusFájlNév);
        
    }
    /**
     * Az õs megfelelõ metódusának elfedése, mert ez a JÁTÉK_VÉGE_MEGHALT_HÕS
     * csak a hõs végét jelenti, a labirintusét nem!
     *
     * @see Labirintus#bolyong(Hõs hõs)
     * @param hõs aki a labirintusban bolyong.
     * @return int a játék állapotát leíró kód.
     */
    public int bolyong(Hõs hõs) {
        
        boolean mindMegvan = true;
        
        for(int i=0; i < kincsek.length; ++i) {
            
            // A hõs rátalált valamelyik kincsre?
            if(kincsek[i].megtalált(hõs))
                hõs.megtaláltam(kincsek[i]);
            
            // ha ez egyszer is teljesül, akkor nincs minden kincs megtalálva
            if(!kincsek[i].megtalálva())
                mindMegvan = false;
            
        }
        
        if(mindMegvan) {
            
            játékÁllapot = JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN;
            return játékÁllapot;
            
        }
        
        for(int i=0; i < szörnyek.length; ++i) {
            
            szörnyek[i].lép(hõs);
            
            if(szörnyek[i].megesz(hõs))  {
                
                if(hõs.megettek())
                    // De ez a játék vége csak a hõs végét
                    // jelenti, a labirintusét nem!
                    return JÁTÉK_VÉGE_MEGHALT_HÕS;
                else
                    return JÁTÉK_MEGY_MEGHALT_HÕS;
                
            }            
        }
        
        return JÁTÉK_MEGY_HÕS_RENDBEN;
    }
}               
