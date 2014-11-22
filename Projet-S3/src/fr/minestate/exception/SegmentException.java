package fr.minestate.exception;
public class SegmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SegmentException() {
		new FichierException("Fichier corrompu: problème avec les segments");
	}

	public SegmentException(Integer i) {
		new FichierException("Fichier corrompu: problème avec les segments");	
	}
	
}