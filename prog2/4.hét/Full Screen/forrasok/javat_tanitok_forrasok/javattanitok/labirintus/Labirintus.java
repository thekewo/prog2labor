/*
 * Labirintus.java
 *
 * DIGIT 2005, Javat tan�tok
 * B�tfai Norbert, nbatfai@inf.unideb.hu
 *
 */
package javattanitok.labirintus;
/**
 * A labirintust le�r� oszt�ly.
 *
 * @author B�tfai Norbert, nbatfai@inf.unideb.hu
 * @version 0.0.1
 */
public class Labirintus {
    /** A labirintus sz�less�ge. */
    protected int sz�less�g;
    /** A labirintus magass�ga. */
    protected int magass�g;
    /** A labirintus szerkezete: hol van fal, hol j�rat. */
    protected boolean[][] szerkezet;
    /** A falat a true �rt�k jelenti. */
    final static boolean FAL = true;
    /** Milyen �llapotban van �ppen a j�t�k. */
    protected int j�t�k�llapot = 0;
    /** Norm�l m�k�d�s, a h�ssel id�k�zben semmi nem t�rt�nt. */
    public static final int J�T�K_MEGY_H�S_RENDBEN = 0;    
    /** A h�st �ppen megett�k, de m�g van �lete. */
    public final static int J�T�K_MEGY_MEGHALT_H�S = 1;
    /** V�ge a j�t�knak, a j�t�kos gy�z�tt. */
    public final static int J�T�K_V�GE_MINDEN_KINCS_MEGVAN = 2;
    /** V�ge a j�t�knak, a j�t�kos vesztett. */
    public final static int J�T�K_V�GE_MEGHALT_H�S = 3;
    /** A labirintus kincsei. */
    protected Kincs [] kincsek;
    /** A labirintus sz�rnyei. */
    protected Sz�rny [] sz�rnyek;
    /**
     * L�trehoz egy megadott szerkezet�
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
        
        magass�g = szerkezet.length;
        sz�less�g = szerkezet[0].length;
        
    }
    /**
     * L�trehoz egy param�terben kapott szerkezet� <code>Labirintus</code> 
     * objektumot.
     *
     * @param      kincsekSz�ma       a kincsek sz�ma a labirintusban.
     * @param      sz�rnyekSz�ma      a sz�rnyek sz�ma a labirintusban.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� t�mb 
     * nincs elk�sz�tve.
     */
    public Labirintus(boolean[][] szerkezet, int kincsekSz�ma, int sz�rnyekSz�ma)
    throws RosszLabirintusKiv�tel {
        
        if(szerkezet == null)
            throw new RosszLabirintusKiv�tel("A labirintust defini�l� t�mb nincs elk�sz�tve.");
        
        this.szerkezet = szerkezet;
        
        magass�g = szerkezet.length;
        sz�less�g = szerkezet[0].length;
        
        kincsekSz�rnyek(kincsekSz�ma, sz�rnyekSz�ma);
        
    }
    /**
     * L�trehoz egy megadott m�ret�, v�letlen szerkezet�
     * <code>Labirintus</code> objektumot.
     *
     * @param      sz�less�g          a labirintus sz�less�ge.
     * @param      magass�g           a labirintus magass�ga.
     * @param      kincsekSz�ma       a kincsek sz�ma a labirintusban.
     * @param      sz�rnyekSz�ma      a sz�rnyek sz�ma a labirintusban.
     */
    public Labirintus(int sz�less�g, int magass�g,
            int kincsekSz�ma, int sz�rnyekSz�ma) {
        
        this.magass�g = magass�g;
        this.sz�less�g = sz�less�g;
        
        szerkezet = new boolean[magass�g][sz�less�g];
        java.util.Random v�letlenGener�tor = new java.util.Random();
        
        for(int i=0; i<magass�g; ++i)
            for(int j=0; j<sz�less�g; ++j)
                if(v�letlenGener�tor.nextInt()%3 == 0)
                    // a labirintus egy harmada lesz fal
                    szerkezet[magass�g][sz�less�g] = false;
                else
                    // k�t harmada pedig j�rat
                    szerkezet[magass�g][sz�less�g] = true;
        
        kincsekSz�rnyek(kincsekSz�ma, sz�rnyekSz�ma);
        
    }
    /**
     * L�trehoz egy 10x10-es, be�p�tett szerkezet� <code>Labirintus</code>
     * objektumot.
     *
     * @param      kincsekSz�ma       a kincsek sz�ma a labirintusban.
     * @param      sz�rnyekSz�ma      a sz�rnyek sz�ma a labirintusban.
     */
    public Labirintus(int kincsekSz�ma, int sz�rnyekSz�ma) {
        
        this();
        
        magass�g = szerkezet.length;
        sz�less�g = szerkezet[0].length;
        
        kincsekSz�rnyek(kincsekSz�ma, sz�rnyekSz�ma);
        
    }
    /**
     * Egy megfelel� szerkezet� sz�veges �llom�nyb�l elk�sz�t egy �j a 
     * <code>Labirintus</code> objektumot.
     * A sz�veges �llom�ny szerkezete a k�vetkez�:
     * <pre>
     * // A labirintus szerkezet�t megad� �llom�ny, szerkezete a k�vetkez�:
     * // a kincsek sz�ma
     * // a sz�rnyek sz�ma
     * // a labirintus sz�less�ge
     * // magass�ga
     * // fal=1 j�rat=0 ...
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
     * @param      labirintusF�jlN�v       a labirintust defini�l�, megfelel� 
     * szerkezet� sz�veges �llom�ny neve.
     * @exception  RosszLabirintusKiv�tel  ha a labirintust defini�l� �llom�ny 
     * nincs meg, nem a megfelel� szerkezet�, vagy gond van az olvas�s�val.
     */
    public Labirintus(String labirintusF�jlN�v) throws RosszLabirintusKiv�tel {
        
        int kincsekSz�ma = 6;  // ezeknek a kezd��rt�keknek nincs jelent�s�ge,
        int sz�rnyekSz�ma = 3; // mert majd a f�jlb�l olvassuk be, amiben ha a 
        // n�gy f� adat hib�s, akkor nem is �p�tj�k fel a labirintust.
        
        // Csatorna a sz�veges �llom�ny olvas�s�hoz
        java.io.BufferedReader sz�vegesCsatorna = null;
        
        try {
            sz�vegesCsatorna = new java.io.BufferedReader(
                    new java.io.FileReader(labirintusF�jlN�v));
            
            String sor = sz�vegesCsatorna.readLine();
            
            while(sor.startsWith("//"))
                sor = sz�vegesCsatorna.readLine();
            
            try {
                
                kincsekSz�ma = Integer.parseInt(sor);
                
                sor = sz�vegesCsatorna.readLine();
                sz�rnyekSz�ma = Integer.parseInt(sor);
                
                sor = sz�vegesCsatorna.readLine();
                sz�less�g = Integer.parseInt(sor);
                
                sor = sz�vegesCsatorna.readLine();
                magass�g = Integer.parseInt(sor);
                
                szerkezet = new boolean[magass�g][sz�less�g];
                
            } catch(java.lang.NumberFormatException e) {
                
                throw new RosszLabirintusKiv�tel("Hib�s a kincsek, sz�rnyek sz�ma, sz�less�g, magass�g megad�si r�sz.");
                
            }
            
            for(int i=0; i<magass�g; ++i) {
                
                sor = sz�vegesCsatorna.readLine();
                
                java.util.StringTokenizer st =
                        new java.util.StringTokenizer(sor);
                
                for(int j=0; j<sz�less�g; ++j) {
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
            
            throw new RosszLabirintusKiv�tel("Nincs meg a f�jl: " + e1);
            
        } catch(java.io.IOException e2) {
            
            throw new RosszLabirintusKiv�tel("IO kiv�tel t�rt�nt: "+e2);
            
        } catch(java.util.NoSuchElementException e3) {
            
            throw new RosszLabirintusKiv�tel("Nem j� a labirintus szerkezete: "
                    +e3);
            
        } finally {
            
            if(sz�vegesCsatorna != null) {
                
                try{
                    sz�vegesCsatorna.close();
                } catch(Exception e) {}
                
            }
            
        }
        
        // Ha ide eljutottunk, akkor fel�p�lt a labirintus,
        // lehet ben�pes�teni:
        kincsekSz�rnyek(kincsekSz�ma, sz�rnyekSz�ma);
        
    }
    /**
     * L�trehozza a kincseket �s a sz�rnyeket.
     *
     * @param      kincsekSz�ma       a kincsek sz�ma a labirintusban.
     * @param      sz�rnyekSz�ma      a sz�rnyek sz�ma a labirintusban.
     */
    private void kincsekSz�rnyek(int kincsekSz�ma, int sz�rnyekSz�ma) {
        // Kincsek l�trehoz�sa
        kincsek = new Kincs[kincsekSz�ma];
        for(int i=0; i<kincsek.length; ++i)
            kincsek[i] = new Kincs(this, (i+1)*100);
        // Sz�rnyek l�trehoz�sa
        sz�rnyek = new Sz�rny[sz�rnyekSz�ma];
        for(int i=0; i<sz�rnyek.length; ++i)
            sz�rnyek[i] = new Sz�rny(this);
        
    }
    /**
     * Megadja a j�t�k aktu�lis �llapot�t.
     *
     * @return int a j�t�k aktu�lis �llapota.
     */
    public int �llapot() {
        
        return j�t�k�llapot;
        
    }
    /**
     * A labirintus mikrovil�g �let�nek egy pillanata: megn�zi, hogy a bolyong�
     * h�s r�tal�lt-e a kincsekre, vagy a sz�rnyek a h�sre. Ennek megfelel�en
     * megv�ltozik a j�t�k �llapota.
     *
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
                j�t�k�llapot = J�T�K_MEGY_MEGHALT_H�S;
                
                if(h�s.megettek())
                    j�t�k�llapot = J�T�K_V�GE_MEGHALT_H�S;
                
                return j�t�k�llapot;
            }
            
        }
        
        return J�T�K_MEGY_H�S_RENDBEN;
    }
    /**
     * Madadja, hogy fal-e a labirintus adott oszlop, sor poz�ci�ja.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha a poz�ci� fal vagy nincs a labirintusban.
     */
    public boolean fal(int oszlop, int sor) {
        
        if(!(oszlop >= 0 && oszlop <= sz�less�g-1
                && sor >= 0 && sor <= magass�g-1))
            return FAL;
        else
            return szerkezet[sor][oszlop] == FAL;
        
    }
    /**
     * Madadja a labirintus sz�less�g�t.
     *
     * @return int a labirintus sz�less�ge.
     */
    public int sz�less�g() {
        
        return sz�less�g;
        
    }
    /**
     * Madadja a labirintus magass�g�t.
     *
     * @return int a labirintus magass�ga.
     */
    public int magass�g() {
        
        return magass�g;
        
    }
    /**
     * Megadja a labirintus szerkezet�t.
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
     * Megadja a labirintus sz�rnyeit.
     *
     * @return Sz�rny[] a labirintus sz�rnyei.
     */
    public Sz�rny[] sz�rnyek() {
        
        return sz�rnyek;
        
    }
    /**
     * Kinyomtatja a labirintus szerkezet�t a System.out-ra.
     */
    public void nyomtat() {
        
        for(int i=0; i<magass�g; ++i) {
            for(int j=0; j<sz�less�g; ++j) {
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else
                    System.out.print("|   ");
                
            }
            
            System.out.println();
            
        }
        
    }
    /**
     * Kinyomtatja a labirintus szerkezet�t �s szerepl�it a System.out-ra.
     *
     * @param h�s akit szint�n belenyomtat a labirintusba.
     */
    public void nyomtat(H�s h�s) {
        
        for(int i=0; i<magass�g; ++i) {
            for(int j=0; j<sz�less�g; ++j) {
                
                boolean vanSz�rny = vanSz�rny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanH�s = (i == h�s.sor() && j == h�s.oszlop());
                
                if(szerkezet[i][j])
                    System.out.print("|FAL");
                else if(vanSz�rny && vanKincs && vanH�s)
                    System.out.print("|SKH");
                else if(vanSz�rny && vanKincs)
                    System.out.print("|SK ");
                else if(vanKincs && vanH�s)
                    System.out.print("|KH ");
                else if(vanSz�rny && vanH�s)
                    System.out.print("|SH ");
                else if(vanKincs)
                    System.out.print("|K  ");
                else if(vanH�s)
                    System.out.print("|H  ");
                else if(vanSz�rny)
                    System.out.print("|S  ");
                else
                    System.out.print("|   ");
                
            }
            
            System.out.println();
            
        }
        
    }
    /**
     * Kinyomtatja a labirintus szerkezet�t �s szerepl�it egy
     * karakteres csatorn�ba.
     *
     * @param h�s akit szint�n belenyomtat a labirintusba.
     * @param csatorna ahova nyomtatunk.
     */
    public void nyomtat(H�s h�s, java.io.PrintWriter csatorna) {
        
        for(int i=0; i<magass�g; ++i) {
            for(int j=0; j<sz�less�g; ++j) {
                
                boolean vanSz�rny = vanSz�rny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanH�s = (i == h�s.sor() && j == h�s.oszlop());
                
                if(szerkezet[i][j])
                    csatorna.print("|FAL");
                else if(vanSz�rny && vanKincs && vanH�s)
                    csatorna.print("|SKH");
                else if(vanSz�rny && vanKincs)
                    csatorna.print("|SK ");
                else if(vanKincs && vanH�s)
                    csatorna.print("|KH ");
                else if(vanSz�rny && vanH�s)
                    csatorna.print("|SH ");
                else if(vanKincs)
                    csatorna.print("|K  ");
                else if(vanH�s)
                    csatorna.print("|H  ");
                else if(vanSz�rny)
                    csatorna.print("|S  ");
                else
                    csatorna.print("|   ");
                
            }
            
            csatorna.println();
            
        }
        
    }
    /**
     * Kinyomtatja a labirintus szerkezet�t �s szerepl�it egy sztringbe.
     *
     * @param h�s akit szint�n belenyomtat a labirintusba.
     * @return String a kinyomtatott labirintus
     */
    public String kinyomtat(H�s h�s) {
        
        StringBuffer stringBuffer = new StringBuffer();
        
        for(int i=0; i<magass�g; ++i) {
            for(int j=0; j<sz�less�g; ++j) {
                
                boolean vanSz�rny = vanSz�rny(i, j);
                boolean vanKincs = vanKincs(i, j);
                boolean vanH�s = (i == h�s.sor() && j == h�s.oszlop());
                
                if(szerkezet[i][j])
                    stringBuffer.append("|FAL");
                else if(vanSz�rny && vanKincs && vanH�s)
                    stringBuffer.append("|SKH");
                else if(vanSz�rny && vanKincs)
                    stringBuffer.append("|SK ");
                else if(vanKincs && vanH�s)
                    stringBuffer.append("|KH ");
                else if(vanSz�rny && vanH�s)
                    stringBuffer.append("|SH ");
                else if(vanKincs)
                    stringBuffer.append("|K  ");
                else if(vanH�s)
                    stringBuffer.append("|H  ");
                else if(vanSz�rny)
                    stringBuffer.append("|S  ");
                else
                    stringBuffer.append("|   ");
                
            }
            
            stringBuffer.append("\n");
            
        }
        
        return stringBuffer.toString();
    }
    /**
     * Madadja, hogy van-e megtal�lhat� kincs a labirintus
     * adott oszlop, sor poz�ci�ja.
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
            && !kincsek[i].megtal�lva()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * Madadja, hogy van-e sz�rny a labirintus adott oszlop,
     * sor poz�ci�ja.
     *
     * @param oszlop a labirintus adott oszlopa
     * @param sor a labirintus adott sora
     * @return true ha van.
     */
    boolean vanSz�rny(int sor, int oszlop) {
        
        boolean van = false;
        
        for(int i=0; i<sz�rnyek.length; ++i)
            if(sor == sz�rnyek[i].sor()
            && oszlop == sz�rnyek[i].oszlop()) {
            van = true;
            break;
            }
        
        return van;
    }
    /**
     * A labirintussal kapcsolatos apr�s�gok �n�ll� kipr�b�l�s�ra
     * szolg�l ez az ind�t� met�dus.
     *
     * @param args parancssor-argumentumok nincsenek.
     */
    public static void main(String[] args) {
        
        Labirintus labirintus = new Labirintus(6, 3);
        H�s h�s = new H�s(labirintus);
        
        System.out.println(labirintus.getClass());
        System.out.println(h�s.getClass());
        
    }
}
