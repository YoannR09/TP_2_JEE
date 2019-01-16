package fr.yoannroche.dao;

public abstract class AbstractDaoFactory {
	
	 public abstract DAOVideo getVideoDao();
	 
	 public abstract DAOSousTitres getSousTitres();

}
