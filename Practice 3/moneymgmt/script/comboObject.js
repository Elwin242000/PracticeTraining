/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : comboObject.js
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.22  
* 1.0 Creation
=========================================================*/

// common global variable
var comboObjects = new Array();
var comboCnt = 0;
var comboValues = "";

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

function s_rlane_cd_OnChange(){
	s_trade_cd.SetEnable(true);
	getTradeComboData();

}