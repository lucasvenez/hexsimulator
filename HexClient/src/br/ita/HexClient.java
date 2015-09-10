package br.ita;

import static br.ita.tools.Shortcut.println;

import java.io.IOException;
import java.net.UnknownHostException;

import br.ita.protocol.Player;

public class HexClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		String name = "";
		int port    = 0;
		
		for (int i = 0; i < args.length; i++) {
			
			switch (args[i]) {
				case "-p": port = Integer.parseInt(args[++i]); break;
				case "-n": name = args[++i]; break;
				case "-h":
				case "help": help(); break;
				default: println("Invalid parameter " + args[i] + ". Use only -n and -p.");
			}				
		}
		
		if (port > 0) {
			Thread client = new Thread(new Player(port, name));
			client.start();
		}
	}

	private static void help() {
		StringBuffer sb = new StringBuffer();
		sb.append("HexClient Help\n\n");
		sb.append("-p refers to the port number used to connected to the HexSimulator;\n");
		sb.append("-n refers to the team name;\n");
		sb.append("-h or help call this message.\n");
		
		println(sb.toString());
	}
}
