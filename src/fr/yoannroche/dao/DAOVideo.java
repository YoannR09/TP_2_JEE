package fr.yoannroche.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.yoannroche.beans.Video;

public interface DAOVideo {
	
	void ajouter( Video video );
	
	List<Video> lire();
	
	void recupId( Video video);
	
	void suppr(Video video);
	
	// void update(Video video, HttpServletRequest request);

}
