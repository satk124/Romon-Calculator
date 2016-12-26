import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.*;
import org.junit.Test;

public class RomanMath {

	public static Character Latters[]={ 'M', 'D','C', 'L', 'X', 'V','I'};
	private static String priorityString="MDCLXVI";
	private static String reversePriorityString="IVXLCDM";
	private static LinkedHashMap<String, String> subtractivesTable = new LinkedHashMap<String, String>();
	private static LinkedHashMap<String, String> compactionTable = new LinkedHashMap<String, String>();
	private static ArrayList<ArrayList<String>> multiplicationTable = new ArrayList<ArrayList<String>>();
	static {
		subtractivesTable.put(new String("VIIII"), new String("IX"));
		subtractivesTable.put(new String("IIII"), new String("IV"));
		subtractivesTable.put(new String("LXXXX"), new String("XC"));
		subtractivesTable.put(new String("XXXX"), new String("XL"));
		subtractivesTable.put(new String("DCCCC"), new String("CM"));
		subtractivesTable.put(new String("CCCC"), new String("CD"));

		// to compact no like LL->C, XXXX->L
		compactionTable.put(new String("IIIII"), new String("V"));
		compactionTable.put(new String("VV"), new String("X"));
		compactionTable.put(new String("XXXXX"), new String("L"));
		compactionTable.put(new String("LL"), new String("C"));
		compactionTable.put(new String("CCCCC"), new String("D"));
		compactionTable.put(new String("DD"), new String("M"));
		for(int i=0; i<Latters.length; i++){
			ArrayList<String> al=new ArrayList<String>();

			al.add("I");
			al.add("V");
			al.add("X");
			al.add("L");
			al.add("C");
			al.add("D");
			al.add("M");			
			multiplicationTable.add(al);

			ArrayList<String> al1=new ArrayList<String>();
			al1.add("V");
			al1.add("XXV");
			al1.add("L");
			al1.add("CCL");
			al1.add("D");
			al1.add("MMD");
			al1.add("-");
			multiplicationTable.add(al1);

			ArrayList<String> al2=new ArrayList<String>();				
			al2.add("X");
			al2.add("L");
			al2.add("C");
			al2.add("D");
			al2.add("M");
			al2.add("-");
			al2.add("-");
			multiplicationTable.add(al2);

			ArrayList<String> al3=new ArrayList<String>();				
			al3.add("L");
			al3.add("CCL");
			al3.add("D");
			al3.add("MMD");
			al3.add("-");
			al3.add("-");
			al3.add("-");
			multiplicationTable.add(al3);

			ArrayList<String> al4=new ArrayList<String>();				
			al4.add("C");
			al4.add("D");
			al4.add("M");
			al4.add("-");
			al4.add("-");
			al4.add("-");
			al4.add("-");
			multiplicationTable.add(al4);

			ArrayList<String> al5=new ArrayList<String>();				

			al5.add("D");
			al5.add("MMD");
			al5.add("-");
			al5.add("-");
			al5.add("-");
			al5.add("-");
			al5.add("-");
			multiplicationTable.add(al5);

			ArrayList<String> al6=new ArrayList<String>();				

			al6.add("M");
			al6.add("-");
			al6.add("-");
			al6.add("-");
			al6.add("-");
			al6.add("-");
			al6.add("-");
			multiplicationTable.add(al6);

			//	System.out.println(multiplicationTable.toString());
		}
	}


