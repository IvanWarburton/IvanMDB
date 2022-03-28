//Deletes film
function deleteFilm(filmToBeDeleted)
{ 
	var data = "film="+filmToBeDeleted.innerHTML;
	//sending the data to the servlet using JQuery method a Delete request
	$.ajax ({url: "mainServlet"+"?"+data, method:"DELETE"});
}

function updateFilm(format)
{
	//declaring the variables to be used building the string to be sent
	var id = getValue('popUpId');
	var title = getValue('popUpTitle');
	var year = getValue('popUpYear');
	var director = getValue('popUpDirector');
	var stars = getValue('popUpStars');
	var review = getValue('popUpReview');
	
	// check format for xml and start building XML string
	if (format.value == "xml") {
		//XML Builder declaring the elements of the XML
		var xml = $('<film>');
		xml.append('<id>');
		xml.append('<title>');
		xml.append('<year>');
		xml.append('<director>');
		xml.append('<stars>');
		xml.append('<review>');
		
		//Filling the XML elements with declared data
		xml.children().eq(0).text(id);
		xml.children().eq(1).text(title);
		xml.children().eq(2).text(year);
		xml.children().eq(3).text(director);
		xml.children().eq(4).text(stars);
		xml.children().eq(5).text(review);
		
		//Taking the XML type data and converting it to a string
		xmlString = xml[0].outerHTML;
		//preparing the data for sending
		var xmlData = "xml="+ xmlString;
		
		//sending the data to the servlet using JQuery method a Put request
		$.ajax ({url: "mainServlet"+"?"+xmlData, method:"PUT"});
		
	} 
	//Check format for JSON and start building JSON string
	else if (format.value == "json") {
		//JSON Builder building the JSON record
		var json = {
			"id" : id,
			"title" : title,
			"year" : year,
			"director" : director,
			"stars" : stars,
			"review" : review
		}
		
		//Turning the json data in to string data and encoding the { } brackets
		var jsonString = JSON.stringify(json).replaceAll(/{/g,"%7B").replaceAll(/}/g,"%7D");
		//preparing the data for sending
		var jsonData = "json=" + jsonString;
		
		//sending the data to the servlet using JQuery method a Put request
		$.ajax ({url: "mainServlet"+"?"+jsonData, method:"PUT"});
		
	} 
	// else start building Text string data
	else {
		//Building the data as individual request parameters to be sent
		var textData = 
		"id="+id+
		"&title="+title+
		"&year="+year+
		"&director="+director+
		"&stars="+stars+
		"&review="+review;
		
		//sending the data to the servlet using JQuery method as a Put request
		$.ajax ({url: "mainServlet"+"?"+textData, method:"PUT"});
	}
}


//This function runs nearly identical to the above update but doen't include the ID as this is not required for the insert
function insertFilm(format)
{
	var title = getValue('insertTitle');
	var year = getValue('insertYear');
	var director = getValue('insertDirector');
	var stars = getValue('insertStars');
	var review = getValue('insertReview');
	
	
	if (format.value == "xml") {
		var xmlString = $('<film>');
		xmlString.append('<title>');
		xmlString.append('<year>');
		xmlString.append('<director>');
		xmlString.append('<stars>');
		xmlString.append('<review>');
		
		xmlString.children().eq(0).text(title);
		xmlString.children().eq(1).text(year);
		xmlString.children().eq(2).text(director);
		xmlString.children().eq(3).text(stars);
		xmlString.children().eq(4).text(review);
		
		xmlString = xmlString[0].outerHTML;
		var xmlData = "xml="+ xmlString;
		
		//sending the data to the servlet using JQuery method as a Post request
		$.ajax ({url: "mainServlet"+"?"+xmlData, method:"POST"});
		
	} else if (format.value == "json") {
		var film = {
			"title" : title,
			"year" : year,
			"director" : director,
			"stars" : stars,
			"review" : review
		}
		
		var json = JSON.stringify(film).replaceAll(/[{]/g,"%7B").replaceAll(/[}]/g,"%7D");
		var jsonData = "json=" + json;
		
		//sending the data to the servlet using JQuery method as a Post request
		$.ajax ({url: "mainServlet"+"?"+jsonData, method:"POST"});
		
	} else {
		var textData = 
		"title="+title+
		"&year="+year+
		"&director="+director+
		"&stars="+stars+
		"&review="+review;
		
		//sending the data to the servlet using JQuery method as a Post request
		$.ajax ({url: "mainServlet"+"?"+textData, method:"POST"});
	}
}

