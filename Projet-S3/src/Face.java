
public class Face {

	Segment a;
	Segment b;
	Segment c;
	
	public Face (Segment a, Segment b, Segment c) {
		this.a  = a;
		this. b = b;
		this. c = c;
		
		// Quand on construit une face, il faut s'assurer qu'elle soit bien fermee.
		// Dans le cas contraire, on leve une FaceException (classe deja creee).
		// Test encore a faire
	}

	public Segment getA() {
		return a;
	}

	public void setA(Segment a) {
		this.a = a;
	}

	public Segment getB() {
		return b;
	}

	public void setB(Segment b) {
		this.b = b;
	}

	public Segment getC() {
		return c;
	}

	public void setC(Segment c) {
		this.c = c;
	}
	
	
}
