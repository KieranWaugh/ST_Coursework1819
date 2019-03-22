import static org.junit.Assert.*;


import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task1_Functional {
	
	private Parser parser;
	 
	@Before
	public void start() {
		parser = new Parser();
	}
	
	// Spec 1
	
	@Test
	public void task1_spec1() {
		parser.add("test", "t", Parser.STRING);
		parser.add("test", "t", Parser.INTEGER);
		parser.parse("--test 1");
		assertEquals(parser.getInteger("t"), 1);
		
	}
	
	
	
	
}
