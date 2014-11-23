package fr.minestate.control;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.minestate.models.VolumeSetModel;
import fr.minestate.view.VolumeSetView;

/**
 * Permet de controler un ensemble de Volume
 * @author scta
 *
 */
public class VolumeSetController {
	
	/**
	 * Permet de detecter le changement d'etat d'un VolumeSetModel
	 * @param m le VolumeSet
	 * @return changeListener
	 */
	public static ChangeListener getTabChangeController(final VolumeSetModel m) {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				m.setCurrentVolume(((VolumeSetView)e.getSource()).getSelectedIndex());
			}
		};
	}
}
