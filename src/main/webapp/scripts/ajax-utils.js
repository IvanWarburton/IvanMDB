// Insert the html data into the element 
// that has the specified id.

function htmlInsert(id, htmlData) {
	document.getElementById(id).innerHTML = htmlData;
}

// Return escaped value of textfield that has given id.
// The builtin "escape" function url-encodes characters.

function getValue(id) {
	return (escape(document.getElementById(id).value));
}

// Given an element, returns the body content.

function getBodyContent(element) {
	element.normalize();
	return (element.childNodes[0].nodeValue);
}

// Given an element object and an array of sub-element names,
// returns an array of the values of the sub-elements.
// E.g., for <foo><a>one</a><c>two</c><b>three</b></foo>,
// if the element points at foo,
// getElementValues(element, ["a", "b", "c"]) would return
// ["one", "three", "two"]

function getElementValues(element, subElementNames) {
	var values = new Array(subElementNames.length);
	for (var i = 0; i < subElementNames.length; i++) {
		var name = subElementNames[i];
		var subElement = element.getElementsByTagName(name)[0];
		values[i] = getBodyContent(subElement);
	}
	return (values);
}

// Takes as input an array of headings (to go into th elements)
// and an array-of-arrays of rows (to go into td
// elements). Builds an xhtml table from the data.

function getTable(headings, rows) {
	var table = "<table border='1' id='returnedDataTable' class='table table-dark'>\n" +
		getTableHeadings(headings) +
		getTableBody(rows) +
		"</table>";
	return (table);
}

function getTableHeadings(headings) {
	var firstRow = "  <tr>";
	for (var i = 0; i < headings.length; i++) {
		firstRow += "<th>" + headings[i] + "</th>";
	}
	firstRow += "</tr>\n";
	return (firstRow);
}

function getTableBody(rows) {
	var body = "";
	var tablePageNum = 0;
	var rowCounter = 0;
	for (var i = 0; i < rows.length; i++) 
	{
		//The below two if statments are used for the page seperation for the result data
		//rowCounter used to count the ammount of records in the tablePage
		//when the rowCounter get to 40 it resets and starts a new tablePage
		if(rowCounter==40)
		{
			body += "</tbody>";
			rowCounter=0;
		}
		if(rowCounter==40 || rowCounter==0)
		{
			tablePageNum+=1;
			//tablePage's are used as makers to limit the ammoun of data being show on a page 
			body += "<tbody id='tablePage"+tablePageNum+"' hidden>";
		}
		
		body += "  <tr>";
		var row = rows[i];
		for (var j = 0; j < row.length; j++) 
		{
			body += "<td class='align-middle'>" + row[j] + "</td>";
		}
		var rowNum = i+1;
		//Adding a update button for every record with a unique showUpdateDetails id
		body += '<td class="align-middle"> <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#updateBox" onclick="showUpdateDetails('+rowNum+')">Update</button> </td>';
		
		body += "</tr>\n";
		rowCounter +=1;
	}
	body += "</tbody>";
	
	return (body);
}