package fr.minestate.figure;

import fr.minestate.utils.Point;

/**
 * Class Segment permettant de manipuler un segment constitu� de 2 Points
 * @see Point
 * @author Louis
 *
 */

public class Segment {
	private Point p1;
	private Point p2;
	
	public Segment() {
		p1 = new Point();
		p2 = new Point();
	}
	
	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Point getPoint1() {
		return p1;
	}
	
	public Point getPoint2() {
		return p2;
	}
	
	/**
	 * Deux segments sont �gaux si les deux points les constituants sont identiques.
	 * @param s
	 * @return true si les deux points constituant les deux segments sont identiques. (deux segments superpos�s)
	 */
	public boolean equals(Segment s) {
		return (this.p1.equals(s.p1) && this.p2.equals(s.p2)) || (this.p1.equals(s.p2) && this.p2.equals(s.p1)); 
	}
	
	/**
	 * un segment est consid�r� comme valide si les deux points qui le constitue sont diff�rents.
	 * @return true si le segment est valide.
	 */
	public boolean isValid() {
		return !p1.equals(p2);
	}
	
	
	
}
