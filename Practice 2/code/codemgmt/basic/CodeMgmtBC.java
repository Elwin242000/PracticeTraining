/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtBC.java
*@FileTitle : Code Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.15
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.dou.doutraining.codemgmt.basic;

import java.util.List;

import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;

/**
 * ALPS-Doutraining Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Doutraining<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */
public interface CodeMgmtBC {
	/**
	 * [searchMasterCodeMgmt] to get a list of code.<br>
	 * 
	 * @param MasterVO	masterVO
	 * @return List<MasterVO>
	 * @exception EventException
	 */
	public List<MasterVO> searchMasterCodeMgmt(MasterVO masterVO) throws EventException;
	
	/**
	 * [searchDetailCodeMgmt] to get a list of code detail.<br>
	 * 
	 * @param MasterVO	masterVO
	 * @return List<MasterVO>
	 * @exception EventException
	 */
	public List<DetailVO> searchDetailCodeMgmt(DetailVO detailVO) throws EventException;
	
	/**
	 * [manageMasterCodeMgmt] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param MasterVO[] masterVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageMasterCodeMgmt(MasterVO[] masterVO, SignOnUserAccount account) throws EventException;
	
	/**
	 * [manageDetailCodeMgmt] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param DetailVO[] detailVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageDetailCodeMgmt(DetailVO[] detailVO, SignOnUserAccount account) throws EventException;
	
	/**
	 *  [checkDuplicateErrMsg] to check duplicate err_msg_cd.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateCodeMgmt(MasterVO masterVO) throws EventException;
}
