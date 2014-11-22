package fr.minestate.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.minestate.models.VolumeSetModel;

public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private VolumeSetModel volumeSetModel;
	private MenuBarView mv;
	private JPanel pan = new JPanel();
	
	public Fenetre() {
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setTitle("Modélisation");
		this.setPreferredSize(new Dimension(1024, 728));
		this.volumeSetModel = new VolumeSetModel();
		this.mv = new MenuBarView(volumeSetModel, this);
		mv.setBounds(0, 0, 1024, 30);
		this.mainPanel = new JPanel();
		this.setBounds(0, 30, 1024, 700);
		this.pan.setLayout(null);
		this.add(mainPanel);
		this.add(mv);
		this.add(pan);
		this.pack();
	}

	public JPanel getPan() {
		return this.pan;
	}
	
	public void setPan(JPanel panel){
		this.pan = panel;
	}
}
