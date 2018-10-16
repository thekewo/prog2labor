class KulcsPar{
    private String values;
    private char key = '_';
    private int freq = 0;
    
    public KulcsPar(String str, char k){
        this.values = str;
        this.key = k;
    }

    public KulcsPar(String str){
        this.values = str;
    }

    public void setValue(String str){
        this.values = str;
    }

    public void setKey(char k){
        this.key = k;
    }

    public String getValue(){
        return this.values;
    }

    public char getKey(){
        return this.key;
    }

    public void incFreq(){
        freq += 1;
    }

    public int getFreq(){
        return freq;
    }





}
