/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_4.js.java
*@FileTitle : Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.16
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.06  
* 1.0 Creation
=========================================================*/
// common global variable
var sheetObjects=new Array();
var sheetCnt=0;
var flagAnnounce =  null;
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

function onlyNumberKey(event){
	var ASCIICode = (event.which) ? event.which : event.keyCode;
	if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57)){
		return false;
	}
	return true;
}

//{processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
function processButtonClick() {
	var sheetObject1=sheetObjects[0];
    var formObject=document.form;
    try {
    	var srcName=ComGetEvent("name");
    	switch(srcName) {
    		case "btn_Retrieve":
	        	doActionIBSheet(sheetObject1,formObject,IBSEARCH);
	        	break;
    		case "btn_Add":
            	doActionIBSheet(sheetObject1,formObject,IBINSERT);
            	break;
    		case "btn_Delete":
    			doActionIBSheet(sheetObject1,formObject,IBDELETE);
    			break;
    		case "btn_Save":
    			doActionIBSheet(sheetObject1,formObject,IBSAVE);
    			break;
    		case "btn_calendar_dt_fr":
    			 var calendar = new ComCalendar();
    			 calendar.select(formObject.s_cre_dt_fm, "yyyy-MM-dd");
    			 break;
    		case "btn_calendar_dt_to":
    			 var calendar = new ComCalendar();
    			 calendar.select(formObject.s_cre_dt_to, "yyyy-MM-dd");
    			 break;
    		case "btn_New":
    			resetSheet(sheetObject1,formObject);
    			break;
    		case "btn_Save":
    			break;
    		case "btn_DownExcel":
				if (sheetObject1.RowCount() < 1) {
					ComShowCodeMessage("COM132501");
				} else {
					doActionIBSheet(sheetObject1,formObject,IBDOWNEXCEL);
				}
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
	s_carrier.SetSelectIndex(0);
	
	for(i = 0; i < sheetObjects.length; i++) {
		ComConfigSheet(sheetObjects[i]);
		initSheet(sheetObjects[i], i + 1);
		ComEndConfigSheet(sheetObjects[i]);
	}
	doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
	
	s_cre_dt_fm.disabled = true;
	s_cre_dt_to.disabled = true;
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
				var HeadTitle="STS|Chk|Carrier|Rev. Lane|Vendor Code|Customer Code|Customer Code|Trade|Delete Flag|Create Date|Create User ID|Update Date|Update User ID";
				
				SetConfig( { SearchMode:2, MergeSheet:5, Page:20, FrozenCol:0, DataRowMerge:1 } );
				
				var info    = { Sort:1, ColMove:1, HeaderCheck:0, ColResize:1 };
				var headers = [ { Text:HeadTitle, Align:"Center"} ];
				InitHeaders(headers, info);
				
				var cols = [ 
		            {Type:"Status",   Hidden:1, Width:50, Align:"Center",  SaveName:"ibflag"}, 
		            {Type:"DelCheck", Hidden:0, Width:50, Align:"Center",  SaveName:"del_chk"}, 
			        {Type:"Text",  	  Hidden:0, Width:100, Align:"Center", SaveName:"jo_crr_cd",   KeyField:1, UpdateEdit:0, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1, EditLen: 3},
			        {Type:"Combo",    Hidden:0, Width:100, Align:"Center", SaveName:"rlane_cd",    KeyField:1, UpdateEdit:0, InsertEdit:1, ComboCode: laneCombo, ComboText: laneCombo},
			        {Type:"Text",     Hidden:0, Width:100, Align:"Center", SaveName:"vndr_seq",    KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys : "N", EditLen:6},
			        {Type:"Text",     Hidden:0, Width:50,  Align:"Center", SaveName:"cust_cnt_cd", KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1, EditLen: 2}, 
				    {Type:"Text",     Hidden:0, Width:100, Align:"Center", SaveName:"cust_seq",    KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys : "N", EditLen: 6}, 
				    {Type:"Text",     Hidden:0, Width:100, Align:"Center", SaveName:"trd_cd",      KeyField:0, UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1, EditLen: 3},
				    {Type:"Combo",    Hidden:0, Width:70,  Align:"Center", SaveName:"delt_flg",    KeyField:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y", ComboText:"N|Y"}, 
				    {Type:"Text",     Hidden:0, Width:200, Align:"Center", SaveName:"cre_dt",      KeyField:0, UpdateEdit:0, InsertEdit:0}, 
				    {Type:"Text",     Hidden:0, Width:200, Align:"Left",   SaveName:"cre_usr_id",  KeyField:0, UpdateEdit:0, InsertEdit:0}, 
				    {Type:"Text",     Hidden:0, Width:200, Align:"Center", SaveName:"upd_dt",      KeyField:0, UpdateEdit:0, InsertEdit:0}, 
				    {Type:"Text",     Hidden:0, Width:200, Align:"Left",   SaveName:"upd_usr_id",  KeyField:0, UpdateEdit:0, InsertEdit:0}
			    ];
		        InitColumns(cols);
		        SetEditable(1);
		        SetWaitImageVisible(0);
		        resizeSheet();
			}
			break;
	}
}

function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
}

