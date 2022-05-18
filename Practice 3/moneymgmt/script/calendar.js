/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : calendar.js
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.22  
* 1.0 Creation
=========================================================*/

function initControl(){
	var formObject = document.form;
	initPeriod();
}

//{initSheet} functions that define the basic properties of the date on the screen
function initPeriod(){
	var formObj = document.form;
	var ymTo = ComGetNowInfo("ym","-");
	formObj.acct_yrmon_to.value = ymTo;
	var ymFrom = ComGetDateAdd(formObj.acct_yrmon_to.value + "-01","M",-15);
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
	if (!checkCondition()){
		initPeriod();
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
	if (ComGetDaysBetween(fromDate, toDate) > 3)
		return false;
	return true;
}