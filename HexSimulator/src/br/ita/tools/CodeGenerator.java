package br.ita.tools;
import static br.ita.hexgame.Move.list;

/**
 * It generates a R code for calculating all possible moves in Hex Game.
 * @author Lucas Venezian Povoa
 */
public class CodeGenerator {
	
	public String codeGenerator() {
		
		String sentence = "mmm <- c(";
		
		for (String move : list)
			sentence += "\"" + move + "\"" + (list.indexOf(move) == list.size() - 1 ? "" : ",");
		
		sentence += ")\n\n";
		
		for (int i = 0; i < 121; i++) {
			
			String v = varname(i);
			
			String tabs = repeat(" ", i);

			String c = "c(";
			
			for (int j = 0; j < i; j++)
				c += varname(j) + (j == i - 1 ? "" : ",");
			
			c += ")";
				
			String set = "setdiff(mmm, " + c + ")";
			
			sentence += tabs + "for (" + v + " in " + set + " )\n";
		}
		
		return sentence;
	}
	
	public static final String varname(int l) {
		return "m" + (l < 10 ? "00" : (l < 100 ? "0" : "")) + l;
	}
	
	public static final String repeat(String pattern, int times, String separator) {
		
		String result = "";
		
		if (times > 0)
			for (int i = 0; i < times; i++)
				result += pattern + separator;
		
		return result;
	}
	
	public static final String repeat(String pattern, int times) {
		return repeat(pattern, times, "");
	}
}
