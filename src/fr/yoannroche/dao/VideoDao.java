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

	private Connection conn;


	public VideoDao(Connection conn) {
		this.conn = conn;
	}

	public List <Video> lire() {

		List<Video> videos = new ArrayList<Video>();

		ResultSet resultat = null;
		Statement statement = null;

		try {
			statement = conn.createStatement();

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
			} catch (SQLException ignore) {
			}
		}
		return videos;
	}


	public void ajouter(Video video) {

		PreparedStatement preparedStatement = null;


		try {
			preparedStatement = conn.prepareStatement("INSERT INTO video(nom, url) VALUES(?, ?);");
			verifNom(video);
			preparedStatement.setString(1, video.getNom());
			preparedStatement.setString(2, video.getUrl());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void recupId(Video video) {

		ResultSet resultat = null;
		Statement statement = null;

		try {
			statement = conn.createStatement();

			resultat = statement.executeQuery("SELECT id,url FROM video WHERE nom = '"+video.getNom()+"'");


			while (resultat.next()) {
				String idVideo = resultat.getString("id");
				String urlVideo = resultat.getString("url");

				video.setId(Integer.parseInt(idVideo));
				video.setUrl(urlVideo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void verifNom(Video video) {

		ResultSet resultat = null;
		Statement statement = null;

		try {

			statement = conn.createStatement();


			resultat = statement.executeQuery("SELECT nom FROM video;");

			while (resultat.next()) {
				String nomVideo = resultat.getString("nom");
				if(nomVideo.equals(video.getNom())) {
					suppr(video);
				}
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public void suppr(Video video) {
		try	{
			conn.setAutoCommit(false);
			this.conn
			.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE
					).executeUpdate(
							"DELETE FROM sous_titres WHERE video_id = " + video.getId()
							);
			this.conn
			.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE
					).executeUpdate(
							"DELETE FROM video WHERE id = " + video.getId()
							);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
