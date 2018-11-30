class Virag{
    
    /*void szur(){
        System.out.print("Megszúrt");
    }*/
}

class SzurosVirag extends Virag{

}

class Hovirag extends Virag{

}

class Rozsa extends SzurosVirag{
    void szur(){
        System.out.print("Megszúrt");
}

}

class Program{
    void fgv(Virag v){
        //v.szur(); kár odatenni mert nem minden virágra jellemző
    }
}