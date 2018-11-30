/*
 * Szerepl�.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus szerepl�it (kincsek, sz�rnyek, h�s) absztrah�l� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1 
 * @see javattanitok.labirintus.Labirintus
 */
public class Szerepl� {
    /** A szerepl� oszlop poz�ci�ja. */
    protected int oszlop;
    /** A szerepl� sor poz�ci�ja. */
    protected int sor;
    /** A labirintus, melyben a szerepl� van. */
    protected Labirintus labirintus;
    /** A labirintus sz�less�ge. */
    protected int maxSz�less�g;
    /** A labirintus magass�ga. */
    protected int maxMagass�g;
    /** V�letlensz�m gener�tor a szerepl�k mozgat�s�hoz. */
    protected static java.util.Random v�letlenGener�tor 
            = new java.util.Random();
    /**
     * L�trehoz egy <code>Szerepl�</code> objektumot.
     *
     * @param      labirintus       amibe a szerepl�t helyezz�k.
     */
    public Szerepl�(Labirintus labirintus) {
        this.labirintus = labirintus;
        maxSz�less�g = labirintus.sz�less�g();
        maxMagass�g = labirintus.magass�g();
        // A szerepl� kezd� poz�ci�ja a labirintusban
        szerepl�HelyeKezdetben();
    }
    /**
     * A szerepl� labirintusbeli kezd� poz�ci�j�nak meghat�roz�sa.
     */
    void szerepl�HelyeKezdetben() {
        // T�bbsz�r pr�b�lkozunk elhelyezni a szerepl�t a labirintusban,
        // sz�molja, hol tartunk ezekkel a pr�b�lkoz�sokkal:
        int sz�ml�l� = 0;
        
        do {
            // itt +2,-2-k, hogy a bal als� sarokt�l t�vol tartsuk
            // a szerepl�ket, mert majd ezt akarjuk a h�s kezd� poz�ci�j�nak
            oszlop = 2+v�letlenGener�tor.nextInt(maxSz�less�g-2);
            sor = v�letlenGener�tor.nextInt(maxMagass�g-2);
            // max. 10-szer pr�b�lkozunk, de ha siker�l nem "falba tenni" a
            // szerepl�t, akkor m�ris kil�p�nk:
        } while(++sz�ml�l�<10 && labirintus.fal(oszlop, sor));
        
    }
    /**
     * A szerepl� felfel� l�p. Ha nem tud, helyben marad.
     */
    public void l�pF�l() {
        
        if(sor > 0)
            if(!labirintus.fal(oszlop, sor-1))
                --sor;        
    }
    /**
     * A szerepl� lefel� l�p. Ha nem tud, helyben marad.
     */
    public void l�pLe() {
        
        if(sor < maxMagass�g-1)
            if(!labirintus.fal(oszlop, sor+1))
                ++sor;
        
    }
    /**
     * A szerepl� balra l�p. Ha nem tud, helyben marad.
     */
    public void l�pBalra() {
        
        if(oszlop > 0)
            if(!labirintus.fal(oszlop-1, sor))
                --oszlop;
        
    }
    /**
     * A szerepl� jobbra l�p. Ha nem tud, helyben marad.
     */
    public void l�pJobbra() {
        
        if(oszlop < maxSz�less�g-1)
            if(!labirintus.fal(oszlop+1, sor))
                ++oszlop;
        
    }
    /**
     * A szerepl� (Euklideszi) t�vols�ga egy m�sik szerepl�t�l a labirintusban.
     *
     * @param szerepl� a m�sik szerepl�
     * @return int t�vols�g a m�sik szerepl�t�l.
     */
    public int t�vols�g(Szerepl� szerepl�) {
        
        return (int)Math.sqrt((double)
        (oszlop - szerepl�.oszlop)*(oszlop - szerepl�.oszlop)
        + (sor - szerepl�.sor)*(sor - szerepl�.sor)
        );
        
    }
    /**
     * Egy poz�ci� (Euklideszi) t�vols�ga egy m�sik szerepl�t�l a 
     * labirintusban.
     *
     * @param oszlop a poz�ci� oszlop koordin�t�ja
     * @param sor a poz�ci� sor koordin�t�ja
     * @param szerepl� a m�sik szerepl�
     * @return int t�vols�g a m�sik szerepl�t�l.
     */
    public int t�vols�g(int oszlop, int sor, Szerepl� szerepl�) {
        
        if(!(oszlop >= 0 && oszlop <= maxSz�less�g-1
                && sor >= 0 && sor <= maxMagass�g-1))
            return Integer.MAX_VALUE;
        else
            return (int)Math.sqrt((double)
            (oszlop - szerepl�.oszlop)*(oszlop - szerepl�.oszlop)
            + (sor - szerepl�.sor)*(sor - szerepl�.sor)
            );
        
    }
    /**
     * Be�ll�tja a szerepl� labirintusbeli poz�ci�j�nak oszlop 
     * koordin�t�j�t.
     *
     * @param oszlop a szerepl� labirintusbeli poz�ci�j�nak oszlop 
     * koordin�t�ja.
     */
    public void oszlop(int oszlop) {
        
        this.oszlop = oszlop;
        
    }
    /**
     * Be�ll�tja a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�j�t.
     *
     * @param sor a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�ja.
     */
    public void sor(int sor) {
        
        this.sor = sor;
        
    }
    /**
     * Megadja a szerepl� labirintusbeli poz�ci�j�nak oszlop koordin�t�j�t.
     *
     * @return int a szerepl� labirintusbeli poz�ci�j�nak oszlop koordin�t�ja.
     */
    public int oszlop() {
        
        return oszlop;
        
    }
    /**
     * Megadja a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�j�t.
     *
     * @return int a szerepl� labirintusbeli poz�ci�j�nak sor koordin�t�ja.
     */
    public int sor() {
        
        return sor;
        
    }

    public String toString() {
    
        return "SZEREPL� oszlop = " 
                + oszlop
                + " sor = "
                + sor;
    }    
}                
