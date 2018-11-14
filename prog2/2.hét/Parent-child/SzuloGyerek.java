class SzuloGyerek{

    public static void main (String[]args){
        Szulo szulo = new Gyerek();
        System.out.println("Gyerek letrehotasa: \nSzulo szulo = new Gyerek()");

        System.out.println("\nA szulo objektum fuggvenyet hasznaljuk:\nszulo.szulokiir()");

        szulo.szulokiir();

        System.out.println("\nA szulo.gyerekkiir()-re már errort adna");

        //szulo.gyerekkiir();
    }
}