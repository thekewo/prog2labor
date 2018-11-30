/*
 * Szereplő.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintus szereplőit (kincsek, szörnyek, hős) absztraháló osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1 
 * @see javattanitok.labirintus.Labirintus
 */
public class Szereplő {
    /** A szereplő oszlop pozíciója. */
    protected int oszlop;
    /** A szereplő sor pozíciója. */
    protected int sor;
    /** A labirintus, melyben a szereplő van. */
    protected Labirintus labirintus;
    /** A labirintus szélessége. */
    protected int maxSzélesség;
    /** A labirintus magassága. */
    protected int maxMagasság;
    /** Véletlenszám generátor a szereplők mozgatásához. */
    protected static java.util.Random véletlenGenerátor 
            = new java.util.Random();
    /**
     * Létrehoz egy <code>Szereplő</code> objektumot.
     *
     * @param      labirintus       amibe a szereplőt helyezzük.
     */
    public Szereplő(Labirintus labirintus) {
        this.labirintus = labirintus;
        maxSzélesség = labirintus.szélesség();
        maxMagasság = labirintus.magasság();
        // A szereplő kezdő pozíciója a labirintusban
        szereplőHelyeKezdetben();
    }
    /**
     * A szereplő labirintusbeli kezdő pozíciójának meghatározása.
     */
    void szereplőHelyeKezdetben() {
        // Többször próbálkozunk elhelyezni a szereplőt a labirintusban,
        // számolja, hol tartunk ezekkel a próbálkozásokkal:
        int számláló = 0;
        
        do {
            // itt +2,-2-k, hogy a bal alsó saroktól távol tartsuk
            // a szereplőket, mert majd ezt akarjuk a hős kezdő pozíciójának
            oszlop = 2+véletlenGenerátor.nextInt(maxSzélesség-2);
            sor = véletlenGenerátor.nextInt(maxMagasság-2);
            // max. 10-szer próbálkozunk, de ha sikerül nem "falba tenni" a
            // szereplőt, akkor máris kilépünk:
        } while(++számláló<10 && labirintus.fal(oszlop, sor));
        
    }
    /**
     * A szereplő felfelé lép. Ha nem tud, helyben marad.
     */
    public void lépFöl() {
        
        if(sor > 0)
            if(!labirintus.fal(oszlop, sor-1))
                --sor;        
    }
    /**
     * A szereplő lefelé lép. Ha nem tud, helyben marad.
     */
    public void lépLe() {
        
        if(sor < maxMagasság-1)
            if(!labirintus.fal(oszlop, sor+1))
                ++sor;
        
    }
    /**
     * A szereplő balra lép. Ha nem tud, helyben marad.
     */
    public void lépBalra() {
        
        if(oszlop > 0)
            if(!labirintus.fal(oszlop-1, sor))
                --oszlop;
        
    }
    /**
     * A szereplő jobbra lép. Ha nem tud, helyben marad.
     */
    public void lépJobbra() {
        
        if(oszlop < maxSzélesség-1)
            if(!labirintus.fal(oszlop+1, sor))
                ++oszlop;
        
    }
    /**
     * A szereplő (Euklideszi) távolsága egy másik szereplőtől a labirintusban.
     *
     * @param szereplő a másik szereplő
     * @return int távolság a másik szereplőtől.
     */
    public int távolság(Szereplő szereplő) {
        
        return (int)Math.sqrt((double)
        (oszlop - szereplő.oszlop)*(oszlop - szereplő.oszlop)
        + (sor - szereplő.sor)*(sor - szereplő.sor)
        );
        
    }
    /**
     * Egy pozíció (Euklideszi) távolsága egy másik szereplőtől a 
     * labirintusban.
     *
     * @param oszlop a pozíció oszlop koordinátája
     * @param sor a pozíció sor koordinátája
     * @param szereplő a másik szereplő
     * @return int távolság a másik szereplőtől.
     */
    public int távolság(int oszlop, int sor, Szereplő szereplő) {
        
        if(!(oszlop >= 0 && oszlop <= maxSzélesség-1
                && sor >= 0 && sor <= maxMagasság-1))
            return Integer.MAX_VALUE;
        else
            return (int)Math.sqrt((double)
            (oszlop - szereplő.oszlop)*(oszlop - szereplő.oszlop)
            + (sor - szereplő.sor)*(sor - szereplő.sor)
            );
        
    }
    /**
     * Beállítja a szereplő labirintusbeli pozíciójának oszlop 
     * koordinátáját.
     *
     * @param oszlop a szereplő labirintusbeli pozíciójának oszlop 
     * koordinátája.
     */
    public void oszlop(int oszlop) {
        
        this.oszlop = oszlop;
        
    }
    /**
     * Beállítja a szereplő labirintusbeli pozíciójának sor koordinátáját.
     *
     * @param sor a szereplő labirintusbeli pozíciójának sor koordinátája.
     */
    public void sor(int sor) {
        
        this.sor = sor;
        
    }
    /**
     * Megadja a szereplő labirintusbeli pozíciójának oszlop koordinátáját.
     *
     * @return int a szereplő labirintusbeli pozíciójának oszlop koordinátája.
     */
    public int oszlop() {
        
        return oszlop;
        
    }
    /**
     * Megadja a szereplő labirintusbeli pozíciójának sor koordinátáját.
     *
     * @return int a szereplő labirintusbeli pozíciójának sor koordinátája.
     */
    public int sor() {
        
        return sor;
        
    }

    public String toString() {
    
        return "SZEREPLŐ oszlop = " 
                + oszlop
                + " sor = "
                + sor;
    }    
}  