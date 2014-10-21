
public class Sommet {

	Integer x;
	Integer y;
	Integer z;
	
	public Sommet (Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "Sommet [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getZ() {
		return z;
	}

	public void setZ(Integer z) {
		this.z = z;
	}
	

}
