package fr.minestate.models;

import java.util.ArrayList;
import java.util.Observable;

import fr.minestate.control.VolumeController;

/**
 * Permet de definir un SET de VolumeModel
 * @author scta
 *
 */
public class VolumeSetModel extends Observable {

	private ArrayList<VolumeModel> volumes;
	int currentVolumeId = -1;
	int removedVolumeId = -1;

	
	/**
	 * Permet de definir un VolumeSetModel
	 */
	public VolumeSetModel() {
		volumes = new ArrayList<VolumeModel>();
	}

	/*private void testInit() {
		Point a = new Point(-1, -1, 0);
		Point b = new Point(1, -1, 0);
		Point c = new Point(0, 1, 0);
		Point d = new Point(0, 0, 1);

		volume.addFace(new Triangle(a, b, c));
		volume.addFace(new Triangle(a, b, d));
		volume.addFace(new Triangle(b, c, d));
		volume.addFace(new Triangle(a, c, d));
	}*/

	/**
	 * Permet de renvoyer le dernier VolumeModel
	 * @return le dernier VolumeModel de la liste volumes
	 */
	public VolumeModel getLastVolume() {
		if(volumes.size() > 0)
			return volumes.get(volumes.size() - 1);
		return null;
	}

	/**
	 * Renvoie le nombre de VolumeModel de la liste volumes
	 * @return la taille de la liste volumes
	 */
	public int getVolumeCount() {
		return volumes.size();
	}

	/**
	 * Permet de faire une translation selon un axe
	 * @param axis l'axe selon lequel on veut translater
	 * @param norm
	 */
	public void translate(int axis, int norm) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).translate(axis, norm);
	}

	/**
	 * Permet d'effectuer une rotation selon un angle et un angle
	 * @param axis l'axe de la rotation
	 * @param angle l'angle de la rotation
	 */
	public void rotate(int axis, int angle) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).rotate(axis, angle);
	}

	/**
	 * Permet de zoom sur un objet
	 * @param factor le niveau de zoom souhaite
	 */
	public void zoom(float factor) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).zoom(factor);
	}

	/**
	 * Permet de changer le volume courant
	 * @param current le nouveau volume
	 */
	public void setCurrentVolume(int current) {
		currentVolumeId = current;
		//VolumeController.setModel(volumes.get(current));
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'ajouter un volumeModele
	 * @param volume le nouveau volumeModel a ajoute
	 */
	public void addVolume(VolumeModel volume) {
		this.volumes.add(volume);
		setCurrentVolume(volumes.size() - 1);
		VolumeController.optimalZoom(volume);
		notifyObservers();
	}


	/**
	 * Permet de supprimer une volumeModele de la liste volumes
	 * @param index l'index dans la liste volumes du volumeModele a supprimer
	 */
	public void deleteVolume(int index) {
		volumes.remove(index);
		removedVolumeId = index;
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet de connaitre l'id du volume courant
	 * @return l'id du volume
	 */
	public int getCurrentVolumeIdx() {
		return currentVolumeId;
	}

	/**
	 * Renvoie le removedVolumeId
	 * @return le removedVolumeId
	 */
	public int getRemovedId() {
		return removedVolumeId;
	}
}
