/*
 * Labirintus.java
 *
 * DIGIT 2005, Javat tanítok
 * Bátfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintust leíró osztály.
 *
 * @author Bátfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class Labirintus {
    /** A labirintus szélessége. */
    protected int szélesség;
    /** A labirintus magassága. */
    protected int magasság;
    /** A labirintus szerkezete: hol van fal, hol járat. */
    protected boolean[][] szerkezet;
    /** A falat a true érték jelenti. */
    final static boolean FAL = true;
    /** Milyen állapotban van éppen a játék. */
    protected int játékÁllapot = 0;
    /** Normál működés, a hőssel időközben semmi nem történt. */
    public static final int JÁTÉK_MEGY_HŐS_RENDBEN = 0;    
    /** A hőst éppen megették, de még van élete. */
    public final static int JÁTÉK_MEGY_MEGHALT_HŐS = 1;
    /** Vége a játéknak, a játékos győzött. */
    public final static int JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN = 2;
    /** Vége a játéknak, a játékos vesztett. */
    public final static int JÁTÉK_VÉGE_MEGHALT_HŐS = 3;
    /** A labirintus kincsei. */
    protected Kincs [] kincsek;
    /** A labirintus szörnyei. */
    protected Szörny [] szörnyek;
    /**
     * Létrehoz egy megadott szerkezetű
     * {@code Labirintus} objektumot.
     */
    public Labirintus() {
        szerkezet = new boolean[][]{
            
    {false, false,  false, true,  false, true,  false, true,  true,  true },
    {false, false, false, false, false, false, false, false, false, false},
    {true,  false, true,  false, true,  false, true,  false, true,  false},
    {false, false, false, false, true,  false, true,  false, false, false},
    {false, true,  true,  false, false, false, true,  true,  false, true },
    {false, false, false, false, true,  false, false, false, false, false},
    {false,  true,  false, false,  false, true,  false, true,  true,  false},
    {false,  false, false, true,  false, true,  false, true,  false, false},
    {false, true, false, false, false, false, false, false, false, true },
    {false, false, false, false,  true,  false, false, false,  true,  true }
    
        };
        
        magasság = szerkezet.length;
        szélesség = szerkezet[0].length;
        
    }
    /**
     * Létrehoz egy paraméterben kapott szerkezetű <code>Labirintus</code> 
     * objektumot.
     *
     * @param      kincsekSzáma       a kincsek száma a labirintusban.
     * @param      szörnyekSzáma      a szörnyek száma a labirintusban.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló tömb 
     * nincs elkészítve.
     */
    public Labirintus(boolean[][] szerkezet, int kincsekSzáma, int szörnyekSzáma)
    throws RosszLabirintusKivétel {
        
        if(szerkezet == null)
            throw new RosszLabirintusKivétel("A labirintust definiáló tömb nincs elkészítve.");
        
        this.szerkezet = szerkezet;
        
        magasság = szerkezet.length;
        szélesség = szerkezet[0].length;
        
        kincsekSzörnyek(kincsekSzáma, szörnyekSzáma);
        
    }
    /**
     * Létrehoz egy megadott méretű, véletlen szerkezetű
     * <code>Labirintus</code> objektumot.
     *
     * @param      szélesség          a labirintus szélessége.
     * @param      magasság           a labirintus magassága.
     * @param      kincsekSzáma       a kincsek száma a labirintusban.
     * @param      szörnyekSzáma      a szörnyek száma a labirintusban.
     */
    public Labirintus(int szélesség, int magasság,
            int kincsekSzáma, int szörnyekSzáma) {
        
        this.magasság = magasság;
        this.szélesség = szélesség;
        
        szerkezet = new boolean[magasság][szélesség];
        java.util.Random véletlenGenerátor = new java.util.Random();
        
        for(int i=0; i<magasság; ++i)
            for(int j=0; j<szélesség; ++j)
                if(véletlenGenerátor.nextInt()%3 == 0)
                    // a labirintus egy harmada lesz fal
                    szerkezet[magasság][szélesség] = false;
                else
                    // két harmada pedig járat
                    szerkezet[magasság][szélesség] = true;
        
        kincsekSzörnyek(kincsekSzáma, szörnyekSzáma);
        
    }
    /**
     * Létrehoz egy 10x10-es, beépített szerkezetű <code>Labirintus</code>
     * objektumot.
     *
     * @param      kincsekSzáma       a kincsek száma a labirintusban.
     * @param      szörnyekSzáma      a szörnyek száma a labirintusban.
     */
    public Labirintus(int kincsekSzáma, int szörnyekSzáma) {
        
        this();
        
        magasság = szerkezet.length;
        szélesség = szerkezet[0].length;
        
        kincsekSzörnyek(kincsekSzáma, szörnyekSzáma);
        
    }
    /**
     * Egy megfelelő szerkezetű szöveges állományból elkészít egy új a 
     * <code>Labirintus</code> objektumot.
     * A szöveges állomány szerkezete a következő:
     * <pre>
     * // A labirintus szerkezetét megadó állomány, szerkezete a következő:
     * // a kincsek száma
     * // a szörnyek száma
     * // a labirintus szélessége
     * // magassága
     * // fal=1 járat=0 ...
     * // .
     * // .
     * // .
     * 6
     * 3
     * 10
     * 10
     * 0 0 0 1 0 1 0 1 1 1
     * 0 0 0 0 0 0 0 0 0 0
     * 1 0 1 0 1 0 1 0 1 0
     * 0 0 0 0 1 0 1 0 0 0
     * 0 1 1 0 0 0 1 1 0 1
     * 0 0 0 0 1 0 0 0 0 0
     * 0 1 0 0 0 1 0 1 1 0
     * 0 0 0 1 0 1 0 1 0 0
     * 0 1 0 0 0 0 0 0 0 1
     * 0 0 0 0 1 0 0 0 1 1
     * </pre>
     *
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelő 
     * szerkezetű szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány 
     * nincs meg, nem a megfelelő szerkezetű, vagy gond van az olvasásával.
     */
    public Labirintus(String labirintusFájlNév) throws RosszLabirintusKivétel {
        
        int kincsekSzáma = 6;  // ezeknek a kezdőértékeknek nincs jelentősége,
        int szörnyekSzáma = 3; // mert majd a fájlból olvassuk be, amiben ha a 
        // négy fő adat hibás, akkor nem is építjük fel a labirintust.
        
        // Csatorna a szöveges állomány olvasásához
        java.io.BufferedReader szövegesCsatorna = null;
        
        try {
            szövegesCsatorna = new java.io.BufferedReader(
                    new java.io.FileReader(labirintusFájlNév));
            
            String sor = szövegesCsatorna.readLine();
            
            while(sor.startsWith("//"))
                sor = szövegesCsatorna.readLine();
            
            try {
                
                kincsekSzáma = Integer.parseInt(sor);
                
                sor = szövegesCsatorna.readLine();
                szörnyekSzáma = Integer.parseInt(sor);
                
                sor = szövegesCsatorna.readLine();
                szélesség = Integer.parseInt(sor);
                
                sor = szövegesCsatorna.readLine();
                magasság = Integer.parseInt(sor);
                
                szerkezet = new boolean[magasság][szélesség];
                
            } catch(java.lang.NumberFormatException e) {
                
                throw new RosszLabirintusKivétel("Hibás a kincsek, szörnyek száma, szélesség, magasság megadási rész.");
                
            }
            
            for(int i=0; i<magasság; ++i) {
                
                sor = szövegesCsatorna.readLine();
                
                java.util.StringTokenizer st =
                        new java.util.StringTokenizer(sor);
                
                for(int j=0; j<szélesség; ++j) {
                    String tegla = st.nextToken();
                    
                    try {
                        
                        if(Integer.parseInt(tegla) == 0)
                            szerkezet[i][j] = false;
                        else
                            szerkezet[i][j] = true;
                        
                    } catch(java.lang.NumberFormatException e) {
                        
                        System.out.println(i+". sor "+j+". oszlop "+e);
                        szerkezet[i][j] = false;
                        
                    }
                }
            }
            
        } catch(java.io.FileNotFoundException e1) {
            
            throw new RosszLabirintusKivétel("Nincs meg a fájl: " + e1);
            
        } catch(java.io.IOException e2) {
            
            throw new RosszLabirintusKivétel("IO kivétel történt: "+e2);
            
        } catch(java.util.NoSuchElementException e3) {
            
            throw new RosszLabirintusKivétel("Nem jó a labirintus szerkezete: "
                    +e3);
            
        } finally {
            
            if(szövegesCsatorna != null) {
                
                try{
                    szövegesCsatorna.close();
                } catch(Exception e) {}
                
            }
            
        }
        
        // Ha ide eljutottunk, akkor felépült a labirintus,
        // lehet benépesíteni:
        kincsekSzörnyek(kincsekSzáma, szörnyekSzáma);
        
    }
    /**
     * Létrehozza a kincseket és a szörnyeket.
     *
     * @param      kincsekSzáma       a kincsek száma a labirintusban.
     * @param      szörnyekSzáma      a szörnyek száma a labirintusban.
     */
    private void kincsekSzörnyek(int kincsekSzáma, int szörnyekSzáma) {
        // Kincsek létrehozása
        kincsek = new Kincs[kincsekSzáma];
        for(int i=0; i<kincsek.length; ++i)
            kincsek[i] = new Kincs(this, (i+1)*100);
        // Szörnyek létrehozása
        szörnyek = new Szörny[szörnyekSzáma];
        for(int i=0; i<szörnyek.length; ++i)
            szörnyek[i] = new Szörny(this);
        
    }
    /**
     * Megadja a játék aktuális állapotát.
     *
     * @return int a játék aktuális állapota.
     */
    public int állapot() {
        
        return játékÁllapot;
        
    }
    /**
     * A labirintus mikrovilág életének egy pillanata: megnézi, hogy a bolyongó
     * hős rátalált-e a kincsekre, vagy a szörnyek a hősre. Ennek megfelelően
     * megváltozik a játék állapota.
     *
     * @param hős aki a labirintusban bolyong.
     * @return int a játék állapotát leíró kód.
     */
    public int bolyong(Hős hős) {
        
        boolean mindMegvan = true;
        
        for(int i=0; i < kincsek.length; ++i) {
            
            // A hős rátalált valamelyik kincsre?
            if(kincsek[i].megtalált(hős))
                hős.megtaláltam(kincsek[i]);
            
            // ha ez egyszer is teljesül, akkor nincs minden kincs megtalálva
            if(!kincsek[i].megtalálva())
                mindMegvan = false;
            
        }
        
        if(mindMegvan) {
            
            játékÁllapot = JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN;
            return játékÁllapot;
            
        }
        
        for(int i=0; i < szörnyek.length; ++i) {
            
            szörnyek[i].lép(hős);
            
            if(szörnyek[i].megesz(hős))  {
                játékÁllapot = JÁTÉK_MEGY_MEGHALT_HŐS;
                
                if(hős.megettek())
                    játékÁllapot = JÁTÉK_VÉGE_MEGHALT_HŐS;
                
                return játékÁllapot;
            }
            
        }
        
        return JÁTÉK_MEGY_HŐS_RENDBEN;
    }
    /**
     * Madadja, hogy fal-e a labirintus adott oszlop, sor pozíciója.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha a pozíció fal vagy nincs a labirintusban.
     */
    public boolean fal(int oszlop, int sor) {
        
        if(!(oszlop >= 0 && oszlop <= szélesség-1
                && sor >= 0 && sor <= magasság-1))
            return FAL;
        else
            return szerkezet[sor][oszlop] == FAL;
        
    }
    /**
     * Madadja a labirintus szélességét.
     *
     * @return int a labirintus szélessége.
     */
    public int szélesség() {
        
        return szélesség;
        
    }
    /**
     * Madadja a labirintus magasságát.
     *
     * @return int a labirintus magassága.
     */
    public int magasság() {
        
        return magasság;
        
    }
    /**
     * Megadja a labirintus szerkezetét.
     *
     * @return boolean[][] a labirintus szerkezete.
     */
    public boolean[][] szerkezet() {
        
        return szerkezet;
        
    }
    /**
     * Megadja a labirintus kincseit.
     *
     * @return Kincs[] a labirintus kincsei.
     */
    public Kincs[] kincsek() {
        
        return kincsek;
        
    }
    /**
     * Megadja a labirintus szörnyeit.
     *
     * @return Szörny[] a labirintus szörnyei.
     */
    public Szörny[] szörnyek() {
        
        return szörnyek;
        
    }
    /**
     * Kinyomtatja a labirintus szerkezetét a System.out-ra.
     */
    public void nyomtat() {
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else
                    System.out.print("|   ");
                
            }
            
            System.out.println();
            
        }
        
    }
    /**
     * Kinyomtatja a labirintus szerkezetét és szereplőit a System.out-ra.
     *
     * @param hős akit szintén belenyomtat a labirintusba.
     */
    public void nyomtat(Hős hős) {
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHős = (i == hős.sor() && j == hős.oszlop());
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else if(vanSzörny && vanKincs && vanHős)
                    System.out.print("|SKH");
                else if(vanSzörny && vanKincs)
                    System.out.print("|SK ");
                else if(vanKincs && vanHős)
                    System.out.print("|KH ");
                else if(vanSzörny && vanHős)
                    System.out.print("|SH ");
                else if(vanKincs)
                    System.out.print("|K  ");
                else if(vanHős)
                    System.out.print("|H  ");
                else if(vanSzörny)
                    System.out.print("|S  ");
                else
                    System.out.print("|   ");
                
            }
            
            System.out.println();
            
        }
        
    }
    /**
     * Kinyomtatja a labirintus szerkezetét és szereplőit egy
     * karakteres csatornába.
     *
     * @param hős akit szintén belenyomtat a labirintusba.
     * @param csatorna ahova nyomtatunk.
     */
    public void nyomtat(Hős hős, java.io.PrintWriter csatorna) {
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHős = (i == hős.sor() && j == hős.oszlop());
                
                if(szerkezet[i][j])
                    csatorna.print("|FAL");
                else if(vanSzörny && vanKincs && vanHős)
                    csatorna.print("|SKH");
                else if(vanSzörny && vanKincs)
                    csatorna.print("|SK ");
                else if(vanKincs && vanHős)
                    csatorna.print("|KH ");
                else if(vanSzörny && vanHős)
                    csatorna.print("|SH ");
                else if(vanKincs)
                    csatorna.print("|K  ");
                else if(vanHős)
                    csatorna.print("|H  ");
                else if(vanSzörny)
                    csatorna.print("|S  ");
                else
                    csatorna.print("|   ");
                
            }
            
            csatorna.println();
            
        }
        
    }
    /**
     * Kinyomtatja a labirintus szerkezetét és szereplőit egy sztringbe.
     *
     * @param hős akit szintén belenyomtat a labirintusba.
     * @return String a kinyomtatott labirintus
     */
    public String kinyomtat(Hős hős) {
        
        StringBuffer stringBuffer = new StringBuffer();
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHős = (i == hős.sor() && j == hős.oszlop());
                
                if(szerkezet[i][j])
                    stringBuffer.append("|FAL");
                else if(vanSzörny && vanKincs && vanHős)
                    stringBuffer.append("|SKH");
                else if(vanSzörny && vanKincs)
                    stringBuffer.append("|SK ");
                else if(vanKincs && vanHős)
                    stringBuffer.append("|KH ");
                else if(vanSzörny && vanHős)
                    stringBuffer.append("|SH ");
                else if(vanKincs)
                    stringBuffer.append("|K  ");
                else if(vanHős)
                    stringBuffer.append("|H  ");
                else if(vanSzörny)
                    stringBuffer.append("|S  ");
                else
                    stringBuffer.append("|   ");
                
            }
            
            stringBuffer.append("\n");
            
        }
        
        return stringBuffer.toString();
    }
    /**
     * Madadja, hogy van-e megtalálható kincs a labirintus
     * adott oszlop, sor pozíciója.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha van.
     */
    boolean vanKincs(int sor, int oszlop) {
        
        boolean van = false;
        
        for(int i=0; i<kincsek.length; ++i)
            if(sor == kincsek[i].sor()
            && oszlop == kincsek[i].oszlop()
            && !kincsek[i].megtalálva()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * Madadja, hogy van-e szörny a labirintus adott oszlop,
     * sor pozíciója.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha van.
     */
    boolean vanSzörny(int sor, int oszlop) {
        
        boolean van = false;
        
        for(int i=0; i<szörnyek.length; ++i)
            if(sor == szörnyek[i].sor()
            && oszlop == szörnyek[i].oszlop()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * A labirintussal kapcsolatos apróságok önálló kipróbálására
     * szolgál ez az indító metódus.
     *
     * @param args parancssor-argumentumok nincsenek.
     */
    public static void main(String[] args) {
        
        Labirintus labirintus = new Labirintus(6, 3);
        Hős hős = new Hős(labirintus);
        
        System.out.println(labirintus.getClass());
        System.out.println(hős.getClass());
        
    }
}