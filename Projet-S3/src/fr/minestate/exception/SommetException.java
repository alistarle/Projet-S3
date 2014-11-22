package fr.minestate.exception;
import fr.minestate.utils.Sommet;

public class SommetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SommetException(Sommet s) {
		new FichierException("Fichier corrompu: problème avec le sommet "+s.toString());
	}

}
