import java.io.*;

class RsaTores {
    public static void main(String[] args) {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("RSAPelda.java.out.blog.txt"));
            int lines = 0;

            String line[] = new String[10000];

            while((line[lines] = inputStream.readLine()) != null) {
                lines++;
            }

            inputStream.close();

            KulcsPar kp[] = new KulcsPar[100];

            boolean volt = false;
            kp[0] = new KulcsPar(line[0]);
            int db = 1;

            for(int i = 1; i < lines; i++) {
                volt = false;
                for(int j = 0; j < db; j++) {
                    if(kp[j].getValue().equals(line[i])) {
                        kp[j].incFreq();
                        volt = true;
                        break;
                    }
                }

                if(volt == false) {
                    kp[db] = new KulcsPar(line[i]);
                    db++;
                }
            }


            for(int i = 0; i < db; i++) {
                for(int j = i + 1; j < db; j++) {
                    if(kp[i].getFreq() < kp[j].getFreq() ) {
                        KulcsPar temp = kp[i];
                        kp[i] = kp[j];
                        kp[j] = temp;
                    }
                }
            }





            FileReader f = new FileReader("angol.txt");

            char[] key = new char[60];
            int kdb=0;
            int k;

            while((k = f.read()) != -1) {
                if((char)k != '\n') {
                    key[kdb] = (char)k;
                    //System.out.println(key[kdb]);
                    kdb++;
                }
            }

            f.close();

            for(int i = 0; i < kdb; i++) {
                kp[i].setKey(key[i]);
            }


            for(int i = 0; i < lines; i++) {
                for(int j = 0; j < db; j++) {
                    if(line[i].equals(kp[j].getValue())) {
                        System.out.print(kp[j].getKey());
                    }
                }
            }

        } catch(IOException e) {
        }

    }






}
