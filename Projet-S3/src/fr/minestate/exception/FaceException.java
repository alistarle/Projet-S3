package fr.minestate.exception;
class FaceException extends Exception{ 
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public FaceException(){
	new FichierException("Fichier corrompu: problème avec les faces");
  }  
}