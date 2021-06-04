
public class equation {
	public static double assign(String s,int x) {
		try {
			String [] toCalc = split(s,x);
			if(isPosibleCalc(toCalc))
				return calc(Double.parseDouble(toCalc[0]),toCalc[1],Double.parseDouble(toCalc[2]));
			else if(toCalc[1]==null)
				return Double.parseDouble(toCalc[0]);
			return calc(assign(toCalc[0],x),toCalc[1],assign(toCalc[2],x));
		}catch(Exception e) {
			return Double.NaN;
		}
		
	}
	public static double calc(double a,String  oper,double b) {
		double result = Double.NaN;
		switch(oper) {
			case "+":
				result = a+b;
				break;
			case "-":
				result = a-b;
				break;
			case "*":
				result = a*b;
				break;
			case "/":
				result = a/b;
				break;
		}
		return result;
	}
	public static boolean isOperator(String s) {
		switch(s) {
			case "+":
			case "-":
			case "*":
			case "/":
				return true;
		}
		return false;
	}
	public static boolean isPosibleCalc(String [] toCalc) {
		try {
			Double.parseDouble(toCalc[0]);
			Double.parseDouble(toCalc[2]);
		}catch(NumberFormatException e) {
			return false;
		}catch(NullPointerException e) {
			return false;
		}
		return isOperator(toCalc[1]);
	}
	public static String[] split(String s, int x) {
		String [] result = new String[3];
		s = s.replaceAll("x", Integer.toString(x));
		s = s.replaceAll("X", Integer.toString(x));
		s = removeFirstLastBracets(s);
		int i;
		for(i=0;i<s.length()&&s.charAt(i)!='+'&&s.charAt(i)!='-';i++)
			if(s.charAt(i)=='(')
				i = indexProperBracet(s, i); 
		if(i==s.length()) 
			for(i=0;i<s.length()&&s.charAt(i)!='*'&&s.charAt(i)!='/';i++)
				if(s.charAt(i)=='(')
					i = indexProperBracet(s, i); 
		if(i==s.length()) {
			if(s.equals(""))
				s = "0";
			result[0]=s;
			result[1]=null;
			result[2]=null;
			return result;
		}
		String part1 = "";
		String part2 = "";
		char oper = s.charAt(i);
		part1 = s.substring(0, i);
		part2 = s.substring(i+1, s.length());
		result[0] = part1;
		result[1] = oper+"";
		result[2] = part2;
		return result;
	}
	public static String removeFirstLastBracets(String s) {
		if(s.length()>0&&s.charAt(0)=='('&& s.charAt(s.length()-1)==')'&&indexProperBracet(s, 0)==s.length()-1)
			return s.substring(1, s.length()-1);
		return s;
	}
	public static int indexProperBracet(String s ,int index) {
		int count = 0;
		if(s.charAt(index)=='(') {
			int i;
			for(i=index;i<s.length()&&s.charAt(i)!=')';i++)
				if(s.charAt(i)=='(')
					count++;
			for(;i<s.length()&&count>0;i++)
				if(s.charAt(i)==')')
					count--;
				else if(s.charAt(i)=='(')
					count++;
			return i-1;
		}else if (s.charAt(index)==')') {
			int i;
			for(i=index;i>=0&&s.charAt(i)!='(';i--)
				if(s.charAt(i)==')')
					count++;
			for(;i>=0&&count>0;i--)
				if(s.charAt(i)=='(')
					count--;
				else if(s.charAt(i)==')')
					count++;
			return i+1;
		}else
			return -1;
	}
	public static void main(String[] args) {
		System.out.println("5*2+4/2-7*3 = "+assign("5*2+4/2-7*3", 5));
		System.out.println("((2+2)*(5-3)) = "+assign("((2+2)*(5-3))", 5));
		System.out.println("X = 2 |(5+(3+x)*(4-5)) = "+assign("(5+(3+x)*(4-5))", 2));
		System.out.println("(((5+2)*(3-4))+7/2)*4 = "+assign("(((5+2)*(3-4))+7/2)*4", 2));
	}

}
