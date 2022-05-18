/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtPrtDBDAO.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.13
*@LastModifier : 
*@LastVersion : 1.0
* 2022.04.07 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.basic.ErrMsgMgmtPrtBCImpl;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.apps.opus.esm.clv.practice1.errmsgmgmtprt.vo.ErrMsgVO;



/**
 * ALPS ErrMsgMgmtPrtDBDAO <br>
 * - JDBC operation to process ALPS-Practice1 system business logic.<br>
 * 
 * @author Truong Vu
 * @see ErrMsgMgmtPrtBCImpl 참조
 * @since J2EE 1.6
 */
public class ErrMsgMgmtPrtDBDAO extends DBDAOSupport {

	/**
	 * [searchptErrMsg] to get a list of ErrMsgVO.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<ErrMsgVO> searchptErrMsg(ErrMsgVO errMsgVO) throws DAOException {
		// create and define a null DBRowSet to store query result.
		DBRowSet dbRowset = null;
		// create and define a list to store list ErrMsgVO.
		List<ErrMsgVO> list = new ArrayList();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(errMsgVO != null){
				Map<String, String> mapVO = errMsgVO.getColumnValues();
				
				// put values in mapVO into param
				param.putAll(mapVO);
				// put values in mapVO into velParam
				velParam.putAll(mapVO);
			}
			// execute queries in ErrMsgMgmtDBDAOErrMsgVORSQL with param and velParam
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO .class);
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
		return list;
	}
	
	/**
	 * [addmanageptErrMsg] to add ErrMsgVO.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @exception DAOException
	 * @exception Exception
	 */
	public void addmanageptErrMsg(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			// put values in mapVO into param
			param.putAll(mapVO);
			// put values in mapVO into velParam
			velParam.putAll(mapVO);
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// execute queries in ErrMsgMgmtPrtDBDAOErrMsgVOCSQL with param and velParam
			int result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVOCSQL(), param, velParam);
			//EXECUTE_FAILED: The constant indicating that an error occured while executing a batch statement.
			//if result = execute_failed throw error
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
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
	}
	
	/**
	 * [modifymanageptErrMsg] to update ErrMsgVO.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int modifymanageptErrMsg(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			// put values in mapVO into param
			param.putAll(mapVO);
			// put values in mapVO into velParam
			velParam.putAll(mapVO);
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// execute queries in ErrMsgMgmtPrtDBDAOErrMsgVOCSQL with param and velParam
			result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVOUSQL(), param, velParam);
			//EXECUTE_FAILED: The constant indicating that an error occured while executing a batch statement.
			//if result = execute_failed throw error
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
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
		return result;
	}
	
	/**
	 * [removemanageptErrMsg] to remove ErrMsgVO.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int removemanageptErrMsg(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			// put values in mapVO into param
			param.putAll(mapVO);
			// put values in mapVO into velParam
			velParam.putAll(mapVO);
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// execute queries in ErrMsgMgmtPrtDBDAOErrMsgVOCSQL with param and velParam
			result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVODSQL(), param, velParam);
			//EXECUTE_FAILED: The constant indicating that an error occured while executing a batch statement.
			//if result = execute_failed throw error
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
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
		return result;
	}

	/**
	 * [addmanageptErrMsgS] to add ErrMsgVOs.<br>
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addmanageptErrMsgS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			//create new SQLExecuter variable to execute query
			SQLExecuter sqlExe = new SQLExecuter("");
			// if list not null, execute query to save add new changes in database
			if(errMsgVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVOCSQL(), errMsgVO,null);
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
	 * [modifymanageptErrMsgS] to update ErrMsgVOs.<br>
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifymanageptErrMsgS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVOUSQL(), errMsgVO,null);
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
	 * [removemanageptErrMsgS] to remove ErrMsgVOs.<br>
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removemanageptErrMsgS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtPrtDBDAOErrMsgVODSQL(), errMsgVO,null);
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
	 * [duplicateErrMsgCd] to check duplicate err_msg_cd.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int duplicateErrMsgCd(ErrMsgVO errMsgVO) throws DAOException,Exception{
		// create and define a null DBRowSet to store query result.
		DBRowSet dbRowset = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		int count = 0;
		try{
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			// put values in mapVO into param
//			param.putAll(mapVO);
			param.put("err_msg_cd",errMsgVO.getErrMsgCd());
			// put values in mapVO into velParam
//			velParam.putAll(mapVO);
			// execute queries in ErrMsgMgmtDBDAOErrMsgDuplicateRSQL with param and velParam
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgDuplicateRSQL(), param, null);
			while (dbRowset.next()){
				String countE = dbRowset.getString(1);
				count = Integer.parseInt(countE);
			}
			System.out.println(count);
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