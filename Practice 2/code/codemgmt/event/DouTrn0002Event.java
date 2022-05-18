/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTrn0002Event.java
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

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;

/**
 * PDTO (Data Transfer Object including Parameters) for DOU_TRN_0002<br>
 * - Created from DOU_TRN_0002HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author Truong Vu
 * @see DOU_TRN_0002HTMLAction 참조
 * @since J2EE 1.6
 */
public class DouTrn0002Event extends EventSupport {
	private static final long serialVersionUID = 1L;
	
	/** Table Value Object query conditions and single case processing  */
	MasterVO masterVO = null;
	DetailVO detailVO = null;
	
	/** Table Value Object Multi Data Processing */
	MasterVO[] masterVOs = null;
	DetailVO[] detailVOs = null;
	
	public DouTrn0002Event(){
		
	}
	
	public void setMasterVO (MasterVO masterVO){
		this.masterVO = masterVO;
	}
	
	public MasterVO getMasterVO(){
		return masterVO;
	}
	
	public void setMasterVOS(MasterVO[] masterVOs){
		this.masterVOs = masterVOs;
	}
	
	public MasterVO[] getMasterVOS(){
		return masterVOs;
	}
	
	public void setDetailVO (DetailVO detailVO){
		this.detailVO = detailVO;
	}
	
	public DetailVO getDetailVO(){
		return detailVO;
	}
	
	public void setDetailVOS(DetailVO[] detailVOs){
		this.detailVOs = detailVOs;
	}
	
	public DetailVO[] getDetailVOS(){
		return detailVOs;
	}
}
