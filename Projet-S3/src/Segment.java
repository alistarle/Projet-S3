
public class Segment {


	Sommet a ;
	Sommet b;
	
	public Segment (Sommet a, Sommet b) {
		this.a = a;
		this.b = b;
	}

	public Sommet getA() {
		return a;
	}

	public void setA(Sommet a) {
		this.a = a;
	}

	public Sommet getB() {
		return b;
	}

	public void setB(Sommet b) {
		this.b = b;
	}
	
	
	@Override
	public String toString() {
		return "Sommet 1 : " + a.getX() + " " + a.getY() + " " +  a.getZ() + 
				" Sommet 2 : " + b.getX() + " " + b.getY() + " " + b.getZ();
 	}

	
}
