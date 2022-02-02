package com.context;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.Etudiant;

/**
 * Cette classe servlet pr�sente des exemples avec le context de servlet
 * 
 * @author BOUDAA
 *
 */

@WebServlet("/contextExamples")
public class ContextExamplesServlet extends HttpServlet {

	public ContextExamplesServlet() {

	}

	/**
	 * Cette m�thode stocke un objet dans le context
	 * 
	 * @param rq
	 *            : l'objet repr�sentant la requete
	 * @param key
	 *            : une chaine de caract�re qui represente la cl� � utiliser
	 *            pour r�f�rencer l'objet dans le context
	 * @param object
	 *            : l'objet � stocker dans le context
	 */
	public void storeObjectInContext(HttpServletRequest rq, String key, Object object) {

		// On r�cup�re le context
		ServletContext context = rq.getServletContext();

		// On stocke l'objet dans le contexte de l'application

		context.setAttribute(key, object);

	}

	/**
	 * M�thode qui permet de r�cup�rer un objet du context
	 * 
	 * @param rq
	 *            : l'objet repr�sentant la requete
	 * @param key
	 *            : une chaine de caract�re qui represente la cl� � utiliser
	 *            pour r�cup�rer l'objet du context
	 * @param object
	 *            : l'objet � r�cup�rer du context
	 */
	public Object getObjectFromContext(HttpServletRequest rq, String key) {

		// On r�cup�re le context
		ServletContext context = rq.getServletContext();

		// On r�cup�re l'objet du context
		return context.getAttribute(key);

	}

	/**
	 * Permet d'�crire un paragraphe dans la r�ponse
	 * 
	 * @param response
	 *            : objet de r�ponse � envoyer � l'utilisateur
	 * @throws IOException
	 */
	public void writeParagraph(HttpServletResponse response, String p) throws IOException {

		PrintWriter out = response.getWriter();
		out.print("<p>" + p + "</p>");
	}

	/**
	 * M�thode qui s'execute lorsque la sevlet recoit une requete de type GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// On r�cup�re un param�tre de la requete qui indique la tache demand�e,
		// ce param�tre attendu est : task
		String param = request.getParameter("task");

		// Suite � ce param�re execute l'une des taches c-dessous :

		// Tache 1 : On stocke un objet �tudiant dans le context
		if ("store".equals(param)) {
			Etudiant et = new Etudiant("Karimi", "Ali", 22);
			storeObjectInContext(request, "etudiant", et);

			// On ins�re du texte dans la r�ponse
			writeParagraph(response, "L'�tudiant est bien ajout�e dans la session");

		}
		// Tache 2 : On r�cup�re un objet �tudiant de la session

		else if ("get".equals(param)) {

			// On r�cup�re un �tudiant du context
			// L'objet r�cup�r� du context est g�n�ralement de type Object,
			// on s'assure qu'il s'agit exactement d'un objet de type Etudiant
			// par instanceof avant de forcer la conversion
			Etudiant etudiant = null;
			Object obj = getObjectFromContext(request, "etudiant");
			if (obj instanceof Etudiant) {
				etudiant = (Etudiant) obj;
			}

			// si un �tudiant est trouv�
			if (etudiant != null) {
				// On ins�re du texte dans la r�ponse
				// on laisse la m�thode toString de Etudiant prendera soin de
				// convertir l'objet en une belle chaine de caract�re
				writeParagraph(response, "L'�tudiant est trouv� : " + etudiant);
			}

			else {
				// On �crit un message indiquant que l'objet recherch� n'existe
				// pas dans le context
				writeParagraph(response, "Aucun �tudiant trouv�");

			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
