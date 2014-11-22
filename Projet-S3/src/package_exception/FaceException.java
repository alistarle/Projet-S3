package package_exception;

/**
 * Permet de lever une Exception de type 'FaceException'
 * @author scta
 *
 */
class FaceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Permet de lever une FaceException
	 */
	public FaceException() {
		System.out.println("Format de face incorrect.");
	}
}