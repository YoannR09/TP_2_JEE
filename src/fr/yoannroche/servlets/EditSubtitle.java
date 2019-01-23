package fr.yoannroche.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.yoannroche.beans.Video;
import fr.yoannroche.dao.DAOSousTitres;
import fr.yoannroche.dao.DAOVideo;
import fr.yoannroche.dao.DaoFactory;
import fr.yoannroche.utilities.Ajout;
import fr.yoannroche.utilities.Lire;
import fr.yoannroche.utilities.Sauvegarde;


@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int TAILLE_TAMPON = 10240;
	public static final String CHEMIN_FICHIERS = "/Users/El-ra/original/";
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		ServletContext context = getServletContext();
		String btn = request.getParameter("submit");
		request.setAttribute("submit", btn);
		System.out.println(btn);

		boolean multipart = false;
		if (request.getContentType().substring(0, 19).equals("multipart/form-data")) 
		{
			multipart = true;
		}


		/**
		 * Si on clique sur le bouton ajouter.
		 * La classe Ajout récupère le fichier srt que l'utilisateur à choisit.
		 * Le texte original sera affiché et des champs vides pour faire la traduction.
		 */
		if(multipart && btn == null) {
			lastBtn = "ajouter";
			Part part = request.getPart("fichier");

			// On vérifie qu'on a bien reçu un fichier
			String nomFichier = getNomFichier(part);


			// Si on a bien un fichier
			if (nomFichier != null && !nomFichier.isEmpty()) {
				String nomChamp = part.getName();
				// Corrige un bug du fonctionnement d'Internet Explorer
				nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
						.substring(nomFichier.lastIndexOf('\\') + 1);

				// On écrit définitivement le fichier sur le disque
				ecrireFichier(part, nomFichier, CHEMIN_FICHIERS);

				request.setAttribute(nomChamp, nomFichier);
			}
			urlVideo =(CHEMIN_FICHIERS+nomFichier);
			Ajout ajout = new Ajout(CHEMIN_FICHIERS,nomFichier);
			request.setAttribute("subtitles", ajout.getSubtitles());
			nomVideo = request.getParameter("nom");
			request.setAttribute("temps", ajout.getTemps());
			request.setAttribute("numero", ajout.getNumero());
			String add = "ok";
			request.setAttribute("add", add);
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
			System.out.println("test request  "+nomFichier);
			Lire lire = new Lire(request,stDao,videoDao);
			request.setAttribute("subtitles", lire.getSubtitles());
			request.setAttribute("traduction", lire.getTranslated());
			request.setAttribute("temps", lire.getTemps());
			request.setAttribute("numero", lire.getNumero());
			nomVideo = nomFichier;
			urlVideo = lire.getUrlVideo();
		}

		else if(btn.equals("supprimer")) {
			Video video = new Video();
			video.setNom(request.getParameter("select"));
			videoDao.recupId(video);
			videoDao.suppr(video);
		}

		/**
		 * On définis la liste de videos disponible dans la base de données.
		 */
		request.setAttribute("videos", videoDao.lire());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

	private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);
			System.out.println(entree);
			System.out.println(sortie);

			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {
			}
			try {
				entree.close();
			} catch (IOException ignore) {
			}
		}
	}

	private static String getNomFichier( Part part ) {
		for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
			if ( contentDisposition.trim().startsWith( "filename" ) ) {
				return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
			}
		}
		return null;
	} 




}
