package fr.minestate.exception;

import javax.swing.JOptionPane;

public class FichierException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FichierException(String message) {
		new JOptionPane();
		JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

}
