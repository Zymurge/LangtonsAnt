package LangtonsAnt;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestDummy {

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testIAmADummy() {
    	var expected = "Dummy";
		var l = new Langtons();
    	var result = l.IAmADummy();
		assertEquals("This too shall pass", result, expected);
	}

}
