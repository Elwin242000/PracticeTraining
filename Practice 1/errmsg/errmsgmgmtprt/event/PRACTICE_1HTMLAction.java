/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_1HTMLAction.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.07
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.vo.ErrMsgVO;

/**
 * HTTP Parser<br>
 * - Parsing the Value of the HTML DOM object sent to the server through the com.clt.apps.opus.esm.clv.practice1 screen as a Java variable<br>
 * - Convert the parsing information into Event, put it in the request and request execution with Practice1SC<br>
 * - Set EventResponse to request to send execution result from Practice1SC to View (JSP)<br>
 * @author Truong Vu
 * @see See Practice1Event
 * @since J2EE 1.6
 */

public class PRACTICE_1HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * PRACTICE_1HTMLAction function constructor
	 */
	public PRACTICE_1HTMLAction() {}

	/**
	 * Parsing the value of HTML DOM object as a Java variable<br>
	 * Parsing the information of HttpRequest as Practice1Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event An object that implements the Event interface.
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		Practice1Event event = new Practice1Event();
		
		if(command.isCommand(FormCommand.MULTI)) {
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {		
			ErrMsgVO errMsgVO  = new ErrMsgVO();
			errMsgVO.setErrMsgCd(JSPUtil.getParameter(request, "s_err_msg_cd", ""));
			errMsgVO.setErrMsg(JSPUtil.getParameter(request, "s_err_msg", ""));
			event.setErrMsgVO(errMsgVO);
		}
		return  event;
	}

	/**
	 * Storing the business scenario execution result value in the attribute of HttpRequest<br>
	 * Setting the ResultSet that transmits the execution result from ServiceCommand to View (JSP) in the request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * Saving HttpRequest parsing result value in HttpRequest attribute<br>
	 * HttpRequest parsing result value set in request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}