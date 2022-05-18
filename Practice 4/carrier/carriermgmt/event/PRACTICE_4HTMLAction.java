/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : PRACTICE_4HTMLAction.java
 *@FileTitle : Carrier Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.16
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.05.16
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice4.carriermgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CarrierVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;

/**
 * HTTP Parser<br>
 * - Parsing the Value of the HTML DOM object sent to the server through the com.clt.apps.opus.esm.clv.practice4 screen as a Java variable<br>
 * - Convert the parsing information into Event, put it in the request and request execution with Practice4SC<br>
 * - Set EventResponse to request to send execution result from Practice4SC to View (JSP)<br>
 * @author Truong Vu
 * @see See Practice3Event
 * @since J2EE 1.6
 */
public class PRACTICE_4HTMLAction extends HTMLActionSupport{
	private static final long serialVersionUID = 1L;
	
	/**
	 * PRACTICE_1HTMLAction function constructor
	 */
	public PRACTICE_4HTMLAction(){}
	
	/**
	 * Parsing the value of HTML DOM object as a Java variable<br>
	 * Parsing the information of HttpRequest as Practice1Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event An object that implements the Event interface.
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		FormCommand command = FormCommand.fromRequest(request);
		Practice4Event event = new Practice4Event();
		
		if (command.isCommand(FormCommand.SEARCH)){
//			event.setCarrierVO((CarrierVO)getVO(request, CarrierVO.class, ""));
			CarrierVO carrierVO = new CarrierVO();
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_carrier", ""));
			carrierVO.setVndrSeq(JSPUtil.getParameter(request, "s_vndr_seq", ""));
			carrierVO.setCreDtFr(JSPUtil.getParameter(request, "s_cre_dt_fm", ""));
			carrierVO.setCreDtTo(JSPUtil.getParameter(request, "s_cre_dt_to", ""));
			event.setCarrierVO(carrierVO);
		}
		else if (command.isCommand(FormCommand.MULTI)){
			event.setCarrierVOs((CarrierVO[])getVOs(request, CarrierVO.class, ""));
		}
		
		return event;
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
