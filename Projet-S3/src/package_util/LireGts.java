package package_util;

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

import package_exception.FichierException;
import package_exception.SegmentException;
import package_exception.SommetException;

class LireGts {

	/**
	 * Stockent les sommets, les segments, et les faces du fichier .gts dans
	 * l'ordre
	 */
	static Map<Integer, Sommet> mapSom;
	static Map<Integer, Segment> mapSeg;
	static Map<Integer, Face> mapFaces;

	/**
	 * Valeurs théoriques inscrites sur la premiere ligne du fichier .gts
	 */
	static int nbFaces;
	static int nbSommets;
	static int nbSegments;

	/**
	 * Valeurs reelles calculees
	 */
	static int nbFacesReel;
	static int nbSommetsReel;
	static int nbSegmentsReel;

	/**
	 * Dans l'ordre, on y fait : - selection d'un fich
	 * 
	 * @param arg
	 * @throws IOException
	 * @throws FichierException
	 */

	public static void main(String[] arg) throws IOException, FichierException {

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
			mapSeg = new HashMap<Integer, Segment>();
			List<Sommet> sommets = listerSommet(fichier);

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

			Integer[] t = nbPointsSegmentsFaces(fichier);
			nbSommets = t[0];
			nbSegments = t[1];
			nbFaces = t[2];

			System.out.println("En theorie, le modele comporte " + nbSommets
					+ " sommets " + nbSegments + " segments " + nbFaces
					+ " faces");
			nbSommetsReel = mapSom.size();
			nbSegmentsReel = mapSeg.size();
			nbFacesReel = mapFaces.size();
			System.out.println("D'apres nos calculs, le modele comporte "
					+ nbSommetsReel + " sommets " + nbSegmentsReel
					+ " segments " + nbFacesReel + " faces");

			if (nbSommets != nbSommetsReel || nbSegments != nbSegmentsReel
					|| nbFaces != nbFacesReel) {
				throw new FichierException(
						"Le nombre de sommets indiques, et ou de segments, et ou de faces est errone.");
			}

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
		List<String> maListe = new ArrayList<String>();
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
			ligne = lecteurAvecBuffer.readLine();
			ligne = lecteurAvecBuffer.readLine();
			while (ligne != null && (!ligne.equals(delimiteur))) {

				char[] tabLigne = ligne.toCharArray();

				for (int i = 0; i < tabLigne.length; i++) {
					if (ligne.charAt(0) == '#') {
						ligne = lecteurAvecBuffer.readLine();
						System.out.println("# trouve !");
					}

					else {
						String s = tabLigne[i] + "";

						if (!s.equals(" ")) {
							maListe.add(tabLigne[i] + "");
						}

						else {
							maListe.add(null);
						}
					}

				}

				Sommet s =null;
				try {
					s = sommetDepuisCoordonnees(maListe);
				} catch (SommetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listeSommet.add(s);
				maListe.clear();
				ligne = lecteurAvecBuffer.readLine();

			}

		} catch (IOException e) {
			System.out.println("listerSommet : probleme");
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
			List<Integer> maListe = new ArrayList<Integer>();
			String delimiteurSegment = "#segment";
			boolean estDansSegment = false;
			ligne = lecteurAvecBuffer.readLine();
			char espace = ' ';

			while (ligne != null && !ligne.equals(delimiteurFace)) {

				if (ligne.equals(delimiteurSegment)) {
					estDansSegment = true;
					ligne = lecteurAvecBuffer.readLine();
				}

				if (ligne.equals(delimiteurFace)) {
					estDansSegment = false;
				}

				if (estDansSegment) {
		
					char[] tabLigne = ligne.toCharArray();

					for (int i = 0; i < tabLigne.length; i++) {
			

						String s = tabLigne[i] + "";
						if (tabLigne[i] != espace) {
							Integer a = Integer.parseInt(s);
							maListe.add(a);
							
						}

						else {
							maListe.add(null);
						}

					}
			
					Segment s = segmentDepuisIndexSommet(maListe);
					listeSegment.add(s);
					maListe.clear();
					ligne = lecteurAvecBuffer.readLine();

				}

				else {
					ligne = lecteurAvecBuffer.readLine();
				}

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
				if (ligne.equals(delimiteur)) {
					estDansFaces = true;
				}
				ligne = lecteurAvecBuffer.readLine();
			}

			if (estDansFaces) {
				List<Integer> maListe = new ArrayList<Integer>();
				while (ligne != null) {
					char[] tabLigne = ligne.toCharArray();
					for (int i = 0; i < tabLigne.length; i++) {
						String s = tabLigne[i] + "";
						if (tabLigne[i] != espace) {
							Integer a = Integer.parseInt(s);
							maListe.add(a);
						}
						else {
							maListe.add(null);
						}
					}
					Face f = faceDepuisIndexSegment(maListe);
				
					listeFaces.add(f);
					maListe.clear();
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

	/**
	 * Affiche les faces du modele. Donne les numeros des 3 segments associes.
	 */
	public static void afficherFaces() {
		System.out.println("Les faces : ");
		for (int i = 0; i < mapFaces.size(); i++) {		
			System.out.println((i + 1) + " Faces : "
					+ indiceSegment(mapFaces.get(i).getA()) + " "
					+ indiceSegment(mapFaces.get(i).getB()) + " "
					+ indiceSegment(mapFaces.get(i).getC()));

		}
	}

	/**
	 * Affiche les segments du modele. Donne les numeros des 2 sommets associes.
	 */
	public static void afficherSegments() {
		System.out.println("Les segments : ");
		for (int i = 0; i < mapSeg.size(); i++) {
			System.out.println((i + 1) + " Segments : "
					+ indiceSommet(mapSeg.get(i).getA()) + " "
					+ indiceSommet(mapSeg.get(i).getB()));

		}
	}


	/**
	 * Donne l'indice d'un segment s
	 * @param s : le segment dont on veut connaitre l'indice dans la mapSeg
	 * @return son indice dans la mapSeg
	 */
	public static Integer indiceSegment(Segment s) {

		for (Map.Entry<Integer, Segment> entry : mapSeg.entrySet()) {
			Integer ind = entry.getKey();
			Segment seg = entry.getValue();
			if (seg.equals(s)) {
				return ind + 1;
			}

		}
		return null;
	}

	/**
	 * Donne l'idice d'un sommet s
	 * @param s : le sommet dont on veut connnaitre l'indice dans la mapSom
	 * @return : son indice dans la mapSom
	 */
	public static Integer indiceSommet(Sommet s) {

		for (Map.Entry<Integer, Sommet> entry : mapSom.entrySet()) {
			Integer ind = entry.getKey();
			Sommet seg = entry.getValue();
			if (seg.equals(s)) {
				return ind + 1;
			}

		}
		return null;
	}

	
	public static Face faceDepuisIndexSegment(List<Integer> list) {

		String s = "";
		Integer[] tab = new Integer[3];
		int cpt = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				s = s + "" + list.get(i);
			}
			if (list.get(i) == null || i == list.size() - 1) {
				String b = s.trim();
				Integer a = Integer.parseInt(b);
				tab[cpt] = a;
				cpt++;
				s = "";
			}

		}

		try {
			Segment s1 = null;
			Segment s2 = null;
			Segment s3 = null;
			
			if (verifieIndiceSegment(tab[0] - 1)) {
					s1 = mapSeg.get(tab[0] - 1);

				if (verifieIndiceSegment(tab[2] - 1)) {
					s2 = mapSeg.get(tab[1] - 1);
				}
				if (verifieIndiceSegment(tab[2] - 1)) {
					 s3 = mapSeg.get(tab[2] - 1);
					 
				}
				
				else {
					throw new SegmentException("La face issue de " + list.toString() + " n'a pu etre construite. Un des segments indique n'est pas dans la base");
				}
			}
			
				Face f = new Face(s1, s2, s3);
				return f;

		} catch (SegmentException e) {
			e.printStackTrace();
		}

		return null;

	}

	
	
	public static boolean verifieIndiceSegment(Integer indice)
			throws SegmentException {
		if (mapSeg.containsKey(indice)) {
			return true;
		}

		else {

			return false;

		}

	}
	
	public static boolean verifieIndiceSommet(Integer indice)
			throws SegmentException {
		if (mapSom.containsKey(indice)) {
			return true;
		}

		else {

			return false;

		}

	}

	public static Segment segmentDepuisIndexSommet(List<Integer> list) {

		String s = "";
		Integer[] tab = new Integer[2];
		int cpt = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				s = s + "" + list.get(i);
			}
			if (list.get(i) == null || i == list.size() - 1) {
				String b = s.trim();
				Integer a = Integer.parseInt(b);
				tab[cpt] = a;
				cpt++;
				s = "";
			}

		}

		Sommet s1 = mapSom.get(tab[0] - 1);
		Sommet s2 = mapSom.get(tab[1] - 1);
		Segment sf = null;
		try {
			sf = new Segment(s1, s2);
		} catch (SegmentException e) {
			System.out.println("La création du segment a échoué.");
			e.printStackTrace();
		}

		return sf;

	}

