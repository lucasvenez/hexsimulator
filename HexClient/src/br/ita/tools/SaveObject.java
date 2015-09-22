package br.ita.tools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import br.ita.mcts.HexTreeSearch;

/**
 * 
 * @author Lucas Venezian Povoa
 *
 */
public class SaveObject {

	/**
	 * 
	 * @param tree
	 * @throws IOException
	 */
	public static void serializeDataOut(HexTreeSearch tree) throws IOException {

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tree.json"), "utf-8"));
			writer.write(tree.toString());
		} catch (IOException ex) {
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static HexTreeSearch serializeDataIn() throws IOException {
		
		JSON
		
		return null;
	}
}
