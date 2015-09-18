package br.ita.tools;

public class GameConversor {

	public static void main(String[] args) {
		String text = "55AA46A62221709A7242458633067528849509135248609834386779374A1753316635261580125099A0766387114432A982270A96A754100158081941568914160094699249813903028518367165681A83059143308861979062A12993A3A207254759247A4023A5736A6404";
		
		System.out.print("PLAYER1 {");
		
		for (int i = 0; i < text.length(); i+= 4) {
			System.out.print(convert(text.substring(i, i+2), 11));
			if (i < text.length()-2)
				System.out.print(",");
			
		}
		System.out.println("}");
		
		System.out.print("PLAYER2 {");
		
		for (int i = 2; i < text.length(); i+= 4) {
			System.out.print(convert(text.substring(i, i+2), 11));
			if (i < text.length()-4)
				System.out.print(",");
			
		}
		System.out.println("}");
		
		System.out.print("ALL {");
		
		for (int i = 0; i < text.length(); i+= 2) {
			System.out.print(convert(text.substring(i, i+2), 11));
			if (i < text.length()-2)
				System.out.print(",");
			
		}
		System.out.println("}");
	}

	
	public static int convert(String move, int n) {
		return n * Integer.parseInt(String.valueOf(move.charAt(0)), 16) + 
				   Integer.parseInt(String.valueOf(move.charAt(1)), 16); 
	}
	
}
