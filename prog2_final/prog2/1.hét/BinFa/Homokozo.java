import java.io.*;
import java.nio.charset.*;



public class Homokozo{
    static void usage(){
        System.out.println("Usage: java Homokozo in_file out_file");
    }


    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            usage();
            System.exit(-1);
        }
        
        File file = new File(args[0]); 

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(file),
                Charset.forName("UTF-8")
            )
        );

        int b;                      
        LZWBinFa binFa = new LZWBinFa();
        boolean kommentben = false;

        while((b = reader.read()) != -1){
            //System.out.println((char)b);
            char c = (char) b;

            if(c == 0x0a)
                break;
        }
        
        reader.close();

        BufferedReader reader2 = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(file),
                Charset.forName("UTF-8")
            )
        );


        while((b = reader2.read()) != -1){
            //System.out.println((char)b);
            //char c = (char) b;

            if(b == 0x3e){
                kommentben = true;
                continue;
            }
                
            if(b == 0x0a){
                kommentben = false;
                continue;
            }

            if(kommentben)
                continue;
            
            if(b == 0x4e)
                continue;

            for (int i = 0; i < 8; i++){
                if ((b & 0x80)== 0x80)
                    binFa.insert('1');
                else
                    binFa.insert('0');
                b <<=1;
            }
                
                
        }

        binFa.kiir(binFa.getGyoker());
        System.out.println("depth = " + binFa.getMelyseg());
        System.out.println("mean = " + binFa.getAtlag());
        System.out.println("var = " + binFa.getSzoras());
    
    }
}