	public static StringBuilder add(StringBuilder numeral1, StringBuilder numeral2){
		//System.out.println(numeral1+" "+numeral2);
		StringBuilder sum;
		numeral1=removeSubtractive(numeral1);

		numeral2=removeSubtractive(numeral2);

		sum=numeral1.append(numeral2);

		sum=sort(sum);

		sum=compact(sum);

		sum=addSubtractive(sum);

		return sum;
	}
	public static StringBuilder subtraction(StringBuilder numeral1,StringBuilder numeral2){
		StringBuilder difference=new  StringBuilder();

		numeral1=removeSubtractive(numeral1);
		numeral2=removeSubtractive(numeral2);

		Map<Character, Integer> hash1=new LinkedHashMap<Character, Integer>();
		Map<Character, Integer> hash2=new LinkedHashMap<Character, Integer>();

		for(Character ch: RomanMath.Latters){
			hash1.put(ch, 0);
			hash2.put(ch, 0);
		}

		for(int i=0; i<numeral1.length(); i++){
			hash1.put(numeral1.charAt(i), hash1.get(numeral1.charAt(i))+1);
		}
		//	System.out.println(hash1.toString());
		for(int i=0; i<numeral2.length(); i++){
			//System.out.println("hh");
			hash2.put(numeral2.charAt(i), hash2.get(numeral2.charAt(i))+1);
		}


		//System.out.println(hash2.toString());
		Set<Character> set=new HashSet<Character>();
		for(int i=numeral2.length()-1; i>=0; i--){

			char ch=numeral2.charAt(i);
			if(set.contains(ch)) continue;
			set.add(ch);
			if(hash2.get(ch)<hash1.get(ch)){
				//System.out.println(ch);
				hash1.put(ch, hash1.get(ch)-hash2.get(ch));
			}
			else if(hash2.get(ch)==hash1.get(ch)){ 

				hash1.put(ch,0);

			}else{
				//System.out.println(ch);
				if(borrow(hash1, ch)){

					hash1.put(ch, hash1.get(ch)-hash2.get(ch));
				}else{
					difference=new StringBuilder("-");
					return difference;
				}
			}
		}
		//System.out.println(hash1.toString());
		for (Entry<Character, Integer> entry : hash1.entrySet()) {
			for(int j=0; j<entry.getValue(); j++){
				difference=difference.append(entry.getKey());
			}
		}


		difference=addSubtractive(compact(difference));
		if(difference.length()==0){
			difference=difference.append(" ");
		}
		//if(difference.toString().compareTo("")==0) return new StringBuilder("-");
		return difference;

	}
	static boolean borrow(Map<Character, Integer> hash1, char ch){


		int start_index=priorityString.indexOf(ch)-1;
		int i;
		for(i=start_index; i>=0; i--){
			char temp1=priorityString.charAt(i);

			if(hash1.get(temp1)>0){

				hash1.put(temp1, hash1.get(temp1)-1);				
				break;
			}
		}
		if(i<0) return false;
		i++;
		for(; i<=start_index; i++){
			if(i%2==1){
				hash1.put(priorityString.charAt(i), 1);
			}else{
				hash1.put(priorityString.charAt(i), 4);
			}
		}
		if(i%2==1){

			hash1.put(ch, hash1.get(ch)+2);
		}
		else{
			hash1.put(ch, hash1.get(ch)+5);
		}
		return true;
	}
	public static StringBuilder multiplication(StringBuilder numeral1, StringBuilder numeral2){
		StringBuilder multiplication=new StringBuilder();

		numeral1=removeSubtractive(numeral1);
		numeral2=removeSubtractive(numeral2);

		for(Character ch1:numeral1.toString().toCharArray()){
			for(Character ch2:numeral2.toString().toCharArray()){
				if(multiplicationTable.get(reversePriorityString.indexOf(ch1)).get(reversePriorityString.indexOf(ch2)).compareTo("-")==0){

					return new StringBuilder("-");

				}
				multiplication.append(multiplicationTable.get(reversePriorityString.indexOf(ch1)).get(reversePriorityString.indexOf(ch2)));
			}
		}
		multiplication=sort(multiplication);
		multiplication=addSubtractive(compact(multiplication));

		return multiplication;
	}

