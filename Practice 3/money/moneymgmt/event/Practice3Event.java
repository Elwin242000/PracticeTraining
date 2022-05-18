/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : Practice3Event.java
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

import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;

/**
 * PDTO (Data Transfer Object including Parameters) for PRACTICE_3<br>
 * - Created from PRACTICE_3HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author Truong Vu
 * @see PRACTICE_3HTMLAction 참조
 * @since J2EE 1.6
 */
public class Practice3Event extends EventSupport {
	private static final long serialVersionUID = 1L;
	
	/** Table Value Object query conditions and single case processing  */
	SummaryVO summaryVO = null;	
	DetailVO detailVO = null;	
	SummarySearchTradeVO tradeVO = null;
	
	/** Table Value Object Multi Data Processing */
	SummaryVO[] summaryVOs = null;
	DetailVO[] detailVOs = null;
	
	public Practice3Event(){}
	
	// get set SummaryVO
	public void setSummaryVO (SummaryVO summaryVO){
		this.summaryVO = summaryVO;
	}	
	public SummaryVO getSummaryVO(){
		return summaryVO;
	}
	
	//get set SummaryVO[]
	public void setSummaryVOS(SummaryVO[] summaryVOs){
		this.summaryVOs = summaryVOs;
	}
	public SummaryVO[] getSummaryVOS(){
		return summaryVOs;
	}
	
	//get set DetailVO
	public void setDetailVO (DetailVO detailVO){
		this.detailVO = detailVO;
	}
	public DetailVO getDetailVO(){
		return detailVO;
	}
	
	//get set DetailVO[]
	public void setDetailVOS(DetailVO[] detailVOs){
		this.detailVOs = detailVOs;
	}
	public DetailVO[] getDetailVOS(){
		return detailVOs;
	}
	
	//get set TradeVO
	public void setTradeVO(SummarySearchTradeVO tradeVO){
		this.tradeVO = tradeVO;
	}
	public SummarySearchTradeVO getTradeVO(){
		return tradeVO;
	}
}
