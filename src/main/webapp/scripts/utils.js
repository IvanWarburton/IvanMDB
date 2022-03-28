//This is used to set up the table navigation bar, setting the max number of pages and setting the page to 1
function showTableNav()
{
	var maxNumPagesFound = false;
	var counter = 1;
	var maxPageNum = 1;
	
	//This counts how many pages there are to give a max page number to display
	while(maxNumPagesFound != true)
	{
		if(!document.body.contains(document.getElementById('tablePage'+counter)))
		{maxNumPagesFound = true;}
		else
		{
			counter +=1
			maxPageNum = counter-1;
		}
	}
	
	//Sets the max page number display
	document.getElementById('maxPagNum').innerHTML = maxPageNum;
	//Sets page number to 1
	document.getElementById('pageNumDisplay').innerHTML = 1;
	//Makes the navigation visable
	document.getElementById('tableNav').removeAttribute('hidden');
}

//This sets the page to the first or last page dependent on whats passede threw
function setPageFirstLast(firstOrLastPage)
{
	//If the requested page is the first page
	if(firstOrLastPage == "first")
	{
		//Get the current page number
		var pageToHide = document.getElementById('pageNumDisplay').innerHTML
		//Check if the current page isnt already the first page
		if(pageToHide != 1)
		{
			//Set the current page number display to 1 
			document.getElementById('pageNumDisplay').innerHTML = 1;
			//Unhide Page 1
			document.getElementById("tablePage1").removeAttribute('hidden');
			//Hide the page that is currently open
			document.getElementById("tablePage"+pageToHide).setAttribute("hidden", "");
		}
	}
	//else the page requested is going to be the last page
	else
	{
		//Get the current page number
		var pageToHide = document.getElementById('pageNumDisplay').innerHTML
		//Get the last page 
		var lastPage = document.getElementById('maxPagNum').innerHTML
		//Chcek if the current page isnt already the last page
		if(pageToHide != lastPage)
		{
			//Set the current page number display to display the last page
			document.getElementById('pageNumDisplay').innerHTML = lastPage;
			//Unhide the last page
			document.getElementById("tablePage"+lastPage).removeAttribute('hidden');
			//Hide the page that is currently open 
			document.getElementById("tablePage"+pageToHide).setAttribute("hidden", "");
		}
	}
}

//Change the page of displayed data plus or minus one
function changePage(plusMinus)
{
	//Get the current page and the max page number
	var pageNumber = parseInt(document.getElementById('pageNumDisplay').innerHTML);
	var maxPageNum = parseInt(document.getElementById('maxPagNum').innerHTML);
	
	//Page numbers plus or minus one
	var pageAddOne = pageNumber+1;
	var pageMinOne = pageNumber-1;
	
	//Setting the next current or pervious page numbers
	var nextPage = "tablePage"+pageAddOne;
	var curentPage = "tablePage"+pageNumber;
	var prevPage = "tablePage"+pageMinOne;
	
	//Check if the page has been requested to be incremented and if the current page is less that the max page number
	if(plusMinus == "+" && pageNumber < maxPageNum)
	{
		//increment the page number
		pageNumber+=1;
		//Set the new page number
		document.getElementById('pageNumDisplay').innerHTML = pageNumber;
		//Hid the current page
		document.getElementById(curentPage).setAttribute("hidden", "");
		//Unhid the next page
		document.getElementById(nextPage).removeAttribute('hidden');
	} 
	//Else check that the page has been requested to be decremented and its not the first page
	else if(plusMinus == "-" &&pageNumber != 1)
	{
		//increment the page number
		pageNumber-=1;
		//Set the new page number
		document.getElementById('pageNumDisplay').innerHTML = pageNumber;
		//Hid the current page
		document.getElementById(curentPage).setAttribute("hidden", "");
		//Unhid the next page
		document.getElementById(prevPage).removeAttribute('hidden');
	}
}

//This fills the update details elements boxes with the relevant data to make the update process more user friendly
function showUpdateDetails(rowOfTable)
{
	//Gets the returned data table of the HMTL page
	var dataTable = document.getElementById('returnedDataTable');
	//Searches threw the dataTable for the rowOfTable which has the perticular row data
	var dataRow = dataTable.rows.item(rowOfTable).cells;
	
	//pulls out each piece of data to be displayed and fills the value 
	document.getElementById('popUpId').value = dataRow.item(0).innerHTML;
	document.getElementById('popUpTitle').value = dataRow.item(1).innerHTML;
	document.getElementById('popUpYear').value = dataRow.item(2).innerHTML;
	document.getElementById('popUpDirector').value = dataRow.item(3).innerHTML;
	document.getElementById('popUpStars').value = dataRow.item(4).innerHTML;
	document.getElementById('popUpReview').value = dataRow.item(5).innerHTML;
	
	//This shows on the delete modal if pressed
	document.getElementById("filmIdTBDeleted").innerHTML= dataRow.item(0).innerHTML; 
	
}
