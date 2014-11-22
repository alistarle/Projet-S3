package fr.minestate.models;

import java.io.BufferedReader;
import java.io.StringReader;

public class ModelEntry {
	private int idModel;
	private String name;
	private String author;
	private String model;
	private String[] keyWords;
	
	public ModelEntry(int idModel, String name, String author, String model, String[] keyWords) {
		this.idModel = idModel;
		this.name = name;
		this.author = author;
		this.model = model;
		this.keyWords = keyWords;
	}
	
	public BufferedReader getModelReader() {
		return new BufferedReader(new StringReader(model));
	}
	
	public int getId() {
		return idModel;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String toString() {
		return name;
	}

	public String[] getKeyWords1() {
		return keyWords;
	}
	
	public String getKeyWords() {
		String res = "";
		for(int i = 0 ; i < keyWords.length ; i++) {
			if(i == 0) 
				res += keyWords[i];
			else
				res += " " + keyWords[i];
		}
		return res;
	}
}
