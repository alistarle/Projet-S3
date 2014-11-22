package fr.minestate.utils;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import fr.minestate.figure.Segment;
import fr.minestate.figure.Triangle;
import fr.minestate.models.ModelEntry;
import fr.minestate.models.VolumeModel;
import fr.minestate.models.VolumeSetModel;

public class GtsParser {
	public VolumeModel volume;
	public VolumeSetModel volumeModel;

	public GtsParser(VolumeSetModel volumeModel) {
		this.volumeModel = volumeModel;
	}

	public static VolumeModel getVolumeFromFile(File selectedFile) {
		VolumeModel volume = new VolumeModel();
		try {
			FileReader fr = new FileReader(selectedFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			// on r�cup�re la 1�re ligne non comment�e
			line = getNextLine(reader);
			String[] numbers = line.split(" ");

			// on r�cup�re les points

			ArrayList<Point> points = new ArrayList<Point>();
			ArrayList<Segment> segments = new ArrayList<Segment>();
			ArrayList<Triangle> triangles = new ArrayList<Triangle>();

			// on r�cup�re les points
			int nb = Integer.parseInt(numbers[0]);
			for(int i = 0 ; i < nb ; i++) {
				line = getNextLine(reader);
				String[] pts = line.split(" ");
				points.add(new Point(Float.parseFloat(pts[0]), Float.parseFloat(pts[1]), Float.parseFloat(pts[2])));
			}

			// on r�cup�re les segments
			nb = Integer.parseInt(numbers[1]);
			for(int i = 0 ; i < nb ; i++) {
				line = getNextLine(reader);
				String[] sgmts = line.split(" ");
				segments.add(new Segment(points.get(Integer.parseInt(sgmts[0]) - 1), 
						points.get(Integer.parseInt(sgmts[1]) - 1)));
			}

			// on r�cup�re les triangles
			nb = Integer.parseInt(numbers[2]);
			for(int i = 0 ; i < nb ; i++) {
				line = getNextLine(reader);
				String[] trs = line.split(" ");
				triangles.add(new Triangle(segments.get(Integer.parseInt(trs[0]) - 1), 
						segments.get(Integer.parseInt(trs[1]) - 1), 
						segments.get(Integer.parseInt(trs[2]) - 1)));
			}
			volume.addAllFace(triangles);



			fr.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Fichier incorrect !");
			return null;
		}

		String[] name = selectedFile.getPath().split("\\\\"); // "\\\\" pour s�parer sur le '\' (sinon bug..?)		
		name = name[name.length - 1].split("[.]"); // re-split pour enlever l'extension
		volume.setName(name[0]);
		return volume;
	}




	public static VolumeModel getVolumeFromModelEntry(ModelEntry model) {
		VolumeModel volume = new VolumeModel();
		try {
			BufferedReader reader = model.getModelReader();
			// on r�cup�re la 1�re ligne non comment�e
			String line = getNextLine(reader);
			String[] numbers = line.split(" ");

			ArrayList<Point> points = new ArrayList<Point>();
			ArrayList<Segment> segments = new ArrayList<Segment>();
			ArrayList<Triangle> triangles = new ArrayList<Triangle>();

			// on r�cup�re les points
			points = getPoints(reader, Integer.parseInt(numbers[0]));

			// on r�cup�re les segments
			segments = getSegments(reader, Integer.parseInt(numbers[1]), points);

			// on r�cup�re les triangles
			triangles = getTriangles(reader, Integer.parseInt(numbers[2]), segments);

			// on ajoute les triangles au volume
			volume.addAllFace(triangles);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
			System.out.println("Fichier incorrect !");
			return null;
		}

		volume.setName(model.getName());
		return volume;
	}


	private static ArrayList<Triangle> getTriangles(BufferedReader reader, int nbLines, ArrayList<Segment> segments) {
		String line = "";
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for(int i = 0 ; i < nbLines ; i++) {
			line = getNextLine(reader);
			String[] trs = line.split(" ");
			triangles.add(new Triangle(segments.get(Integer.parseInt(trs[0]) - 1), 
					segments.get(Integer.parseInt(trs[1]) - 1), 
					segments.get(Integer.parseInt(trs[2]) - 1)));
		}
		return triangles;

	}


	private static ArrayList<Segment> getSegments(BufferedReader reader, int nbLines, ArrayList<Point> points) {
		String line = "";
		ArrayList<Segment> segments = new ArrayList<Segment>();
		for(int i = 0 ; i < nbLines ; i++) {
			line = getNextLine(reader);
			String[] sgmts = line.split(" ");
			segments.add(new Segment(points.get(Integer.parseInt(sgmts[0]) - 1), 
					points.get(Integer.parseInt(sgmts[1]) - 1)));
		}
		return segments;
	}

	private static ArrayList<Point> getPoints(BufferedReader reader, int nbLines) {
		// on r�cup�re les points
		String line = "";
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i = 0 ; i < nbLines ; i++) {
			line = getNextLine(reader);
			String[] pts = line.split(" ");
			points.add(new Point(Float.parseFloat(pts[0]), Float.parseFloat(pts[1]), Float.parseFloat(pts[2])));
		}
		return points;
	}



	private static String getNextLine(BufferedReader reader) {
		try {
			String line;
			do {
				line = reader.readLine();
			} while(line != null && isComment(line));
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean isComment(String line) {
		for(int i = 0 ; i < line.length() ; i++) {
			char c = line.charAt(i);
			if(c == '#') 
				return true;

			if(c != ' ')
				return false;
		}
		return true;
	}

	public static boolean isValidFile(String fileContent) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(fileContent));
			// on r�cup�re la 1�re ligne non comment�e
			String line = getNextLine(reader);
			String[] numbers = line.split(" ");

			ArrayList<Point> points = new ArrayList<Point>();
			ArrayList<Segment> segments = new ArrayList<Segment>();
			ArrayList<Triangle> triangles = new ArrayList<Triangle>();

			// on r�cup�re les points
			points = getPoints(reader, Integer.parseInt(numbers[0]));
			// on verifie la validit� des points
			if(points == null) return false;

			// on r�cup�re les segments
			segments = getSegments(reader, Integer.parseInt(numbers[1]), points);
			// on verifie la validite des segments
			if(segments == null) return false;
			for(Segment s : segments) {
				if(!s.isValid()) return false;
			}

			// on r�cup�re les triangles
			triangles = getTriangles(reader, Integer.parseInt(numbers[2]), segments);
			// on verifie la validite des triangles
			if(triangles == null) return false;
			for(Triangle t : triangles) {
				if(!t.isValid()) return false;
			}
			
			if(getNextLine(reader) != null) return false;
			
		} catch (Exception e) {
			return false;
		}


		// si rien a ete retourne jusquici, cest que le fichier est valide !
		return true;
	}
}
