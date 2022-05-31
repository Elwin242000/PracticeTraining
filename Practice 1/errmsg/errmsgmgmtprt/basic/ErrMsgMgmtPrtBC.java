/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtPrtBC.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.07 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.basic;

import java.util.List;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.vo.ErrMsgVO;

/**
 * ALPS-Practice1 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Practice1<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */

public interface ErrMsgMgmtPrtBC {

	/**
	 * [searchErrMsg] to get a list of ErrMsgVO.<br>
	 * 
	 * @param ErrMsgVO	errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchptErrMsg(ErrMsgVO errMsgVO) throws EventException;
	
	/**
	 * [manageptErrMsg] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageptErrMsg(ErrMsgVO[] errMsgVO,SignOnUserAccount account) throws EventException;
	
	/**
	 *  [checkDuplicateErrMsg] to check duplicate err_msg_cd.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateErrMsg(ErrMsgVO errMsgVO) throws EventException;
}