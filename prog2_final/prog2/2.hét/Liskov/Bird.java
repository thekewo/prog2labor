class Bird {
  public void fly(){}
  public void eat(){}
}
class Crow extends Bird {}
class Ostrich extends Bird{
  fly(){
    throw new UnsupportedOperationException();
  }
}
 
public BirdTest{
  public static void main(String[] args){
    List<Bird> birdList = new ArrayList<Bird>();
    birdList.add(new Bird());
    birdList.add(new Crow());
    birdList.add(new Ostrich());
    letTheBirdsFly ( birdList );
  }
  static void letTheBirdsFly ( List<Bird> birdList ){
    for ( Bird b : birdList ) {
      b.fly();
    }
  }
}
