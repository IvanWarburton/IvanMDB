package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//This class is used mainly for building the XML, it needs the class to build the element wrapper that wraps all the individual records around the main filmList element.

@XmlRootElement(namespace= "model")
@XmlAccessorType(XmlAccessType.FIELD)
public class xmlFilmList {
	
	@XmlElementWrapper(name="filmList")
	@XmlElement(name="film")
	private ArrayList<Film> filmList;
	
	public void setFilmList(ArrayList<Film> filmList)
	{
		this.filmList = filmList;
	}
	
	public ArrayList<Film> getFilmList()
	{
		return filmList;
	}

}
