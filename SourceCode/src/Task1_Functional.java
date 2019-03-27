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
	
	
	//PART 3 - parser.add with shortcut
	
	// Specification 1 - override 
	
	@Test
	public void override() {
		parser.add("output", "o", Parser.STRING); // creating output with shortcut o and string return 
		parser.parse("-o test"); // parsing a string
		assertEquals(parser.getString("o"), "test"); // testing if the string is being parsed
		
		parser.add("output", "o", Parser.INTEGER); // overriding output with integer parse 
		assertEquals(parser.getInteger("o"), 0); // testing for override 	
	
	}
	
	// Specification 2 - banned characters 
	
	@Test(expected = RuntimeException.class)
	public void not_alpha_name() {
	    parser.add("!", "shortcut", Parser.INTEGER); // testing adding with non alphabet, number, underscore character as the name
	}
	
	@Test(expected = RuntimeException.class) // testing adding with non alphabet, number, underscore character as the shortcut
	public void not_alpha_shortcut() {
	    parser.add("someName", "!", Parser.INTEGER);
	}
	
	@Test(expected = RuntimeException.class)
	public void first_char_name_num() {
	    parser.add("1abc", "shortcut", Parser.INTEGER); // testing number as first character in name
	}
	
	@Test(expected = RuntimeException.class)
	public void fist_char_shortcut_num() {
	    parser.add("name", "1a", Parser.INTEGER); // testing number as first character in shortcut
	}
	
	@Test
	public void underscore_name() {
	    parser.add("_", "sortcut", Parser.INTEGER); // testing an underscore is accepted as a name 
	}
	
	@Test
    public void underscore_shortcut() {
        parser.add("name", "_", Parser.INTEGER); // testing an underscore is accepted as a shortcut
    }
	
	// Specification 3 - case-sensitive 
	
	@Test
	public void case_sens_name() {
	    parser.add("A", "shortcut_capital", Parser.STRING);
	    parser.add("a", "shortcut", Parser.INTEGER);
	    parser.parse("--a string");
	    parser.parse("--A string");
	    assertEquals(parser.getInteger("a"), 0);
	    assertEquals(parser.getString("A"), "string");
	}
	
	@Test
	public void case_sens_shortcut() {
	    parser.add("name1", "a", Parser.STRING);
	    parser.add("name2", "A", Parser.INTEGER);
	    parser.parse("-a string");
	    parser.parse("-A string");
	    assertEquals(parser.getInteger("A"), 0);
	    assertEquals(parser.getString("a"), "string");
	}
	
	@Test
    public void case_sens_name_equal() {
        parser.add("A", "shortcut_capital", Parser.STRING);
        parser.add("a", "shortcut", Parser.STRING);
        parser.parse("--a string");
        parser.parse("--A other_string");
        assertFalse(parser.getString("a").equals(parser.getString("A")));
    }
	
	@Test
    public void case_sens_shortcut_equal() {
        parser.add("name1", "a", Parser.STRING);
        parser.add("name2", "A", Parser.STRING);
        parser.parse("-a string");
        parser.parse("-A other_string");
        assertFalse(parser.getString("a").equals(parser.getString("A")));
	}
	
	// Specification 4 - same name 
	
	@Test
	public void same_name_shortcut() {
	    parser.add("name", "n", Parser.INTEGER);
	    parser.add("n", Parser.INTEGER);
	    parser.parse("--n 5");
	    parser.parse("-n 3");
	    assertFalse(parser.getInteger("name") == parser.getInteger("n"));
	}
	
	// Specification 5 - boolean values 
	
	@Test
	public void boolTest1() {
	    parser.add("one","a", Parser.BOOLEAN);
	    parser.parse("-a abc");
	    assertTrue(parser.getBoolean("a"));
	}
	
	@Test
	public void boolTest2() {
	    parser.add("two", "a", Parser.BOOLEAN);
	    parser.parse("-a FaLSe");
	    assertFalse(parser.getBoolean("a"));
	}
	
	@Test
    public void boolTest3() {
        parser.add("three", "a", Parser.BOOLEAN);
        parser.parse("-a TrUe");
        assertTrue(parser.getBoolean("a"));  
    }
	
	@Test
    public void boolTest4() {
        parser.add("four", "a", Parser.BOOLEAN);
        parser.parse("-a 0");
        assertFalse(parser.getBoolean("a"));  
    }
	
	@Test
    public void boolTest5() {
        parser.add("five", "a", Parser.BOOLEAN);
        parser.parse("-a ");
        assertTrue(parser.getBoolean("a"));  
    }
	
	@Test
    public void boolTest6() {
        parser.add("five", "a", Parser.BOOLEAN);
        parser.parse("-a ");
        assertTrue(parser.getBoolean("a"));  
    }
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// PART 4 - parser.add without shortcut
	
	@Test
    public void noShortcut_override() {
        parser.add("output",Parser.STRING); // creating output with shortcut o and string return 
        parser.parse("--output test"); // parsing a string
        assertEquals(parser.getString("output"), "test"); // testing if the string is being parsed
        
        parser.add("output", Parser.INTEGER); // overriding output with integer parse 
        assertEquals(parser.getInteger("output"), 0); // testing for override   
    
    }
    
    // Specification 2 - banned characters 
    
    @Test(expected = RuntimeException.class)
    public void noShortcut_not_alpha_name() {
        parser.add("!",Parser.INTEGER); // testing adding with non alphabet, number, underscore character as the name
    }
    
    
    @Test(expected = RuntimeException.class)
    public void noShortcut_first_char_name_num() {
        parser.add("1abc",Parser.INTEGER); // testing number as first character in name
    }
   
    
    // Specification 3 - case-sensitive 
    
    @Test
    public void noShortcut_case_sens_name() {
        parser.add("A",Parser.STRING);
        parser.add("a",Parser.INTEGER);
        parser.parse("--a string");
        parser.parse("--A string");
        assertEquals(parser.getInteger("a"), 0);
        assertEquals(parser.getString("A"), "string");
    }
     
    @Test
    public void noShortcut_case_sens_name_equal() {
        parser.add("A", Parser.STRING);
        parser.add("a", Parser.STRING);
        parser.parse("--a string");
        parser.parse("--A other_string");
        assertFalse(parser.getString("a").equals(parser.getString("A")));
    }
    
    
    // Specification 5 - boolean values 
    
    @Test
    public void noShortcut_boolTest1() {
        parser.add("one",Parser.BOOLEAN);
        parser.parse("--one abc");
        assertTrue(parser.getBoolean("one"));
    }
    
    @Test
    public void noShortcut_boolTest2() {
        parser.add("two",Parser.BOOLEAN);
        parser.parse("--two FaLSe");
        assertFalse(parser.getBoolean("--two"));
    }
    
    @Test
    public void noShortcut_boolTest3() {
        parser.add("three",Parser.BOOLEAN);
        parser.parse("--three TrUe");
        assertTrue(parser.getBoolean("three"));  
    }
    
    @Test
    public void noShortcut_boolTest4() {
        parser.add("four",Parser.BOOLEAN);
        parser.parse("--four 0");
        assertFalse(parser.getBoolean("four"));  
    }
    
    @Test
    public void noShortcut_boolTest5() {
        parser.add("five",Parser.BOOLEAN);
        parser.parse("--five ");
        assertTrue(parser.getBoolean("five"));  
    }
    
    @Test
    public void noShortcut_boolTest6() {
        parser.add("five",Parser.BOOLEAN);
        parser.parse("--five ");
        assertTrue(parser.getBoolean("five"));  
    }
    
    //////////////////////////////////////////////////////////////////////////////////
    
    // PART 5 - parsing
    
    // Specification 1 - -- used for name 
    
    @Test
    public void fullName() {
        parser.add("name", "n", Parser.INTEGER);
        parser.add("n", "name", Parser.INTEGER);
        parser.parse("--name 5");
        parser.parse("-name 10");
        assertEquals(parser.getInteger("name"), 5);
    }
    
    // Specification 2 - - used for shortcut
    
    @Test
    public void shortcut() {
        parser.add("name", "n", Parser.INTEGER);
        parser.add("n", "name", Parser.INTEGER);
        parser.parse("--n 5");
        parser.parse("-n 10");
        assertEquals(parser.getInteger("name"), 10);
    }
    
    // Specification 3 - using = or space 
    
    @Test
    public void equals_or_space() {
        parser.add("option", Parser.INTEGER);
        parser.add("opt", Parser.INTEGER);
        parser.parse("--option 1");
        parser.parse("--opt=1");
        assertTrue(parser.getInteger("option") == parser.getInteger("opt"));
    }
    
    // Specification 4 - quotation marks in value
    
    @Test
    public void quotes() {
    	parser.add("option1", "a", Parser.INTEGER);
    	parser.add("option2", "b", Parser.INTEGER);
    	parser.add("option3", "c", Parser.INTEGER);
    	parser.parse("-a \"1\"");
    	parser.parse("-b \'1\'");
    	parser.parse("-c 1");
    	assertTrue((parser.getInteger("a") == parser.getInteger("b")) && (parser.getInteger("a") == parser.getInteger("c")));
    }
    
    // Specification 5 - decorative quotes
    
    @Test
    public void decorative_quotes() {
    	parser.add("option", Parser.STRING);
    	parser.parse("--option='\"test\"");
    	assertEquals(parser.getString("option"), "\"test\"");
    }
    
    // Specification 6 - multiple allocations
    
    @Test
    public void multiple_allocations() {
    	parser.add("option", Parser.INTEGER);
    	parser.parse("--option 1");
    	assertEquals(parser.getInteger("option"), 1);
    	parser.parse("--option 2");
    	assertEquals(parser.getInteger("option"), 2);
    }
    
    // Specification 7 - default values
    
    @Test
    public void defaults() {
    	parser.add("opt1","a", Parser.BOOLEAN);
    	parser.add("opt2", "b", Parser.INTEGER);
    	parser.add("opt3", "c", Parser.STRING);
    	parser.add("opt4", "d", Parser.CHAR);
    	
    	parser.parse("--opt1");
    	parser.parse("--opt2");
    	parser.parse("--opt3");
    	parser.parse("--opt4");
    	assertTrue(parser.getBoolean("opt1"));
    	assertEquals(parser.getInteger("opt2"), 0);
    	assertEquals(parser.getString("opt3"), "");
    	assertEquals(parser.getChar("opt4"), '\0');
    	
    	parser.parse("-a");
    	parser.parse("-b");
    	parser.parse("-c");
    	parser.parse("-d");
    	assertTrue(parser.getBoolean("a"));
    	assertEquals(parser.getInteger("b"), 0);
    	assertEquals(parser.getString("c"), "");
    	assertEquals(parser.getChar("d"), '\0');
    }
   
    // TODO Specification 8 - multiple use 
    
    @Test
    public void multiple_times() {
    	parser.add("input", Parser.INTEGER);
    	parser.add("output", Parser.INTEGER);
    	parser.parse("--input 1 --output 2");
    	assertEquals(parser.getInteger("input"), 1);
    	assertEquals(parser.getInteger("output"), 2);
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    // Part 6
    
    // Specification 1 - order of search
    
    @Test
    public void order() {
    	parser.add("o", Parser.INTEGER);
    	parser.add("option", "o", Parser.INTEGER);
    	parser.parse("-o 1");
    	parser.parse("--o 2");
    	assertEquals(parser.getInteger("o"), 2);
    }
    
    @Test
    public void default_values() {
    	parser.add("opt1","a", Parser.BOOLEAN);
    	parser.add("opt2", "b", Parser.INTEGER);
    	parser.add("opt3", "c", Parser.STRING);
    	parser.add("opt4", "d", Parser.CHAR);
    	
    	parser.parse("--opt1");
    	parser.parse("--opt2");
    	parser.parse("--opt3");
    	parser.parse("--opt4");
    	assertTrue(parser.getBoolean("opt1"));
    	assertEquals(parser.getInteger("opt2"), 0);
    	assertEquals(parser.getString("opt3"), "");
    	assertEquals(parser.getChar("opt4"), '\0');
    	
    	parser.parse("-a");
    	parser.parse("-b");
    	parser.parse("-c");
    	parser.parse("-d");
    	assertTrue(parser.getBoolean("a"));
    	assertEquals(parser.getInteger("b"), 0);
    	assertEquals(parser.getString("c"), "");
    	assertEquals(parser.getChar("d"), '\0');
    }
    
}