//{doActionIBSheet} functions that define transaction logic between UI and server
function doActionIBSheet(sheetObj,formObj,sAction) {
	switch(sAction) {
		case IBSEARCH:
			ComOpenWait(true);
			formObj.f_cmd.value = SEARCH;
			sheetObj.DoSearch("PRACTICE_4GS.do", FormQueryString(formObj));
			break;
		case IBINSERT:
			sheetObj.DataInsert(-1);
			break;
		case IBDELETE:
			for( var i = sheetObj.LastRow(); i >= sheetObj.HeaderRows(); i-- ) {
				if(sheetObj.GetCellValue(i, "del_chk") == 1){
					sheetObj.SetRowHidden(i, 1);
					sheetObj.SetRowStatus(i,"D");
				}
			}
			flagAnnounce = 'Delete';
			break;
		case IBSAVE:
			formObj.f_cmd.value = MULTI;
            sheetObj.DoSave("PRACTICE_4GS.do", FormQueryString(formObj));
			break;
		case IBDOWNEXCEL:
			if(sheetObj.RowCount() < 1){
				ComShowCodeMessage("COM132501");
			}
			else{
				sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj), SheetDesign:1, Merge:1});
			}
			break;
	}
}

//Reset search option and sheet
function resetSheet(sheetObj, formObj){
	formObj.reset();
	sheetObj.RemoveAll();
	s_carrier.SetSelectIndex(0);
}

function sheet1_OnChange(Row, Col, Value, OldValue, RaiseFlag){
}

//Handling event after searching
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
}

//Handling event before save 
function sheet1_OnBeforeSave(){
	var sheetObj1 = sheetObjects[0];
	if (sheetObj1.GetCellValue(sheetObj1.GetSelectRow(), "ibflag") == 'I'){
		flagAnnounce = 'Insert';
	}
	else if (sheetObj1.GetCellValue(sheetObj1.GetSelectRow(), "ibflag") == 'U'){
		flagAnnounce = 'Update';
	}
	else if (sheetObj1.GetCellValue(sheetObj1.GetSelectRow(), "ibflag") == 'D'){
		flagAnnounce = 'Delete';
	}
}

//Handling event after save 
function sheet1_OnSaveEnd(Code, Msg){
	if (Msg >= 0){
		if (flagAnnounce == 'Insert'){
			ComShowCodeMessage('COM130102', sheetObjects[0].id);
			flagAnnounce = null;
		}
		if (flagAnnounce == 'Update'){
			ComShowCodeMessage('COM130502', sheetObjects[0].id);
			flagAnnounce = null;
		}
		if (flagAnnounce == 'Delete'){
			ComShowCodeMessage('COM130303', sheetObjects[0].id);
			flagAnnounce = null;
		}
		doActionIBSheet(sheetObjects[0],document.form,IBSEARCH);
	}
	else {
		ComShowCodeMessage('COM130103', sheetObjects[0].id);
	}
	
}
// Validate
function validateForm(sheetObj, formObj, sAction) {
	sheetObj.ShowDebugMsg(false);
}

function PRACTICE_4() {
	this.processButtonClick		= tprocessButtonClick;
	this.setSheetObject 		= setSheetObject;
	this.loadPage 				= loadPage;
	this.initSheet 				= initSheet;
	this.initControl            = initControl;
	this.doActionIBSheet 		= doActionIBSheet;
	this.setTabObject 			= setTabObject;
	this.validateForm 			= validateForm;
}