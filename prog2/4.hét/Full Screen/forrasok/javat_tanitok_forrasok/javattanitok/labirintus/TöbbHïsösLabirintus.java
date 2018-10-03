/*
 * T�bbH�s�sLabirintus.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A t�bb h�s�s labirintust le�r� oszt�ly, ahol egy h�s hal�la
 * m�r nem jelenti a labirintus j�t�k v�g�t. A j�t�k �llapot�t
 * a kor�bbi j�t�kokban a labirintushoz kapcsoltuk, most, hogy
 * olyan tov�bbfejlesztett labirintust akarunk, amiben t�bb h�s
 * is bolyonghat, �gy �rezz�k, hogy a j�t�k v�ge ink�bb a h�sh�z
 * tartozik, semmint a labirintushoz. Mindkett� igaz: mert, ha a
 * kincsek fogynak el, akkor a labirintus oldal�r�l van v�ge a
 * j�t�knak.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class T�bbH�s�sLabirintus extends Labirintus {
    /**
     * Argumentum n�lk�li konstruktor, gyerekek implicit super()-�hez.
     */
    public T�bbH�s�sLabirintus() {}
    /**
     * A <code>T�bbH�s�sLabirintus</code> objektum elk�sz�t�se.
     *
     * @param      labirintusF�jlN�v       a labirintust defini�l�, megfelel� 
     * szerkezet� sz�veges �llom�ny neve.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� �llom�ny nem 
     * a megfelel� szerkezet�
     */
    public T�bbH�s�sLabirintus(String labirintusF�jlN�v) throws 
            RosszLabirintusKiv�tel {
        
        super(labirintusF�jlN�v);
        
    }
    /**
     * Az �s megfelel� met�dus�nak elfed�se, mert ez a J�T�K_V�GE_MEGHALT_H�S
     * csak a h�s v�g�t jelenti, a labirintus�t nem!
     *
     * @see Labirintus#bolyong(H�s h�s)
     * @param h�s aki a labirintusban bolyong.
     * @return int a j�t�k �llapot�t le�r� k�d.
     */
    public int bolyong(H�s h�s) {
        
        boolean mindMegvan = true;
        
        for(int i=0; i < kincsek.length; ++i) {
            
            // A h�s r�tal�lt valamelyik kincsre?
            if(kincsek[i].megtal�lt(h�s))
                h�s.megtal�ltam(kincsek[i]);
            
            // ha ez egyszer is teljes�l, akkor nincs minden kincs megtal�lva
            if(!kincsek[i].megtal�lva())
                mindMegvan = false;
            
        }
        
        if(mindMegvan) {
            
            j�t�k�llapot = J�T�K_V�GE_MINDEN_KINCS_MEGVAN;
            return j�t�k�llapot;
            
        }
        
        for(int i=0; i < sz�rnyek.length; ++i) {
            
            sz�rnyek[i].l�p(h�s);
            
            if(sz�rnyek[i].megesz(h�s))  {
                
                if(h�s.megettek())
                    // De ez a j�t�k v�ge csak a h�s v�g�t
                    // jelenti, a labirintus�t nem!
                    return J�T�K_V�GE_MEGHALT_H�S;
                else
                    return J�T�K_MEGY_MEGHALT_H�S;
                
            }            
        }
        
        return J�T�K_MEGY_H�S_RENDBEN;
    }
}               
