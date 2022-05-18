/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_3.js
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.22  
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
var sheetObjects=new Array();
var sheetCnt=0;
//Define an event handler that receives and handles button click events
document.onclick=processButtonClick;
document.onkeydown=logKey;


function logKey(key){
	var sheetObject1 = sheetObjects[0];
	var sheetObject2 = sheetObjects[1];
	var formObj = document.form;
	
	if(key.code == 'Enter'){
		if (!checkOverThreeMonth()){
    		if (confirm("Year Month over 3 months, do you really want to get data?")){
				doActionIBSheet(sheetObject1, formObj, IBSEARCH);
				doActionIBSheet(sheetObject2, formObj, IBSEARCH);
    		}
    		else{
    			return;
    		}
    	}
	}
}
//{processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
function processButtonClick() {
	var sheetObject1=sheetObjects[0];
    var formObject=document.form;
    try {
    	var srcName=ComGetEvent("name");
        switch(srcName) {
	        case "btn_Retrieve":
	        	if (!checkOverThreeMonth()){
	        		if (confirm("Year Month over 3 months, do you really want to get data?")){
	        			doActionIBSheet(sheetObject1, formObject, IBSEARCH);
	        			doActionIBSheet(sheetObjects[1], formObject, IBSEARCH)
	        		}
	        		else{
	        			return;
	        		}
	        	}
	        	
	        	break;
	        case "btn_datefrom_down":
	        	addMonth(formObject.acct_yrmon_from, -1);
	        	yearmonth_onchange();
	        	break;
	        case "btn_datefrom_up":
	        	addMonth(formObject.acct_yrmon_from, 1);
	        	excuteCheck();
	        	yearmonth_onchange();
	        	break;
	        case "btn_dateto_down":
	        	addMonth(formObject.acct_yrmon_to, -1);
	        	excuteCheck();
	        	yearmonth_onchange();
	        	break;
	        case "btn_dateto_up":
	        	addMonth(formObject.acct_yrmon_to, 1);
	        	yearmonth_onchange();
	        	break;
	        case "btn_New":
	        	resetForm(formObject);
	        	break;
	        case "btn_DownExcel":
            	doActionIBSheet(sheetObject1,formObject,IBDOWNEXCEL);
            	break;
        }
    }
    catch(e) {
    	if( e == "[object Error]") {
    		ComShowMessage(OBJECT_ERROR);
    	} 
    	else {
    		ComShowMessage(e);
    	}
    }
}

//{loadPage} functions that calls a common function that sets the default settings of the sheet
//It is the first called area when fire jsp onload event
function loadPage(){
	for ( var k = 0; k < comboObjects.length; k++) {
		initCombo(comboObjects[k], k + 1);
	}
	initControl();
	s_jo_crr_cd.SetSelectIndex(0);
	acct_yrmon_from.disabled = true;
	acct_yrmon_to.disabled = true;
	s_rlane_cd.SetEnable(false);
	s_trade_cd.SetEnable(false);
	
	for(k = 0;k < tabObjects.length; k++){
		initTab(tabObjects[k], k + 1);
		tabObjects[k].SetSelectedIndex(0);
	}
	
	for(i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	

	doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	doActionIBSheet(sheetObjects[1], document.form, IBSEARCH);	
}

//{setSheetObject} to put sheet objects in global variable "sheetObjects"
function setSheetObject(sheet_obj){
	sheetObjects[sheetCnt++] = sheet_obj;
}

//{initSheet} functions that define the basic properties of the sheet on the screen
function initSheet(sheetObj,sheetNo) {
	var cnt = 0;
	switch (sheetNo) {
		case 1:
			with(sheetObj){    
				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Curr.|Revenue|Expense|Code|Name"
	
	            SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
	
	            var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	            var headers = [ { Text: HeadTitle1, Align: "Center"},
	                            { Text: HeadTitle2, Align: "Center"}];
	            InitHeaders(headers, info);
	            
	            var cols = [ 
	       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center",   ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center",   ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
	       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}
	       	             ];
	            InitColumns(cols);
				SetEditable(1);
				SetAutoSumPosition(1);
				SetWaitImageVisible(0);
				resizeSheet(); 
			}
			break;
		case 2:
			with(sheetObj){
				var HeadTitle1 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Customer/S.Provider|Customer/S.Provider";
				var HeadTitle2 = "STS|Partner|Lane|Invoice No|Slip No|Approved|Rev/Exp|Item|Curr.|Revenue|Expense|Code|Name";
				
				SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
				
	            var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
	            var headers = [ { Text: HeadTitle1, Align: "Center"},
	                            { Text: HeadTitle2, Align: "Center"}];
	            InitHeaders(headers, info);
	            
	            var cols = [ 
		       	             { Type: "Status", Hidden: 1, Width: 50,  Align: "Center", ColMerge: 0, SaveName: "ibflag" },
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "jo_crr_cd",       KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rlane_cd",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 150, Align: "Center", ColMerge: 0, SaveName: "inv_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 200, Align: "Center", ColMerge: 0, SaveName: "csr_no",          KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}, 
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "apro_flg",        KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	             { Type: "Combo",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "rev_exp",         KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0, ComboText: "Rev|Exp", ComboCode: "R|E"},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "item",        	 KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "locl_curr_cd",    KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	             { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_rev_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "inv_exp_act_amt", KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "prnr_ref_no",     KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0},
		       	          	 { Type: "Text",   Hidden: 0, Width: 100, Align: "Center", ColMerge: 0, SaveName: "cust_vndr_eng_nm",KeyField: 1, Format: "", UpdateEdit: 0, InsertEdit: 0}
		       	             ];
		            InitColumns(cols);
					SetEditable(1);
					SetAutoSumPosition(1);
					SetWaitImageVisible(0);
					SetSheetHeight(500);
					resizeSheet();
			}
			break;
	}
}

