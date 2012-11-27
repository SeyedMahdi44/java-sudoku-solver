package c02dt.sudoku.solver;

public class TreeTest {

	protected String tWord;
	protected String aWord;
	
	public static void main(String[] args) {
		
		TreeTest myTT = new TreeTest();
		myTT.tWord = "kitten";
		myTT.aWord = "a";
		
		myTT.solve();
	}
	
	public boolean solve() {
		
		System.out.println(aWord);
		// See if we're done
		if(aWord.equals(tWord)) {
			System.out.println("joy!");
			return true;
		}
		
		// See if we're stuffed 
		if(aWord.charAt(aWord.length()-1) == 'z') {
			System.out.println("gutted :(");
			return false;
		}
		
		// Make the change and branch
		
		if(startsWith(aWord, tWord)) {
			
			aWord += "a";
		}
		else {
			aWord = changeLastChar(aWord);
			
		}
		
		return solve();
	}
	
	private static String changeLastChar(String foo) {
		return foo.substring(0, foo.length()-1)  +  incChar(foo.substring(foo.length()-1));
	}
	
	private static String incChar(String foo) {
		Character bar = foo.charAt(0);
		bar += 1;
		return "" + bar;
	}
	
	private static boolean startsWith(String start, String target) {
		return target.substring(0, start.length()).compareTo(start) == 0;
	}
}
