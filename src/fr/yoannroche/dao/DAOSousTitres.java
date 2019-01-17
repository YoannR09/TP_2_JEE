package fr.yoannroche.dao;

import java.util.ArrayList;

import fr.yoannroche.beans.SousTitres;
import fr.yoannroche.beans.Video;

public interface DAOSousTitres {
	
	void ajouter( SousTitres sousTitres );
	
	void lire( Video video, ArrayList<String> originalSubtitles, ArrayList<String> translatedSubtitles, ArrayList<String> numeroLigne, ArrayList<String> tempsLigne);

}
