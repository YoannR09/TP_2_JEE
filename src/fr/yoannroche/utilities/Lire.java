package fr.yoannroche.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import fr.yoannroche.dao.DAOSousTitres;

public class Lire {

	private ArrayList<String> originalSubtitles = null;
	private ArrayList<String> translatedSubtitles = null;
	private ArrayList<String> numeroLigne = null;
	private ArrayList<String> tempsLigne = null;
	int ligne = 0;
	int i = 0;
	int second = 0;


	/**
	 * Classe qui permet de lire les fichiers déjà enregistrer dans la base de données et qui sont déjà placés dans les fichiers
	 * On récupère le nom de la video dans le select et on va chercher la video en fonction du nom.
	 * @param request
	 * @param file
	 * @param stDao
	 */
	public Lire(HttpServletRequest request, String file, DAOSousTitres stDao) {
		originalSubtitles = new ArrayList<String>();
		translatedSubtitles = new ArrayList<String>();
		numeroLigne = new ArrayList<String>();
		tempsLigne = new ArrayList<String>();

		BufferedReader br;
		try {
			int type = 1 ;
			br = new BufferedReader(new FileReader(file));
			String line;

			/**
			 * Boucle pour lire les lignes.
			 */
			while ( (line=br.readLine())!=null ) {
				switch (type) {
				case 1 : //Première ligne pour récupérer le numero.
					second= 0;
					numeroLigne.add(line);
					type++; 
					break;
				case 2 : //Deuxième ligne pour récupérer le temps.
					tempsLigne.add(line);
					type++;
					break; 
				case 3: //Si le texte est vide on repart au début.
					if (line.isEmpty()) {
						type = 1;
					} else {
						if(second == 0) {
							second++;
						}
						else {
							tempsLigne.add(""); // Pas de deuxième ligne on met un texte vide pour se lié au temps précédent.
							numeroLigne.add(""); // Pas de deuxième ligne on met un texte vide pour se lié au numéro de ligne précédent
						}
						originalSubtitles.add(line);	
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			int type = 1 ;
			br = new BufferedReader(new FileReader(file));
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
						translatedSubtitles.add(line);	
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
	public ArrayList<String> getNumero() {
		return numeroLigne;
	}
	public ArrayList<String> getTemps() {
		return tempsLigne;
	}

}
