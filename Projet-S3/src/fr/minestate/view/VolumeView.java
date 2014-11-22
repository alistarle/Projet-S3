package fr.minestate.view; 

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
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
	
	public VolumeView() {
		volumeModel = new VolumeModel();
	}
	
	public VolumeView(VolumeModel v) {
		add(new JLabel(v.getName()));
		this.volumeModel = v;
		this.volumeModel.addObserver(this);
	}
	
	public void setVolumeModel(VolumeModel volumeModel) {
		volumeModel.deleteObservers();
		this.volumeModel = volumeModel;
		this.volumeModel.addObserver(this);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Collection<Triangle> triangles = volumeModel.getPolygons();
		for (Triangle t : triangles) 
			drawTriangle(t, g);
		//drawAffineFrame(g, volumeModel.getAffineFrame());
	}
	
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
	
	/*private Color getRandomColor() {
		Random r = new Random();		
		return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}*/

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	
	public void removeMouseWheelListener() {
		for(MouseWheelListener l : this.getMouseWheelListeners()) {
			this.removeMouseWheelListener(l);
		}
	}
	
	public void removeMouseMotionListeners() {
		for(MouseMotionListener l : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(l);
		}
	}
	
}
