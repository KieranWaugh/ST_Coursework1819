package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	public static final int INTEGER = 1;
	public static final int BOOLEAN = 2;
	public static final int STRING = 3;
	public static final int CHAR = 4;
	
	private OptionMap optionMap;
	
	public Parser() {
		optionMap = new OptionMap();
	}
	
	public void add(String option_name, String shortcut, int value_type) {
		optionMap.store(option_name, shortcut, value_type);
	}
	
	public void add(String option_name, int value_type) {
		optionMap.store(option_name, "", value_type);
	}

	public int getInteger(String option) {
		String value = getString(option);
		int type = getType(option);
		int result;
		switch (type) {
		case INTEGER:
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
		        try {
		            new BigInteger(value);
		        } catch (Exception e1) {
		            result = 0;
		        }
		        result = 0;
		    }
			break;
		case BOOLEAN:
			if (getBoolean(option) == false) {
				result = 0;
			} else {
				result = 1;
			}
			break;
		case STRING:
		    int power = 1;
		    result = 0;
		    char c;
		    for (int i = value.length() - 1; i >= 0; --i){
		        c = value.charAt(i);
		        if (!Character.isDigit(c)) return 0;
		        result = result + power * (c - '0');
		        power *= 10;
		    }
		    break;
		case CHAR:
			result = (int)getChar(option);
			break;
		default:
			result = 0;
		}
		return result;
	}
	
	public boolean getBoolean(String option) {
		String value = getString(option);
		boolean result;
		if (value.toLowerCase().equals("false") || value.equals("0") || value.equals("")) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
	
	public String getString(String option) {
		String result = optionMap.getValue(option);
		return result;
	}
	
	public char getChar(String option) {
		String value = getString(option);
		char result;
		if (value.equals("")) {
			result = '\0';
		} else {
			result = value.charAt(0);
		}
		return result;
	}
	
	public int parse(String command_line_options) {
		if (command_line_options == null) {
			return -1;
		}
		int length = command_line_options.length();
		if (length == 0) {
			return -2;
		}
		
		int char_index = 0;
		while (char_index < length) {
			char current_char = command_line_options.charAt(char_index);

			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (current_char != ' ') {
					break;
				}
				char_index++;
			}
			
			boolean isShortcut = true;
			String option_name = "";
			String option_value = "";
			if (current_char == '-') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
				if (current_char == '-') {
					isShortcut = false;
					char_index++;
				}
			} else {
				return -3;
			}
			current_char = command_line_options.charAt(char_index);
			
			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (Character.isLetterOrDigit(current_char) || current_char == '_') {
					option_name += current_char;
					char_index++;
				} else {
					break;
				}
			}
			
			boolean hasSpace = false;
			if (current_char == ' ') {
				hasSpace = true;
				while (char_index < length) {				
					current_char = command_line_options.charAt(char_index);
					if (current_char != ' ') {
						break;
					}
					char_index++;
				}
			}

			if (current_char == '=') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
			}
			if ((current_char == '-'  && hasSpace==true ) || char_index == length) {
				if (getType(option_name) == BOOLEAN) {
					option_value = "true";
					if (isShortcut) {
						optionMap.setValueWithOptioShortcut(option_name, option_value);
					} else {
						optionMap.setValueWithOptionName(option_name, option_value);
					}
				} else {
					return -3;
				}
				continue;
			} else {
				char end_symbol = ' ';
				current_char = command_line_options.charAt(char_index);
				if (current_char == '\'' || current_char == '\"') {
					end_symbol = current_char;
					char_index++;
				}
				while (char_index < length) {
					current_char = command_line_options.charAt(char_index);
					if (current_char != end_symbol) {
						option_value = option_value + current_char;
						char_index++;
					} else {
						break;
					}
				}
			}
			
			if (isShortcut) {
				optionMap.setValueWithOptioShortcut(option_name, option_value);
			} else {
				optionMap.setValueWithOptionName(option_name, option_value);
			}
			char_index++;
		}
		return 0;
	}
	
	private int getType(String option) {
		int type = optionMap.getType(option);
		return type;
	}
	
	@Override
	public String toString() {
		return optionMap.toString();
	}
	
	
	// Task 3 implementation 
	
    public List<Integer> getIntegerList(String option) {
        ArrayList<Integer> returnList = new ArrayList<Integer>(); // return list

        String optionValue = optionMap.getValue(option); // value from the option

        if (optionValue.equals("")) { // no value return empty list
            return returnList;
        }

        List<String> values = Arrays.asList(optionValue.split("[^-\\d]")); // splitting the string on everything but
                                                                           // numbers and -

        // now check for range values and convert to integer types

        for (String string : values) {
            try {
                returnList.add(Integer.parseInt(string)); // try to convert string to integer meaning its just a normal number and add it to return list 
            } catch (Exception e) { // catch the exception if not number 

                if (string.endsWith("-")) { // not valid from specification 6 return empty 
                    return returnList;

                }

                if (!string.contains("--")) { // does not contain a range with negative second number 

                    if (string.startsWith("-")) { // only first value can be negative,
                        String[] val = string.split("-"); // split values on hyphen 
                        val[1] = "-" + val[1]; // add hyphen to first number as it is negative 
                        int one = Integer.parseInt(val[1]); // parse the strings 
                        int two = Integer.parseInt(val[2]);
                        for (int i = one; i <= two; i++) {
                            returnList.add(i); // add the range to retuen list 
                        }
                    } else {
                        String[] val = string.split("-"); // first value is not negative
                        int one = Integer.parseInt(val[0]);
                        int two = Integer.parseInt(val[1]);
                        
                        if (one > two) { // if first number is > than second 
                            for (int i = two; i <= one; i++) {
                                returnList.add(i);
                            }
                        }else {
                            for (int i = one; i <= two; i++) {// if first number is < than second 
                                returnList.add(i);
                            }
                        }  
                    }

                } else { // contains range with negative second number 
                    char[] chars = string.toCharArray(); // creating char array from string

                    ArrayList<Integer> rangeArray = new ArrayList<Integer>(); // array of the two numbers to find range of 
                    String currentVal = ""; // current string value of number 
                    for (int i = 0; i < chars.length; i++) {

                        if (Character.isDigit(chars[i])) { // if char is digit then add it to the CurrentVal string
                            currentVal = currentVal + chars[i];
                        }

                        if (chars[i] == '-') { // found hyphen

                            if (i == 0) { // first elem is negative
                                currentVal = "-";
                                continue; // go to next loop iteration 

                            }

                            if (chars[i + 1] == '-') { // double hyphen found indicating negative range 
                                rangeArray.add(Integer.parseInt(currentVal)); // set first range value
                                currentVal = ""; // reseting currentVal
                                i = i + 1;  
                            }
                            currentVal = currentVal + "-";
                        }

                        if (i == chars.length - 1) { // on last character 
                            rangeArray.add(Integer.parseInt(currentVal)); // add whatever string is in currentVal to the range array 
                            if (rangeArray.get(0) > rangeArray.get(1)) { // finding the ranges depending on their size order 
                                for (int k = rangeArray.get(0); k >= rangeArray.get(1); k--) {
                                    returnList.add(k);
                                }
                            }
                            if (rangeArray.get(0) < rangeArray.get(1)) {
                                for (int k = rangeArray.get(0); k <= rangeArray.get(1); k++) {
                                    returnList.add(k);
                                }
                            }

                        }

                    }

                }

            }
        }

        Collections.sort(returnList); // sorting the return array to ascending order 
        return returnList; // final list return 

    }

}

