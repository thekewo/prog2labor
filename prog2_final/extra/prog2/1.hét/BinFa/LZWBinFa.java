//import org.graalvm.compiler.core.common.type.ArithmeticOpTable.UnaryOp.Math.sqrt;
import java.lang.Math;

public class LZWBinFa{
    LZWBinFa(){
        this.gyoker = new Csomopont('/');
        this.fa = gyoker;
    }

    public Csomopont getGyoker(){
        return gyoker;
    }
    int getMelyseg(){
        melyseg = maxMelyseg = 0;
        rmelyseg(gyoker);
        return maxMelyseg - 1;
    }

    double getAtlag(){
        melyseg = atlagosszeg = atlagdb = 0;
        ratlag(gyoker);
        atlag = ((double) atlagosszeg) / atlagdb;
        return atlag;
    } 

    double getSzoras(){
        atlag = getAtlag();
        szorasosszeg = 0.0;
        melyseg = atlagdb = 0;

        rszoras(gyoker);

        if(atlagdb - 1 > 0){
            szoras = Math.sqrt(szorasosszeg / (atlagdb -1));
        }
        else{
            szoras = Math.sqrt(szorasosszeg);
        }
        return szoras;
    }

    Csomopont fa;
    Csomopont gyoker;
    int melyseg, atlagosszeg, atlagdb;
    double szorasosszeg;
    int maxMelyseg;
    double atlag, szoras;
    void rmelyseg(Csomopont elem){
        if(elem != null){
            melyseg++;
            if(melyseg > maxMelyseg){
                maxMelyseg = melyseg;
            }
            rmelyseg(elem.egyesGyermek());
            rmelyseg(elem.nullasGyermek());
            melyseg--;
        }
    }
    void ratlag(Csomopont elem){
        if(elem != null){
            melyseg++;
            ratlag(elem.egyesGyermek());
            ratlag(elem.nullasGyermek());
            melyseg--;
            if(elem.egyesGyermek() == null & elem.nullasGyermek() == null){
                atlagdb++;
                atlagosszeg += melyseg;
            }

        }

    }
    void rszoras(Csomopont elem){
        if(elem != null){
            ++melyseg;
            rszoras(elem.egyesGyermek());
            rszoras(elem.egyesGyermek());
            --melyseg;
            if(elem.egyesGyermek() == null & elem.nullasGyermek() == null){
                atlagdb++;
                szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
            }
        }
    }

    void insert(char c){
        if(c == '0'){
            //System.out.print("kint" + c);
            if(fa.nullasGyermek() == null){
                //System.out.print("nullasgyermek" + c);
                fa.ujNullasGyermek(new Csomopont('0'));
                fa = gyoker;
            }
            else{
                fa = fa.nullasGyermek();
            }
        }
        else{
            if(fa.egyesGyermek() == null){
                fa.ujEgyesGyermek(new Csomopont('1'));
                fa = gyoker;
                //System.out.print("egyesgyermek" + c);
            }
            else{
                fa = fa.egyesGyermek();
            }
        }
    }

    void kiir(Csomopont elem){
        if(elem != null){
            melyseg++;
            kiir(elem.egyesGyermek());
            for(int i = 0; i < melyseg; i++)
                System.out.print("---");
            System.out.println(elem.getBetu() + "(" + (melyseg - 1) + ")");
            kiir(elem.nullasGyermek());
            melyseg--;
        }
    }

}