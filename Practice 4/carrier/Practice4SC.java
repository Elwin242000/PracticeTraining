/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : CarrierMgmtBC.java
 *@FileTitle : Carrier Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.16
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.05.16
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice4;

import java.util.List;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.event.Practice4Event;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CustomerVO;
import com.clt.apps.opus.esm.clv.practice4.customer.event.CustomerEvent;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-Practice4 Business Logic Service Command - Process the business transaction for ALPS-Practice4.
 * 
 * @author Truong Vu
 * @see CarrierMgmtDBDAO
 * @since J2EE 1.6
 */
public class Practice4SC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;
	
	/**
	 * Practice4 system work scenario precedent work<br>
	 * Creating related internal objects when calling a business scenario<br>
	 */
	public void doStart() {
		log.debug("Start Practice4SC");
		try {
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	/**
	 * Practice4 system work scenario finishing work<br>
	 * Release related internal objects at the end of the business scenario<br>
	 */
	public void doEnd() {
		log.debug("Practice4SC end");
	}
	
	/**
	 * Carry out business scenarios corresponding to each event<br>
	 * Branch processing of all events occurring in the ALPS-Practice4 system business<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		EventResponse eventResponse = null;
		if (e.getEventName().equalsIgnoreCase("Practice4Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCarrier(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)){
				eventResponse = initData();
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)){
				eventResponse = manageCarrier(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.COMMAND01)){
				eventResponse = chkDupData(e);
			}
			
		}
		
		if (e.getEventName().equalsIgnoreCase("CustomerEvent")){
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCustomer(e);
			}
		}
		return eventResponse;
	}
	
	/**
	 * Search list carrier data.
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchCarrier(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice4Event event = (Practice4Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();

		try{
			List<CarrierVO> list = command.searchCarrierCarrierMgmt(event.getCarrierVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * Generate ETCData for CarrierCode and RlaneCode
	 * @return EventResponse
	 * @throws EventException
	 */
	private EventResponse initData() throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		try{
			List<CarrierVO> listCarrier = command.searchCarrierCombo();
			StringBuilder carrierBuilder = new StringBuilder();
			if (listCarrier != null && listCarrier.size() > 0){
				for (int i = 0; i < listCarrier.size(); i++){
					carrierBuilder.append(listCarrier.get(i).getJoCrrCd());
					if (i < listCarrier.size() - 1){
						carrierBuilder.append("|");
					}
				}
			}
			eventResponse.setETCData("carriers", carrierBuilder.toString());
			
			List<CarrierVO> listLane = command.searchLaneCombo();
			StringBuilder laneBuilder = new StringBuilder();
			if (listLane != null && listLane.size() > 0){
				for (int i = 0; i < listLane.size(); i++){
					laneBuilder.append(listLane.get(i).getRlaneCd());
					if (i < listLane.size() - 1){
						laneBuilder.append("|");
					}
				}
			}
			eventResponse.setETCData("lanes", laneBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 *  Save changed data.
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @throws EventException
	 */
	private EventResponse manageCarrier(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice4Event event = (Practice4Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		try{
			begin();
			command.manageCarrierCarrierMgmt(event.getCarrierVOs(),account);
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
	
	/**
	 * Search list customer data.<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @throws EventException
	 */
	private EventResponse searchCustomer(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		CustomerEvent event = new CustomerEvent();
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		
		try{
			List<CustomerVO> list = command.searchCustomer(event.getCustomerVO());
			eventResponse.setRsVoList(list);
			System.out.println(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * Check duplicate data input.
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @throws EventException
	 */
	private EventResponse chkDupData(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice4Event event = (Practice4Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();
		
		try{
			int num = command.checkDuplicateInput(event.getCarrierVO());
			eventResponse.setETCData("ISEXIST", num > 0 ? "Y" : "N");
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
}
