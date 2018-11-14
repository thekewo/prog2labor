package asd;

public aspect ElotteUtana {
	
	public pointcut fgvHivas(): call(public void HelloVilag.hello());
	
	before(): fgvHivas(){
		System.out.println("ElotteUtana>Alma");
	}
	
	after():fgvHivas(){
		System.out.println("ElotteUtana>Korte");
	}

}
