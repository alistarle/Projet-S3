package package_exception;
import package_util.Sommet;

/**
 * Permet de creer une exception de type SommetException
 * @author scta
 *
 */
public class SommetException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Permet de lever une SommetException sur un Sommet
	 * @param s le sommet defectueux
	 */
	public SommetException(Sommet s) {
		System.out.println("Format du sommet " + s.toString() + " incorrectes. Veuillez verifier ses coordonnees.");
	}

}
