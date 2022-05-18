/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : MoneyMgmtBC.java
 *@FileTitle : Money Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.13
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.04.22
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice3.moneymgmt.basic;

import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;

/**
 * ALPS-Practice3 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Practice3<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */
public interface MoneyMgmtBC {
	/**
	 * [searchSummaryMoneyMgmt] to get a list of summaryMoney.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryMoneyMgmt(SummaryVO summaryVO)
			throws EventException;

	/**
	 * [searchPartner] to get a list of partner.<br>
	 * 
	 * @param
	 * @return List<SummarySearchPartnerVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchPartner() 
			throws EventException;

	/**
	 * [searchLane] to get a list of lane.<br>
	 * 
	 * @param SummarySearchLaneVO	laneVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchLane(SummaryVO laneVO)
			throws EventException;

	/**
	 * [searchTrade] to get a list of trade.<br>
	 * 
	 * @param SummarySearchTradeVO	tradeVO
	 * @return Map<String, String>
	 * @exception EventException
	 */
	public Map<String, String> searchTrade(SummarySearchTradeVO tradeVO)
			throws EventException;

	/**
	 * [searchDetailMoneyMgmt] to get a list of detailMoney.<br>
	 * 
	 * @param DetailVO	detailVO
	 * @return List<DetailVO>
	 * @exception EventException
	 */
	public List<DetailVO> searchDetailMoneyMgmt(DetailVO detailVO)
			throws EventException;
}
