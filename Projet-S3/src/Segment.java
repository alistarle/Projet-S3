/**
 * 
 * @author MineState
 *
 */

public class Segment {
	
	Sommet a ;
	Sommet b;
	
	/**
	 * Construit un Segment a partir de 2 Sommet
	 * @param a : le 1er sommet
	 * @param b : le second sommet
	 */
	public Segment (Sommet a, Sommet b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * Retourne le 1er sommet du segment
	 * @return
	 */
	public Sommet getA() {
		return a;
	}

	/**
	 * Permet de modifier le 1er sommet du segment
	 * @param a
	 */
	public void setA(Sommet a) {
		this.a = a;
	}

	/**
	 * Retourne le second sommet du segment
	 * @return
	 */
	public Sommet getB() {
		return b;
	}

	/**
	 * Permet de modifier le second sommet du segment
	 * @param b
	 */
	public void setB(Sommet b) {
		this.b = b;
	}
	
	/**
	 * Retourne les coordonnees des sommets qui composent le segment (sous forme de String)
	 */
	@Override
	public String toString() {
		return "Sommet 1 : " + a.getX() + " " + a.getY() + " " +  a.getZ() + 
				" Sommet 2 : " + b.getX() + " " + b.getY() + " " + b.getZ();
 	}

	/**
	 * Renvoie la longueur d'un segment
	 * @return
	 */
	public double longueurSegment () {
		double longueur = 0;
		double longueurCarree = Math.pow((this.b.getX()) - this.a.getX(),2) + Math.pow(this.b.getY()-this.a.getY(),2) 
				+ Math.pow(this.b.getZ() - this.a.getZ(),2);
		
		longueur = Math.sqrt(longueurCarree);		
		return longueur;
	}
	
	/**
	 * Retourne le milieu d'un Segment
	 * @return
	 */
	public Sommet milieuSegment () {
		Sommet milieu;	
		double milieuX = (this.getA().x + this.getB().x) / 2;
		double milieuY = (this.getA().y + this.getB().y) / 2 ;
		double milieuZ = (this.getA().z + this.getB().z) / 2;
		milieu = new Sommet (milieuX, milieuY, milieuZ);
		return milieu;
	}
	
}
