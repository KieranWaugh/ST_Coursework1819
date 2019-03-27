import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>();
        input.add("-2--10");
        
        
        //System.out.println(bString.toString());
        List<Integer> ret = new ArrayList<>();
        
        
        for (String string : input) {
            try {
                ret.add(Integer.parseInt(string));  
            }catch (Exception e) {
                List<String> bString = Arrays.asList(string.split("-"));
                int one = Integer.parseInt(bString.get(0));
                int two = Integer.parseInt(bString.get(1));
                for (int i = one; i <= two; i++) {
                    ret.add(i);
                }
            
            }
        }
        System.out.println(ret.toString());
        
    }
}
