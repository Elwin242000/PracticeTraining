/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0002HTMLAction.java
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.15
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.codemgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;

/**
 * HTTP Parser<br>
 * - Parsing the Value of the HTML DOM object sent to the server through the com.clt.apps.opus.dou.doutraining screen as a Java variable<br>
 * - Convert the parsing information into Event, put it in the request and request execution with DouTrainingSC<br>
 * - Set EventResponse to request to send execution result from DouTrainingSC to View (JSP)<br>
 * @author Truong Vu
 * @see See DouTrn0002Event
 * @since J2EE 1.6
 */
public class DOU_TRN_0002HTMLAction extends HTMLActionSupport {
	private static final long serialVersionUID = 1L;
	
	/**
	 * DOU_TRN_0002HTMLAction function constructor
	 */
	public DOU_TRN_0002HTMLAction(){
	
	}
	
	/**
	 * Parsing the value of HTML DOM object as a Java variable<br>
	 * Parsing the information of HttpRequest as Practice1Event and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event An object that implements the Event interface.
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		DouTrn0002Event event = new DouTrn0002Event();
		
		if(command.isCommand(FormCommand.SEARCH)) {
			MasterVO masterVO = new MasterVO();
			masterVO.setOwnrSubSysCd(JSPUtil.getParameter(request, "s_ownr_sub_sys_cd", ""));
			masterVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id", ""));
			event.setMasterVO(masterVO);
		}
		else if(command.isCommand(FormCommand.SEARCH01)){
//			event.setDetailVO((DetailVO)getVO(request, DetailVO .class,""));
			DetailVO detailVO = new DetailVO();
			detailVO.setIntgCdId(JSPUtil.getParameter(request, "intg_cd_id", ""));
			event.setDetailVO(detailVO);
		}
		else if(command.isCommand(FormCommand.MULTI)) {
			event.setMasterVOS((MasterVO[])getVOs(request, MasterVO.class, ""));		
		}
		else if (command.isCommand(FormCommand.MULTI01)){
			event.setDetailVOS((DetailVO[])getVOs(request, DetailVO.class, ""));
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
