package fr.minestate.modif;


public abstract class Transformation extends Matrix {

	public Transformation(float[][] matrix) {
		super(matrix);
	}
	
	protected abstract void updateMatrix();
}