//Gets film data either as a whole or a select result set in XML, JSON or Text 
function getFilmsOfDataType(format, resultRegion, filmToSearch) {
	if (format.value == "xml") {
		xmlTable(resultRegion, filmToSearch);
	} else if (format.value == "json") {
		jsonTable(resultRegion, filmToSearch);
	} else {
		stringTable(resultRegion, filmToSearch);
	}
}

//Determines if the request is a get films or get all films request
function xmlTable(resultRegion, filmToSearch) {
	//checks if filmToSearch has a film to search otherwise send all films
	if(filmToSearch!="")
	{var data = "format=xml&film=" + filmToSearch.value;}
	else
	{var data = "format=xml";}
	//JQuery Get request
	$.get("mainServlet"+"?"+data, function(request) {showXmlInfo(request, resultRegion);});
}

//Decoding the xml data and sending it getTable to be built as a table
function showXmlInfo(request, resultRegion) {
		var headings = ["Film ID", "Title", "Year", "Stars", "Director", "Review", "Update"];
		var films = request.getElementsByTagName("film");
		var rows = new Array(films.length);
		var subElementNames = ["id", "title", "year", "stars", "director", "review"];
		for (var i = 0; i < films.length; i++) {
			rows[i] =
				getElementValues(films[i], subElementNames);
		}
		var table = getTable(headings, rows);
		htmlInsert(resultRegion, table);
		//Unhiding the first page
		document.getElementById('tablePage1').removeAttribute('hidden');
		//Unhiding the Table Navigaion
		showTableNav();
}

//Determines if the request is a get films or get all films request
function jsonTable(resultRegion, filmToSearch) {
	//checks if filmToSearch has a film to search otherwise send all films
	if(filmToSearch!="")
	{var data = "format=json&film=" + filmToSearch.value;}
	else
	{var data = "format=json";}
	//JQuery Get request
	$.get("mainServlet"+"?"+data, function(request) {showJsonInfo(request, resultRegion);});
}

//Decoding the JSON data and sending it getTable to be built as a table
function showJsonInfo(request, resultRegion) {
		var headings = ["Film ID", "Title", "Year", "Stars", "Director", "Review", "Update"];
		var data = eval("(" + request + ")");
		var rows = new Array();
		for(var i=0; i<data.length; i++)
		{
			var film = data[i];
			rows[i] = [film.id, film.title, film.year, film.stars, film.director, film.review];
		}
		var table = getTable(headings, rows);
		htmlInsert(resultRegion, table);
		//Unhiding the first page
		document.getElementById('tablePage1').removeAttribute('hidden');
		//Unhiding the Table Navigaion
		showTableNav();
}

function stringTable(resultRegion, filmToSearch) {
	//checks if filmToSearch has a film to search otherwise send all films
	if(filmToSearch!="")
	{var data = "format=text&film=" + filmToSearch.value;}
	else
	{var data = "format=text";}
	//JQuery Get request
	$.get("mainServlet"+"?"+data, function(request) {showStringInfo(request, resultRegion);});
}

//Decoding the Text data and sending it getTable to be built as a table
function showStringInfo(request, resultRegion) {
		var rowStrings = request.split(/[\n\r]+/);
		var headings = ["Film ID", "Title", "Year", "Stars", "Director", "Review", "Update"];
		var rows = new Array(rowStrings.length);
		for (var i = 0; i < rowStrings.length; i++) {
			rows[i] = rowStrings[i].split("||");
		}
		var table = getTable(headings, rows);
		htmlInsert(resultRegion, table);
		//Unhiding the first page
		document.getElementById('tablePage1').removeAttribute('hidden');
		//Unhiding the Table Navigaion
		showTableNav();
}