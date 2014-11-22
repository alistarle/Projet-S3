package fr.minestate.modif;

/**
 * Classe abstraite qui permet de definir une transformation
 * @author scta
 *
 */
public abstract class Transformation extends Matrix {

	/**
	 * Permet de definir une Transformation
	 * @param matrix le tableau de float selon lequel on definit la rotation
	 */
	public Transformation(float[][] matrix) {
		super(matrix);
	}
	
	protected abstract void updateMatrix();
}