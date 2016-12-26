import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class testRomanMath {

	@Test
	public void test_add(){

		assertEquals("LIX",RomanMath.add( new StringBuilder("XLIX"), new StringBuilder("X") ).toString());
		assertEquals("MMMM",RomanMath.add( new StringBuilder("MMMCMXCIX"), new StringBuilder("I") ).toString());//39999+1 Overflow
		assertEquals("DCXCVIII",RomanMath.add( new StringBuilder("CXCIX"), new StringBuilder("CDXCIX") ).toString());//199+499
		assertEquals("MCCCXCVI",RomanMath.add( new StringBuilder("DCXCVIII"), new StringBuilder("DCXCVIII") ).toString());//698+698=1396
		assertEquals("MDC",RomanMath.add( new StringBuilder("MC"), new StringBuilder("D") ).toString());//1100+500=1396
		assertEquals("CCLVI",RomanMath.add( new StringBuilder("CXXIII"), new StringBuilder("CXXXIII") ).toString());//123+133=256
	}



	@Test
	public void test_subtraction(){
		//System.out.println("1"+RomanMath.subtraction( new StringBuilder("CXCIX"), new StringBuilder("CXCIX"))+"1" );
		assertEquals(" ",RomanMath.subtraction( new StringBuilder("CXCIX"), new StringBuilder("CXCIX") ).toString());// 0
		assertEquals("-",RomanMath.subtraction( new StringBuilder("V"), new StringBuilder("X") ).toString());	
		assertEquals("V",RomanMath.subtraction( new StringBuilder("X"), new StringBuilder("V") ).toString());	
		assertEquals("MMMCMXCVIII",RomanMath.subtraction( new StringBuilder("MMMCMXCIX"), new StringBuilder("I") ).toString());//39999-1
		assertEquals("CCC",RomanMath.subtraction( new StringBuilder("CDXCIX"), new StringBuilder("CXCIX") ).toString());//499-199
	}


	@Test
	public void test_multiplication(){


		assertEquals("L",RomanMath.multiplication( new StringBuilder("V"), new StringBuilder("X") ).toString());
		assertEquals("CC",RomanMath.multiplication( new StringBuilder("X"), new StringBuilder("XX") ).toString());	
		assertEquals("MMM",RomanMath.multiplication( new StringBuilder("M"), new StringBuilder("III") ).toString());
		assertEquals("MMMM",RomanMath.multiplication( new StringBuilder("M"), new StringBuilder("IV") ).toString());//overflow still allowed
		assertEquals("-",RomanMath.multiplication( new StringBuilder("M"), new StringBuilder("X") ).toString());//overflow
		assertEquals("-",RomanMath.multiplication( new StringBuilder("M"), new StringBuilder("VI") ).toString());//overflow
		assertEquals("MMMCMXCVIII",RomanMath.multiplication( new StringBuilder("MCMXCIX"), new StringBuilder("II") ).toString());// 1999 *2= 3998

	}
	@Test
	public void test_division(){
		//System.out.println("1"+RomanMath.division( new StringBuilder("X"), new StringBuilder("V") ).toString()+"1");

		assertEquals("I  ",RomanMath.division( new StringBuilder("V"), new StringBuilder("V") ).toString());
		assertEquals("II  ",RomanMath.division( new StringBuilder("X"), new StringBuilder("V") ).toString());
		assertEquals("MCMXCIX  ",RomanMath.division( new StringBuilder("MMMCMXCVIII"), new StringBuilder("II") ).toString());
		assertEquals("MCMXCIX  ",RomanMath.division( new StringBuilder("MMMCMXCVIII"), new StringBuilder("II") ).toString());
		assertEquals("XXXVII II",RomanMath.division( new StringBuilder("CL"), new StringBuilder("IIII") ).toString());
		assertEquals("XXX  ",RomanMath.division( new StringBuilder("CCC"), new StringBuilder("X") ).toString());
		assertEquals("XXXVII II",RomanMath.division( new StringBuilder("CL"), new StringBuilder("IIII") ).toString());

	}


	@Test
	public void test_removeSubtractive(){

		assertEquals("IIII",RomanMath.removeSubtractive(new StringBuilder("IV")).toString());		
		assertEquals("VIIII",RomanMath.removeSubtractive(new StringBuilder("IX")).toString());
		assertEquals("XXXX",RomanMath.removeSubtractive((new StringBuilder("XL"))).toString());
		assertEquals("LXXXX",RomanMath.removeSubtractive((new StringBuilder("XC"))).toString());
		assertEquals("CCCC",RomanMath.removeSubtractive((new StringBuilder("CD"))).toString());
		assertEquals("DCCCC",RomanMath.removeSubtractive((new StringBuilder("CM"))).toString());		
		assertEquals("DCCCCXXXXVIIII",RomanMath.removeSubtractive((new StringBuilder("CMXLIX"))).toString());
	}
	
	@Test
	public void test_addSubtractive(){

		assertEquals("IV",RomanMath.addSubtractive((new StringBuilder("IIII"))).toString());		
		assertEquals("IX",RomanMath.addSubtractive((new StringBuilder("VIIII"))).toString());
		assertEquals("XL",RomanMath.addSubtractive((new StringBuilder("XXXX"))).toString());
		assertEquals("XC",RomanMath.addSubtractive((new StringBuilder("LXXXX"))).toString());
		assertEquals("CD",RomanMath.addSubtractive((new StringBuilder("CCCC"))).toString());
		assertEquals("CM",RomanMath.addSubtractive((new StringBuilder("DCCCC"))).toString());		
		assertEquals("CMXLIX",RomanMath.addSubtractive((new StringBuilder("DCCCCXXXXVIIII"))).toString());
	}

	@Test
	public void test_sort(){

		assertEquals("DDCXI",RomanMath.sort((new StringBuilder("XICDD"))).toString());		
		assertEquals("XVIII",RomanMath.sort((new StringBuilder("IIVIX"))).toString());
		assertEquals("MDCII",RomanMath.sort((new StringBuilder("IICMD"))).toString());
		assertEquals("CXXXXVVIIIIII",RomanMath.sort((new StringBuilder("XCVVIIIIIIXXX"))).toString());
		assertEquals("DDCC",RomanMath.sort((new StringBuilder("CDCD"))).toString());
		assertEquals("MMMD",RomanMath.sort((new StringBuilder("MMMD"))).toString());		
		assertEquals("MCLXXI",RomanMath.sort((new StringBuilder("CMXLIX"))).toString());
	}

	@Test
	public void test_compact(){
		//System.out.println(RomanMath.sort((new StringBuilder("XICDD"))).toString());
		assertEquals("V",RomanMath.compact((new StringBuilder("IIIII"))).toString());		
		assertEquals("X",RomanMath.compact((new StringBuilder("VIIIII"))).toString());
		assertEquals("X",RomanMath.compact((new StringBuilder("VV"))).toString());
		assertEquals("L",RomanMath.compact((new StringBuilder("XXXXX"))).toString());
		assertEquals("LXX",RomanMath.compact((new StringBuilder("XXXXXXX"))).toString());
		assertEquals("DCLVI",RomanMath.compact((new StringBuilder("CCCCCCXXXXXIIIIII"))).toString());	

		assertEquals("XX",RomanMath.compact((new StringBuilder("IIIIIIIIIIIIIIIIIIII"))).toString());
		assertEquals("D",RomanMath.compact((new StringBuilder("CCCCLXXXXVIIIII"))).toString());	


	}


	@Test
	public void test_isValidNumeral() {

		assertFalse(RomanMath.isValidNumeral(new StringBuilder("A")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("a")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("1")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("0")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder(" ")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("!")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IA")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("Ia")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IA")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("Ia")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("I1")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("I0")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("I ")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("I!")));		

		assertFalse(RomanMath.isValidNumeral(new StringBuilder("iiivi")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("MdcxI")));


		assertFalse(RomanMath.isValidNumeral(new StringBuilder("+")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("-")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder(".")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("-XI")));

		assertTrue(RomanMath.isValidNumeral(new StringBuilder("MdcxI")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder(" ")));
		
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("I")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IIII")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IIIII")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("IV")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("IX")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("VIIII")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("VIV")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("VV")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IIIIIIIIII")));

		assertFalse(RomanMath.isValidNumeral(new StringBuilder("XXXXX")));		
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IL")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("XLIX")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("IC")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("L")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("I")));


		assertFalse(RomanMath.isValidNumeral(new StringBuilder("XIXV")));
		assertTrue(RomanMath.isValidNumeral(new StringBuilder("MMMCMXCIX")));
		assertFalse(RomanMath.isValidNumeral(new StringBuilder("MMMCMXCIXI")));


	}

}
