package fr.minestate.control;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import fr.minestate.models.VolumeModel;
import fr.minestate.modif.Matrix;
import fr.minestate.modif.Rotation;
import fr.minestate.modif.Translation;

public class VolumeController {

	public static int previousMouseX;
	public static int previousMouseY;

	private static JComponent panel;
	@SuppressWarnings("unused")
	private static VolumeModel model;

	public static MouseMotionListener getMouseController(final VolumeModel m) {
		return new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					m.translate(Translation.X_AXIS, e.getX() - previousMouseX);
					m.translate(Translation.Y_AXIS, e.getY() - previousMouseY);
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					m.rotate(Rotation.Y_AXIS, e.getX() - previousMouseX);
					m.rotate(Rotation.X_AXIS, previousMouseY - e.getY());
				}

				if (SwingUtilities.isMiddleMouseButton(e)) {
					m.rotate(new Matrix(new float[][] {
							{ e.getX() - previousMouseX },
							{ previousMouseY - e.getY() }, { 0 } }));
				}

				previousMouseX = e.getX();
				previousMouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				previousMouseX = e.getX();
				previousMouseY = e.getY();
			}
		};
	}

	public static MouseWheelListener getMouseWheelController(final VolumeModel m) {
		return new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				m.zoom(e.getWheelRotation() * -5);
			}
		};
	}

	public static void setPanel(JComponent panel) {
		VolumeController.panel = panel;
	}

	public static void optimalZoom(VolumeModel model) {
		optimalZoom(model, panel.getSize());
	}

	public static void optimalZoom(VolumeModel model, Dimension d) {
		model.optimalZoom(d);
	}
}
