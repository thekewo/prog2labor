import java.io.*;
import java.util.*;

public class leet {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("normal.txt"));
        PrintStream out = new PrintStream(new File("leet.txt"));
        leetSpeak(input, out);
    }
    
    public static void leetSpeak(Scanner input, PrintStream output) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Scanner tokens = new Scanner(line);
            
            while (tokens.hasNext()) {
                String token = tokens.next();
                token = token.replace("a", "4");
                token = token.replace("o", "0");
                token = token.replace("l", "1");
                token = token.replace("e", "3");
                token = token.replace("t", "7");
				token = token.replace("b", "8");
				token = token.replace("d", "|)");
				token = token.replace("f", "|=");
				token = token.replace("g", "6");
				token = token.replace("j", "_|");
				token = token.replace("r", "|2");
                
            }
            
            output.println();
        }   
    }
}
