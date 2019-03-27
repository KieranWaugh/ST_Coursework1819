import static org.junit.Assert.*;

import org.junit.Test;
import st.Parser;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

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
        parser.add("list", Parser.STRING);
        parser.parse("--list=1,2,3");
        assertEquals(parser.getIntegerList("list"), Arrays.asList(1,2,3));
    }
    
    @Test
    public void test1() {
        parser.add("list", "l", Parser.STRING);
        parser.parse("--list=1,2,4-7,10");
        assertEquals(parser.getIntegerList("list"), Arrays.asList(1,2,4,5,6,7,10));
    }
    
    @Test
    public void spec1() {
        parser.add("o", Parser.STRING);
        parser.add("option", "o", Parser.STRING);
        parser.parse("-o=1,2,3,4,5");
        parser.parse("--o=6,7,8,9,10");
        assertEquals(parser.getIntegerList("o"), Arrays.asList(6,7,8,9,10));
    }
    
    @Test
    public void spec2() {
        parser.add("list", Parser.STRING);
        parser.parse("--list");
        assertEquals(parser.getIntegerList("list"), Arrays.asList());
    }
    
    @Test // TODO add more separators perhaps in loop
    public void spec3() {
        parser.add("list", Parser.STRING);
        parser.parse("--list=1,2.4");
        assertEquals(parser.getIntegerList("list"), Arrays.asList(1,2,4));
    }
    
    @Test
    public void spec4() {
        parser.add("list", Parser.STRING);
        parser.parse("--list=1-5");
        assertEquals(parser.getIntegerList("list"), Arrays.asList(1,2,3,4,5));
    }
    
    @Test
    public void spec5() {
        parser.add("list", Parser.STRING);
        parser.parse("--list=-7--5");
        assertEquals(parser.getIntegerList("list"), Arrays.asList(-7,-6,-5));
    }
    
    @Test
    public void spec6() {
        parser.add("list", Parser.STRING);
        parser.parse("--list=3-");
        assertEquals(parser.getIntegerList("list"), Arrays.asList());
    }
    
    

}
