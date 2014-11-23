package fr.minestate.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;

import fr.minestate.control.VolumeController;
import fr.minestate.control.VolumeSetController;
import fr.minestate.models.VolumeModel;
import fr.minestate.models.VolumeSetModel;

/**
 * Permet d'afficher un VolumeSet
 * JTabbedPane = permet d'avoir plusieurs onglets
 */
public class VolumeSetView extends JTabbedPane implements Observer {
	private static final long serialVersionUID = 1L;

	private VolumeSetModel volumeSetModel;
	public VolumeSetView(VolumeSetModel volumeSetModel) {
		this.volumeSetModel = volumeSetModel;
		volumeSetModel.addObserver(this);
		addChangeListener(VolumeSetController.getTabChangeController(volumeSetModel));
	}

	
	/**
	 * Permet d'afficher un objet dans un nouvel onglet
	 * @param v l'objet a afficher
	 */
	private void addTab(VolumeModel v) {
		VolumeView vView = new VolumeView(v);
		vView.addMouseMotionListener(VolumeController.getMouseController(v));
		vView.addMouseWheelListener(VolumeController.getMouseWheelController(v));
		addTab("tab", vView);
		int tabIdx = getTabCount() - 1;
		setSelectedIndex(tabIdx);
	}

	/**
	 * Permet de mettre a jour
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(volumeSetModel.getVolumeCount() > getTabCount()) {
			// On a ajoute un volume
			addTab(volumeSetModel.getLastVolume());
		} else if(volumeSetModel.getVolumeCount() < getTabCount()) {
			// On a supprime un volume
			remove(volumeSetModel.getRemovedId());
			setSelectedIndex(volumeSetModel.getCurrentVolumeIdx());
		}
	}
	
	/**
	 * Retourne le VolumeSetModel
	 * @return VolumeSetModel
	 */
	public VolumeSetModel getVolumeSetModel() {
		return volumeSetModel;
	}
}




