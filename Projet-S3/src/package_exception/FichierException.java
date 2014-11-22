package package_exception;

/**
 * Permet de creer une exception de type 'FichierException'
 * @author scta
 */
public class FichierException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Permet de lever une exception de type FichierException
	 * @param message le message a afficher
	 */
	public FichierException(String message) {
		super(message);
	}

}
