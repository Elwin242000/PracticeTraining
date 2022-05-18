package com.clt.apps.opus.esm.clv.practice3;

import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.basic.MoneyMgmtBC;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.basic.MoneyMgmtBCImpl;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.event.Practice3Event;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class Practice3SC extends ServiceCommandSupport{
	// Login User Information
	private SignOnUserAccount account = null;
	
	public void doStart() {
		log.debug("Practice3SC start");
		try {
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public void doEnd() {
		log.debug("Practice3SC end");
	}
	
	public EventResponse perform(Event e) throws EventException {
		EventResponse eventResponse = null;
		if (e.getEventName().equalsIgnoreCase("Practice3Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchSummary(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)){
				eventResponse = initData();
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)){
				eventResponse = searchLane(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH02)){
				eventResponse = searchTrade(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH03)){
				eventResponse = searchDetail(e);
			}
		}
		return eventResponse;
	}
	
	private EventResponse searchSummary(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice3Event event = (Practice3Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<SummaryVO> list = command.searchSummaryMoneyMgmt(event.getSummaryVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchDetail(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice3Event event = (Practice3Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<DetailVO> list = command.searchDetailMoneyMgmt(event.getDetailVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	//search partner
	private EventResponse initData() throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<SummaryVO> list = command.searchPartner();
			StringBuilder partnerBuilder = new StringBuilder();
			if(null != list && list.size() > 0){
				for(int i =0; i < list.size(); i++){
					partnerBuilder.append(list.get(i).getJoCrrCd());
					if (i < list.size() - 1){
						partnerBuilder.append("|");
					}	
				}
			}
			System.out.println(list);
			eventResponse.setETCData("partners", partnerBuilder.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchLane(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice3Event event = (Practice3Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try{		
			List<SummaryVO> list = command.searchLane(event.getSummaryVO());
			StringBuilder laneBuilder = new StringBuilder();
			if(list!= null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					laneBuilder.append(list.get(i).getRlaneCd());
					if (i < list.size() - 1){
						laneBuilder.append("|");
					}	
				}
			}
			eventResponse.setETCData("lanes",laneBuilder.toString());
			System.out.println(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchTrade(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice3Event event = (Practice3Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try{		
			Map<String,String> list = command.searchTrade(event.getTradeVO());
			StringBuilder tradeBuilder = new StringBuilder();
			String temp = "";
			for (Map.Entry<String, String> entry : list.entrySet()){
				tradeBuilder.append("|" + entry.getValue());
			}
			if (tradeBuilder.toString().length() > 0){
				temp = tradeBuilder.toString().substring(1);
			}
			eventResponse.setETCData("trades",temp);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
}