	public static Sommet sommetDepuisCoordonnees(List<String> list) throws SommetException {
		int cptCoordonnees = 0;
		String s = "";
		double[] tab = new double[3];
		int cpt = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				s = s + "" + list.get(i);
			}
			if (list.get(i) == null || i == list.size() - 1) {
				cptCoordonnees ++;
				String b = s.trim();
				double a = Double.parseDouble(b);
				tab[cpt] = a;
				cpt++;
				s = "";
			}

		}
		
	
		double s1 = tab[0];
		double s2 = tab[1];
		double s3 = tab[2];
		
		Sommet sf = new Sommet(s1, s2, s3);
		if (cptCoordonnees!=3) {
			throw new SommetException(sf);
		}
		return sf;

	}

	public static Integer[] nbPointsSegmentsFaces(File fichier) {
		BufferedReader lecteurAvecBuffer = null;
		String ligne;
		List<Integer> maListe = new ArrayList<Integer>();

		try {
			lecteurAvecBuffer = new BufferedReader(new FileReader(fichier));
		}

		catch (FileNotFoundException exc) {
			System.out.println("Erreur d'ouverture : methode listerSegment");
			System.out.println(exc.getMessage());
		}

		try {
			ligne = lecteurAvecBuffer.readLine();
			char[] tabLigne = ligne.toCharArray();

			for (int i = 0; i < tabLigne.length; i++) {

				String s = tabLigne[i] + "";
				if (tabLigne[i] != ' ') {
					Integer a = Integer.parseInt(s);
					maListe.add(a);
				}

				else {
					maListe.add(null);
				}

			}
			System.out.println("ligne = " + ligne);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Integer[] t = nombre(maListe);

		try {
			lecteurAvecBuffer.close();
		} catch (IOException e) {
			System.out
					.println("Fermeture de \"lecteurAvecBuffer\" impossible.");
			e.printStackTrace();
		}
		return t;
	}

	public static Integer[] nombre(List<Integer> list) {
		String s = "";
		Integer[] tab = new Integer[3];
		int cpt = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				s = s + "" + list.get(i);
			}

			if (list.get(i) == null || i == list.size() - 1) {
				String b = s.trim();
				Integer a = Integer.parseInt(b);
				tab[cpt] = a;
				cpt++;
				s = "";
			}

		}

		return tab;

	}
}
