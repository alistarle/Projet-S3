import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

class Start {
	
	// Objectifs
	// D'ici la fin du mois ce serait cool de pouvoir afficher les objets en "fil de fer"
	
    public static void main(String[] arg) throws IOException {
    	
    	
	
	FileFilter java = new FiltreSimple("Fichiers Java",".java");
	FileFilter classes = new FiltreSimple("Fichiers Class",".class");
	FileFilter text= new FiltreSimple("Fichiers Text",".txt");
	FileFilter jar = new FiltreSimple("Fichiers JAR",".jar");
	
	
		JFileChooser dialogue = new JFileChooser();
		dialogue.addChoosableFileFilter(java);
		dialogue.addChoosableFileFilter(classes);
		dialogue.addChoosableFileFilter(jar);
		dialogue.addChoosableFileFilter(text);
		dialogue.showOpenDialog(null);
		
		File fichier;

		fichier = dialogue.getSelectedFile();
		
		// Test : la reconnaissance de l'extension fonctionne
		String extension = fichier.getName().substring(fichier.getName().length()-4, fichier.getName().length());
		System.out.println("Le fichier a pour extension : " + extension);
		System.out.println("Et il se nomme : " + fichier.getName());
		
		// A remplacer par if (extension.equals(".gts")) { ..... }
		if (extension.equals(".txt")) {
			System.out.println("Le fichier est bien un fichier .txt");
		}
		
		else {
			System.out.println("Ah non mon con, ce n'est pas un fichier .txt");
		}

    }
    
}