package fr.minestate.models;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import fr.minestate.figure.Triangle;
import fr.minestate.modif.Homothety;
import fr.minestate.modif.Matrix;
import fr.minestate.modif.Rotation;
import fr.minestate.modif.Transformation;
import fr.minestate.modif.Translation;
import fr.minestate.utils.Point;

/**
 * Class Volume pour manipuler un volume constitu� d'un ensemble de triangle
 * @author Louis
 *
 */
public class VolumeModel extends Observable {
	private Set<Triangle> volume;
	
	private Transformation rotationX;
	private Transformation rotationY;
	private Transformation rotationZ;
	private Transformation zoom;
	private Transformation translationX;
	private Transformation translationY;
	
	private String name;
	
	public VolumeModel() {
		volume = new HashSet<Triangle>();
		
		initVolume();
		
	}
	
	public VolumeModel(Collection<Triangle> col) {
		this(col, "volume sans nom");
	}
	
	public VolumeModel(Collection<Triangle> col, String name) {
		this.name = name;
		for(Triangle t : col) {
			volume.add(t);
		}
		initVolume();
	}
	
	
	private void initVolume() {
		rotationX = new Rotation(Rotation.X_AXIS, 0);
		rotationY = new Rotation(Rotation.Y_AXIS, 0);
		rotationZ = new Rotation(Rotation.Z_AXIS, 0);
		zoom = new Homothety(100);
		translationX = new Translation(Translation.X_AXIS, 300);
		translationY = new Translation(Translation.Y_AXIS, 300);		
	}
	
	/**
	 * Ajoute un triangle au volume.
	 * @param t
	 */
	public void addFace(Triangle t) {
		volume.add(t);
	}
	
	/**
	 * Ajoute une collection de triangle au volume (Set, List...)
	 * @param col
	 */
	public void addAllFace(Collection<Triangle> col) {
		for(Triangle t : col) {
			volume.add(t);
		}
	}
	
	public Collection<Triangle> getPolygons() {
		Collection<Triangle> originals = volume;
		List<Triangle> out = new ArrayList<Triangle>();
		Matrix transformation = translationX.add(translationY).prod(rotationX).prod(rotationY)
				.prod(rotationZ).prod(zoom);
		//System.out.println(transformation);
		
		
		for (Triangle t : originals) {
			out.add(t.transform(transformation));
		}
		
		Collections.sort(out);
		
		return out;
	}
	
	@Deprecated
	public void setTranslation(int axis, int norm) {
		if (axis == Translation.X_AXIS) {
			((Translation) translationX).setNorm(norm);
		} else if (axis == Translation.Y_AXIS) {
			((Translation) translationY).setNorm(norm);
		}
	}	
	
	@Deprecated
	public void setRotation(int axis, int angle) {
		if (axis == Rotation.X_AXIS) {
			((Rotation) rotationX).setAngle(angle);
		} else if (axis == Rotation.Y_AXIS) {
			((Rotation) rotationY).setAngle(angle);
		} else {
			((Rotation) rotationZ).setAngle(angle);
		}
	}
	
	@Deprecated
	public void setZoom(float factor) {
		((Homothety) zoom).setFactor(factor);
		setChanged();
		notifyObservers();
	}
	
