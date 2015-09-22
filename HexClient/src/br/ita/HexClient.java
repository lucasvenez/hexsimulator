package br.ita;

import static br.ita.tools.Shortcut.println;

import java.io.IOException;
import java.net.UnknownHostException;

import br.ita.protocol.Player;

public class HexClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		String host = "localhost";
		
		int port = 0;
		
		int games = 15;
		
		int player = 0;

		String name = "LOO";
		
		for (int i = 0; i < args.length; i++) {
			
			switch (args[i]) {
				case "-p": port   = Integer.parseInt(args[++i]); break;
				case "-n": name   = args[++i]; break;
				case "-h": host   = args[++i]; break;
				case "-g": games  = Integer.parseInt(args[++i]); break;
				case "-i": player = Integer.parseInt(args[++i]); break;
				case "help": help(); break;
				default: println("Invalid parameter " + args[i] + ". Use only -n and -p.");
			}				
		}
		
		if (player != 1 && player != 2) {
			System.out.println("It's required use -i parameter for defining the initial player.\n");
			help();
		} else if (port > 0) {
			Thread client = new Thread(new Player(player, host, port, name, games));
			client.start();
		}
	}

	private static void help() {
		StringBuffer sb = new StringBuffer();
		sb.append("HexClient Help\n\n");
		sb.append("-p refers to the port number used to connected to the HexSimulator [1,..., 4000];\n");
		sb.append("-n refers to the team name;\n");
		sb.append("-h or help call this message.\n");
		sb.append("-g refers to number of consecutive games [1, ..., inf].\n");
		sb.append("-i refers to initial player [1, 2].\n");
		
		println(sb.toString());
	}
}
