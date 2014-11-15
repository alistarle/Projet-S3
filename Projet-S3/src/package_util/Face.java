package package_util;

/**
 * 
 * @author MineState
 *
 */

public class Face {

	@Override
	public String toString() {
		return "Face [a=" + a + ", b=" + b + ", c=" + c + ", estVisible="
				+ estVisible + "]";
	}

	Segment a;
	Segment b;
	Segment c;
	boolean estVisible;
	
	
	/**
	 * Construit une face a partir de trois Segment (sous forme de triangle donc)
	 * @param a
	 * @param b
	 * @param c
	 */
	public Face (Segment a, Segment b, Segment c) {
		this.a  = a;
		this. b = b;
		this. c = c;
		
		// Quand on construit une face, il faut s'assurer qu'elle soit bien fermee.
		// Dans le cas contraire, on leve une FaceException (classe deja creee).
		// Test encore a faire
	}


	
	
	/**
	 * Retourne le 1er Segment d'une face
	 * @return
	 */
	public Segment getA() {
		return a;
	}

	/**
	 * Permet de modifier le 1er Segment d'une face
	 * @param a
	 */
	public void setA(Segment a) {
		this.a = a;
	}

	/**
	 * Retourne le 2eme Segement d'une face
	 * @return
	 */
	public Segment getB() {
		return b;
	}

	/**
	 * Permet de modifier le 2eme segment d'une face
	 * @param b
	 */
	public void setB(Segment b) {
		this.b = b;
	}

	/**
	 * Retourne le 3eme segment d'une face
	 * @return
	 */
	public Segment getC() {
		return c;
	}

	/**
	 * Permet de modifier le 3eme Segment d'une face
	 * @param c
	 */
	public void setC(Segment c) {
		this.c = c;
	}
	
	
}
