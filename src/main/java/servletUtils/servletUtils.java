package servletUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Film;
import model.xmlFilmList;

public class servletUtils {
	
	//This builds an XML string from a given data ArrayList
	public String xmlBuilder(ArrayList<Film> data) 
	{
		String xmlOutput;
		
		//Uses the xmlFilmList with the xmlAnotation for the outer filmList Annotation of the whole XML document
		xmlFilmList newFL = new xmlFilmList();
		newFL.setFilmList(data);
		
		try {
			//JaxB builds the XML string using the XMl annotations put in the Film and xmlFilmList classes
	    	JAXBContext context = JAXBContext.newInstance(xmlFilmList.class);
	    	
		    Marshaller mar= context.createMarshaller();
		    
		    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    
		    //Writes the XML to a StringWriter and converts it to a string
		    StringWriter stringWriter = new StringWriter();
			mar.marshal(newFL, stringWriter);
			xmlOutput = stringWriter.toString();
			
			//returns the XML as a string
			return xmlOutput;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//This builds an JSON string from a given data ArrayList
	public String jsonBuilder(ArrayList<Film> data) 
	{
		String jsonOutput;
		
		//GsonBuilder is used to build the JSON using the data ArrayList  
		jsonOutput = new GsonBuilder().setPrettyPrinting().create().toJson(data);
		
		//Returning the JSON String
		return jsonOutput;
	}
	
	//This runs the data arrayList around a loop and pulls each record and changes it to a string record
	public String textBuilder(ArrayList<Film> data) 
	{
		String textOutput = "";
		
		for(int i =0; i<data.size(); i++)
		{
		textOutput = textOutput + data.get(i).toString();
		}
		
		return textOutput;
	}

	//This Deconstructs XML from a InputStream and turns it into a Film object
	public Film xmlDeconstructor(InputStream xmlInputStream)
	{
		try {
			//The opposite process of creating the XML String turns a XMl InputStream to a FilmObject
	    	JAXBContext context = JAXBContext.newInstance(Film.class);
		    Unmarshaller unMar= context.createUnmarshaller();
			
		    Film returnedFilm = (Film) unMar.unmarshal(xmlInputStream);
		    
			return returnedFilm;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//This Deconstructs JSON from a InputStream and turns it into a Film object
	public Film jsonDeconstructor(String json)
	{
		Film returnFilm = new Gson().fromJson(json, Film.class);
		return returnFilm;
	}
	

}
