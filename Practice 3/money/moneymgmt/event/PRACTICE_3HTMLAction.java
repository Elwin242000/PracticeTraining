/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : PRACTICE_3HTMLAction.java
 *@FileTitle : Money Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.13
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.04.22
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice3.moneymgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

/**
 * HTTP Parser<br>
 * - Parsing the Value of the HTML DOM object sent to the server through the com.clt.apps.opus.esm.clv.practice3 screen as a Java variable<br>
 * - Convert the parsing information into Event, put it in the request and request execution with Practice3SC<br>
 * - Set EventResponse to request to send execution result from Practice3SC to View (JSP)<br>
 * @author Truong Vu
 * @see See Practice3Event
 * @since J2EE 1.6
 */
public class PRACTICE_3HTMLAction extends HTMLActionSupport {
	private static final long serialVersionUID = 1L;
	
	/**
	 * PRACTICE_1HTMLAction function constructor
	 */
	public PRACTICE_3HTMLAction(){}
	
	/**
	 * Parsing the value of HTML DOM object as a Java variable<br>
	 * Parsing the information of HttpRequest as Practice1Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event An object that implements the Event interface.
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		Practice3Event event = new Practice3Event();
		
		if(command.isCommand(FormCommand.MULTI)) {
			event.setSummaryVOS((SummaryVO[])getVOs(request, SummaryVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {		
//			event.setSummaryVO((SummaryVO)getVO(request, SummaryVO.class,""));
			SummaryVO summaryVO = new SummaryVO();
			summaryVO.setAcctYrmonFr(JSPUtil.getParameter(request, "acct_yrmon_from", ""));
			summaryVO.setAcctYrmonTo(JSPUtil.getParameter(request, "acct_yrmon_to", ""));
			summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			summaryVO.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			summaryVO.setTrdCd(JSPUtil.getParameter(request, "s_trade_cd", ""));
			event.setSummaryVO(summaryVO);
		}
		else if(command.isCommand(FormCommand.SEARCH01)){
			SummaryVO laneVO = new SummaryVO();
			laneVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			event.setSummaryVO(laneVO);
		}
		else if (command.isCommand(FormCommand.SEARCH02)){
			SummarySearchTradeVO tradeVO = new SummarySearchTradeVO();
			tradeVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			tradeVO.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			event.setTradeVO(tradeVO);
		}
		else if (command.isCommand(FormCommand.SEARCH03)){
			DetailVO detailVO = new DetailVO();
			detailVO.setAcctYrmonFr(JSPUtil.getParameter(request, "acct_yrmon_from", ""));
			detailVO.setAcctYrmonTo(JSPUtil.getParameter(request, "acct_yrmon_to", ""));
			detailVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", ""));
			detailVO.setRlaneCd(JSPUtil.getParameter(request, "s_rlane_cd", ""));
			detailVO.setTrdCd(JSPUtil.getParameter(request, "s_trade_cd", ""));
			event.setDetailVO(detailVO);
		}
		return  event;
	}
	
	/**
	 * Storing the business scenario execution result value in the attribute of HttpRequest<br>
	 * Setting the ResultSet that transmits the execution result from ServiceCommand to View (JSP) in the request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse object that implements the interface
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}
	
	/**
	 * Saving HttpRequest parsing result value in HttpRequest attribute<br>
	 * HttpRequest parsing result value set in request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event object that implements the interface
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}
