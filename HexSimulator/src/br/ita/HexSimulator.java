package br.ita;

import br.ita.hexgame.HexStatus;
import br.ita.protocol.Player;
import br.ita.view.View;

import static br.ita.tools.Shortcut.println;

public class HexSimulator {

	public static void main(String args[]) {
		
		if (args.length < 4) {
			help();
		} else {
		
			int[] port     = new int[2];
			int  games     = 15;
			int  tableSize = 11;
			int  viewPort  = 8000;
			
			for (int i = 0; i < args.length; i++) {
				
				switch (args[i]) {
					case "-p1": port[0]   = Integer.parseInt(args[++i]); break;
					case "-p2": port[1]   = Integer.parseInt(args[++i]); break;
					case "-g":  games     = Integer.parseInt(args[++i]); break;
					case "-t":  tableSize = Integer.parseInt(args[++i]); break;
					case "-v":  viewPort  = Integer.parseInt(args[++i]); break;
					default: 
						println("Invalid parameter " + args[i] + ". Use at least -p1 and -p2.");
				}				
			}

			if (port[0] != port[1]) {
				
				HexStatus.setTableSize(tableSize);
				HexStatus.setNumberOfGames(games);
				
				Thread p1 = new Thread(new Player(1, port[0], games));
				Thread p2 = new Thread(new Player(2, port[1], games));
				Thread v  = new Thread(new View(viewPort));
				
				p1.start();
				p2.start();
				v.start();
			} else {
				println("Port numbers should be different.");
			}			
		}
	}

	private static void help() {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("Please, inform parameters -p1 and -p2, ");
		sb.append("where the first refers to the player #1 port and ");
		sb.append("the second one to the player #2 port.");
		
		println(sb.toString());
	}
}