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
var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";
var tabObjects=new Array();
var tabCnt=0 ;
var beforetab=1;
var checkOK = false;
//Define an event handler that receives and handles button click events
document.onclick=processButtonClick;
document.onkeydown=logKey;


function logKey(key){
	var sheetObject1 = sheetObjects[0];
	var sheetObject2 = sheetObjects[1];
	var formObj = document.form;
	
	if(key.code == 'Enter'){
		if (!checkOverThreeMonth()){
			if (!checkOK){
				if (confirm("Year Month over 3 months, do you really want to get data?")){
					checkOK = true;
				
	    		}
	    		else{
	    			return;
	    		}
			}
    	}
		doActionIBSheet(sheetObject1, formObj, IBSEARCH);
		doActionIBSheet(sheetObject2, formObj, IBSEARCH);
	}
}
//{processButtonClick} function for branching to the corresponding logic when a button on the screen is pressed
function processButtonClick() {
	var sheetObject1=sheetObjects[0];
	var sheetObject2=sheetObjects[1];
    var formObject=document.form;
    try {
    	var srcName=ComGetEvent("name");
        switch(srcName) {
	        case "btn_Retrieve":
	        	if (!checkOverThreeMonth()){
	    			if (!checkOK){
	    				if (confirm("Year Month over 3 months, do you really want to get data?")){
	    					checkOK = true;
	    	    		}
	    	    		else{
	    	    			return;
	    	    		}
	    			}
	        	}
	    		doActionIBSheet(sheetObject1, formObject, IBSEARCH);
	    		doActionIBSheet(sheetObject2, formObject, IBSEARCH);
	        	break;
	        case "btn_datefrom_down":
	        	addMonth(formObject.acct_yrmon_from, -1);
	        	yearmonth_onchange();
	        	break;
	        case "btn_datefrom_up":
	        	addMonth(formObject.acct_yrmon_from, 1);
	        	yearmonth_onchange();
	        	excuteCheck();
	        	break;
	        case "btn_dateto_down":
	        	addMonth(formObject.acct_yrmon_to, -1);
	        	yearmonth_onchange();
	        	excuteCheck();
	        	break;
	        case "btn_dateto_up":
	        	addMonth(formObject.acct_yrmon_to, 1);
	        	yearmonth_onchange();
	        	break;
	        case "btn_New":
	        	doActionIBSheet(sheetObject1,formObject,IBRESET);
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
	initCalender();
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
		case IBRESET:
			resetForm(formObj);
			break;
	}
}

function resizeSheet() {
	ComResizeSheet(sheetObjects[0]);
	ComResizeSheet(sheetObjects[1]);
}

function initCalender(){
	var formObject = document.form;
	initPeriod();
}

//{initSheet} functions that define the basic properties of the date on the screen
function initPeriod(){
	var formObj = document.form;
	var ymTo = ComGetNowInfo("ym","-");
	formObj.acct_yrmon_to.value = ymTo;
	var ymFrom = ComGetDateAdd(formObj.acct_yrmon_to.value + "-01","M",-1);
	formObj.acct_yrmon_from.value = GetDateFormat(ymFrom,"ym");
}
 //Get format date
function GetDateFormat(obj, sFormat){
	var sVal = String(getArgValue(obj));
	sVal = sVal.replace(/\/|\-|\.|\:|\ /g,"");
	if (ComIsEmpty(sVal)) return "";
	
	var retValue = "";
	switch(sFormat){
		case "ym":
			retValue = sVal.substring(0,6);
			break;
	}
	retValue = ComGetMaskedValue(retValue,sFormat);
	return retValue;
}

//Add month
function addMonth(obj, month){
	if (obj.value != ""){
			obj.value = ComGetDateAdd(obj.value + "-01", "M", month).substr(0,7);
	}
}

//Check from date is bigger than to date
function checkCondition(){
	var formObj = document.form;
	var fromDate = formObj.acct_yrmon_from.value.replaceStr("-","") + "01";
	var toDate   = formObj.acct_yrmon_to.value.replaceStr("-","") + "01";
	if (ComGetDaysBetween(fromDate, toDate) <= 0)
		return false;
	return true;
}

//Excute check condition 
function excuteCheck(){
	formObj = document.form;
	if (!checkCondition()){
		ComShowMessage("Date From is bigger than Date To.")
		var ymFrom = ComGetDateAdd(formObj.acct_yrmon_to.value + "-01","M",-1);
		formObj.acct_yrmon_from.value = GetDateFormat(ymFrom,"ym");
	}
}

// Handling event after change yearmonth
function yearmonth_onchange(){
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
}

//Check between from date and to date over three month
function checkOverThreeMonth(){
	var formObj = document.form;
	var fromDate = formObj.acct_yrmon_from.value.replaceStr("-","") + "01";
	var toDate   = formObj.acct_yrmon_to.value.replaceStr("-","") + "01";
	console.log(ComGetDaysBetween(fromDate, toDate));
	if (ComGetDaysBetween(fromDate, toDate) > 88)
		return false;
	return true;
}

//{setComboObject} to put combo objects in global variable "comboObjects"
function setComboObject(combo_obj) {
	comboObjects[comboCnt++] = combo_obj;
}

//{initCombo} functions that define the basic properties of the combo on the screen
function initCombo(comboObj, comboNo) {
	var formObj = document.form
	switch (comboNo) {
		case 1:
			with (comboObj) {
				SetMultiSelect(1);
		        SetDropHeight(200);
		        ValidChar(2,1);
			}
			var comboItems = partnerCombo.split("|");
			addComboItem(comboObj, comboItems);
	}
}

// Add combo item into Combo box
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

//Generate data combo
function generDataCombo(comboObj, dataStr){
	if (typeof dataStr !== 'undefined'){
		if (dataStr.indexOf("|") != -1)
		{
			var data = dataStr.split("|");
			for (var i = 0; i < data.length; i++){
				comboObj.InsertItem(-1, data[i], data[i]);
			}
		}
		if (dataStr.length > 0 && dataStr.indexOf("|") == -1){
			comboObj.InsertItem(-1, dataStr, dataStr);
		}
	}
}

//Get LameCombo data from server
function getLaneComboData(){
	s_rlane_cd.RemoveAll();
 	s_trade_cd.RemoveAll();
	document.form.f_cmd.value = SEARCH01;
	var xml = sheetObjects[0].GetSearchData("PRACTICE_3GS.do", FormQueryString(document.form));
	lanes = ComGetEtcData(xml,"lanes");
	generDataCombo(comboObjects[1], lanes);
	if(s_rlane_cd.GetItemCount() > 0){
		s_rlane_cd.SetSelectIndex(0,1);
		s_rlane_cd.SetEnable(true);
	}
	else
		s_rlane_cd.SetEnable(false);
}

//Get TradeCombo data from server
function getTradeComboData(){
	s_trade_cd.RemoveAll();
	document.form.f_cmd.value = SEARCH02;
	var xml = sheetObjects[0].GetSearchData("PRACTICE_3GS.do", FormQueryString(document.form));
	trades = ComGetEtcData(xml,"trades");
	generDataCombo(comboObjects[2], trades);
	if(s_trade_cd.GetItemCount() > 0){
		s_trade_cd.SetSelectIndex(0,1);
		s_trade_cd.SetEnable(true);
	}
	else
		s_trade_cd.SetEnable(false);
}

//Handling event when check click combo box
function s_jo_crr_cd_OnCheckClick(Index, Code, Checked) {
	var count = s_jo_crr_cd.GetItemCount();
	var checkSelectCount = 0;
	
	if (Code == 0){
		var bChk = s_jo_crr_cd.GetItemCheck(Code);
        if(bChk){
            for(var i=1 ; i < count ; i++) {
            	s_jo_crr_cd.SetItemCheck(i,false);
            }
            s_rlane_cd.RemoveAll();
         	s_trade_cd.RemoveAll();
        	s_rlane_cd.SetEnable(false);
        	s_trade_cd.SetEnable(false);
        }
	}
	else {
        var bChk=s_jo_crr_cd.GetItemCheck(Code);
        if (bChk) {
        	s_jo_crr_cd.SetItemCheck(0,false);
        	s_rlane_cd.SetEnable(true);
        	getLaneComboData();
        }
    }

	for (var i = 0; i < count; i++){
		if (s_jo_crr_cd.GetItemCheck(i)){
			checkSelectCount += 1;
			getLaneComboData();
		}	
	}
	 if(checkSelectCount == 0) {
		s_jo_crr_cd.SetItemCheck(0,true,false);
		s_rlane_cd.RemoveAll();
     	s_trade_cd.RemoveAll();
     	s_rlane_cd.SetEnable(false);
     	s_trade_cd.SetEnable(false);
	 }
}

//Handling event when lane combo change
function s_rlane_cd_OnChange(){
	s_trade_cd.SetEnable(true);
	getTradeComboData();

}

//{setTabObject} to put combo objects in global variable "tabObjects"
function setTabObject(tab_obj){
	tabObjects[tabCnt++]=tab_obj;
}
//{initTab} functions that define the basic properties of the tab on the screen
function initTab(tabObj , tabNo) {
	switch(tabNo) {
	case 1:
		with (tabObj) {
			var cnt=0 ;
				InsertItem( "Summary" , "");
				InsertItem( "Detail" , "");
		}
		break;
	}
}
//handling event when have change
function tab1_OnChange(tabObj, nItem)
{
	var objs=document.all.item("tabLayer");
	objs[nItem].style.display="Inline";		
	//--------------- this is important! --------------------------//
	for(var i = 0; i<objs.length; i++){
		  if(i != nItem){
		   objs[i].style.display="none";
		   objs[beforetab].style.zIndex=objs[nItem].style.zIndex - 1 ;
		  }
		}
	//------------------------------------------------------//
	beforetab=nItem;
    resizeSheet();
} 

//Find position info 
function selectRowToOtherSheet(sheetFrom, sheetTo, Row, sFr, sTo){
	var indexSelected = -1;
	for (var i = 1; i < sheetTo.SearchRows() + 1; i++){
		var indexPartner = sheetTo.FindText(1,sheetFrom.GetCellValue(Row,1),i);
		if (indexPartner != -1){
			i = indexPartner;
			indexLane     = sheetTo.FindText(2       ,sheetFrom.GetCellValue(Row,2) ,i);
			indexInvoice  = sheetTo.FindText(3       ,sheetFrom.GetCellValue(Row,3) ,i);
			indexSlip     = sheetTo.FindText(4       ,sheetFrom.GetCellValue(Row,4) ,i);
			indexApproved = sheetTo.FindText(5	     ,sheetFrom.GetCellValue(Row,5) ,i);
			indexCurr     = sheetTo.FindText(6  + sTo,sheetFrom.GetCellValue(Row,sFr + 6) ,i);
			indexRevenue  = sheetTo.FindText(7  + sTo,sheetFrom.GetCellValue(Row,sFr + 7) ,i);
//			indexExpense  = sheetTo.FindText(8  + sTo,sheetFrom.GetCellValue(Row,8) ,i);
			indexCode     = sheetTo.FindText(9  + sTo,sheetFrom.GetCellValue(Row,sFr + 9) ,i);
			indexName     = sheetTo.FindText(10 + sTo,sheetFrom.GetCellValue(Row,sFr + 10),i);
			if (indexLane == indexPartner && 
					indexInvoice == indexLane && 
					indexSlip == indexInvoice && 
					indexApproved == indexSlip && 
					indexCurr == indexApproved && 
					indexRevenue == indexCurr && 
					indexCode == indexRevenue && 
					indexName == indexCode){
				indexSelected = i;
				break;
			}
		}
	}
	console.log(indexSelected);
	sheetTo.SetSelectRow(indexSelected);
}

//reset search option and sheet
function resetForm(formObj){
	sheetObjects[0].RemoveAll();
	sheetObjects[1].RemoveAll();
	formObj.reset();
	s_jo_crr_cd.SetSelectIndex(0);
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