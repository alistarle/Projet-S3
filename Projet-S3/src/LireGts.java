import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

class LireGts {

	static Map<Integer, Sommet> mapSom;
	static Map<Integer, Segment> mapSeg;
	static Map<Integer, Face> mapFaces;

	public static void main(String[] arg) throws IOException, FichierException {

		// FiltreSimple = fichier propose dans le JFileChooser
		FileFilter gts = new FiltreSimple("Fichiers GTS", ".gts");

		// Creation et instanciation d'un JFileChooser
		JFileChooser dialogue = new JFileChooser();
		dialogue.addChoosableFileFilter(gts);
		dialogue.showOpenDialog(null);

		File fichier;
		boolean estGts = false;

		// Recuperation du fichier selectionne dans le JFC
		fichier = dialogue.getSelectedFile();

		String extension = fichier.getName().substring(
				fichier.getName().length() - 4, fichier.getName().length());

		if (extension.equals(".gts")) {
			estGts = true;
		}

		else {
			throw new FichierException("Format de fichier incorrect.");

		}

		if (estGts) {
			List<Sommet> sommets = listerSommet(fichier);
			int nbSommets = sommets.size();
			mapSom = mapSommet(sommets);
			afficherSommets();

			List<Segment> segments = listerSegment(fichier);
			int nbSegments = segments.size();
			mapSeg = mapSegment(segments);
			afficherSegments();

			List<Face> faces = listerFaces(fichier);
			int nbFaces = faces.size();
			mapFaces = mapFace(faces);
			
			
			afficherFaces();

		}

	}

	/**
	 * Cette fonction retourne une List <Sommet>
	 * 
	 * @param fichier
	 *            : le nom du fichier (.gts) a ouvrir
	 * @return la liste de tous les sommets du fichier
	 */
	public static List<Sommet> listerSommet(File fichier) {

		List<Sommet> listeSommet = new ArrayList<Sommet>();

		BufferedReader lecteurAvecBuffer = null;
		String ligne;

		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
		}

		catch (FileNotFoundException exc) {
			System.out.println("Fichier non trouve.");
			System.out.println(exc.getMessage());
		}

		try {
			String delimiteur = "#segment";
			ligne = lecteurAvecBuffer.readLine();

			int i = 0;
			int cptChiffre = 0;
			char espace = ' ';
			int[] tmp = new int[3];
			while (ligne != null && (!ligne.equals(delimiteur))) {

				if (i > 1) {
					for (char c : ligne.toCharArray()) {
						if (c != espace) {

							int n = c - '0';
							tmp[cptChiffre] = n;
							cptChiffre++;
							if (cptChiffre == 3) {
								Sommet s = new Sommet(tmp[0], tmp[1], tmp[2]);
								listeSommet.add(s);
							}

						}

					}
					cptChiffre = 0;
					ligne = lecteurAvecBuffer.readLine();
				}

				else {
					ligne = lecteurAvecBuffer.readLine();
				}
				i = i + 1;
			}

		} catch (IOException e) {
			System.out.println("Probleme");
			e.printStackTrace();
		}

