<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page
	import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page
	import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page
	import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page
	import="com.clt.apps.opus.esm.clv.practice4.carriermgmt.event.Practice4Event"%>
<%@ page import="org.apache.log4j.Logger"%>

<%
	Practice4Event event = null; //PDTO(Data Transfer Object including Parameters)
	Exception serverException = null; //서버에서 발생한 에러
	String strErrMsg = ""; //에러메세지
	int rowCount = 0; //DB ResultSet 리스트의 건수

	String successFlag = "";
	String codeList = "";
	String pageRows = "100";

	String carrier = "";
	String lane = "";

	try {
		event = (Practice4Event) request.getAttribute("Event");
		serverException = (Exception) request
				.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);

		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException)
					.loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request
				.getAttribute("EventResponse");
		carrier = eventResponse.getETCData("carriers");
		lane = eventResponse.getETCData("lanes");

	} catch (Exception e) {
		out.println(e.toString());
	}
%>
<script language="javascript">
		var carrierCombo = "ALL|<%=carrier%>";
		var laneCombo = "|<%=lane%>";
		function setupPage(){
			var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
	}
</script>
<form name="form">
	<input type="hidden" name="f_cmd"> 
	<input type="hidden" name="pagerows">

	<div class="page_title_area clear">
		<h2 class="page_title">
			<button type="button">
				<span id="title">Carrier Management</span>
			</button>
		</h2>
		<div class="opus_design_btn">
			<button type="button" class="btn_accent" name="btn_Retrieve" id="btn_Retrieve">Retrieve</button><!--
			--><button type="button" class="btn_normal" name="btn_New" id="btn_New">New</button><!--
			--><button type="button" class="btn_normal" name="btn_Save" id="btn_Save">Save</button><!--
			--><button type="button" class="btn_normal" name="btn_DownExcel" id="btn_DownExcel">Down Excel</button>
		</div>
		<div class="location">
			<span id="navigation"></span>
		</div>
	</div>

	<div class="wrap_search">
		<div class="opus_design_inquiry wfit">
			<table>
				<tbody>
					<tr>
						<th width="50">Carrier</th>
						<td width="120">
							<script type="text/javascript">ComComboObject('s_carrier', 1, 120, 1, 0, 0);</script>
						</td>

						<th width="50">Vendor</th>
						<td width="120">
							<input type="text" style="width: 120px;" class="input" value="" maxlength="6" dataformat="num" name="s_vndr_seq" id="s_vndr_seq">
						</td>

						<th width="100">Create Date</th>
						<td>
							<input type="text" style="width: 120px; text-align: center;" placeholder="YYYY-MM-DD" name="s_cre_dt_fm" id="s_cre_dt_fm"><!--  
							--><button type="button" class="calendar ir" name="btn_calendar_dt_fr" id="btn_calendar_dt_fr"></button> ~ 
							<input type="text" style="width: 120px; text-align: center;" placeholder="YYYY-MM-DD" name="s_cre_dt_to" id="s_cre_dt_to" onchange="excuteCheck(this.value)"><!-- 
							--><button type="button" class="calendar ir" name="btn_calendar_dt_to" id="btn_calendar_dt_to"></button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="wrap_result">
		<div class="opus_design_grid">
			<div class="opus_design_btn">
				<button type="button" class="btn_normal" name="btn_Delete" id="btn_Delete">Row Delete</button><!-- 
				--><button type="button" class="btn_normal" name="btn_Add" id="btn_Add">RowAdd</button>
			</div>
			<script language="javascript">
				ComSheetObject('sheet1');
			</script>
		</div>
	</div>
</form>