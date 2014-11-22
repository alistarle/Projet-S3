package package_exception;

/**
 * Permet de creer une exception de type SegmentException
 * @author scta
 *
 */
public class SegmentException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Permet de lever une exception de type SegmentException
	 */
	public SegmentException() {
		System.out.println("Format du segment incorrect.");
	}

	/**
	 * Permet de lever une exception de type SegmentException
	 * @param i
	 */
	public SegmentException(Integer i) {
		System.out.println("Segment non présent dans la base.");
	}
	
	/**
	 * Permet de lever une exception de type SegmentException
	 * @param message le message a afficher
	 */
	public SegmentException(String message) {
		System.out.println(message);
	}
	
}