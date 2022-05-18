/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : CarrierMgmtBCImpl.java
 *@FileTitle : Carrier Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.16
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.05.16
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice4.carriermgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.integration.CarrierMgmtDBDAO;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CarrierVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-Practice4 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Practice4<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */
public class CarrierMgmtBCImpl extends BasicCommandSupport implements CarrierMgmtBC{
	// Database Access Object
	private transient CarrierMgmtDBDAO dbDao = null;
	
	/**
	 * function constructor CarrierMgmtBCImpl<br>
	 * To initialize CarrierMgmtDBDAO<br>
	 */
	public CarrierMgmtBCImpl(){
		dbDao = new CarrierMgmtDBDAO();
	}
	
	/**
	 * [searchCarrierCarrierMgmt] to get a list of Carrier.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierCarrierMgmt(CarrierVO carrierVO) throws EventException {
		try{
			return dbDao.searchCarrierVO(carrierVO);
		}
		catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	/**
	 * [searchCarrierCombo] to get a list of Carrier for Combo box.<br>
	 * 
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierCombo() throws EventException {
		try{
			return dbDao.searchCarrierCombo();
		}
		catch (DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}

	/**
	 * [searchLaneCombo] to get a list of Lane for Combo box.<br>
	 * 
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchLaneCombo() throws EventException {
		try{
			return dbDao.searchLaneCombo();
		}
		catch (DAOException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
		catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(), ex);
		}
	}
	
	/**
	 * [checkDuplicateJoCrrCd] to check duplicate jo_crr_cd.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateJoCrrCd(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.duplicateJoCrrCd(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [manageCarrierCarrierMgmt] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param CarrierVO[] carrierVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageCarrierCarrierMgmt(CarrierVO[] carrierVO, SignOnUserAccount account) throws EventException {
		try{
			List<CarrierVO> insertVOList = new ArrayList<CarrierVO>();
			List<CarrierVO> updateVOList = new ArrayList<CarrierVO>();
			List<CarrierVO> deleteVOList = new ArrayList<CarrierVO>();
			
			for (int i = 0; i < carrierVO.length; i++){
				if ( carrierVO[i].getIbflag().equals("I")){
					if (checkDuplicateJoCrrCd(carrierVO[i]) >= 1){
						throw new DAOException(new ErrorHandler("ERR00001").getMessage());
					}
					else {
						insertVOList.add(carrierVO[i]);
					}			
				}
				else if (carrierVO[i].getIbflag().equals("U")){
					updateVOList.add(carrierVO[i]);
				}
				else if (carrierVO[i].getIbflag().equals("D")){
					deleteVOList.add(carrierVO[i]);
				}
				carrierVO[i].setCreUsrId(account.getUsr_id());
				carrierVO[i].setUpdUsrId(account.getUsr_id());
			}
			
			if (insertVOList.size() > 0){
				dbDao.addmanageCarrierS(insertVOList);
			}
			
			if (updateVOList.size() > 0){
				dbDao.modifymanageCarrierS(updateVOList);
			}
			
			if (deleteVOList.size() > 0){
				dbDao.removemanageCarrierS(deleteVOList);
			}
		}
		catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
}
