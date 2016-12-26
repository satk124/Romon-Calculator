import java.util.*;
import java.io.*;

public class romanCalculator {
	public static void main(String[] args) {

		StringBuilder numeral1=new StringBuilder(), numeral2=new StringBuilder();
		int operation=1;
		Scanner sc=new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		System.out.println("***Roman Arithmetic*** ");
		System.out.println("Allowed Operations [1->addition, 2->subtraction, 3->multiplication, 4->division]\nRULE\n1. Numeral must be in standard representation[e.g.,following subtractives are allowed IV & IX , XL & XC, CD & CM ]\n2. Following subtractives are not allowed [IL, IC, IM, XD, XM, LC, LD, LM] ");
		System.out.println("3. Limit[latter's limit=[MDCLXVI], Max Roman numeral=MMMCMXCIX &  Min Roman numeral=I]");
		System.out.println("INPUT Format\nnumeral1 numeral2 operation [e.g., XL X 1] ");
		

		//check()  we can "subtract" a symbol representing a IV & IX , XL & XC, CD & CM from the next two higher symbols,
		//convert to proper format remove subtractive->compact[-> add subtractive]
		//
		boolean flag=true;

		while(flag){

			boolean check=true;
			try{
				numeral1=new StringBuilder(sc.next().toUpperCase());
				numeral2=new StringBuilder(sc.next().toUpperCase());
				operation =sc.nextInt();
			}catch(Exception e){
				System.out.print("\nInput not valid: "+"\n");
				check=false;
			}
			if(check && !RomanMath.isValidNumeral(numeral1) || !RomanMath.isValidNumeral(numeral2)){
				System.out.print("\nNumeral is not valid ");
				check=false;				
			}
			if(check && operation<1 || operation >4){
				System.out.print("\n Operation is not supported");
				check=false;
			}

			if(check){	
				switch(operation){

				case 1:
					System.out.println(RomanMath.add(numeral1, numeral2));
					break;
				case 2:
					if(RomanMath.subtraction(numeral1, numeral2).toString().equals("-"))
						System.out.println("not possible");
					else
						System.out.println(RomanMath.subtraction(numeral1, numeral2));
					break;
				case 3:			
					if(RomanMath.multiplication(numeral1, numeral2).toString().equals("-"))
						System.out.println("out of range");
					else
						System.out.println(RomanMath.multiplication(numeral1, numeral2));
					break;
				case 4:
					if(RomanMath.division(numeral1, numeral2).toString().equals("-"))
						System.out.println("not possible");
					else
						System.out.println(RomanMath.division(numeral1, numeral2));
					break;
				default :
				}
			}
			System.out.print("\n **[Enter 1 to try again]**\n");
			try{
				if(!check)sc.next();
				int con=sc.nextInt();
				if(con!=1) flag=false;
			}catch( InputMismatchException e ){
				System.exit(0);
			}
			
		}
		sc.close();
	}

}
