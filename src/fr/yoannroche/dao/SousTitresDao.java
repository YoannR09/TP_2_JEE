package fr.yoannroche.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.yoannroche.beans.SousTitres;

public class SousTitresDao implements DAOSousTitres{
	
	private DaoFactory daoFactory;

	public SousTitresDao(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(SousTitres sousTitres) {
		
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;


		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO sous_titres(numero_ligne, temps, ligne1,ligne2,video_id,traduction1,traduction2) VALUES(?, ?,?,?,?,?,?);");
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

	

}
