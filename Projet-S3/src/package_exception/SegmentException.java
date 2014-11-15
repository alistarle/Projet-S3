package package_exception;
public class SegmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SegmentException() {
		System.out.println("Format du segment incorrect.");
	}

	public SegmentException(Integer i) {
		System.out.println("Segment non présent dans la base.");
	}
	
	public SegmentException(String message) {
		System.out.println(message);
	}
	
}