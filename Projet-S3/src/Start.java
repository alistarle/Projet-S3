import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		boolean estGts = false;
			
		fichier = dialogue.getSelectedFile();
		
		String extension = fichier.getName().substring(fichier.getName().length()-4, fichier.getName().length());
      	//System.out.println("Le fichier a pour extension : " + extension);
		//System.out.println("Et il se nomme : " + fichier.getName());
		
		
		if (extension.equals(".gts")) {
			System.out.println("Le fichier est bien un fichier .gts");
			estGts = true;
		}
		
		else {
			System.out.println("Le fichier n'est pas un fichier .gts");
		}
		
		if (estGts) {
			listerSommet(fichier);
		}

    }
    
    
   static void listerSommet (File fichier) {
	   
    	List <Sommet> listeSommet = new ArrayList <Sommet> ();
    	
    	  BufferedReader lecteurAvecBuffer = null;
    	  String ligne;
    	  
    	  try  {
    		  lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
          }
    	  
        catch(FileNotFoundException exc) {
        	System.out.println("Erreur d'ouverture");
        	System.out.println(exc.getMessage());
          }
    	  
    	  try {
    		  String delimiteur = "#segment";
    		  ligne = lecteurAvecBuffer.readLine();
    		  
    		  int i = 0;
    		  int cptChiffre = 0;
    		  char espace = ' ';
    		  int [] tmp = new int [3];
			  while (ligne != null && (!ligne.equals(delimiteur))) {
				  
			      if (i > 1) {
			    	  for (char c : ligne.toCharArray()) {
			    		  System.out.print(c);
			    		  if (c!=espace) {
			    			  
			    			  tmp[cptChiffre] = c;
			    			  cptChiffre ++;
			    			  if (cptChiffre==3) {
			    				  Sommet s = new Sommet (tmp[0],tmp[1],tmp[2]);
			    				  listeSommet.add(s);
			    			  }
			    			  
			    		  }
			    		  
			    		  }
			    	  cptChiffre = 0;
			    	  System.out.println("");
			    	 // System.out.println(ligne);
				      ligne = lecteurAvecBuffer.readLine();
			      }
			      
			      else {
			    	  ligne = lecteurAvecBuffer.readLine();
			      }
			      i = i+1;
			}
			
			  System.out.println("On a " + listeSommet.size() + " sommets");
    		  
		} catch (IOException e) {
			System.out.println("Comme c'etait difficile. Mais t'as quand meme echoue.");
			e.printStackTrace();
		}

    	  try {
				lecteurAvecBuffer.close();
			} catch (IOException e1) {
				System.out.println("Le fichier ne s'est pas ferme !");
				e1.printStackTrace();
			}
    }
    
}