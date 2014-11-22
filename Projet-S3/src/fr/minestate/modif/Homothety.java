package fr.minestate.modif;

public class Homothety extends Transformation {

	private float factor;
	
	public Homothety(float[][] matrix) {
		super(matrix);
	}

	public Homothety(int factor) {
		super(new float[4][4]);
		setFactor(factor);
	}

	public void setFactor(float factor) {
		this.factor = factor;
		if (this.factor < 0.1) 
			this.factor = 0.1f;
		updateMatrix();		
	}

	public float getFactor() {
		return factor;
	}
	
	protected void updateMatrix() {
		this.matrix = new float [][] {
				{factor, 0, 0, 0},
				{0, factor, 0, 0},
				{0, 0, factor, 0},
				{0, 0, 0, 1}};
	}

	public void addFactor(float factor) {
		setFactor(this.factor * (1 + (factor / 100)));
	}
}
