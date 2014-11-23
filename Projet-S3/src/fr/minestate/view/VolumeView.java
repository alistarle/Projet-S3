package fr.minestate.view; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.minestate.figure.Triangle;
import fr.minestate.models.VolumeModel;
import fr.minestate.utils.Point;

/**
 * Panel permettant de dessiner un volume
 * @author Simon
 *
 */
public class VolumeView extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	
	
	private VolumeModel volumeModel;
	
	/**
	 * Permet d'initialser un VolumeView sans parametre
	 */
	public VolumeView() {
		volumeModel = new VolumeModel();
	}
	
	/**
	 * Permet d'initialiser un VolumeView en precisant un VolumeModel
	 * @param v le VolumeModel que l'on veut dessiner
	 */
	public VolumeView(VolumeModel v) {
		add(new JLabel(v.getName()));
		this.volumeModel = v;
		this.volumeModel.addObserver(this);
	}
	
	/**
	 * Permet de changer le volumeModel
	 * @param le nouveau volumeModel
	 */
	public void setVolumeModel(VolumeModel volumeModel) {
		volumeModel.deleteObservers();
		this.volumeModel = volumeModel;
		this.volumeModel.addObserver(this);
		repaint();
	}
	
	/**
	 * Cette methode permet d'afficher un objet (tous les triangles)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Collection<Triangle> triangles = volumeModel.getPolygons();
		for (Triangle t : triangles) 
			drawTriangle(t, g);
	}
	
	/**
	 * Cette methode permet d'afficher un triangle
	 * @param t le triangle a afficher
	 * @param g permet d'afficher
	 */
	private void drawTriangle(Triangle t, Graphics g) {
		Point[] points = t.getCoords();
		Polygon p = new Polygon();
		for (Point m : points) 
			p.addPoint((int) (m.getX()),(int)m.getY());
		g.setColor(Color.orange);
		g.fillPolygon(p);
		g.setColor(Color.blue);
		g.drawPolygon(p);
	}
	
	/**
	 * Permet de mettre a jour
	 */
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	/**
	 * Permet de supprimer les listeners associes a la roulette de la souris
	 */
	public void removeMouseWheelListener() {
		for(MouseWheelListener l : this.getMouseWheelListeners()) {
			this.removeMouseWheelListener(l);
		}
	}
	
	/**
	 * Permet de supprimer les listeners associes aux mouvements de la souris
	 */
	public void removeMouseMotionListeners() {
		for(MouseMotionListener l : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(l);
		}
	}
	
}
