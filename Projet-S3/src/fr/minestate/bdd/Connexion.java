package fr.minestate.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Connexion {
	private String DBPath = "src/fr/minestate/bdd/Database.db";
	private Connection connexion = null;
	private PreparedStatement pstmt;

	public Connexion() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.connexion = this.getConnexion();
	}

	public Connection getConnexion() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public void closeConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUpdateStatement(String query) {
		int i = 0;
		this.connexion = this.getConnexion();
		try {
			this.pstmt = this.connexion.prepareStatement(query);
			i = this.pstmt.executeUpdate();
			this.pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public ResultSet getExecuteStatement(String query) {
		this.connexion = this.getConnexion();
		ResultSet r = null;
		try {
			this.pstmt = this.connexion.prepareStatement(query);
			r = this.pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	public int ajouterObjet(String name, String link) {
		return this.getUpdateStatement("INSERT INTO objets(nom, lien) VALUES('"+ name + "','" + link + "');");
	}
	
	public void afficherListeObjet(){
		ResultSet rs = this.getExecuteStatement("SELECT nom, lien FROM objets");
		try {
			while(rs.next()){
				System.out.println("Nom: "+rs.getString("nom")+"\n Lien: "+rs.getString("lien"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getListObjet(){
		Map<String, String> mapObjet = new HashMap<String, String>();
		ResultSet rs = this.getExecuteStatement("SELECT nom, lien FROM objets");
		try {
			while(rs.next()){
				mapObjet.put(rs.getString("nom"), rs.getString("lien"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapObjet;
	}
	
	public String getCheminObjet(String objet){
		ResultSet rs = this.getExecuteStatement("SELECT lien FROM objets WHERE nom='"+objet+"';");
		String path = "";
		try {
			if(rs.next()){
				path = rs.getString("lien");
			}
			else{
				System.out.println("objet introuvable");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public void supprimerObjet(String objet){
		int rs = this.getUpdateStatement("DELETE FROM objets WHERE nom='"+objet+"';");
		System.out.println("Result: "+rs);
		if(rs == 0){
			System.out.println("Objet non existant");
		}
		else{
			System.out.println("Suppression ok");
		}
	}

}
