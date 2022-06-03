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
var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";
//Define an event handler that receives and handles button click events
document.onclick=processButtonClick;
document.onkeydown=logKey;

/**
 * function to set button enter
 */
function logKey(key){
	var sheetObject1 = sheetObjects[0];
	var formObj = document.form;
	
	if(key.code == 'Enter'){
		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
	}
}

/**
 * function for branching to the corresponding logic when a button on the screen is pressed.
 */ 
function processButtonClick() {
	var sheetObject1=sheetObjects[0];
	var formObject=document.form;
	try {
		var srcName=ComGetEvent("name");
		switch(srcName) {
			case "btn_calendar_dt_fr":
				var calendar = new ComCalendar();
				calendar.select(formObject.s_cre_dt_fm, "yyyy-MM-dd");
				break;
			case "btn_calendar_dt_to":
				var calendar = new ComCalendar();
				calendar.select(formObject.s_cre_dt_to, "yyyy-MM-dd");
				break;
			case "btn_Retrieve":
				if (!checkCondition()){
					document.form.s_cre_dt_to.value = '';
					return;
				}
				doActionIBSheet(sheetObject1,formObject,IBSEARCH);
				break;
			case "btn_New":
				doActionIBSheet(sheetObject1,formObject,IBRESET);
				break;
			case "btn_Save":
				doActionIBSheet(sheetObject1,formObject,IBSAVE);
				break;
			case "btn_DownExcel":
				if (sheetObject1.RowCount() < 1) {
					ComShowCodeMessage("COM132501");
				} else {
					doActionIBSheet(sheetObject1,formObject,IBDOWNEXCEL);
				}
				break;
			case "btn_Add":
				doActionIBSheet(sheetObject1,formObject,IBINSERT);
				break;
			case "btn_Delete":
				doActionIBSheet(sheetObject1,formObject,IBDELETE);
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
/**
 * functions that calls a common function that sets the default settings of the sheet.
 * It is the first called area when fire jsp onload event
 */
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
	
	initControl();
}

/**
 * function to put sheet objects in global variable "sheetObjects"
 * 
 * @param sheet_obj  IBSheet Object
 */
function setSheetObject(sheet_obj){
	sheetObjects[sheetCnt++] = sheet_obj;
}

/**
 * Functions that define the basic properties of the form on the screen.<br>
 * It is the place defining events on form.
 */
function initControl(){
	document.getElementById('s_vndr_seq').addEventListener('keypress', function() {ComKeyOnlyNumber(this);});
}


/**
 * functions that define the basic properties of the sheet on the screen
 * 
 * @param sheetObj  IBSheet Object
 * @param sheetNo   Number of IBSheet Object
 */
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
					{Type:"Status",   Hidden:1, Width:50,  Align:"Center",  SaveName:"ibflag"}, 
					{Type:"DelCheck", Hidden:0, Width:50,  Align:"Center",  SaveName:"del_chk"}, 
					{Type:"Popup",    Hidden:0, Width:100, Align:"Center",  SaveName:"jo_crr_cd",   KeyField:1, UpdateEdit:0, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1, EditLen: 3},
					{Type:"Combo",    Hidden:0, Width:100, Align:"Center",  SaveName:"rlane_cd",    KeyField:1, UpdateEdit:0, InsertEdit:1, ComboCode: laneCombo, ComboText: laneCombo},
					{Type:"Popup",    Hidden:0, Width:100, Align:"Center",  SaveName:"vndr_seq",    KeyField:1, UpdateEdit:1, InsertEdit:1},
					{Type:"Popup",    Hidden:0, Width:50,  Align:"Center",  SaveName:"cust_cnt_cd", KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1, EditLen: 2}, 
					{Type:"Popup",    Hidden:0, Width:100, Align:"Center",  SaveName:"cust_seq",    KeyField:1, UpdateEdit:1, InsertEdit:1, AcceptKeys : "N", EditLen: 6}, 
					{Type:"Popup",    Hidden:0, Width:100, Align:"Center",  SaveName:"trd_cd",      KeyField:0, UpdateEdit:1, InsertEdit:1, AcceptKeys : "E", InputCaseSensitive : 1, EditLen: 3},
					{Type:"Combo",    Hidden:0, Width:70,  Align:"Center",  SaveName:"delt_flg",    KeyField:0, UpdateEdit:1, InsertEdit:1, ComboCode:"N|Y",  ComboText:"N|Y"}, 
					{Type:"Text",     Hidden:0, Width:200, Align:"Center",  SaveName:"cre_dt",      KeyField:0, UpdateEdit:0, InsertEdit:0}, 
					{Type:"Text",     Hidden:0, Width:200, Align:"Left",    SaveName:"cre_usr_id",  KeyField:0, UpdateEdit:0, InsertEdit:0}, 
					{Type:"Text",     Hidden:0, Width:200, Align:"Center",  SaveName:"upd_dt",      KeyField:0, UpdateEdit:0, InsertEdit:0}, 
					{Type:"Text",     Hidden:0, Width:200, Align:"Left",    SaveName:"upd_usr_id",  KeyField:0, UpdateEdit:0, InsertEdit:0}
				];
				InitColumns(cols);
				SetEditable(1);
				SetWaitImageVisible(0);
				resizeSheet();
			}
			break;
	}
}

