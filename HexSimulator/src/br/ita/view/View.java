package br.ita.view;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import br.ita.hexgame.HexStatus;
import br.ita.tools.FileManagement;

public class View implements Runnable {
	
    public static class Index implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = FileManagement.readInternal("web/index.html");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public static class HexagonJS implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = FileManagement.readInternal("web/Hexagon.js");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static class HexTableJS implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = FileManagement.readInternal("web/HexTable.js");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public static class JQueryJS implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = FileManagement.readInternal("web/jquery.js");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    public static class Move implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            
            if (HexStatus.board[0] + HexStatus.board[1] == HexStatus.getNumberOfGames()) {
            	int j = 0;
            	List<Integer> g = HexStatus.getMoves();
            	for (Integer i : g) {
            		response += i + (j == g.size() - 1? "" : " ");
            		j++;
            	}
            }
            
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private int port;
    
	public View(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		
		HttpServer server;
		
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			
			server.createContext("/view", new Index());
			
			server.createContext("/view/index", new Index());
			
			server.createContext("/view/index.html", new Index());
			
			server.createContext("/view/Hexagon.js", new HexagonJS());
			
			server.createContext("/view/HexTable.js", new HexTableJS());
			
			server.createContext("/view/jquery.js", new JQueryJS());
			
			server.createContext("/view/move", new Move());
	        
			server.setExecutor(null); // creates a default executor
	        
			server.start();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}