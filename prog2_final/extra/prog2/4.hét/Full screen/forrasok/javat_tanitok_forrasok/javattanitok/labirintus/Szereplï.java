/*
 * Szereplõ.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus szereplõit (kincsek, szörnyek, hõs) absztraháló osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1 
 * @see javattanitok.labirintus.Labirintus
 */
public class Szereplõ {
    /** A szereplõ oszlop pozíciója. */
    protected int oszlop;
    /** A szereplõ sor pozíciója. */
    protected int sor;
    /** A labirintus, melyben a szereplõ van. */
    protected Labirintus labirintus;
    /** A labirintus szélessége. */
    protected int maxSzélesség;
    /** A labirintus magassága. */
    protected int maxMagasság;
    /** Véletlenszám generátor a szereplõk mozgatásához. */
    protected static java.util.Random véletlenGenerátor 
            = new java.util.Random();
    /**
     * Létrehoz egy <code>Szereplõ</code> objektumot.
     *
     * @param      labirintus       amibe a szereplõt helyezzük.
     */
    public Szereplõ(Labirintus labirintus) {
        this.labirintus = labirintus;
        maxSzélesség = labirintus.szélesség();
        maxMagasság = labirintus.magasság();
        // A szereplõ kezdõ pozíciója a labirintusban
        szereplõHelyeKezdetben();
    }
    /**
     * A szereplõ labirintusbeli kezdõ pozíciójának meghatározása.
     */
    void szereplõHelyeKezdetben() {
        // Többször próbálkozunk elhelyezni a szereplõt a labirintusban,
        // számolja, hol tartunk ezekkel a próbálkozásokkal:
        int számláló = 0;
        
        do {
            // itt +2,-2-k, hogy a bal alsó saroktól távol tartsuk
            // a szereplõket, mert majd ezt akarjuk a hõs kezdõ pozíciójának
            oszlop = 2+véletlenGenerátor.nextInt(maxSzélesség-2);
            sor = véletlenGenerátor.nextInt(maxMagasság-2);
            // max. 10-szer próbálkozunk, de ha sikerül nem "falba tenni" a
            // szereplõt, akkor máris kilépünk:
        } while(++számláló<10 && labirintus.fal(oszlop, sor));
        
    }
    /**
     * A szereplõ felfelé lép. Ha nem tud, helyben marad.
     */
    public void lépFöl() {
        
        if(sor > 0)
            if(!labirintus.fal(oszlop, sor-1))
                --sor;        
    }
    /**
     * A szereplõ lefelé lép. Ha nem tud, helyben marad.
     */
    public void lépLe() {
        
        if(sor < maxMagasság-1)
            if(!labirintus.fal(oszlop, sor+1))
                ++sor;
        
    }
    /**
     * A szereplõ balra lép. Ha nem tud, helyben marad.
     */
    public void lépBalra() {
        
        if(oszlop > 0)
            if(!labirintus.fal(oszlop-1, sor))
                --oszlop;
        
    }
    /**
     * A szereplõ jobbra lép. Ha nem tud, helyben marad.
     */
    public void lépJobbra() {
        
        if(oszlop < maxSzélesség-1)
            if(!labirintus.fal(oszlop+1, sor))
                ++oszlop;
        
    }
    /**
     * A szereplõ (Euklideszi) távolsága egy másik szereplõtõl a labirintusban.
     *
     * @param szereplõ a másik szereplõ
     * @return int távolság a másik szereplõtõl.
     */
    public int távolság(Szereplõ szereplõ) {
        
        return (int)Math.sqrt((double)
        (oszlop - szereplõ.oszlop)*(oszlop - szereplõ.oszlop)
        + (sor - szereplõ.sor)*(sor - szereplõ.sor)
        );
        
    }
    /**
     * Egy pozíció (Euklideszi) távolsága egy másik szereplõtõl a 
     * labirintusban.
     *
     * @param oszlop a pozíció oszlop koordinátája
     * @param sor a pozíció sor koordinátája
     * @param szereplõ a másik szereplõ
     * @return int távolság a másik szereplõtõl.
     */
    public int távolság(int oszlop, int sor, Szereplõ szereplõ) {
        
        if(!(oszlop >= 0 && oszlop <= maxSzélesség-1
                && sor >= 0 && sor <= maxMagasság-1))
            return Integer.MAX_VALUE;
        else
            return (int)Math.sqrt((double)
            (oszlop - szereplõ.oszlop)*(oszlop - szereplõ.oszlop)
            + (sor - szereplõ.sor)*(sor - szereplõ.sor)
            );
        
    }
    /**
     * Beállítja a szereplõ labirintusbeli pozíciójának oszlop 
     * koordinátáját.
     *
     * @param oszlop a szereplõ labirintusbeli pozíciójának oszlop 
     * koordinátája.
     */
    public void oszlop(int oszlop) {
        
        this.oszlop = oszlop;
        
    }
    /**
     * Beállítja a szereplõ labirintusbeli pozíciójának sor koordinátáját.
     *
     * @param sor a szereplõ labirintusbeli pozíciójának sor koordinátája.
     */
    public void sor(int sor) {
        
        this.sor = sor;
        
    }
    /**
     * Megadja a szereplõ labirintusbeli pozíciójának oszlop koordinátáját.
     *
     * @return int a szereplõ labirintusbeli pozíciójának oszlop koordinátája.
     */
    public int oszlop() {
        
        return oszlop;
        
    }
    /**
     * Megadja a szereplõ labirintusbeli pozíciójának sor koordinátáját.
     *
     * @return int a szereplõ labirintusbeli pozíciójának sor koordinátája.
     */
    public int sor() {
        
        return sor;
        
    }

    public String toString() {
    
        return "SZEREPLÕ oszlop = " 
                + oszlop
                + " sor = "
                + sor;
    }    
}                
