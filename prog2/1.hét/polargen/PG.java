public class PG {
	
	boolean nT = true;
	double t;

	public PG(){
	nT = true;
	}

	public double kov(){
	if(nT){
	double u1,u2,v1,v2,w;
	do {
	u1=Math.random();
	u2=Math.random();
	v1=2*u1-1;
	v2=2*u2-1;
	w=v1*v1+v2*v2;
	}while(w>1);
	double r=Math.sqrt((-2*Math.log(w))/w);
	t=r*v2;
	nT=!nT;
	return r*v1;
	}else{
		nT=!nT;
		return t;
	}
	}

	public static void main(String[] args){
		PG g = new PG();
		for(int i=0; i<10; ++i){
			System.out.println(g.kov());
		}
	}
}