	public static StringBuilder division(StringBuilder dividend, StringBuilder divisor){

		dividend=removeSubtractive(dividend);
		divisor=removeSubtractive(divisor);

		if(RomanMath.subtraction(dividend, divisor).toString().compareTo("-")==0){
			return new StringBuilder("  "+dividend);
		}


		StringBuilder quotient=new  StringBuilder();
		Map<Character, String> hm=new HashMap<Character, String>();
		String steps="CLXVII";
		for(Character c:steps.toCharArray()){
			hm.put(c, RomanMath.multiplication(divisor, new StringBuilder(c.toString())).toString()  );
		}
		//	System.out.println(hm.toString());

		for(Character c:steps.toCharArray()){	
			if(hm.get(c).compareTo("-")==0) continue;
			if(dividend.toString().equals(" ")){
				break;
			}
			//	System.out.println(dividend+" " + hm.get(c)+" " +c+" "+RomanMath.subtraction(dividend,new StringBuilder( hm.get(c))));
			while(RomanMath.subtraction(dividend,new StringBuilder( hm.get(c))).toString().compareTo("-")!=0){

				dividend=RomanMath.subtraction(dividend, new StringBuilder(hm.get(c)));
				//	System.out.println(dividend +" "+c);
				quotient=quotient.append(c);
				if(dividend.toString().equals(" ")){
					break;
				}
			}
		}
		quotient=addSubtractive(compact(quotient));
		dividend=addSubtractive(compact(dividend));
		//	System.out.println("q");
		return  new StringBuilder(""+quotient.append(" ").append(dividend));

	}
	public static boolean isValidNumeral(StringBuilder numeral) {
		numeral=new StringBuilder(numeral.toString().toUpperCase());

		for (int i = 0; i < numeral.length(); i++) {
			if (numeral.charAt(i) != 'I' && numeral.charAt(i) != 'V'
					&& numeral.charAt(i) != 'X' && numeral.charAt(i) != 'L'
					&& numeral.charAt(i) != 'C' && numeral.charAt(i) != 'D'
					&& numeral.charAt(i) != 'M') {
				return false;
			}
		}
		//	numeral=removeSubtractive(numeral);
		//	numeral=compact(numeral);
		//	numeral=addSubtractive(numeral);

		String regex = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
		try {
			Pattern r = Pattern.compile(regex);
			Matcher m = r.matcher(numeral.toString());
			if (!m.matches())
				return false;
		} catch (PatternSyntaxException e) {
			System.out.println(e);
		}
		return true;
	}

	static StringBuilder removeSubtractive(StringBuilder numeral) {
		boolean flag = true;
		while (flag) {
			flag = false;
			for (Entry<String, String> entry : subtractivesTable.entrySet()) {
				int index = numeral.indexOf(entry.getValue());
				if (index != -1) {
					numeral.replace(index, index + 2, entry.getKey());
					flag = true;
				}
			}
		}
		return numeral;

	}
	static StringBuilder addSubtractive(StringBuilder numeral) {

		for (Entry<String, String> entry : subtractivesTable.entrySet()) {
			int index = numeral.indexOf(entry.getKey());
			if (index != -1) {
				numeral.replace(index, index + entry.getKey().length(),
						entry.getValue());
			}
		}
		return numeral;

	}


	static StringBuilder compact(StringBuilder numeral) {

		for (Entry<String, String> entry : compactionTable.entrySet()) {
			int index = numeral.indexOf(entry.getKey());
			while (index != -1) {

				numeral.replace(index, index + entry.getKey().length(),
						entry.getValue());
				index = numeral.indexOf(entry.getKey());
			}
		}
		return numeral;

	}
	static StringBuilder sort(StringBuilder numeral){
		StringBuilder sorted=new StringBuilder();
		Map<Character, Integer> hash=new HashMap<Character, Integer>();
		for(Character ch: RomanMath.Latters){
			hash.put(ch, 0);
		}
		for(int i=0; i<numeral.length(); i++){
			hash.put(numeral.charAt(i), hash.get(numeral.charAt(i))+1);
		}
		for(Character ch:priorityString.toCharArray()){
			for(int i=0;i<hash.get(ch); i++){
				sorted.append(ch);
			}
		}
		return sorted;

	}
}
