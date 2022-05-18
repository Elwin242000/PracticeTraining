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
			var comboItems = carrierCombo.split("|");
			addComboItem(comboObj, comboItems);
	}
}

//Add combo item into Combo box
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

//Handling event when check click combo box
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