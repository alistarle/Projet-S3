package package_util;

public class Matrice {

	protected float[][] Matrice;	
	
	// constante donnant la precision exigee
	private final float EPSILON = 0.000001f;
	
	public Matrice(float[][] matrice) {
		this.Matrice = Matrice;
	}
	
	public Matrice(int rowCount, int colCount) {
		this.Matrice = new float[rowCount][colCount];
	}
	
	public int height() {
		return Matrice.length;
	}
	
	public int width() {
		return Matrice[0].length;
	}
	
	/**
	 * 
	 * @param m
	 * @return la somme des deux matrices ou null si les matrices sont incompatibles
	 */
	public Matrice add(Matrice m) {
		if (height() != m.height() || width() != m.width()) 
			return null;
		
		float[][] out = new float[height()][width()];
		
		for (int i = 0; i < height(); i ++)
			for (int j = 0; j < width(); j++)
				out[i][j] = get(i,j) + m.get(i, j);
				
		return new Matrice(out);
	}
	
	public boolean equals(Matrice m) {
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
		return Matrice[row][col];
	}
	
	public void set(int row, int col, float value) {
		Matrice[row][col] = value;
	}

	public Matrice prod(Matrice m) {
		if (width() != m.height()) {
			return null;
		}
		
		Matrice out = new Matrice(new float[height()][m.width()]);
		
		for (int i = 0; i < out.height(); i++) 
			for (int j = 0; j < out.width(); j++) {
				float value = 0;
				for (int k = 0; k < width(); k++) 
					value += get(i, k) * m.get(k, j);
				out.set(i, j, value);
			}
		
		return out;
	}
	
	public Matrice invert() {
		if (width() != height())
			return null;
		
		Matrice out = initGauss();
		
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
	
	private Matrice initGauss() {
		Matrice out = new Matrice(new float[height()][2 * width()]);
		for (int i = 0; i < height(); i ++) {
			for (int j = 0; j < width(); j++) {
				out.set(i, j, get(i,j));
				if (i == j)
					out.set(i, j + width(), 1);
			}
		}
		return out;
	}
	
	private Matrice concludeGauss(Matrice m) {
		Matrice out = new Matrice(new float[height()][width()]);
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
		if (Matrice == null)
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
