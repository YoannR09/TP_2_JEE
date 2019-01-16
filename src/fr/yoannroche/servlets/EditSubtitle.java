package fr.yoannroche.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.yoannroche.dao.AbstractDaoFactory;
import fr.yoannroche.dao.DAOSousTitres;
import fr.yoannroche.dao.DAOVideo;
import fr.yoannroche.dao.DaoFactory;
import fr.yoannroche.utilities.Ajout;
import fr.yoannroche.utilities.Lire;
import fr.yoannroche.utilities.Sauvegarde;



@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOVideo videoDao;
	private DAOSousTitres stDao;
	private String nomVideo;
	private String urlVideo;
	private String lastBtn;
	

	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.videoDao = daoFactory.getVideoDao();
		this.stDao = daoFactory.getSousTitres();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("videos", videoDao.lire());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String btn = request.getParameter("submit");
		request.setAttribute("submit", btn);
		ServletContext context = getServletContext();
		
		
		/**
		 * Si on clique sur le bouton ajouter.
		 * La classe Ajout récupère le fichier srt que l'utilisateur à choisit.
		 * Le texte original sera affiché et des champs vides pour faire la traduction.
		 */
		
		if(btn.equals("ajouter")) {
			lastBtn = btn;
			urlVideo = request.getParameter("fichier");
			Ajout ajout = new Ajout(urlVideo);
			request.setAttribute("subtitles", ajout.getSubtitles());
			nomVideo = request.getParameter("nom");
			urlVideo = request.getParameter("fichier");
			request.setAttribute("temps", ajout.getTemps());
			request.setAttribute("numero", ajout.getNumero());
		}
		
		/**
		 * Si on clique sur le bouton sauvegarde.
		 * La classe Sauvegarde va enregistrer toutes les lignes dans la bases de données.
		 * La classe créée un fichier srt pour la traduction et pour enregistrer l'original ou la remplacer si celle-ci existe.
		 */
		
		else if(btn.equals("sauvegarde")) {
			new Sauvegarde(request,videoDao,stDao,nomVideo,urlVideo,lastBtn,context);
		}
		
		/**
		 * Si on clique sur le bouton lire.
		 * La classe lire va récupèrer les fichiers srt dans les fichiers traduction et original.
		 */
		
		else if(btn.equals("lire")) {
			lastBtn = btn;
			String nomFichier = request.getParameter("select");
			String file = "/WEB-INF/original/"+nomFichier+".srt";
			String fileTrade = "/WEB-INF/traduction/"+nomFichier+".srt";
			Lire lire = new Lire(request,context.getRealPath(file),stDao,context.getRealPath(fileTrade));
			request.setAttribute("subtitles", lire.getSubtitles());
			request.setAttribute("traduction", lire.getTranslated());
			request.setAttribute("temps", lire.getTemps());
			request.setAttribute("numero", lire.getNumero());
			nomVideo = nomFichier;
			urlVideo = file;
		}
		
		/**
		 * On définis la liste de videos disponible dans la base de données.
		 */
		request.setAttribute("select", request.getParameter("select"));
		request.setAttribute("videos", videoDao.lire());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

}
