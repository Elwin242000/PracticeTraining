/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtPrtBCImpl.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.integration.ErrMsgMgmtPrtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.vo.ErrMsgVO;

/**
 * ALPS-Practice1 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Practice1<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */
public class ErrMsgMgmtPrtBCImpl extends BasicCommandSupport implements ErrMsgMgmtPrtBC {

	// Database Access Object
	private transient ErrMsgMgmtPrtDBDAO dbDao = null;

	/**
	 * function constructor ErrMsgMgmtPrtBCImpl<br>
	 * To initialize ErrMsgMgmtPrtDBDAO<br>
	 */
	public ErrMsgMgmtPrtBCImpl() {
		dbDao = new ErrMsgMgmtPrtDBDAO();
	}
	/**
	 *  [searchErrMsg] to get a list of ErrMsgVO.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchptErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			// return list ErrMsg
			return dbDao.searchptErrMsg(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 *  [checkDuplicateErrMsg] to check duplicate err_msg_cd.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateErrMsg(ErrMsgVO errMsgVO) throws EventException {
		try {
			// return count
			return dbDao.duplicateErrMsgCd(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [manageptErrMsg] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageptErrMsg(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
			// storage list ErrMsgVO to insert
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			// storage list ErrMsgVO to update
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			// storage list ErrMsgVO to delete
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			// storage duplicate msg_cd
			StringBuilder dups = new StringBuilder();
			// count msg_cd duplicate
			int count = 0;
			
			for ( int i=0; i<errMsgVO .length; i++ ) {
				// Find and add new errMsgVO to insertVoList 
				if ( errMsgVO[i].getIbflag().equals("I")){
					//Check duplicate Msg_code
					if (checkDuplicateErrMsg(errMsgVO[i]) >= 1){
						dups.append("[" + errMsgVO[i].getErrMsgCd());
						if (i < errMsgVO .length - 1){
							dups.append("],");
						}
						if (i == errMsgVO.length - 1){
							dups.append("]");
						}
						count++;
					}
					if (i == errMsgVO.length-1){
						if (count > 0){
							throw new DAOException(new ErrorHandler("ERR00011", new String[]{dups.toString(), dups.toString()}).getMessage());
						}
						else {
							errMsgVO[i].setCreUsrId(account.getUsr_id());
							errMsgVO[i].setUpdUsrId(account.getUsr_id());
							insertVoList.add(errMsgVO[i]);
						
						}
					}
				} 
				// Find and add new errMsgVO to updateVoList 
				else if ( errMsgVO[i].getIbflag().equals("U")){
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);
				} 
				// Find and add new errMsgVO to deleteVoList 
				else if ( errMsgVO[i].getIbflag().equals("D")){
					deleteVoList.add(errMsgVO[i]);
				}	
			}
			//Validate Msg_Code (the first 3 characters are uppercase letters, the last 5 characters are numbers)
//			for (ErrMsgVO serrMsgVO: errMsgVO){
//				if (!Pattern.matches("^[A-Z]{3}[0-9]{5}", serrMsgVO.getErrMsgCd())){
//					throw new DAOException(new ErrorHandler("ERR11111").getMessage());
//				}
//			}
			// if insertVoList > 0, call dbDao to add to database
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageptErrMsgS(insertVoList);
			}
			// if updateVoList > 0, call dbDao to update to database
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageptErrMsgS(updateVoList);
			}
			// if deleteVoList > 0, call dbDao to delete in database
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageptErrMsgS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/*Check duplicate Msg_code, 
	 * if duplicate, return false, else return true
	 * */
	public boolean checkDuplicateMsgCd(ErrMsgVO errMsg ,List<ErrMsgVO> errList){
		for(ErrMsgVO err : errList){
			if (err.getErrMsgCd().equals(errMsg.getErrMsgCd())){
				return false;
			}
		}
		return true;
	}
	
}