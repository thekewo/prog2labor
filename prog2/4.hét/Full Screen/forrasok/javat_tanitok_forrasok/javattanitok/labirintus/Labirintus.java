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
    /** Normál mûködés, a hõssel idõközben semmi nem történt. */
    public static final int JÁTÉK_MEGY_HÕS_RENDBEN = 0;    
    /** A hõst éppen megették, de még van élete. */
    public final static int JÁTÉK_MEGY_MEGHALT_HÕS = 1;
    /** Vége a játéknak, a játékos gyõzött. */
    public final static int JÁTÉK_VÉGE_MINDEN_KINCS_MEGVAN = 2;
    /** Vége a játéknak, a játékos vesztett. */
    public final static int JÁTÉK_VÉGE_MEGHALT_HÕS = 3;
    /** A labirintus kincsei. */
    protected Kincs [] kincsek;
    /** A labirintus szörnyei. */
    protected Szörny [] szörnyek;
    /**
     * Létrehoz egy megadott szerkezetû
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
     * Létrehoz egy paraméterben kapott szerkezetû <code>Labirintus</code> 
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
     * Létrehoz egy megadott méretû, véletlen szerkezetû
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
     * Létrehoz egy 10x10-es, beépített szerkezetû <code>Labirintus</code>
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
     * Egy megfelelõ szerkezetû szöveges állományból elkészít egy új a 
     * <code>Labirintus</code> objektumot.
     * A szöveges állomány szerkezete a következõ:
     * <pre>
     * // A labirintus szerkezetét megadó állomány, szerkezete a következõ:
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
     * @param      labirintusFájlNév       a labirintust definiáló, megfelelõ 
     * szerkezetû szöveges állomány neve.
     * @exception  RosszLabirintusKivétel  ha a labirintust definiáló állomány 
     * nincs meg, nem a megfelelõ szerkezetû, vagy gond van az olvasásával.
     */
    public Labirintus(String labirintusFájlNév) throws RosszLabirintusKivétel {
        
        int kincsekSzáma = 6;  // ezeknek a kezdõértékeknek nincs jelentõsége,
        int szörnyekSzáma = 3; // mert majd a fájlból olvassuk be, amiben ha a 
        // négy fõ adat hibás, akkor nem is építjük fel a labirintust.
        
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
     * hõs rátalált-e a kincsekre, vagy a szörnyek a hõsre. Ennek megfelelõen
     * megváltozik a játék állapota.
     *
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
                játékÁllapot = JÁTÉK_MEGY_MEGHALT_HÕS;
                
                if(hõs.megettek())
                    játékÁllapot = JÁTÉK_VÉGE_MEGHALT_HÕS;
                
                return játékÁllapot;
            }
            
        }
        
        return JÁTÉK_MEGY_HÕS_RENDBEN;
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
     * Kinyomtatja a labirintus szerkezetét és szereplõit a System.out-ra.
     *
     * @param hõs akit szintén belenyomtat a labirintusba.
     */
    public void nyomtat(Hõs hõs) {
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHõs = (i == hõs.sor() && j == hõs.oszlop());
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else if(vanSzörny && vanKincs && vanHõs)
                    System.out.print("|SKH");
                else if(vanSzörny && vanKincs)
                    System.out.print("|SK ");
                else if(vanKincs && vanHõs)
                    System.out.print("|KH ");
                else if(vanSzörny && vanHõs)
                    System.out.print("|SH ");
                else if(vanKincs)
                    System.out.print("|K  ");
                else if(vanHõs)
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
     * Kinyomtatja a labirintus szerkezetét és szereplõit egy
     * karakteres csatornába.
     *
     * @param hõs akit szintén belenyomtat a labirintusba.
     * @param csatorna ahova nyomtatunk.
     */
    public void nyomtat(Hõs hõs, java.io.PrintWriter csatorna) {
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHõs = (i == hõs.sor() && j == hõs.oszlop());
                
                if(szerkezet[i][j])
                    csatorna.print("|FAL");
                else if(vanSzörny && vanKincs && vanHõs)
                    csatorna.print("|SKH");
                else if(vanSzörny && vanKincs)
                    csatorna.print("|SK ");
                else if(vanKincs && vanHõs)
                    csatorna.print("|KH ");
                else if(vanSzörny && vanHõs)
                    csatorna.print("|SH ");
                else if(vanKincs)
                    csatorna.print("|K  ");
                else if(vanHõs)
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
     * Kinyomtatja a labirintus szerkezetét és szereplõit egy sztringbe.
     *
     * @param hõs akit szintén belenyomtat a labirintusba.
     * @return String a kinyomtatott labirintus
     */
    public String kinyomtat(Hõs hõs) {
        
        StringBuffer stringBuffer = new StringBuffer();
        
        for(int i=0; i<magasság; ++i) {
            for(int j=0; j<szélesség; ++j) {
                
                boolean vanSzörny = vanSzörny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanHõs = (i == hõs.sor() && j == hõs.oszlop());
                
                if(szerkezet[i][j])
                    stringBuffer.append("|FAL");
                else if(vanSzörny && vanKincs && vanHõs)
                    stringBuffer.append("|SKH");
                else if(vanSzörny && vanKincs)
                    stringBuffer.append("|SK ");
                else if(vanKincs && vanHõs)
                    stringBuffer.append("|KH ");
                else if(vanSzörny && vanHõs)
                    stringBuffer.append("|SH ");
                else if(vanKincs)
                    stringBuffer.append("|K  ");
                else if(vanHõs)
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
        Hõs hõs = new Hõs(labirintus);
        
        System.out.println(labirintus.getClass());
        System.out.println(hõs.getClass());
        
    }
}
