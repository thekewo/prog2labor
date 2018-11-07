import java.io.*;

class BinTree{
	private final Node root;
	private Node current;
	private int treeHeight;
	private int melyseg, maxMelyseg, atlagosszeg, atlagdb;
	private double atlag,szorasosszeg, szoras;
	
	
	public BinTree(){
		root = new Node('/');
		current = root;
		treeHeight = -1;
	}

	public Node getRoot(){
		return root;
	}
	public void setCurrent(Node rhs){
		current = rhs;
	}
	
	public void write(char b){
		if(b == '0'){
			if(current.getLeft() == null){
				current.setLeft(new Node('0'));
				setCurrent(root);
			}
			else{
				setCurrent(current.getLeft());
			}
		}
		
		if(b == '1'){
			if(current.getRight() == null){
				current.setRight(new Node('1'));
				setCurrent(root);
			}
			else{
				setCurrent(current.getRight());
			}
		}
		
		
		
	}
	
	
	public void writeOut(Node n, PrintWriter pw){
		if(n != null)
		{
			++treeHeight;
			
			writeOut(n.getLeft(),pw);
			
			for(int i = 0; i < treeHeight; i++){
				pw.print('-');
			}
			
			pw.println(n.getNode() + " (" + treeHeight + ")");
			
			writeOut(n.getRight(),pw);
			--treeHeight;
		}
	}

	public int getMelyseg(){
		melyseg = maxMelyseg = 0;
		rMelyseg(getRoot());
		return --maxMelyseg;
	}
	
	private void rMelyseg(Node rhs){
		if(rhs != null)
		{
			++melyseg;
			if(melyseg > maxMelyseg)
				maxMelyseg = melyseg;
			
			rMelyseg(rhs.getLeft());
			rMelyseg(rhs.getRight());
			
			--melyseg;
		}
	} 
	
	public double getAtlag(){
		atlagdb = atlagosszeg = melyseg = 0;
		rAtlag(getRoot());
		atlag = (double)atlagosszeg/atlagdb;
		
		return atlag;
	}
	
    private void rAtlag(Node rhs){
	 if(rhs != null){
        melyseg++;
        rAtlag(rhs.getLeft());
        rAtlag(rhs.getRight());
        melyseg--;

        if(rhs.getLeft() == null && rhs.getRight() == null)
        {
            atlagdb ++;
            atlagosszeg += melyseg;
        }

     }	
	}

    public double getSzoras(){
        atlag = getAtlag();
        
        szorasosszeg = 0.0;
        atlagdb = melyseg = 0;
        
        rSzoras(getRoot());
        
        if(atlagdb-1 > 0)
            szoras = Math.sqrt(szorasosszeg/(atlagdb-1));
        else
            szoras = Math.sqrt(szorasosszeg);
            
        return szoras;
    }
    
    private void rSzoras(Node rhs){
       if(rhs != null){ 
        ++melyseg;
        rSzoras(rhs.getLeft());
        rSzoras(rhs.getRight());
        --melyseg;

             
        if(rhs.getLeft() == null && rhs.getRight() == null)
        {
            atlagdb ++;
            szorasosszeg += Math.pow((melyseg-atlag), 2);
        }
       }
    }	
}
