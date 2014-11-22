package fr.minestate.control;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.minestate.models.VolumeSetModel;
import fr.minestate.view.VolumeSetView;

public class VolumeSetController {
	
	public static ChangeListener getTabChangeController(final VolumeSetModel m) {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				m.setCurrentVolume(((VolumeSetView)e.getSource()).getSelectedIndex());
			}
		};
	}
}
