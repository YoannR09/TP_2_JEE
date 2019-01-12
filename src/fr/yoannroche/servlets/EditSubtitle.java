package fr.yoannroche.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		else if(btn.equals("sauvegarde")) {
			System.out.println(nomVideo);
			System.out.println(urlVideo);
			new Sauvegarde(request,videoDao,stDao,nomVideo,urlVideo,lastBtn,context);
		}
		else if(btn.equals("lire")) {
			lastBtn = btn;
			String nomFichier = request.getParameter("select");
			String file = "/WEB-INF/original/"+nomFichier+".srt";
			Lire lire = new Lire(request,context.getRealPath(file),stDao);
			request.setAttribute("subtitles", lire.getSubtitles());
			request.setAttribute("traduction", lire.getTranslated());
			request.setAttribute("temps", lire.getTemps());
			request.setAttribute("numero", lire.getNumero());
		}
		
		request.setAttribute("select", request.getParameter("select"));
		request.setAttribute("videos", videoDao.lire());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

}
