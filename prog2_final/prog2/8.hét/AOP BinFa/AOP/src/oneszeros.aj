public aspect oneszeros {

	private int ones = 0;
	private int zeroes = 0;
	
	public pointcut nullasGyermek(): call(void Csomopont.ujNullasGyermek(Csomopont));
	public pointcut egyesGyermek(): call(void Csomopont.ujEgyesGyermek(Csomopont));
	public pointcut vege(): execution(public static void main(String[]));
	
	after():nullasGyermek(){
		zeroes++;
	}
	
	after():egyesGyermek(){
		ones++;
	}
	
	after():vege(){
		System.out.println(ones + " db 1-es lett beszurva");
		System.out.println(zeroes + " db 0-as lett beszurva");
	}
}