		try {
			lecteurAvecBuffer.close();
		} catch (IOException e1) {
			System.out.println("Le fichier ne s'est pas ferme !");
			e1.printStackTrace();
		}
		return listeSommet;
	}

	/**
	 * Cette fonction retourne une Map <Integer,Sommet>
	 * 
	 * @param l
	 *            : liste des sommets
	 * @return une hashmap avec tous les couples (numero de sommet, sommet)
	 */
	public static Map<Integer, Sommet> mapSommet(List<Sommet> l) {

		Map<Integer, Sommet> mapRes = new HashMap<Integer, Sommet>();
		Integer i = 0;
		Iterator<Sommet> it = (Iterator<Sommet>) l.iterator();
		while (it.hasNext()) {
			Sommet s = (Sommet) it.next();
			mapRes.put(i, s);
			i++;
		}
		return mapRes;
	}

	/**
	 * Cette fonction retourne une List <Segment>
	 * 
	 * @param fichier
	 * @return la liste de tous les segments
	 */
	public static List<Segment> listerSegment(File fichier) {
		List<Segment> listeSegment = new ArrayList<Segment>();
		BufferedReader lecteurAvecBuffer = null;
		String ligne;

		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
		}

		catch (FileNotFoundException exc) {
			System.out.println("Erreur d'ouverture : methode listerSegment");
			System.out.println(exc.getMessage());
		}

		try {
			String delimiteurFace = "#face";
			String delimiteurSegment = "#segment";
			boolean estDansSegment = false;
			ligne = lecteurAvecBuffer.readLine();

			// i = compteur de lignes
			int i = 0;
			int cptChiffre = 0;
			char espace = ' ';
			int[] tmp = new int[2];

			while (ligne != null && !ligne.equals(delimiteurFace)) {

				if (ligne.equals(delimiteurSegment)) {
					estDansSegment = true;
					ligne = lecteurAvecBuffer.readLine();
				}

				if (ligne.equals(delimiteurFace)) {
					estDansSegment = false;
				}

				if (estDansSegment) {
					for (char c : ligne.toCharArray()) {
						if (c != espace) {

							int n = c - '0';
							// System.out.println("Il y a " + cptChiffre +
							// " chiffre");
							tmp[cptChiffre] = n;
							cptChiffre++;
							// Il faut 2 coordonnees pour un segment
							if (cptChiffre == 2) {
								Integer indice1 = tmp[0];
								Integer indice2 = tmp[1];

								// System.out.println("Indice 1 " + indice1);
								double x1 = mapSom.get(indice1 - 1).getX();
								double y1 = mapSom.get(indice1 - 1).getY();
								double z1 = mapSom.get(indice1 - 1).getZ();
								Sommet s1 = new Sommet(x1, y1, z1);

								// System.out.println("Indice 2  : " + indice2);
								double x2 = mapSom.get(indice2 - 1).getX();
								double y2 = mapSom.get(indice2 - 1).getY();
								double z2 = mapSom.get(indice2 - 1).getZ();
								Sommet s2 = new Sommet(x2, y2, z2);

								Segment s = new Segment(s1, s2);
								listeSegment.add(s);
							}

						}

					}
					cptChiffre = 0;
					ligne = lecteurAvecBuffer.readLine();
				}

				else {
					ligne = lecteurAvecBuffer.readLine();
				}
				i = i + 1;
			}

		} catch (IOException e) {
			System.out
					.println("Comme c'etait difficile. Mais t'as quand meme echoue.");
			e.printStackTrace();
		}

		try {
			lecteurAvecBuffer.close();
		} catch (IOException e1) {
			System.out.println("Le fichier ne s'est pas ferme !");
			e1.printStackTrace();
		}
		return listeSegment;

	}

	/**
	 * Cette fonction retourne une Map <Integer, Segment)
	 * 
	 * @param l
	 * @return
	 */
	public static Map<Integer, Segment> mapSegment(List<Segment> l) {

		Map<Integer, Segment> mapRes = new HashMap<Integer, Segment>();
		Integer i = 0;
		Iterator<Segment> it = (Iterator<Segment>) l.iterator();
		while (it.hasNext()) {
			Segment s = (Segment) it.next();
			mapRes.put(i, s);
			i++;
		}
		return mapRes;
	}

	/*
	 * Cette methode affiche les segments avec leur indice dans la MapSeg
	 */
	public static void afficherSegments() {
		System.out.println("Les segments : ");
		for (int i = 0; i < mapSeg.size(); i++) {
			System.out.println((i + 1) + " : " + mapSeg.get(i).toString());
		}
	}

	/*
	 * Cette methode affiche les sommets avec leur indice dans la MapSom
	 */

	public static void afficherSommets() {
		System.out.println("Les sommets : ");
		for (int i = 0; i < mapSom.size(); i++) {
			System.out.println((i + 1) + " : " + mapSom.get(i).toString());
		}
	}

	/**
	 * Retourne la liste de toutes les faces
	 * 
	 * @param fichier
	 * @return
	 */
	public static List<Face> listerFaces(File fichier) {

		List<Face> listeFaces = new ArrayList<Face>();

		BufferedReader lecteurAvecBuffer = null;
		String ligne;

		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
		}

		catch (FileNotFoundException exc) {
			System.out.println("Fichier non trouve.");
			System.out.println(exc.getMessage());
		}

		try {
			String delimiteur = "#face";
			boolean estDansFaces = false;
			char espace = ' ';
			ligne = lecteurAvecBuffer.readLine();

			while (!estDansFaces && ligne != null) {
				System.out.println("Ligne = " + ligne);

				if (ligne.equals(delimiteur)) {
					System.out.println("Ligne == delimiteur ! ");
					estDansFaces = true;
				}
				ligne = lecteurAvecBuffer.readLine();

			}

			if (estDansFaces) {
				Integer tok = 0;
				int cpt= 0;
				int nbSeg = 0;
				int cptFaces = 0;
				while (ligne!=null) {
					int cptChiffre = 0;
					char[] tabLigne = ligne.toCharArray();
					
					int[] tabNumSom = new int[3];
					for (int i = 0; i < tabLigne.length; i++) {
						
						

						if (i < (tabLigne.length - 1 )) {
							
							if (tabLigne[i] != espace && tabLigne[i +1] == espace) {
					
								
								
									if ( i > 0 &&tabLigne[i-1]!= espace && tabLigne[i]!=espace && tabLigne[i+1]==espace) {
										System.out.println("On prend pas");
									}
								
									else  {
										int n = tabLigne[i] - '0';

										//System.out.println("n = " + n);
									tabNumSom[cptChiffre] = n;	
									//System.out.println("tabNumSom[" +cptChiffre + "]=" +  tabNumSom[cptChiffre]);
									cptChiffre++;
								}
								

							}

							else if (tabLigne[i] != espace && tabLigne[i + 1] != espace) {
								
								String s = tabLigne[i] + "" + tabLigne[i + 1];
								
								int a = Integer.parseInt(s);		
								tabNumSom[cptChiffre] = a;
								cptChiffre++;
							}
							System.out.println("i=" + i);

							if (cptChiffre == 3) {
								cptFaces++;
								System.out.println("tbn 0 = " + tabNumSom[0]);
								System.out.println("tbn1 = " + tabNumSom[1]);
								System.out.println("tbn2 = " + tabNumSom[2]);
								
								int idx1 = tabNumSom[0]-1;
								int idx2 = tabNumSom[1]-1;
								int idx3 = tabNumSom[2]-1;
								
								Segment s1 = mapSeg.get(idx1);
								Segment s2 = mapSeg.get(idx2);
								Segment s3 = mapSeg.get(idx3);
								
								Face f = new Face(s1,s2,s3);
								
								listeFaces.add(f);
								
								
								System.out.println("On a " + cptFaces + " faces.");
							}

						}
					
					}
					System.out.println("Fin, ligne = " + ligne);
					ligne = lecteurAvecBuffer.readLine();
				}

			

			
				
			}

		} catch (IOException e) {
			System.out.println("Probleme");
			e.printStackTrace();
		}

		try {
			lecteurAvecBuffer.close();
		} catch (IOException e1) {
			System.out.println("Le fichier ne s'est pas ferme !");
			e1.printStackTrace();
		}
		System.out.println("mapSeg(5) = " + mapSeg.get(4));
		return listeFaces;
	}

	/**
	 * Cette fonction retourne une Map <Integer, Faces)
	 * 
	 * @param l
	 * @return
	 */
	public static Map<Integer, Face> mapFace(List<Face> l) {

		Map<Integer, Face> mapRes = new HashMap<Integer, Face>();
		Integer i = 0;
		Iterator<Face> it = (Iterator<Face>) l.iterator();
		while (it.hasNext()) {
			Face f = (Face) it.next();
			mapRes.put(i, f);
			i++;
		}
		return mapRes;
	}

	public static void afficherFaces() {
		System.out.println("Les faces : ");
		for (int i = 0; i < mapFaces.size(); i++) {
			System.out.println((i + 1) + " : " + mapFaces.get(i).toString());
		}
	}

	public static String supprimeSpace(String s) {
		String d = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				d = d + s.charAt(i);
			} else {
				// System.out.println("espace trouve");
			}
		}
		return d;
	}
}
