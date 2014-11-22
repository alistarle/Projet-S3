package fr.minestate.figure;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.minestate.modif.Matrix;
import fr.minestate.figure.Segment;
import fr.minestate.utils.Point;

public class Triangle implements Comparable<Triangle>{

	private Point[] points;
	private Segment[] segments;
	
	public Triangle(Point p1, Point p2, Point p3) {
		points = new Point[] {p1, p2, p3};
	}
	
	public Triangle(Segment s1, Segment s2, Segment s3) {
		segments = new Segment[] {s1, s2, s3};
		
		
		points = new Point[3];
		Set<Point> set = new HashSet<Point>();
		set.add(s1.getPoint1());
		set.add(s1.getPoint2());
		set.add(s2.getPoint1());
		set.add(s2.getPoint2());
		set.add(s3.getPoint1());
		set.add(s3.getPoint2());
		int i = 0;
		for(Point p : set) {
			points[i] = p;
			i++;
		}
	}

	public float getGravityZ() {
		float z = 0;
		for (int i = 0; i < 3; i++) 
			z += points[i].getZ();
		return z / 3;
		
		/*float z = (points[0].getZ() + points[2].getZ()) / 2;
		for (int i = 0; i < 2 ; i++) {
			float tmp = (points[i].getZ() + points[i+1].getZ()) / 2;
			if (tmp > z) {
				z = tmp;
			}
		}
		return z;*/
	}

	@Override
	public int compareTo(Triangle t) {
		float comp = getGravityZ() - t.getGravityZ();
		if (comp > 0)
			return 1;
		if (comp == 0)
			return 0;
		return -1;
	}
	
	public Point[] getCoords() {
		return points;
	}
	
	public Triangle transform(Matrix m) {
		Point[] out = new Point[3];
		
		for (int i = 0; i < out.length; i++) {
			out[i] = points[i].transform(m);			
		}
		
		return new Triangle(out[0], out[1], out[2]);
	}
	
	
	/**
	 * Un segment est valide si il est constitu� de trois segments diff�rents valides 
	 * ET que le nombre de points qui constitue ces segments est de 3.
	 * @return true si le triangle est valide
	 */
	public boolean isValid() {
		return allSegmentValid() && getNumberOfPoint() == 3 && allSegmentDifferents();
	}
	
	/*
	 * Retourne vrai si les 3 segments que constitue le triangle sont differents.
	 */
	private boolean allSegmentDifferents() {
		
		
		return !segments[0].equals(segments[1])
				&& !segments[1].equals(segments[2])
				&& !segments[2].equals(segments[0]);
	}

	/*
	 * Retourne le nombre de points DIFFERENT constituant le triangle.
	 */
	private int getNumberOfPoint() {
		List<Point> points = new ArrayList<Point>();
		for(Segment s : segments) {
			if(!points.contains(s.getPoint1()))
				points.add(s.getPoint1());
			if(!points.contains(s.getPoint2()))
				points.add(s.getPoint2());
		}
		return points.size();
	}

	/*
	 * Retourne vrai si les 3 segments sont valide (non "null")
	 */
	private boolean allSegmentValid() {
		for(Segment s : segments) {
			if(!s.isValid())
				return false;
		}
		return true;
	}
	
	
	
}