/**
 * Function to set the height according to the display
 */
function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
}

/**
 * functions that define transaction logic between UI and server
 * 
 * @param sheetObj  IBSheet Object
 * @param formObj   Form Object
 * @param sAction   Action code
 */ 
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
		case IBRESET:
			formObj.reset();
			sheetObj.RemoveAll();
			s_carrier.SetSelectIndex(0);
			break;
	}
}

/**
 * Function to put combo objects in global variable "comboObjects" 
 * 
 * @param combo_obj  IBMultiCombo Object
 */
function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}

function checkCondition(){
	var formObj = document.form;
	var fromDate = formObj.s_cre_dt_fm.value.replaceStr("-","");
	var toDate   = formObj.s_cre_dt_to.value.replaceStr("-","");
	console.log(ComGetDaysBetween(fromDate, toDate));
	if (ComGetDaysBetween(fromDate, toDate) <= 0)
		return false;
	return true;
}

/**
 * Functions that define the basic properties of the combo on the screen 
 * 
 * @param comboObj  IBMultiCombo Object
 * @param comboNO   Number of IBMultiCombo Object
 */
function initCombo(comboObj, comboNo) {
	var formObj = document.form
	switch (comboNo) {
		case 1:
			with (comboObj) {
				SetMultiSelect(1);
				SetDropHeight(200);
				ValidChar(2,1);
			}
			var comboItems = carrierCombo.split("|");
			addComboItem(comboObj, comboItems);
	}
}

//
/**
 * Function to add combo item into Combo box
 * 
 * @param comboObj    IBMultiCombo Object
 * @param comboItems  Item of IBMultiCombo Object
 */
function addComboItem(comboObj, comboItems) {
	for (var i=0 ; i < comboItems.length ; i++) {
		var comboItem=comboItems[i].split(",");
		if(comboItem.length == 1){
			comboObj.InsertItem(i, comboItem[0], comboItem[0]);
		}else{
			comboObj.InsertItem(i, comboItem[0] + "|" + comboItem[1], comboItem[1]);
		}
	}	
}

/**
 * Handling event when the check box is clicked.
 * If multiple selection is used. 
 * 
 * @param Index
 * @param Code
 * @param Checked 
 */
function s_carrier_OnCheckClick(Index, Code, Checked) {
	var count = s_carrier.GetItemCount();
	var checkSelectCount = 0;
	
	if (Code == 0){
		var bChk = s_carrier.GetItemCheck(Code);
		if(bChk){
			for(var i=1 ; i < count ; i++) {
				s_carrier.SetItemCheck(i,false);
			}
		}
	}else {
		var bChk=s_carrier.GetItemCheck(Code);
		if (bChk) {
			s_carrier.SetItemCheck(0,false);
		}
	}

	for (var i = 0; i < count; i++){
		if (s_carrier.GetItemCheck(i))
			checkSelectCount += 1;
	}
	if(checkSelectCount == 0) {
		s_carrier.SetItemCheck(0,true,false);
	}
}


/**
 * Handling event after search.
 * 
 * @param sheetObj
 * @param Code
 * @param Msg
 * @param StCode
 * @param StMsg
 */
function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	ComOpenWait(false);
}

/**
 * Handling event before save.
 */ 
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

/**
 * Handling event after save.
 * 
 * @param Code
 * @param Msg
 */
function sheet1_OnSaveEnd(sheetObj, Code, Msg){
	if (Code >= 0){
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
		var dupData = sheetObj.FindStatusRow("I");
		var aRows = dupData.split(';');
		for (var i = 0; i < aRows.length; i++){
			if (Msg.includes(sheetObj.GetCellValue(aRows[i],"jo_crr_cd")) &&
					Msg.includes(sheetObj.GetCellValue(aRows[i],"rlane_cd"))){
				sheetObj.SetRowBackColor(aRows[i],"#FF6666");
			}
			console.log(aRows);
		}
	}
	
}

