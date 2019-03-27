import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<String> input = new ArrayList<>();
        input.add("1");
        input.add("2");
        input.add("1-15");
        input.add("1--3");
        
        
        //System.out.println(bString.toString());
        List<Integer> ret = new ArrayList<>();
        
        
        for (String string : input) {
            try {
                ret.add(Integer.parseInt(string));  
            }catch (Exception e) {
                
                
                if (!string.contains("--")) {
                    
                    if (string.startsWith("-")) { // only first value can be neg,  
                        String[] val = string.split("-");
                        val[1] = "-" + val[1];
                        int one = Integer.parseInt(val[1]);
                        int two = Integer.parseInt(val[2]);
                        for (int i = one; i <= two; i++) {
                            ret.add(i);
                        }
                    }else {
                        String[] val = string.split("-");
                        int one = Integer.parseInt(val[0]);
                        int two = Integer.parseInt(val[1]);
                        for (int i = one; i <= two; i++) {
                            ret.add(i);
                        }
                    }
                    
                }else {
                    char[] chars = string.toCharArray();

                    ArrayList<Integer> rangeArray = new ArrayList<Integer>();
                    String currentVal = "";
                    boolean range = false;
                    for (int i = 0; i < chars.length; i++) {
                        
                        
                        if (Character.isDigit(chars[i])) {
                            currentVal = currentVal + chars[i];
                        }
                        
                        if (chars[i] == '-' ) { // negative first value 
                            
                            if (i == 0) { // first elem is negative
                                currentVal = "-";
                                continue;
                                
                            }
                            
                            if (Character.isDigit(chars[i+1])) { // non negative range
                                range = true;
                                if (currentVal != "") {
                                    rangeArray.add(Integer.parseInt(currentVal));
                                }
                                String restOfNum = "";
                                for (int j = i +1; j < chars.length; j++) {
                                    restOfNum =  restOfNum + chars[j];
                                }
                                rangeArray.add((Integer.parseInt(restOfNum)));
                                i = chars.length - 1;
                            }
                            
                            if (chars[i+1] == '-') { // negative range 
                                range = true;
                                rangeArray.add(Integer.parseInt(currentVal)); // set first range value 
                                currentVal = "";
                                i = i+1;
                            }
                            currentVal = currentVal + "-";
                        }
                        
                        if (i == chars.length - 1) {                       
                            rangeArray.add(Integer.parseInt(currentVal));
                            if (rangeArray.get(0) > rangeArray.get(1)) {
                                for (int k = rangeArray.get(0); k >= rangeArray.get(1); k--) {
                                    ret.add(k);
                                }
                            }
                            if (rangeArray.get(0) < rangeArray.get(1)) {
                                for (int k = rangeArray.get(0); k <= rangeArray.get(1); k++) {
                                    ret.add(k);
                                }
                            }
                        
                }
                
                }
                    
                  
                    
                
                }
                
                
                
            
            }
        }
        System.out.print(ret.toString());
        
    }
}
