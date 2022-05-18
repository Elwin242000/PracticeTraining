/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : Practice1SC.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.04.06
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.06 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1;

import java.util.List;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.basic.ErrMsgMgmtPrtBC;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.basic.ErrMsgMgmtPrtBCImpl;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.event.Practice1Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.vo.ErrMsgVO;


/**
 * ALPS-Practice1 Business Logic Service Command - Process the business transaction for ALPS-Practice1.
 * 
 * @author Truong Vu
 * @see ErrMsgMgmtPrtDBDAO
 * @since J2EE 1.6
 */

public class Practice1SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * Practice1 system work scenario precedent work<br>
	 * Creating related internal objects when calling a business scenario<br>
	 */
	public void doStart() {
		log.debug("Start Practice1SC");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * Practice1 system work scenario finishing work<br>
	 * Release related internal objects at the end of the business scenario<br>
	 */
	public void doEnd() {
		log.debug("Exit Practice1SC");
	}

	/**
	 * Carry out business scenarios corresponding to each event<br>
	 * Branch processing of all events occurring in the ALPS-Practice1 system business<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// The part to use when SC handles multiple events
		if (e.getEventName().equalsIgnoreCase("Practice1Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchptErrMsg(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageptErrMsg(e);
			}
		}
		return eventResponse;
	}
	/**
	 * PRACTICE_1 : [Event]<br>
	 * [Act] for [Business Target].<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchptErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice1Event event = (Practice1Event)e;
		ErrMsgMgmtPrtBC command = new ErrMsgMgmtPrtBCImpl();

		try{
			List<ErrMsgVO> list = command.searchptErrMsg(event.getErrMsgVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	/**
	 * PRACTICE_1 : [Event]<br>
	 * [Act] for [Business Target].<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageptErrMsg(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice1Event event = (Practice1Event)e;
		ErrMsgMgmtPrtBC command = new ErrMsgMgmtPrtBCImpl();
		try{
			begin();
			command.manageptErrMsg(event.getErrMsgVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXXX").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
}