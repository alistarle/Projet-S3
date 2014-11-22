package fr.minestate.modif;

public class Translation extends Transformation {

	public final static int X_AXIS = 0;
	public final static int Y_AXIS = 1;

	private int axis;
	private int norm;

	public Translation(float[][] matrix) {
		super(matrix);
		this.norm = (int) matrix[0][3];
	}

	public Translation(int axis, int norm) {
		super(new float[4][4]);
		this.axis = axis;
		this.setNorm(norm);
	}

	public void setNorm(int norm) {
		this.norm = norm;
		updateMatrix();
	}
	
	public int getNorm() {
		return norm;
	}

	@Override
	protected void updateMatrix() {
		float x = 0;
		float y = 0;
		
		if (axis == X_AXIS) {
			x = norm;
		} else {
			y = norm;
		}
		
		
		this.matrix = new float[][] {
									{1f, 0, 0, x},
									{0, 1, 0, y},
									{0, 0, 1, 0},
									{0, 0, 0, 1}};
	}

	public void addNorm(int norm) {
		setNorm(this.norm + norm);
	}
}