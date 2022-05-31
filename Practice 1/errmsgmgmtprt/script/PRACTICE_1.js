/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_1.js
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.07 
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  Event identification code: [Initialization]INIT=0; [input]ADD=1; [Query]SEARCH=2; [List inquiry]SEARCHLIST=3;
					[Edit] MODIFY=4; [Delete]REMOVE=5; [Remove list]REMOVELIST=6 [Multiprocessing]MULTI=7
					Other extra character constants COMMAND01=11; ~COMMAND20=30;
 ***************************************************************************************/

/*------------------The following code is added to make JSDoc well. ------------------*/
   /**
     * @fileoverview This is a JavaScript file commonly used in work, and calendar-related functions are defined.
     * @author 한진해운
     */
    
// common global variable
var tabObjects=new Array();
var tabCnt=0 ;
var beforetab=1;
var sheetObjects=new Array();
var sheetCnt=0;
var rowcount=0;
var flag = null;
//Define an event handler that receives and handles button click events
document.onclick=processButtonClick;
document.onkeydown=logKey;


function logKey(key){
	var sheetObject1 = sheetObjects[0];
	var formObj = document.form;
	
	if(key.code == 'Enter'){
		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
	}
}

/*
 * function for branching to the corresponding logic when a button on the screen is pressed
 */
function processButtonClick(){
	var sheetObject1=sheetObjects[0];
    var formObject=document.form;
    // Run and if exception, the UI will show an error popup
    try {
    	// Get name of object  
    	var srcName=ComGetEvent("name");
    	// Each object will have its action
        switch(srcName) {
        	case "btn_Add":
        		// To insert a row  below the currently selected row
            	doActionIBSheet(sheetObject1,formObject,IBINSERT);
            	break;
            case "btn_Retrieve":
        		// To show the search result and show on grid.
            	doActionIBSheet(sheetObject1,formObject,IBSEARCH);
            	break;
            case "btn_Save":
            	// To save changes on grid to server database.
            	doActionIBSheet(sheetObject1,formObject,IBSAVE);
            	break;
            case "btn_DownExcel":
            	// To download the grid in excel form to your computer.
            	doActionIBSheet(sheetObject1,formObject,IBDOWNEXCEL);
            	break;
            }
    }
    catch(e) {
    	// if message is [object Error], popup will show a message through OBJECT_ERROR code
    	// else error popup will show the error thrown.
    	if( e == "[object Error]") {
    		ComShowMessage(OBJECT_ERROR);
    	} 
    	else {
    		ComShowMessage(e);
    	}
    }
}

/*
 * function to put sheet objects in global variable "sheetObjects"
 */
function setSheetObject(sheet_obj){
	sheetObjects[sheetCnt++]=sheet_obj;
}
/*
 * functions that calls a common function that sets the default settings of the sheet
 * It is the first called area when fire jsp onload event
 */
function loadPage() {
	for(i=0;i<sheetObjects.length;i++){
		// Set the basic design of the sheet. This function must be called before sheet initialization. (CoObject.js)
		ComConfigSheet (sheetObjects[i]);
		//Sheet initialization
		initSheet(sheetObjects[i],i+1);
		// Finish the basic design of the sheet. This function must be called after sheet initialization. (CoObject.js)
		ComEndConfigSheet(sheetObjects[i]);
	}
	// To show the search result on grid.
	doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
}

/*
 * functions that define the basic properties of the sheet on the screen
 */
