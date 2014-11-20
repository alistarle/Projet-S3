package package_exception;
import package_util.Sommet;

public class SommetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SommetException(Sommet s) {
		System.out.println("Format du sommet " + s.toString() + " incorrectes. Veuillez verifier ses coordonnees.");
	}

}
