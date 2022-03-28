package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//XML annotation to define the root element of this record
@XmlRootElement(name= "film")
//XML annotation to define the order in which the XML recored will be built
@XmlType(propOrder = { "id", "title", "year", "director", "stars", "review" })
//Java class for building a film record with getters and setters
public class Film {
	public Film(int id, String title, int year, String director, String stars, String review) {
		super();
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.stars = stars;
		this.review = review;
	}

	public Film() {
		super();
	}

	int id;
	String title;
	int year;
	String director;
	String stars;
	String review;

	public int getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement(name = "title")
	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	@XmlElement(name = "year")
	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	@XmlElement(name = "director")
	public void setDirector(String director) {
		this.director = director;
	}

	public String getStars() {
		return stars;
	}

	@XmlElement(name = "stars")
	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getReview() {
		return review;
	}

	@XmlElement(name = "review")
	public void setReview(String review) {
		this.review = review;
	}

	//method used to build the text data format record
	@Override
	public String toString() {
		return id + "||" + title + "||" + year + "||" + stars + "||" + director
				+ "||" + review + "\r\n";
	}
}
