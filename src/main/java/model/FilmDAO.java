package model;

import java.util.ArrayList;
import java.sql.*;


public class FilmDAO {
	
	// variable declaration for details used for connecting to the SQL database and pulling data from the database
	Film oneFilm = null;
	Connection conn = null;
    Statement stmt = null;
	String user = "warburti";
    String password = "Geesdonk9";
    // Note none default port used, 6306 not 3306
    String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/"+user;

	public FilmDAO() {}

	
	// opening the connection to the database
		private void openConnection(){
			// loading jdbc driver for mysql
			try{
			    Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch(Exception e) { System.out.println(e); }

			// connecting to database
			try{
				// setting the connection to connect to the database using earlier defined URL, user, password
	 			conn = DriverManager.getConnection(url, user, password);
			    stmt = conn.createStatement();
			} catch(SQLException se) { System.out.println(se); }	   
	    }
		
		//closing the connection to the database
		private void closeConnection(){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Used for setting a specific record of a result set and setting as a Film record
		private Film getNextFilm(ResultSet rs){
	    	Film thisFilm=null;
			try {
				thisFilm = new Film(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getInt("year"),
						rs.getString("director"),
						rs.getString("stars"),
						rs.getString("review"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return thisFilm;		
		}
		
		

		//method for returning an Array List of an SQL select statement to grab all data from a SQL database
		public ArrayList<Film> getAllFilms(){
		   
			// Array List for all the films taken from the database to be stored in
			ArrayList<Film> allFilms = new ArrayList<Film>();
			//open a connection to the database
			openConnection();
			
		    // Create select statement and execute it
			try{
			    String selectSQL = "select * from films";
			    //execute SQL statement and store data in a result set
			    ResultSet rs1 = stmt.executeQuery(selectSQL);
			    
			    //loop through the length of the result set and for each record add to the allFilms Array List
			    while(rs1.next()){
			    	oneFilm = getNextFilm(rs1);
			    	allFilms.add(oneFilm);
			   }

			    //empty the SQL statement for use again
			    stmt.close();
			    //close the connection to the database
			    closeConnection();
			} catch(SQLException se) { System.out.println(se); }

			//return the data pulled from the SQL database
		    return allFilms;
		}

		//method for returning a single record from the database using an id
		public Film getFilmByID(int id){
		   
			openConnection();
			oneFilm=null;
		    // Create select statement and execute it
			try{
			    String selectSQL = "select * from films where id="+id;
			    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
			    while(rs1.next()){
			    	oneFilm = getNextFilm(rs1);
			    }

			    stmt.close();
			    closeConnection();
			} catch(SQLException se) { System.out.println(se); }

		    return oneFilm;
		}
	   
		//method for returning an Array List of films by getting films with a names like the one passed in
		public ArrayList<Film> getFilm(String name){
		   	ArrayList<Film> filmsByName = new ArrayList<Film>();
			openConnection();
			oneFilm=null;
		    // Create select statement and execute it
			try{
			    String selectSQL = "select * from films where title like '%"+name+"%'";
			    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    // Retrieve the results
			    while(rs1.next()){
			    	oneFilm = getNextFilm(rs1);
			    	filmsByName.add(oneFilm);
			    }

			    stmt.close();
			    closeConnection();
			} catch(SQLException se) { System.out.println(se); }

		   return filmsByName;
	   }
	   
	   //inserting a film record using a Film record passed in
	   public void insertFilm(Film f) {
		   
			openConnection();
			
		    // Create insert statement using the value of the Film record and execute it
			try{
			    String insertSQL = "INSERT INTO films(title, year, director, stars, review) VALUES('"
			    		+ f.getTitle().toUpperCase() +"', '"
			    		+ f.getYear() +"', '"
			    		+ f.getDirector().toUpperCase() +"', '"
			    		+ f.getStars().toUpperCase() +"', '"
			    		+ f.getReview().toUpperCase() + "');"
			    		;
			    
			    stmt.executeUpdate(insertSQL);

			    stmt.close();
			    closeConnection();
			} catch(SQLException se) { System.out.println(se); }
	   }
	   
	   //updating a film using a Film record passed in
	   public void updateFilm(Film f) {
		   
			openConnection();
			
		    // Create update statement using the value of the Film record and execute it
			try{
			    String updateSQL = "UPDATE  films "
			    		+ "SET title = '" + f.getTitle().toUpperCase() +"', "
			    		+ "year = '" + f.getYear() +"', "
			    		+ "director = '" + f.getDirector().toUpperCase() +"', "
			    		+ "stars = '" + f.getStars().toUpperCase() +"', "
			    		+ "review = '" + f.getReview().toUpperCase() +"'"
			    		+ "WHERE id = '" + f.getId() + "';"
			    		;
			    
			    stmt.executeUpdate(updateSQL);

			    stmt.close();
			    closeConnection();
			} catch(SQLException se) { System.out.println(se); }
	   }
	   
	   //deleting a record from the database using a Film record to get the ID to delete
	   public void deleteFilm(Film f) {
		   
			openConnection();
			
		    // Create delete statement using the Film record ID and execute it
			try{
			    String deleteSQL = "DELETE FROM films WHERE id='" + f.getId() + "';";
			    
			    stmt.executeUpdate(deleteSQL);

			    stmt.close();
			    closeConnection();
			} catch(SQLException se) { System.out.println(se); }
	   }

}
