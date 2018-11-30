public class Csomopont{
    private char betu;
    private Csomopont balNulla;
    private Csomopont jobbEgy;

    Csomopont(char c){
        this.betu = c;
        this.balNulla = null;
        this.jobbEgy = null;
    }

    public Csomopont nullasGyermek(){
        return balNulla;
    }

    public Csomopont egyesGyermek(){
        return jobbEgy;
    }

    public void ujNullasGyermek(Csomopont gy){
        balNulla = gy;
    }

    public void ujEgyesGyermek(Csomopont gy){
        jobbEgy = gy;
    }

    public char getBetu(){
        return betu;
    }

}