	public void translate(int axis, int norm) {
		if (axis == Translation.X_AXIS) {
			((Translation) translationX).addNorm(norm);
		} else if (axis == Translation.Y_AXIS) {
			((Translation) translationY).addNorm(norm);
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void rotate(int axis, int angle) {
		if (axis == Rotation.X_AXIS) {
			((Rotation) rotationX).addAngle(angle);
		} else if (axis == Rotation.Y_AXIS) {
			((Rotation) rotationY).addAngle(angle);
		} else {
			((Rotation) rotationZ).addAngle(angle);
		}
		setChanged();
		notifyObservers();
	}
	
	public void zoom(float factor) {
		((Homothety) zoom).addFactor(factor);
		setChanged();
		notifyObservers();
	}
	
	public void optimalZoom (Dimension d) {
		float[] volumeDim = getMaxDimensions();
		float minX = volumeDim[0];
		float maxX = volumeDim[1];
		float minY = volumeDim[2];
		float maxY = volumeDim[3];
		
		/* On calcule les ratios ideaux en largeur et en
		 * longueur en apportant une marge et on applique
		 * le plus petit d'entre eux
		 */
		float xRatio = (d.width - 50) / (maxX - minX);
		float yRatio = (d.height - 80) / (maxY - minY);
		float ratio = Math.min(xRatio, yRatio);
		setZoom(ratio);
		// On calcule les coordonnees apres application du zoom
		minX *= ratio;
		minY *= ratio;
		maxX *= ratio;
		maxY *= ratio;
		/* On calcule les translations a effectuer pour deplacer le centre
		* du volume sur le centre de l'ecran (en apportant une correction
		* pour la barre d'onglets en Y)
		*/
		setTranslation(Translation.X_AXIS,(int) ((d.width + maxX + minX)/2));
		setTranslation(Translation.Y_AXIS,(int) ((d.height - minY - maxY - 40)/2));
	}
	
	
	/**
	 * 
	 * @return an array containing minX, maxX, minY, maxY
	 */
	private float[] getMaxDimensions() {
		/* On remet à "zero" le zoom et les translations 
		* pour effectuer le calcul sur les coord reelles 
		* des points de l'objet 
		*/
		float factor = ((Homothety) zoom).getFactor();
		((Homothety) zoom).setFactor(1);
		int xNorm = ((Translation) translationX).getNorm();
		((Translation) translationX).setNorm(0);
		int yNorm = ((Translation) translationY).getNorm();
		((Translation) translationY).setNorm(0);
		
		Float minX = null, maxX=null,  minY=null, maxY=null;
		
		// On itere sur tous les points pour
		// recuperer les valeurs min et max
		for (Triangle t : getPolygons()) {
			for (Point p : t.getCoords()) {
				if (minX == null) {
					minX = p.getX();
					maxX = p.getX();
					minY = p.getY();
					maxY = p.getY();
				}
				if (p.getX() < minX) 
					minX = p.getX();
				else if (p.getX() > maxX)
				maxX = p.getX();
				if (p.getY() < minY)
					minY = p.getY();	
				else if (p.getY() > maxY)
					maxY = p.getY();
			}
		}
		
		// on restaure les valeurs de zoom
		// et translations (au cas ou la methode serait appellee 
		// dans une situation autre que le zoom opti/centrage
		((Homothety) zoom).setFactor(factor);
		((Translation) translationX).setNorm(xNorm);
		((Translation) translationY).setNorm(yNorm);
		return new float[] {minX, maxX, minY, maxY};
	}
	
	public Point[] getAffineFrame() {
		Matrix m = rotationZ.prod(rotationY).prod(rotationX).prod(new Homothety(20));
		Point[] out = new Point[3];
		
		for (int i = 0; i < 3; i++) {
			out[i] = new Point(m.get(0, i), m.get(1, i), m.get(2, i));
		}
		
		return out;
	}
	
	private Matrix getBase() {
		Matrix rotation = rotationZ.prod(rotationY).prod(rotationX);
		Matrix out = new Matrix(3, 3);
		
		for (int i = 0; i < out.height(); i ++) {
			for (int j = 0; j < out.width(); j++) {
				out.set(i, j, rotation.get(i,j));
			}
		}
		
		return out;
	}
	
	public void rotate(Matrix vector) {
		//TODO : verif si vecteur et exception ?	
		/*Matrix id = new Matrix(new float[][] {
				{1, 0, 0}, 
				{0, 1, 0},
				{0, 0, 1}});*/
		
		Matrix vect = getBase().invert().prod(vector);
		
		rotate(Rotation.Y_AXIS, (int) vect.get(0, 0));
		rotate(Rotation.X_AXIS, (int) vect.get(1, 0));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
