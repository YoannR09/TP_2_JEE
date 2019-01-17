package fr.yoannroche.dao;

import java.util.List;

import fr.yoannroche.beans.Video;

public interface DAOVideo {
	
	void ajouter( Video video );
	
	List<Video> lire();
	
	void recupId( Video video);
	
	void suppr(Video video);

}
