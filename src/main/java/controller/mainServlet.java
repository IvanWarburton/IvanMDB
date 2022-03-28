package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servletUtils.servletUtils;
import model.Film;
import model.FilmDAO;


public class mainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	servletUtils servletUtils = new servletUtils();
	
	//This returns an array of films either all or ones like the film passed threw in the formats XML, JSON or Text 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String film = request.getParameter("film");
		
		ArrayList<Film> filmsToReturn;
		FilmDAO filmDAO = new FilmDAO();
		
		//Check if film has been filled if not it will be a getAllFilms request
		if(film==null) 
		{
			filmsToReturn = filmDAO.getAllFilms();
		}
		//Else the request is to find films with name like "film"
		else 
		{
			filmsToReturn = filmDAO.getFilm(film);
		}
		
		String format = request.getParameter("format");
		String outputPage;
		
		// If no format is passed through by default the results are returned in JSON
		if(format==null) {format="json";}
		
		//This checks for the format which has been requested to be returned in
		if ("xml".equals(format)) {
			
			request.setAttribute("xmlData", servletUtils.xmlBuilder(filmsToReturn));
			response.setContentType("text/xml");
			outputPage = "/WEB-INF/results/film-xml.jsp";
			System.out.println("Showing XML");
		} 
		else if ("json".equals(format)) {
			
			request.setAttribute("jsonData", servletUtils.jsonBuilder(filmsToReturn));
			response.setContentType("text/javascript");
			outputPage = "/WEB-INF/results/film-json.jsp";
			System.out.println("Showing JSON");
		} 
		else {
			
			request.setAttribute("textData", servletUtils.textBuilder(filmsToReturn));
			response.setContentType("text/plain");
			outputPage = "/WEB-INF/results/film-text.jsp";
			System.out.println("Showing Text");
		}
		
		//Packages up the data and sends it back
		RequestDispatcher dispatcher = request.getRequestDispatcher(outputPage);
		dispatcher.include(request, response);
	}
	
	//Inserts a new film into the database using an XMl, JSON or text String
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Film theFilm = new Film();
		FilmDAO filmDAO = new FilmDAO();
		
		//Check if the data sent back is in XML, JSON or Text 
		if(request.getParameter("xml") != null) 
		{
			//Converts the XML string to a input stream
			InputStream xmlInputString = new ByteArrayInputStream(request.getParameter("xml").getBytes());
			
			//Deconstructs the XML Input stream turns it into a film object 
			Film filmToBeUpdated = servletUtils.xmlDeconstructor(xmlInputString);
			
			//insert film using the Film Object
			filmDAO.insertFilm(filmToBeUpdated);
			
			
		}
		else if(request.getParameter("json") != null)
		{
			//Deconstructs the JSON string turns it into a film object 
			Film filmToBeUpdated = servletUtils.jsonDeconstructor(request.getParameter("json"));
			
			//insert film using the Film Object
			filmDAO.insertFilm(filmToBeUpdated);
			
		}
		else 
		{	
			//Gets each string parameter and set the Film object
			theFilm.setId(Integer.parseInt(request.getParameter("id")));
			theFilm.setTitle(request.getParameter("title"));
			theFilm.setYear(Integer.parseInt(request.getParameter("year")));
			theFilm.setDirector(request.getParameter("director"));
			theFilm.setStars(request.getParameter("stars"));
			theFilm.setReview(request.getParameter("review"));
			
			//insert film using the Film Object
			filmDAO.insertFilm(theFilm);
		}
		
	}
	
	//This works similar to the doPost but Updates the film so it does not need the ID parameter
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Film theFilm = new Film();
		FilmDAO filmDAO = new FilmDAO();
		
		if(request.getParameter("xml") != null) 
		{
			InputStream xmlInputString = new ByteArrayInputStream(request.getParameter("xml").getBytes());;
			
			Film filmToBeUpdated = servletUtils.xmlDeconstructor(xmlInputString);
			
			//update film
			filmDAO.updateFilm(filmToBeUpdated);
			
			
		}
		else if(request.getParameter("json") != null)
		{
			Film filmToBeUpdated = servletUtils.jsonDeconstructor(request.getParameter("json"));
			
			//update film
			filmDAO.updateFilm(filmToBeUpdated);
			
		}
		else 
		{		
			theFilm.setId(Integer.parseInt(request.getParameter("id")));
			theFilm.setTitle(request.getParameter("title"));
			theFilm.setYear(Integer.parseInt(request.getParameter("year")));
			theFilm.setDirector(request.getParameter("director"));
			theFilm.setStars(request.getParameter("stars"));
			theFilm.setReview(request.getParameter("review"));
			
			//update film
			filmDAO.updateFilm(theFilm);
		}
		

	}
	
	//Pulls the requested film to be deleted and deletes from the database
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int film = Integer.parseInt(request.getParameter("film"));
		
		FilmDAO filmDAO = new FilmDAO();
		Film theFilm = filmDAO.getFilmByID(film);
		
		//delete the film
		filmDAO.deleteFilm(theFilm);
		
	}
}