//{doActionIBSheet} functions that define transaction logic between UI and server
function doActionIBSheet(sheetObj,formObj,sAction) {
	switch (sAction) {
		case IBSEARCH: // retrieve
 			if (sheetObj.id == "sheet1" ) {
 				ComOpenWait(true);
 				formObj.f_cmd.value = SEARCH;
 	 			sheetObj.DoSearch("PRACTICE_3GS.do", FormQueryString(formObj));
			}
			else if (sheetObj.id == "sheet2"){
				ComOpenWait(true);
				formObj.f_cmd.value = SEARCH03;
				sheetObj.DoSearch("PRACTICE_3GS.do", FormQueryString(formObj) );
			}
			break;
		case IBDOWNEXCEL:
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}
			else{
				sheetObjects[0].Down2ExcelBuffer(true);
				sheetObjects[0].Down2Excel({FileName:'excel2',SheetName:'sheet1',DownCols: makeHiddenSkipCol(sheetObjects[0]), SheetDesign:1, Merge:1});
				sheetObjects[1].Down2Excel({FileName:'excel2',SheetName:'sheet2',DownCols: makeHiddenSkipCol(sheetObjects[1]), SheetDesign:1, Merge:1});
				sheetObjects[0].Down2ExcelBuffer(false);
			}
			break;
	}
}

function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
	ComResizeSheet(sheetObjects[1]);
}

//reset search option and sheet
function resetForm(formObj){
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	formObj.reset();
	initPeriod();
}

//handling event double click when double click one row in sheet1
function sheet1_OnDblClick(sheetObj, Row, Col, CellX, CellY, CellW, CellH){
	var formObj = document.form;
	if (sheetObj.GetCellValue(Row,"jo_crr_cd") == ''){
		return;
	}
	else{
		selectRowToOtherSheet(sheetObj, sheetObjects[1],Row,0,2);
		tab1.SetSelectedIndex(1);
	}
}

//handling event double click when double click one row in sheet2
function sheet2_OnDblClick(sheetObj, Row, Col, CellX, CellY, CellW, CellH){
	var formObj = document.form;
	if (sheetObj.GetCellValue(Row,"jo_crr_cd") == ''){
		return;
	}
	else{
		selectRowToOtherSheet(sheetObj, sheetObjects[0],Row,2,0);
		tab1.SetSelectedIndex(0);
	}
}

//Handling event after searching sheet1
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
	var totalRow = sheetObj.RowCount();
	for (var i = 1; i <= totalRow+1; i++){
		if (sheetObj.GetCellValue(i, "jo_crr_cd") == ''){
			if (sheetObj.GetCellValue(i, "inv_no") !== ''){
				sheetObj.SetRowFontColor(i,"#ff3300");
				sheetObj.SetRangeFontBold(i,1,i,10,1);
				sheetObj.SetCellValue(i,"inv_no","");
			}
			else if (sheetObj.GetCellValue(i,"inv_no") == ''){
				sheetObj.SetRowBackColor(i,"#ff9933");
				sheetObj.SetRangeFontBold(i,1,i,10,1);
			}

		}
	}
}

//Handling event after searching sheet2
function sheet2_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
	var totalRow = sheetObj.RowCount();
	for (var i = 1; i <= totalRow+1; i++){
		if (sheetObj.GetCellValue(i, "jo_crr_cd") == ''){
			if (sheetObj.GetCellValue(i, "inv_no") !== ''){
				sheetObj.SetRowFontColor(i,"#ff3300");
				sheetObj.SetRangeFontBold(i,1,i,12,1);
				sheetObj.SetCellValue(i,"inv_no","");
			}
			else if (sheetObj.GetCellValue(i,"inv_no") == ''){
				sheetObj.SetRowBackColor(i,"#ff9933");
				sheetObj.SetRangeFontBold(i,1,i,12,1);
			}

		}
	}
}

//Handling validate
function validateForm(sheetObj, formObj, sAction) {
	sheetObj.ShowDebugMsg(false);
}

function PRACTICE_3() {
	this.processButtonClick		= tprocessButtonClick;
	this.setSheetObject 		= setSheetObject;
	this.loadPage 				= loadPage;
	this.initSheet 				= initSheet;
	this.initControl            = initControl;
	this.doActionIBSheet 		= doActionIBSheet;
	this.setTabObject 			= setTabObject;
	this.validateForm 			= validateForm;
}