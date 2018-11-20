import java.nio.charset.Charset;
public class pelda {
    public static void main(String[] args) {
        Charset dfset = Charset.defaultCharset();
        System.out.println(dfset.name());
        //System.out.println("file.encoding=" + System.getProperty("file.encoding"));
    }
}

//https://docs.oracle.com/javase/6/docs/technotes/guides/intl/encoding.doc.html