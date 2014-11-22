package fr.minestate.modif;


public class Matrix {

	protected float[][] matrix;	
	
	// constante donnant la precision exigee
	private final float EPSILON = 0.000001f;
	
	public Matrix(float[][] matrix) {
		this.matrix = matrix;
	}
	
	public Matrix(int rowCount, int colCount) {
		this.matrix = new float[rowCount][colCount];
	}
	
	public int height() {
		return matrix.length;
	}
	
	public int width() {
		return matrix[0].length;
	}
	
	/**
	 * 
	 * @param m
	 * @return la somme des deux matrices ou null si les matrices sont incompatibles
	 */
	public Matrix add(Matrix m) {
		if (height() != m.height() || width() != m.width()) 
			return null;
		
		float[][] out = new float[height()][width()];
		
		for (int i = 0; i < height(); i ++)
			for (int j = 0; j < width(); j++)
				out[i][j] = get(i,j) + m.get(i, j);
				
		return new Matrix(out);
	}
	
	public boolean equals(Matrix m) {
		if (height() != m.height() || width() != m.width()) 
			return false;
		
		for (int i = 0; i < height(); i ++)
			for (int j = 0; j < width(); j++)
				if (!closeEnough(get(i, j), m.get(i, j))) {
					return false;
				}			
				
		return true;
	}
	
	private boolean closeEnough(float f1, float f2) {
		return Math.abs(f1 - f2) < EPSILON;
	}
	
	public float get(int row, int col) {
		return matrix[row][col];
	}
	
	public void set(int row, int col, float value) {
		matrix[row][col] = value;
	}

	public Matrix prod(Matrix m) {
		if (width() != m.height()) {
			return null;
		}
		
		Matrix out = new Matrix(new float[height()][m.width()]);
		
		for (int i = 0; i < out.height(); i++) 
			for (int j = 0; j < out.width(); j++) {
				float value = 0;
				for (int k = 0; k < width(); k++) 
					value += get(i, k) * m.get(k, j);
				out.set(i, j, value);
			}
		
		return out;
	}
	
	public Matrix invert() {
		if (width() != height())
			return null;
		
		Matrix out = initGauss();
		
		for (int i = 0; i < width(); i++) {
			int j = i;
			while (j < out.height() && closeEnough(out.get(j, i), 0)) {
				j++;
			}
			if (j == out.height())
				return null;
			out.swap(j, i);
			out.multiplication(j, 1 / out.get(j, i));
			for (int k = 0; k < out.height(); k++) {
				if (k == j)
					continue;
				out.linearCombination(i, k, - out.get(k, i));
			}
		}
		return concludeGauss(out);
	}
	
	private Matrix initGauss() {
		Matrix out = new Matrix(new float[height()][2 * width()]);
		for (int i = 0; i < height(); i ++) {
			for (int j = 0; j < width(); j++) {
				out.set(i, j, get(i,j));
				if (i == j)
					out.set(i, j + width(), 1);
			}
		}
		return out;
	}
	
	private Matrix concludeGauss(Matrix m) {
		Matrix out = new Matrix(new float[height()][width()]);
		for (int i = 0; i < height(); i ++) {
			for (int j = 0; j < width(); j++) {
				out.set(i, j, m.get(i, width() + j));
			}
		}
		return out;		
	}
	
	private void swap (int row1, int row2) {
		float tmp;
		for (int i = 0; i < width(); i++) {
			tmp = get(row1, i);
			set(row1, i, get(row2, i));
			set(row2, i, tmp);
		}
	}
	
	private void multiplication(int row, float factor) {
		for (int i = 0; i < width(); i++) {
			set(row, i, get(row, i) * factor);
		}
	}
	
	/*
	 * sets the values of row2 to factor * row1 + row2
	 */
	private void linearCombination(int row1, int row2, float factor) {
		for (int i = 0; i < width(); i++) {
			set(row2, i, get(row2, i) + factor * get(row1, i));
		}
	}
	
	public String toString() {
		if (matrix == null)
			return "null";
		String out = "";
		for (int i = 0; i < height(); i++) {
			for (int j = 0; j < width(); j++) {
				out += get(i, j) + "\t";
			}
			out += "\n";
		}
		return out;
	}
}
