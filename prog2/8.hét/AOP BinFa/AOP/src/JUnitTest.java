import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JUnitTest {

	@Test
	public void test() throws Exception {
		//private String[] be1 = new String[2];
		//be1[0] = "be.txt";
		//be1[1] = "ki.txt";
		
		Homokozo test = new Homokozo();
		
		//test.main(new String[] {"be.txt", "ki.txt"});
		
		Homokozo.main(new String[] {"be.txt", "ki.txt"});
		
		assertEquals(3, test.binFa.getMelyseg(), "a melysegnek 3-nak kell lennie");
		assertEquals(2.5, test.binFa.getAtlag(), "az atlagnak 2.5-nek kell lennie");
		assertEquals(1, test.binFa.getSzoras(), "a szorasnak 1-nek kell lennie");
	
	}

}
