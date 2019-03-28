import static org.junit.Assert.*;

import org.junit.Test;
import st.Parser;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task3_TDD_N {
    
    private Parser parser;
    
    @Before
    public void start() {
        parser = new Parser();
    }
    
    @Test
    public void basicTest() {
        parser.add("list", Parser.STRING); // basic list parsing test
        parser.parse("--list=1,2,3");
        assertEquals(Arrays.asList(1,2,3), parser.getIntegerList("list"));
    }
    
    @Test
    public void test1() {
        parser.add("list", "l", Parser.STRING); // test with both numbers and range 
        parser.parse("--list=1,2,4-7,10");
        assertEquals(Arrays.asList(1,2,4,5,6,7,10), parser.getIntegerList("list"));
    }
    
    @Test
    public void ascending_order() { // ordering test 
        parser.add("list", Parser.STRING);
        parser.parse("--list=2,4,3,1");
        assertEquals(Arrays.asList(1,2,3,4), parser.getIntegerList("list"));
    }
    
    @Test
    public void spec1() { // order of search test 
        parser.add("o", Parser.STRING);
        parser.add("option", "o", Parser.STRING);
        parser.parse("-o=1,2,3,4,5");
        parser.parse("--o=6,7,8,9,10");
        assertEquals(Arrays.asList(6,7,8,9,10),parser.getIntegerList("o") );
    }
    
    @Test
    public void spec2() { // empty value test
        parser.add("list", Parser.STRING);
        parser.parse("--list");
        assertEquals(Arrays.asList(), parser.getIntegerList("list"));
    }
    
    @Test  // punctuation separator 
    public void spec3() {
        parser.add("list", Parser.STRING);
        parser.parse("--list=1,2.4");
        assertEquals(Arrays.asList(1,2,4), parser.getIntegerList("list") );
    }
    
    @Test
    public void spec4() { // ranges test
        parser.add("list", Parser.STRING);
        parser.parse("--list=1-5");
        assertEquals(Arrays.asList(1,2,3,4,5), parser.getIntegerList("list") );
    }
    
    @Test
    public void spec4_2() { // test to see if ranges are equal 
        parser.add("list", Parser.STRING);
        parser.add("list1", Parser.STRING);
        parser.parse("--list=4-7");
        parser.parse("--list1=7-4");
        assertTrue(parser.getIntegerList("list").toString().equals(parser.getIntegerList("list1").toString()));
    }
    
    @Test
    public void spec4_3() { // range starting from negative value 
        parser.add("list", Parser.STRING);
        parser.parse("--list=-1-5");
        assertEquals(Arrays.asList(-1,0,1,2,3,4,5), parser.getIntegerList("list") );
    }
    
    @Test
    public void spec5() { // test of range between two negative values 
        parser.add("list", Parser.STRING);
        parser.parse("--list=-7--5");
        assertEquals(Arrays.asList(-7,-6,-5), parser.getIntegerList("list"));
    }
    
    @Test
    public void spec5_2() { // range starting from negative test 
        parser.add("list", Parser.STRING);
        parser.parse("--list=-2-1");
        assertEquals(Arrays.asList(-2,-1,0,1), parser.getIntegerList("list"));
    }
    
    @Test
    public void spec5_3() { // positive to negative range test ƒSystem.
        parser.add("list", Parser.STRING);
        parser.parse("--list=5--1");
        assertEquals(Arrays.asList(-1,0,1,2,3,4,5), parser.getIntegerList("list"));
    }
    
    @Test
    public void spec6() { // hyphen suffix test
        parser.add("list", Parser.STRING);
        parser.parse("--list=3-");
        assertEquals(Arrays.asList(), parser.getIntegerList("list"));
    }
    
   

}
