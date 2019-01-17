package fr.yoannroche.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.yoannroche.beans.SousTitres;
import fr.yoannroche.beans.Video;

public class SousTitresDao implements DAOSousTitres{
	
	private Connection conn;
	private Video video;

	public SousTitresDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void ajouter(SousTitres sousTitres) {
		
		
		PreparedStatement preparedStatement = null;


		try {
			preparedStatement = conn.prepareStatement("INSERT INTO sous_titres(numero_ligne, temps, ligne1,ligne2,video_id,traduction1,traduction2) VALUES(?, ?,?,?,?,?,?);");
			preparedStatement.setLong(1, sousTitres.getNumeroLigne());
			preparedStatement.setString(2, sousTitres.getTemps());
			preparedStatement.setString(3, sousTitres.getLigne1());
			preparedStatement.setString(4, sousTitres.getLigne2());
			preparedStatement.setLong(5, sousTitres.getVideoId());
			preparedStatement.setString(6, sousTitres.getTraduction1());
			preparedStatement.setString(7, sousTitres.getTraduction2());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void lire(Video video, ArrayList<String> originalSubtitles, ArrayList<String> translatedSubtitles,
			ArrayList<String> numeroLigne, ArrayList<String> tempsLigne) {
		
		ResultSet resultat = null;
		Statement statement = null;
		try {
            statement = conn.createStatement();

            // Exécution de la requête
            resultat = statement.executeQuery("SELECT numero_ligne,temps,ligne1,ligne2,traduction1,traduction2 FROM sous_titres WHERE video_id = '"+video.getId()+"'");
            
            // Récupération des données
            while (resultat.next()) {
            	String numero = resultat.getString("numero_ligne");
                numeroLigne.add(numero);
                String temps = resultat.getString("temps");
                tempsLigne.add(temps);
                String ligne1 = resultat.getString("ligne1");
                originalSubtitles.add(ligne1);
                String ligne2 = resultat.getString("ligne2");
                if(!ligne2.isEmpty()) {
                	numeroLigne.add("");
                	tempsLigne.add("");
                	originalSubtitles.add(ligne2);
                }
                String traduction1 = resultat.getString("traduction1");
                translatedSubtitles.add(traduction1);
                
                if(!ligne2.isEmpty()) {
                	 String traduction2 = resultat.getString("traduction2");
                     translatedSubtitles.add(traduction2);
                }
               
                
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
		
	}
	

}
