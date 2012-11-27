package c02dt.sudoku.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

public class MenneskeLoader implements PuzzleLoader {
	
	private static Boolean	hasNextPuzzle;
	private static Puzzle 	nextPuzzle;
	private static Puzzle	thisPuzzle;
	
	private static URL BASEURL;
	
	private static final Pattern CLUEREGEX = Pattern.compile(">(\\d)</td>");
	private static final Pattern CELLREGEX = Pattern.compile(">&nbsp;</td>");
	
	private static Matcher clueMatch;
	private static Matcher cellMatch;
	
	public MenneskeLoader(int difficulty) {
		try {
			BASEURL = new URL("http://www.menneske.no/sudoku/eng/random.html?diff=" + difficulty);
		} catch (MalformedURLException e) {
			System.err.println("Possible non-valid difficulty");
		}
		MenneskeLoader.prepareNextPuzzle();
	}
	
	public Puzzle nextElement() {
		thisPuzzle = nextPuzzle;
		MenneskeLoader.prepareNextPuzzle();
		return thisPuzzle;
	}
	
	public boolean hasMoreElements() {
		return hasNextPuzzle;
	}
	
	private static void prepareNextPuzzle() {
		
		nextPuzzle = new Puzzle();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(BASEURL.openStream()));
			
			String inputLine;
			int i = 0;
			
			while ((inputLine = in.readLine()) != null) {
				
				clueMatch = CLUEREGEX.matcher(inputLine);
				cellMatch = CELLREGEX.matcher(inputLine);
			
				if (cellMatch.find()) {
					i++;
					
				} else if (clueMatch.find()) {
					nextPuzzle.setClue(i/9, i%9, Integer.parseInt(clueMatch.group(1)));
					i++;
				}
			}
			
			in.close();
			hasNextPuzzle = true;
			
		} catch (IOException e) {
			hasNextPuzzle = false;
		}
	}
	
}
