/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : tabObjects.js
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
var tabObjects=new Array();
var tabCnt=0 ;
var beforetab=1;
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