/**
 * Handling event when sheet change
 * 
 * @param sheetObj
 * @param Row
 * @param Col
 * @param Value
 * @param OldValue
 * @param RaiseFlag
 */
function sheet1_OnChange(sheetObj, Row, Col, Value, OldValue, RaiseFlag){
	var formObj=document.form ;
	var colName=sheetObj.ColSaveName(Col);
	
	if(colName == "jo_crr_cd" || colName == "rlane_cd"){
		if(sheetObj.GetCellValue(Row,"jo_crr_cd") != "" && sheetObj.GetCellValue(Row,"rlane_cd") != ""){
			//check on UI
//			var headerRowNum = sheetObj.HeaderRows();
//			for(var i = headerRowNum; i <= sheetObj.RowCount(); i++){
//				if(i != Row && sheetObj.GetCellValue(Row,"jo_crr_cd") == sheetObj.GetCellValue(i,"jo_crr_cd")
//						&& sheetObj.GetCellValue(Row,"rlane_cd") == sheetObj.GetCellValue(i,"rlane_cd")){
//					ComShowCodeMessage("COM12115","The Carrier and Rev.Lane");
//					sheetObj.SetCellValue(Row, Col,OldValue,0);
//					sheetObj.SelectCell(Row, Col);
//					return;
//				}
//			}
			if(sheetObj.ColValueDup("jo_crr_cd|rlane_cd") > -1){
				ComShowCodeMessage("COM12115", "The Carrier and Rev.Lane");
				sheetObj.SetCellValue(Row, Col,OldValue,0);
				sheetObj.SelectCell(Row, Col);
				return;
			}		
			//check on Service side
			formObj.f_cmd.value	= COMMAND01;
			var sParam = FormQueryString(formObj) + "&jo_crr_cd=" + sheetObj.GetCellValue(Row,"jo_crr_cd") + "&rlane_cd=" + sheetObj.GetCellValue(Row,"rlane_cd");
			var sXml   = sheetObj.GetSearchData("PRACTICE_4GS.do", sParam, {sync:1});	
			var flag   = ComGetEtcData(sXml, "ISEXIST");
			if(flag == 'Y'){
				ComShowCodeMessage("COM12115","The Carrier and Rev.Lane");
				sheetObj.SetCellValue(Row, Col,OldValue,0);
				sheetObj.SelectCell(Row, Col);
			}
		}
	}
	
}

/**
 * Handling event when click popup.
 * 
 * @param sheetObj
 * @param Row
 * @param Col
 */
function sheet1_OnPopupClick(sheetObj,Row, Col){
	var colName = sheetObj.ColSaveName(Col);
	
	switch(colName){
		case "cust_cnt_cd":
		case "cust_seq":
			ComOpenPopup('/opuscntr/CustomerPU.do', 800, 500, 'setCustCd', '1,0,1,1,1,1', true);
			break;
		case "vndr_seq":
			ComOpenPopup('/opuscntr/COM_COM_0007.do', 900, 520, 'setVndrCd', '1,0,1', true, false);
			break;
		case "trd_cd":
			ComOpenPopup('/opuscntr/COM_COM_0012.do', 800, 500, 'setTrdCd', '1,0,0,1,1,1,1,1', true);
			break;
		case "jo_crr_cd":
			// This function open the pop-up
				// url: the url of the popup to be called
				// width: the width of the popup
				// height: the height of the popup
				// func: func return data to parent window
				// display: whether column of the grid in popup is hidden (1: visible, 0: invisible) 
				// bModal: whether the popup is modal (default: false)
			ComOpenPopup('/opuscntr/COM_ENS_0N1.do', 800, 500, 'setCrrCd', '1,0,0,1,1,1,1,1', true);
			break;
	}
}

/**
 * Set value to customer code from pop-up
 * 
 * @param aryPopupData
 */
function setCustCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_cnt_cd", aryPopupData[0][2]);
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "cust_seq",    aryPopupData[0][3]);
}

/**
 * Set value to vendor code from pop-up.
 * 
 * @param aryPopupData
 */
function setVndrCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "vndr_seq", aryPopupData[0][2]);
	console.log(sheetObjects[0].GetSelectRow());
	console.log(aryPopupData[0][2]);
}
	
/**
 * Set value to trade from pop-up
 * @param aryPopupData
 */
function setTrdCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "trd_cd", aryPopupData[0][3]);
}

/**
 * Set value to partner from pop-up
 * 
 * @param aryPopupData
 */
function setCrrCd(aryPopupData){
	sheetObjects[0].SetCellValue(sheetObjects[0].GetSelectRow(), "jo_crr_cd", aryPopupData[0][3]);
}