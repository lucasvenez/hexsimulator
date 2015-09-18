package br.ita.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class FileManagement {

	public static String readInternal(String filename) {
		String result = "";
		
		InputStream bis = FileManagement.class.getClassLoader().getResourceAsStream(filename);
		
		try {
			int i;
			
			while ((i = bis.read()) > -1)
				result += new String(new byte[] {(byte)i});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String read(String filename) {
		
		String r = "";
		
		Scanner sc;
		try {
			sc = new Scanner(new File(filename));
			while(sc.hasNextLine()){
			    r += sc.nextLine();                     
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return r;
		
	}
	
	public static boolean write(String content, String filename) {
		
		boolean result = true;
		
		PrintWriter writer;
		
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.println(content);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			result = false;
		}
		
		return result;
	}
}
