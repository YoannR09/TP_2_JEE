package fr.yoannroche.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory extends AbstractDaoFactory {
	private static Connection conn = Singleton.getInstance();

	DaoFactory() {
	
	}

	public static DaoFactory getInstance()  {
		DaoFactory instance = new DaoFactory();
		return instance;
	}

	// Récupération du Dao
	public DAOVideo getVideoDao() {
		return new VideoDao(conn);
	}
	public DAOSousTitres getSousTitres() {
		return new SousTitresDao(conn);
	}

}
