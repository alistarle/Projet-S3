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
import java.util.Set;

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
			mapSeg = new HashMap<Integer, Segment>();
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
		List<Integer> maListe = new ArrayList<Integer>();

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
			char espace = ' ';
			while (ligne != null && (!ligne.equals(delimiteur))) {

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
				// System.out.println(maListe.toString());
				Sommet s = sommetDepuisCoordonnees(maListe);
				listeSommet.add(s);
				maListe.clear();
				ligne = lecteurAvecBuffer.readLine();

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
					System.out.println("Ligne == delimiteur ! ");
					estDansFaces = true;
				}
				ligne = lecteurAvecBuffer.readLine();

			}

			// la
			if (estDansFaces) {
				System.out.println("dans faces");
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

	public static void afficherFaces() {
		System.out.println("Les faces : ");
		for (int i = 0; i < mapFaces.size(); i++) {
			System.out.println((i + 1) + " Faces : "
					+ indiceSegment(mapFaces.get(i).getA()) + " "
					+ indiceSegment(mapFaces.get(i).getB()) + " "
					+ indiceSegment(mapFaces.get(i).getC()));

		}
	}
	public static void afficherSegments() {
		System.out.println("Les segments : ");
		for (int i = 0; i < mapSeg.size(); i++) {
			System.out.println((i + 1) + " Segments : "
					+ indiceSommet(mapSeg.get(i).getA()) + " "
					+ indiceSommet(mapSeg.get(i).getB()));

		}
	}

	public static String supprimeSpace(String s) {
		String d = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				d = d + s.charAt(i);
			}

		}
		return d;
	}

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
			if (verifieIndiceSegment(tab[0] - 1)
					&& verifieIndiceSegment(tab[1] - 1)
					&& verifieIndiceSegment(tab[2] - 1)) {
				Segment s1 = mapSeg.get(tab[0] - 1);
				Segment s2 = mapSeg.get(tab[1] - 1);
				Segment s3 = mapSeg.get(tab[2] - 1);
				Face f = new Face(s1, s2, s3);
				return f;
			}
		} catch (SegmentException e) {
			// TODO Auto-generated catch block
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
			//throw new SegmentException(indice);
			System.out.println("lala");
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
		Segment sf = new Segment(s1, s2);
		return sf;

	}

	public static Sommet sommetDepuisCoordonnees(List<Integer> list) {

		String s = "";
		double[] tab = new double[3];
		int cpt = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				s = s + "" + list.get(i);
			}

			if (list.get(i) == null || i == list.size() - 1) {
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
		return sf;

	}

}
