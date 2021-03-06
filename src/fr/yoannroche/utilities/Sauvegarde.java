package fr.yoannroche.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import fr.yoannroche.beans.SousTitres;
import fr.yoannroche.beans.Video;
import fr.yoannroche.dao.DAOSousTitres;
import fr.yoannroche.dao.DAOVideo;

public class Sauvegarde {

	int type = 1 ;
	String text ="";
	int boucle = 0;
	int ligneAjout = 0;
	String trade;
	boolean ajoutDeux = false;
	public static final int TAILLE_TAMPON = 10240;
	public static final String CHEMIN_FICHIERS = "/Users/El-ra/traduction/"; // url création du fichier de la traduction


	/**
	 * Classe qui permet de sauvegarder un nouveau fichier srt ou alors de les mettres à jours.
	 * Elle créée aussi les informations dans la base de données.
	 * @param request
	 * @param videoDao
	 * @param stDao
	 * @param nomVideo
	 * @param file
	 * @param lastBtn
	 * @param context
	 */
	public Sauvegarde(HttpServletRequest request, DAOVideo videoDao, DAOSousTitres stDao, String nomVideo, String urlVideo, String lastBtn, ServletContext context) {
		BufferedReader br;
		int type = 1;
		int ligne = 0 ;
		try {


			/**
			 * Création des fichiers ou alors remplacement si ils existent déjà.
			 * Le fichier prendra le nom de la video.
			 */
			
			 PrintWriter writerT = new PrintWriter(CHEMIN_FICHIERS+nomVideo+".srt","UTF-8");


			String line;
			SousTitres st = new SousTitres();
			Video video = new Video();
			video.setNom(nomVideo);
			video.setUrl(urlVideo);
			videoDao.ajouter(video); // Création de la video dans la base données.
			videoDao.recupId(video); // Les sous titres on besoin de la clé étrangère video_id.
			br = new BufferedReader(new FileReader(urlVideo));
			int i = 0; 


			/**
			 * Boucle pour lire les lignes.
			 */
			while ( (line=br.readLine())!=null ) {


				switch (type) {
				case 1 : //Première ligne pour récupérer le numero.
					st.setNumeroLigne(Integer.parseInt(line));
					writerT.println(line);
					type++; 
					break;
				case 2 : //Deuxième ligne pour récupérer le temps.
					st.setTemps(line);
					writerT.println(line);
					type++;
					break; 
				case 3: //Si le texte est vide on repart au début.
					if(line.isEmpty()) {
						if(!ajoutDeux) {
							st.setLigne2("");
							st.setTraduction2("");
						}
						st.setVideoId(video.getId());
						stDao.ajouter(st); // Enregistrement de la ligne de sous-titres dans la base de données.
						writerT.println("");
						ligne=0;
						type=1;
					}
					else {
						if(lastBtn.equals("ajouter")) {
							trade = request.getParameter("lineTraductionAjouter"+i);
						}
						else if(lastBtn.equals("lire")){
							trade = request.getParameter("lineTraductionLire"+i);
						}
						i++;
						if(ligne==0) { // On ajoute la première ligne.
							st.setLigne1(line);
							st.setTraduction1(trade);
							writerT.println(trade);
							ligne++;
							ajoutDeux = false;
						}
						else {	// On ajoute la deuxième ligne.
							st.setLigne2(line);
							st.setTraduction2(trade);
							writerT.println(trade);
							ajoutDeux = true;
						}
					}
				}
				
			}
			
			 writerT.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}


