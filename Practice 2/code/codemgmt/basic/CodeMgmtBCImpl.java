/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtBCImpl.java
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

import java.util.ArrayList;
import java.util.List;

import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.dou.doutraining.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.MasterVO;
import com.clt.apps.opus.dou.doutraining.codemgmt.vo.DetailVO;


/**
 * ALPS-Doutraining Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Doutraining<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */
public class CodeMgmtBCImpl extends BasicCommandSupport implements CodeMgmtBC {
	// Database Access Object
	private transient CodeMgmtDBDAO dbDao = null;
	
	/**
	 * function constructor CodeMgmtBCImpl<br>
	 * To initialize CodeMgmtDBDAO<br>
	 */
	public CodeMgmtBCImpl(){
		dbDao = new CodeMgmtDBDAO();
	}

	/**
	 * [searchMasterCodeMgmt] to get a list of code.<br>
	 * 
	 * @param MasterVO	masterVO
	 * @return List<MasterVO>
	 * @exception EventException
	 */
	public List<MasterVO> searchMasterCodeMgmt(MasterVO masterVO) throws EventException {
		try {
			return dbDao.searchMasterCodeMgmt(masterVO);
		}
		catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}

	/**
	 * [searchDetailCodeMgmt] to get a list of code detail.<br>
	 * 
	 * @param MasterVO	masterVO
	 * @return List<MasterVO>
	 * @exception EventException
	 */
	public List<DetailVO> searchDetailCodeMgmt(DetailVO detailVO) throws EventException {
		try{
			return dbDao.searchDetailCodeMgmt(detailVO);
		}
		catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	/**
	 *  [checkDuplicateErrMsg] to check duplicate err_msg_cd.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateCodeMgmt(MasterVO masterVO) throws EventException {
		try{
			return dbDao.duplicatecodeMgmt(masterVO);
		}
		catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	/**
	 * [manageMasterCodeMgmt] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param MasterVO[] masterVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageMasterCodeMgmt(MasterVO[] masterVO, SignOnUserAccount account) throws EventException{
		try {
			List<MasterVO> insertVoList = new ArrayList<MasterVO>();
			List<MasterVO> updateVoList = new ArrayList<MasterVO>();
			List<MasterVO> deleteVoList = new ArrayList<MasterVO>();
			List<DetailVO> deleteDetailVoList = new ArrayList<DetailVO>();
			
			for ( int i=0; i<masterVO .length; i++ ) {
				if ( masterVO[i].getIbflag().equals("I")){
					if (checkDuplicateCodeMgmt(masterVO[i]) >= 1){
						throw new DAOException(new ErrorHandler("ERR54321").getMessage());
					}
					else{
						insertVoList.add(masterVO[i]);
					}
					
				} else if ( masterVO[i].getIbflag().equals("U")){
					updateVoList.add(masterVO[i]);
				} else if ( masterVO[i].getIbflag().equals("D")){
					DetailVO detailVO = new DetailVO();
					detailVO.setIntgCdId(masterVO[i].getIntgCdId());
					deleteDetailVoList.add(detailVO);
					deleteVoList.add(masterVO[i]);
				}
				masterVO[i].setCreUsrId(account.getUsr_id());
				masterVO[i].setUpdUsrId(account.getUsr_id());
			}
			
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageMasterCodeMgmtS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageMasterCodeMgmtS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageMasterCodeMgmtS(deleteVoList);
				dbDao.removeDetailByMasterCdId(deleteDetailVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [manageDetailCodeMgmt] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param DetailVO[] detailVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageDetailCodeMgmt(DetailVO[] detailVO, SignOnUserAccount account) throws EventException{
		try {
			List<DetailVO> insertVoList = new ArrayList<DetailVO>();
			List<DetailVO> updateVoList = new ArrayList<DetailVO>();
			List<DetailVO> deleteVoList = new ArrayList<DetailVO>();
			
			for ( int i=0; i<detailVO .length; i++ ) {
				if ( detailVO[i].getIbflag().equals("I")){
					insertVoList.add(detailVO[i]);
				} else if ( detailVO[i].getIbflag().equals("U")){
					updateVoList.add(detailVO[i]);
				} else if ( detailVO[i].getIbflag().equals("D")){
					deleteVoList.add(detailVO[i]);
				}
				detailVO[i].setCreUsrId(account.getUsr_id());
				detailVO[i].setUpdUsrId(account.getUsr_id());
			}
			
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageDetailCodeMgmtS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageDetailCodeMgmtS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageDetailCodeMgmtS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
}
