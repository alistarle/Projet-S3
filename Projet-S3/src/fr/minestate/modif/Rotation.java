package fr.minestate.modif;

public class Rotation extends Transformation {

	public final static int X_AXIS = 0;
	public final static int Y_AXIS = 1;
	public final static int Z_AXIS = 2;

	private int axis;
	private int angle;

	public Rotation(float[][] matrix) {
		super(matrix);
	}

	public Rotation(int axis, int angle) {
		super(new float[4][4]);
		this.axis = axis;
		setAngle(angle);
	}

	public void addAngle(int angle) {
		setAngle((this.angle + angle) %360);
	}

	public void setAngle(int angle) {
		this.angle = angle;
		updateMatrix();
	}

	protected void updateMatrix() {
		float rad = (float) Math.toRadians(angle);
		if (axis == X_AXIS) {
			this.matrix = new float [][] {
					{1f, 0, 0, 0},
					{0, (float) Math.cos(rad), (float) (-1 * Math.sin(rad)), 0},
					{0, (float) Math.sin(rad), (float) Math.cos(rad), 0},
					{0, 0, 0, 1}};
		} else if (axis == Y_AXIS) {
			this.matrix = new float [][] {
					{(float) Math.cos(rad), 0, (float) Math.sin(rad), 0},
					{0 ,1 ,0 , 0},
					{-1 * (float) Math.sin(rad), 0, (float) Math.cos(rad), 0},
					{0, 0, 0, 1}};
		} else {
			this.matrix = new float [][] {
					{(float) Math.cos(rad), -1 * (float) Math.sin(rad), 0, 0},
					{(float) Math.sin(rad), (float) Math.cos(rad), 0 , 0},
					{0, 0, 1, 0},
					{0, 0, 0, 1}};
		}
	}
}


