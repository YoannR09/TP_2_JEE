package fr.yoannroche.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import fr.yoannroche.beans.Video;
import fr.yoannroche.dao.DAOSousTitres;
import fr.yoannroche.dao.DAOVideo;

public class Lire {

	private ArrayList<String> originalSubtitles = null;
	private ArrayList<String> translatedSubtitles = null;
	private ArrayList<String> numeroLigne = null;
	private ArrayList<String> tempsLigne = null;
	private String urlVideo;
	int ligne = 0;
	int i = 0;
	int second = 0;


	/**
	 * Classe qui permet de lire les fichiers déjà enregistrer dans la base de données et qui sont déjà placés dans les fichiers
	 * On récupère le nom de la video dans le select et on va chercher la video en fonction du nom.
	 * @param request
	 * @param file
	 * @param stDao
	 * @param fileTrade 
	 * @param videoDao 
	 */
	public Lire(HttpServletRequest request, DAOSousTitres stDao, DAOVideo videoDao) {
		originalSubtitles = new ArrayList<String>();
		translatedSubtitles = new ArrayList<String>();
		numeroLigne = new ArrayList<String>();
		tempsLigne = new ArrayList<String>();

		Video video = new Video();
		video.setNom(request.getParameter("select"));
		videoDao.recupId(video);
		urlVideo = video.getUrl();
		stDao.lire(video,originalSubtitles,translatedSubtitles,numeroLigne,tempsLigne);
		request.setAttribute("fichier", video.getUrl());
	}


	public String getUrlVideo() {
		return urlVideo;
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
