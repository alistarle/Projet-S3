package fr.minestate.models;

import java.util.ArrayList;
import java.util.Observable;

import fr.minestate.control.VolumeController;

public class VolumeSetModel extends Observable {

	private ArrayList<VolumeModel> volumes;
	int currentVolumeId = -1;
	int removedVolumeId = -1;


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

	public VolumeModel getLastVolume() {
		if(volumes.size() > 0)
			return volumes.get(volumes.size() - 1);
		return null;
	}

	public int getVolumeCount() {
		return volumes.size();
	}

	public void translate(int axis, int norm) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).translate(axis, norm);
	}

	public void rotate(int axis, int angle) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).rotate(axis, angle);
	}

	public void zoom(float factor) {
		if(currentVolumeId >= 0)
			volumes.get(currentVolumeId).zoom(factor);
	}

	public void setCurrentVolume(int current) {
		currentVolumeId = current;
		//VolumeController.setModel(volumes.get(current));
		setChanged();
		notifyObservers();
	}

	public void addVolume(VolumeModel volume) {
		this.volumes.add(volume);
		setCurrentVolume(volumes.size() - 1);
		VolumeController.optimalZoom(volume);
		notifyObservers();
	}


	public void deleteVolume(int index) {
		volumes.remove(index);
		removedVolumeId = index;
		setChanged();
		notifyObservers();
	}

	public int getCurrentVolumeIdx() {
		return currentVolumeId;
	}

	public int getRemovedId() {
		return removedVolumeId;
	}
}
