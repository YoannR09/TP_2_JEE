package fr.yoannroche.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
	private String url;
	private String username;
	private String password;
	private static DaoFactory instance;

	DaoFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static DaoFactory getInstance() {

		if (instance == null) {
			synchronized (DaoFactory.class) {
				try {
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException e) {

				}

				instance = new DaoFactory(
						"jdbc:postgresql://localhost:5432/trade", "postgres", "Yocorps17");
			}
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	// Récupération du Dao
	public DAOVideo getVideoDao() {
		return new VideoDao(this);
	}
	public DAOSousTitres getSousTitres() {
		return new SousTitresDao(this);
	}
}
