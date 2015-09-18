package br.ita.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.ita.mcst.Tree;

public class SaveObject {

	public static void serializeDataOut(Tree ish)throws IOException{
	    String fileName= "tree.txt";
	    
	    FileOutputStream fos;
	    ObjectOutputStream oos = null;
	    
	    try {
	    	fos = new FileOutputStream(fileName);
	    	oos = new ObjectOutputStream(fos);
	    	oos.writeObject(ish);
	    } finally {
	    	oos.close();
	    }
	}

	public static Tree serializeDataIn() {
	   String fileName= "tree.txt";
	   
	   Tree tree = null;
	   
	   FileInputStream fin;
	   ObjectInputStream ois = null;
	   
	   try {
		   fin = new FileInputStream(fileName);
		   ois = new ObjectInputStream(fin);
		   
		   tree = (Tree) ois.readObject();
		   
	   } catch(IOException | ClassNotFoundException e) {
		   e.printStackTrace();
	   } finally {
		   try {
			   ois.close();
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   return tree;
	}
}
