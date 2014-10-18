import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

class Start {
	
	// Un joli JFileChooser pompé sur le net (ça en ouvre 2 ya un pb)
	
    public static void main(String[] arg) throws IOException {
    	
    	
	
	FileFilter java = new FiltreSimple("Fichiers Java",".java");
	FileFilter classes = new FiltreSimple("Fichiers Class",".class");
	FileFilter text= new FiltreSimple("Fichiers Text",".txt");
	FileFilter jar = new FiltreSimple("Fichiers JAR",".jar");
	
	
		JFileChooser dialogue = new JFileChooser(new File("."));
		dialogue.addChoosableFileFilter(java);
		dialogue.addChoosableFileFilter(classes);
		dialogue.addChoosableFileFilter(jar);
		dialogue.addChoosableFileFilter(text);
		dialogue.showOpenDialog(null);
		
		
		PrintWriter sortie;
		File fichier;
		
		if (dialogue.showOpenDialog(null)== 
		    JFileChooser.APPROVE_OPTION) {
			
			
		    fichier = dialogue.getSelectedFile();
		    sortie = new PrintWriter
			(new FileWriter(fichier.getPath(), true));
		    sortie.println(arg[0]);
		    sortie.close();
		}
    }
}