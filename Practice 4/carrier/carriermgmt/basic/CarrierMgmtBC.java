/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : CarrierMgmtBC.java
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

import java.util.List;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CustomerVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-Practice4 Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Practice4<br>
 *
 * @author Truong Vu
 * @since J2EE 1.6
 */
public interface CarrierMgmtBC {
	/**
	 * [searchCarrierCarrierMgmt] to get a list of Carrier.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierCarrierMgmt(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [searchCarrierCombo] to get a list of Carrier for Combo box.<br>
	 * 
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchCarrierCombo() throws EventException;
	
	/**
	 * [searchLaneCombo] to get a list of Lane for Combo box.<br>
	 * 
	 * @return List<CarrierVO>
	 * @exception EventException
	 */
	public List<CarrierVO> searchLaneCombo() throws EventException;
	
	/**
	 * [manageCarrierCarrierMgmt] to save the change(add, delete, update) in database.<br>
	 * 
	 * @param CarrierVO[] carrierVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageCarrierCarrierMgmt(CarrierVO[] carrierVO, SignOnUserAccount account) throws EventException;
	
	/**
	 * [searchCustomer] to get a list of Customer.<br>
	 * 
	 * @param CustomerVO	customerVO
	 * @return List<CustomerVO>
	 * @exception EventException
	 */
	public List<CustomerVO> searchCustomer(CustomerVO customerVO) throws EventException; 
	
	/**
	 * [checkDuplicateInput] to check duplicate input.<br>
	 * 
	 * @param CarrierVO	carrierVO
	 * @return int
	 * @exception EventException
	 */
	public int checkDuplicateInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkCrrCdInput] to check carrier code exist. </br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkCrrCdInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkRlaneCdInput] to check lane code exist. </br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkRlaneCdInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkVndrCdInput] to check vendor code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkVndrCdInput(CarrierVO carrierVO) throws EventException;
	
	/**
	 * [checkTrdCdInput] to check trade code exist.</br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @throws EventException
	 */
	public int checkTrdCdInput(CarrierVO carrierVO) throws EventException;
}
