	package com.clt.apps.opus.esm.clv.practice4;

import java.util.List;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.event.Practice4Event;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CarrierVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class Practice4SC extends ServiceCommandSupport {
	private SignOnUserAccount account = null;
	
	public void doStart() {
		log.debug("Practice4SC start");
		try {
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public void doEnd() {
		log.debug("Practice4SC end");
	}
	
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
			
		}
		return eventResponse;
	}
	
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
}