function initSheet(sheetObj,sheetNo) {
	var cnt=0;
	var sheetID=sheetObj.id;
    switch(sheetNo) {
    	case 1:
    		with(sheetObj){
    			// define a string to store head titles
            	var HeadTitle="STS|Del|Msg Cd|Msg Type|Msg level|Message|Description" ;
            	// SetConfig: configure how to fetch initialized sheet, location of frozen rows or columns and other basic configurations
            		// SearchMode: 2 (is where you can configure search mode)
            			// LazyLoad mode
            			// Search all data and display search result data on the screen by page as set in Page property value according to the scroll location
            		// MergeSheet: 5 (is where you can configure merge styles)
            			// Value: msHeaderOnly
            			// Allow merge in the header rows only
            		// FrozenCol: 0 (is where you can select the frozen column count in the left)
            		// DataRowMerge: 1 (Whether to allow horizontal merge of the entire row.)
                SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
                //Define header functions such as sorting and column movement permissions in json format
                	// Sort: 1 (allow sorting by clicking on the header)
                	// ColMove: 1 (allow column movement in header)
                	// HeaderCheck : 0 (the CheckAll in the header is not checked)
                	// ColResize: 1 (allow resizing of column width)
                var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
                // Define header title and alignment in json format.
                var headers = [ { Text:HeadTitle, Align:"Center"} ];
                // Define the header title and function using this method.
                InitHeaders(headers, info);
                // Type			 (String) : Column data type
                // Hidden 		 (Boolean): Whether a column is hidden
                // Width		 (Number) : Column width
                // Align 		 (String) : Data alignment
                // ColMerge 	 (Boolean): Whether to allow column merging
                // SaveName		 (String) : A parameter name used to save or search data
                // KeyField 	 (Boolean): Required fields
                // UpdateEdit    (Boolean): Whether to allow data editing when transaction is in "Search" state
                // InsertEdit 	 (Boolean): Whether to allow data editing when transaction is in "Insert" state
                // EditLen 	     (Number) : Editable data legnth
                // ComboText 	 (String) : Combo list text string group
                // ComboCode 	 (String) : Combo list code group
                // MultiLineText (Boolean): Whether to use multilines
                var cols = [ 
                    {Type:"Status",   Hidden:1, Width:30,  Align:"Center", ColMerge:0, SaveName:"ibflag" },
                    {Type:"DelCheck", Hidden:0, Width:45,  Align:"Center", ColMerge:0, SaveName:"DEL",        KeyField:0, UpdateEdit:1, InsertEdit:1 },
	                {Type:"Text",     Hidden:0, Width:80,  Align:"Center", ColMerge:1, SaveName:"err_msg_cd", KeyField:1, UpdateEdit:0, InsertEdit:1, EditLen: 8 },
	                {Type:"Combo",    Hidden:0, Width:80,  Align:"Center", ColMerge:1, SaveName:"err_tp_cd",  KeyField:1, UpdateEdit:1, InsertEdit:1, ComboText:"Server|UI|Both",   ComboCode:"S|U|B" },
	                {Type:"Combo",    Hidden:0, Width:80,  Align:"Center", ColMerge:1, SaveName:"err_lvl_cd", KeyField:1, UpdateEdit:1, InsertEdit:1, ComboText:"ERR|WARNING|INFO", ComboCode:"E|W|I" },
	                {Type:"Text",     Hidden:0, Width:400, Align:"Left",   ColMerge:0, SaveName:"err_msg",    KeyField:1, UpdateEdit:1, InsertEdit:1, MultiLineText:1 },
	                {Type:"Text",     Hidden:0, Width:250, Align:"Left",   ColMerge:0, SaveName:"err_desc",   KeyField:0, UpdateEdit:1, InsertEdit:1 } 
	            ];
                // Configure data type, format and functionality of each column.
                InitColumns(cols);
                //Set not to display waiting image for processing
                SetWaitImageVisible(0);
                resizeSheet();
            }
            break;
    }
}
    
function resizeSheet(){
	ComResizeSheet(sheetObjects[0]);
}

/*
 * functions that define transaction logic between UI and server
 */
