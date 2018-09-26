class Node {
	private char value;
	private Node left;
	private Node right;
	
	public Node(char rhs){
		value = rhs;
		left = null;
		right = null;
	}
	
	
	public char getNode(){
		return value;
	} 
	
	public void setLeft(Node rhs){
		left = rhs;
	}
	
	public void setRight(Node rhs){
		right = rhs;
	}
	
	public Node getLeft(){
		return left;
	}
	
	public Node getRight(){
		return right;
	}
	
}