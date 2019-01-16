package fr.yoannroche.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton
{   
	 private String url = "jdbc:postgresql://localhost:5432/trade";
	  //Nom du user
	  private String user = "postgres";
	  //Mot de passe de l'utilisateur
	  private String passwd = "Yocorps17";
	  //Objet Connection
	  private static Connection connect;
	   
	  //Constructeur privé
	  private Singleton(){
	    try {
	   
	    	Class.forName("org.postgresql.Driver");
	      connect = DriverManager.getConnection(url, user, passwd);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	   
	  //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
	   public static Connection getInstance(){
	    if(connect == null){
	      new Singleton();
	    }
	    return connect;   
	  }   
}