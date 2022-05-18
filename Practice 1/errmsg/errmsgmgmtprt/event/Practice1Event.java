/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : Practice1Event.java
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

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.vo.ErrMsgVO;


/**
 * PDTO (Data Transfer Object including Parameters) for PRACTICE_1<br>
 * - Created from PRACTICE_1HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author Truong Vu
 * @see PRACTICE_1HTMLAction 참조
 * @since J2EE 1.6
 */

public class Practice1Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	/** Table Value Object query conditions and single case processing  */
	ErrMsgVO errMsgVO = null;
	
	/** Table Value Object Multi Data Processing */
	ErrMsgVO[] errMsgVOs = null;

	public Practice1Event(){}
	
	public void setErrMsgVO(ErrMsgVO errMsgVO){
		this. errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs){
		this.errMsgVOs  = errMsgVOs;
	}

	public ErrMsgVO getErrMsgVO(){
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS(){
		return errMsgVOs;
	}
}