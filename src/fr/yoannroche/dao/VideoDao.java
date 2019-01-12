package fr.yoannroche.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.yoannroche.beans.Video;

public class VideoDao implements DAOVideo{

	private DaoFactory daoFactory;

	public VideoDao(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public List <Video> lire() {

		List<Video> videos = new ArrayList<Video>();
		Connection connexion = null;
		ResultSet resultat = null;
		Statement statement = null;

		 try {
			 	connexion = daoFactory.getConnection();
	            statement = connexion.createStatement();

	            // Exécution de la requête
	            resultat = statement.executeQuery("SELECT id,nom, url FROM video;");

	            // Récupération des données
	            while (resultat.next()) {
	            	int id	= Integer.parseInt(resultat.getString("id"));
	                String nom = resultat.getString("nom");
	                String url = resultat.getString("url");
	                
	                Video video = new Video();
	                video.setId(id);
	                video.setNom(nom);
	                video.setUrl(url);
	                
	                videos.add(video);
	            }
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        } finally {
	            // Fermeture de la connexion
	            try {
	                if (resultat != null)
	                    resultat.close();
	                if (statement != null)
	                    statement.close();
	                if (connexion != null)
	                    connexion.close();
	            } catch (SQLException ignore) {
	            }
	        }
		return videos;
	}


	public void ajouter(Video video) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;


		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO video(nom, url) VALUES(?, ?);");
			preparedStatement.setString(1, video.getNom());
			preparedStatement.setString(2, video.getUrl());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void recupId(Video video) {
		
		Connection connect = null;
		ResultSet resultat = null;
		Statement statement = null;
		
		 try {
			 	connect = daoFactory.getConnection();
	            statement = connect.createStatement();
	            
	            resultat = statement.executeQuery("SELECT id FROM video WHERE nom = '"+video.getNom()+"'");
	            
	            
	            while (resultat.next()) {
	                String idVideo = resultat.getString("id");
	                
	                video.setId(Integer.parseInt(idVideo));
	            }
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
	}
}
