/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : CarrierMgmtDBDAO.java
 *@FileTitle : Carrier Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.16
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.05.16
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice4.carriermgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CustomerVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * ALPS CarrierMgmtDBDAO <br>
 * - JDBC operation to process ALPS-Practice4 system business logic.<br>
 * 
 * @author Truong Vu
 * @see CarrierMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class CarrierMgmtDBDAO extends DBDAOSupport {
	/**
	 * [searchCarrierVO] to get a list of carrier.<br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return List<CarrierVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws DAOException {
 		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if(carrierVO != null){
				Map<String, String> mapVO = carrierVO.getColumnValues();
				List<String> obj_list_no = new ArrayList();
				if (carrierVO.getJoCrrCd() != null){
					String[] crr_cd = carrierVO.getJoCrrCd().split(",");
					for (int i = 0; i < crr_cd.length; i++){
						obj_list_no.add(crr_cd[i]);
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCarrierVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
		}
		catch (SQLException se){
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}
		catch (Exception ex){
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
 	}
	
	/**
	 * [searchCarrierCombo] to get a list of carrier for combo box.<br>
	 * 
	 * @return List<CarrierSearchCarrierVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchCarrierCombo() throws DAOException {
		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		 
		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCarrierSearchCarrierRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
		} 
		catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} 
		catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	}
	
	/**
	 * [searchLaneCombo] to get a list of lane for combo box.<br>
	 * 
	 * @return List<CarrierVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CarrierVO> searchLaneCombo() throws DAOException {
		DBRowSet dbRowset = null;
		List<CarrierVO> list = new ArrayList();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		 
		try{
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCarrierSearchLaneRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
		} 
		catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} 
		catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	}
	
	/**
	 * [searchCustomer] to get a list of customer.<br>
	 * 
	 * @param CustomerVO customerVO
	 * @return List<CustomerVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<CustomerVO> searchCustomer(CustomerVO customerVO) throws DAOException {
 		DBRowSet dbRowset = null;
		List<CustomerVO> list = new ArrayList();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if(customerVO != null){
				Map<String, String> mapVO = customerVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOSearchCustomerRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CustomerVO .class);
		}
		catch (SQLException se){
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		}
		catch (Exception ex){
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
 	}
	
	/**
	 * [addmanageCarrierS] to add ErrMsgVOs.<br>
	 * 
	 * @param List<CarrierVO> carrierVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addmanageCarrierS(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// if list not null, execute query to save add new changes in database
			if(carrierVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDBDAOCarrierVOCSQL(), carrierVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			//show error in console with error message
			log.error(se.getMessage(),se);
			//throw EventException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			//show error in console with error message
			log.error(ex.getMessage(),ex);
			//throw EventException
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	
	/**
	 * [modifymanageCarrierS] to update ErrMsgVOs.<br>
	 * 
	 * @param List<CarrierVO> carrierVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifymanageCarrierS(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDBDAOCarrierVOUSQL(), carrierVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			//show error in console with error message
			log.error(se.getMessage(),se);
			//throw EventException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			//show error in console with error message
			log.error(ex.getMessage(),ex);
			//throw EventException
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	
	/**
	 * [removemanageCarrierS] to delete ErrMsgVOs.<br>
	 * 
	 * @param List<CarrierVO> carrierVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removemanageCarrierS(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDBDAOCarrierVODSQL(), carrierVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			//show error in console with error message
			log.error(se.getMessage(),se);
			//throw EventException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			//show error in console with error message
			log.error(ex.getMessage(),ex);
			//throw EventException
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	/**
	 * [duplicateErrMsgCd] to check duplicate jo_crr_cd.<br>
	 * 
	 * @param CarrierVO carrierVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int duplicateInput(CarrierVO carrierVO) throws DAOException,Exception{
		DBRowSet dbRowset = null;
		Map<String, Object> param = new HashMap<String, Object>();
		int count = 0;
		try{
			Map<String, String> mapVO = carrierVO .getColumnValues();
//			param.put("jo_crr_cd",carrierVO.getJoCrrCd());
			param.putAll(mapVO);
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDBDAOCheckDuplicateJoCrrCdRSQL(), param, null);
			while (dbRowset.next()){
				String countE = dbRowset.getString(1);
				count = Integer.parseInt(countE);
			}
		}
		catch(SQLException se) {
			//show error in console with error message
			log.error(se.getMessage(),se);
			//throw EventException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			//show error in console with error message
			log.error(ex.getMessage(),ex);
			//throw EventException
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return count;
	}
}