function doActionIBSheet(sheetObj,formObj,sAction) {
	switch(sAction) {
		case IBSEARCH:
			// ComOpenWait:Whether a loading image will appears and lock the screen
				// true: lock the screen and appear loading image
				// false: return normal
			ComOpenWait(true);
			// Set value for f_cmd in form
			formObj.f_cmd.value=SEARCH;
			// Connect to search page to read search XML, and then load XML data internally in IBSheet
 			sheetObj.DoSearch("PRACTICE_1GS.do", FormQueryString(formObj) );
			break;
		case IBSAVE:
			if (!validateForm(sheetObj, formObj, sAction)) return;
			// Set value for f_cmd in form
            formObj.f_cmd.value=MULTI;
            // Save data based on data transaction status or column.
            sheetObj.DoSave("PRACTICE_1GS.do", FormQueryString(formObj));
			break;
		case IBINSERT:
			// Insert a row below the currently selected row
			sheetObj.DataInsert();
			break;
		case IBDOWNEXCEL:
			// if sheet null, display popup error, else down sheet in excel format to your computer
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}
			else{
				// Download the data displayed in IBSheet into an excel file
					// DownCols: parameter is a string connecting all downloading rows using "|".
						/* (IBSheetInfo.js)
						 * function makeHiddenSkipCol(sobj){
    							var lc = sobj.LastCol();
    							var rtnStr = "";
    							for(var i=0;i<=lc;i++){
    								if( ! ( 1==sobj.GetColHidden(i) || 
    									sobj.GetCellProperty(0,i,"Type") == "CheckBox" || 
    									sobj.GetCellProperty(0,i,"Type") == "DummyCheck" || 
    									sobj.GetCellProperty(0,i,"Type") == "Radio"||  
    									sobj.GetCellProperty(0,i,"Type") == "Status" ||
    									sobj.GetCellProperty(0,i,"Type") =="DelCheck" )){
    										rtnStr += "|"+ i;
    								}
    							}
    							return rtnStr.substring(1);
							}
						 */
					// SheetDesign: Whether to apply IBSheet design concept to download file (0: no, 1: yes)
					// Merge: Whether to apply merge to download (0: no, 1: yes)
				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj), SheetDesign:1, Merge:1});
			}
			break;
    }
}

//Handling validate
function validateForm(sheetObj,formObj,sAction){
	var regrex = new RegExp("^[A-Z]{3}[0-9]{5}$");
	for (var i = sheetObj.HeaderRows(); i <= sheetObj.LastRow(); i++){
		if (sheetObj.GetCellValue(i, "ibflag") == 'I' && !regrex.test(sheetObj.GetCellValue(i,"err_msg_cd"))){
			//ComShowMessage('Invalid ErrMsgCd: ErrMsgCd 8 characters are required, the first 3 characters are uppercase letters, the last 5 characters are numbers.');
			ComShowCodeMessage("COM132201", "Msg cd");
			return false;
		}
	}
	return true;
}

//Handling event after searching
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	// ComOpenWait:Whether a loading image will appears and lock the screen
	// true: lock the screen and appear loading image
	// false: return normal
	ComOpenWait(false);
}
//Handling event before saving
function sheet1_OnBeforeSave(){
	var sheetObj1 = sheetObjects[0];
	if (sheetObj1.GetCellValue(sheetObj1.GetSelectRow(), "ibflag") == 'I'){
		flag = 'Insert';
	}
	else if (sheetObj1.GetCellValue(sheetObj1.GetSelectRow(), "ibflag") == 'U'){
		flag = 'Update';
	}	
	else if (sheetObj1.GetCellValue(sheetObj1.GetSelectRow(), "ibflag") == 'D'){
		flag = 'Delete';
	}
}
//Handling event after saving
function sheet1_OnSaveEnd(Code, Msg){
	if (Msg >= 0){
		if (flag == 'Insert'){
			ComShowCodeMessage('COM130102', sheetObjects[0].id);
			flag = null;
		}
		else if (flag == 'Update'){
			ComShowCodeMessage('COM130502', sheetObjects[0].id);
			flag = null;
		}
		else if (flag == 'Delete'){
			ComShowCodeMessage('COM130303', sheetObjects[0].id);
			flag = null;
		}
//		alert("Save Successfully");
		doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	}
	else{
		ComShowCodeMessage('COM130103', sheetObjects[0].id);
	}
	
}