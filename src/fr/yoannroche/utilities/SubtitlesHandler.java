package fr.yoannroche.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.yoannroche.beans.Video;

public class SubtitlesHandler {

	private ArrayList<String> originalSubtitles = null;
	private ArrayList<String> translatedSubtitles = null;
	int type = 1 ;
	int ligne = 0;
	
	

	public SubtitlesHandler(String fileName) {
		originalSubtitles = new ArrayList<String>();
		translatedSubtitles = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;

			/**
			 * Boucle pour lire les lignes.
			 */
			while ( (line=br.readLine())!=null ) {
				switch (type) {
				case 1 : //Première ligne pour récupérer le numero.
					type++; 
					break;
				case 2 : //Deuxième ligne pour récupérer le temps.
					type++;
					break; 
				case 3: //Si le texte est vide on repart au début.
					if (line.isEmpty()) {
						type = 1;
					} else {
						originalSubtitles.add(line);	
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getSubtitles() {
		return originalSubtitles;
	}
	public ArrayList<String> getTranslated() {
		return translatedSubtitles;
	}
	


}
