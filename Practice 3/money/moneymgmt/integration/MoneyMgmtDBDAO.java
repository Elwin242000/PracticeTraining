/*=========================================================
 *Copyright(c) 2022 CyberLogitec
 *@FileName : MoneyMgmtDBDAO.java
 *@FileTitle : Money Management
 *Open Issues :
 *Change history :
 *@LastModifyDate : 2022.05.13
 *@LastModifier : 
 *@LastVersion : 1.0
 * 2022.04.22
 * 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice3.moneymgmt.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummarySearchTradeVO;
import com.clt.apps.opus.esm.clv.practice3.moneymgmt.vo.SummaryVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

/**
 * ALPS MoneyMgmtDBDAO <br>
 * - JDBC operation to process ALPS-Practice3 system business logic.<br>
 * 
 * @author Truong Vu
 * @see See MoneyMgmtBCImpl
 * @since J2EE 1.6
 */
public class MoneyMgmtDBDAO extends DBDAOSupport{
	/**
	 * [searchSummaryVO] to get a list of SummaryMoney.<br>
	 * 
	 * @param SummaryVO summaryVO
	 * @return List<SummaryVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
 	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws DAOException {
 		DBRowSet dbRowset = null;
		List<SummaryVO> list = new ArrayList();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if(summaryVO != null){
				Map<String, String> mapVO = summaryVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(null != summaryVO.getJoCrrCd()){
					String[] crr_cd = summaryVO.getJoCrrCd().split(",");
					for(int i = 0; i < crr_cd.length; i++){
						obj_list_no.add(crr_cd[i]);
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAOSummaryVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
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
	 * [searchPartner] to get a list of partner.<br>
	 * 
	 * @return List<SummaryVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> searchPartner() throws DAOException {
		DBRowSet dbRowset = null;
		 List<SummaryVO> list = new ArrayList();
		 //query parameter
		 Map<String, Object> param = new HashMap<String, Object>();
		 //velocity parameter
		 Map<String, Object> velParam = new HashMap<String, Object>();
		 
		 try{
			 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAOSummarySearchPartnerRSQL(), param, velParam);
			 list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		 } catch(SQLException se) {
			 log.error(se.getMessage(),se);
			 throw new DAOException(new ErrorHandler(se).getMessage());
		 } catch(Exception ex) {
			 log.error(ex.getMessage(),ex);
			 throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	}
	
	/**
	 * [searchLane] to get a list of lane.<br>
	 * 
	 * @param SummarySearchLaneVO laneVO
	 * @return Map<String,String>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryVO> searchLane(SummaryVO laneVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<SummaryVO> list = new ArrayList();
		 //query parameter
		 Map<String, Object> param = new HashMap<String, Object>();
		 //velocity parameter
		 Map<String, Object> velParam = new HashMap<String, Object>();
		 
		 try{
			 if(laneVO != null){
				 Map<String, String> mapVO = laneVO.getColumnValues();
				 List<String> obj_list_no = new ArrayList<>();
					if(null != laneVO.getJoCrrCd()){
						String[] crr_cd = laneVO.getJoCrrCd().split(",");
						for(int i = 0; i < crr_cd.length; i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
					param.putAll(mapVO);
					param.put("obj_list_no", obj_list_no);
					
					velParam.putAll(mapVO);
					velParam.put("obj_list_no", obj_list_no);
			 }
			 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAOSummarySearchLaneRSQL(), param, velParam);
			 list = (List)RowSetUtil.rowSetToVOs(dbRowset, SummaryVO .class);
		 } catch(SQLException se) {
			 log.error(se.getMessage(),se);
			 throw new DAOException(new ErrorHandler(se).getMessage());
		 } catch(Exception ex) {
			 log.error(ex.getMessage(),ex);
			 throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	}
	
	/**
	 * [searchLane] to get a list of trade.<br>
	 * 
	 * @param SummarySearchTradeVO tradeVO
	 * @return Map<String,String>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> searchTrade(SummarySearchTradeVO tradeVO) throws DAOException {
		 DBRowSet dbRowset = null;
		 Map<String, String> list = new HashMap<String, String>();
		 //query parameter
		 Map<String, Object> param = new HashMap<String, Object>();
		 //velocity parameter
		 Map<String, Object> velParam = new HashMap<String, Object>();
		 
		 try{
			 if(tradeVO != null){
				 Map<String, String> mapVO = tradeVO.getColumnValues();
				 List<String> obj_list_no = new ArrayList<>();
					if(null != tradeVO.getJoCrrCd()){
						String[] crr_cd = tradeVO.getJoCrrCd().split(",");
						for(int i = 0; i < crr_cd.length; i++){
							obj_list_no.add(crr_cd[i]);
						}
					}
					param.putAll(mapVO);
					param.put("obj_list_no", obj_list_no);
					
					velParam.putAll(mapVO);
					velParam.put("obj_list_no", obj_list_no);
			 }
			 dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAOSummarySearchTradeRSQL(), param, velParam);
			 while(dbRowset.next()){
				 String trade_cd = dbRowset.getString(1);
				 list.put(trade_cd, trade_cd);
			 }	
			 System.out.println(list);
		 } catch(SQLException se) {
			 log.error(se.getMessage(),se);
			 throw new DAOException(new ErrorHandler(se).getMessage());
		 } catch(Exception ex) {
			 log.error(ex.getMessage(),ex);
			 throw new DAOException(new ErrorHandler(ex).getMessage());
		 }
		 return list;
	}
	
	/**
	 * [searchDetailVO] to get a list of DetailMoney.<br>
	 * 
	 * @param DetailVO detailVO
	 * @return List<DetailVO>
	 * @exception DAOException
	 */
	@SuppressWarnings("unchecked")
 	public List<DetailVO> searchDetailVO(DetailVO detailVO) throws DAOException {
 		DBRowSet dbRowset = null;
		List<DetailVO> list = new ArrayList();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try{
			if(detailVO != null){
				Map<String, String> mapVO = detailVO.getColumnValues();
				List<String> obj_list_no = new ArrayList<>();
				if(null != detailVO.getJoCrrCd()){
					String[] crr_cd = detailVO.getJoCrrCd().split(",");
					for(int i = 0; i < crr_cd.length; i++){
						obj_list_no.add(crr_cd[i]);
					}
				}
				param.putAll(mapVO);
				param.put("obj_list_no", obj_list_no);
				
				velParam.putAll(mapVO);
				velParam.put("obj_list_no", obj_list_no);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new MoneyMgmtDBDAODetailVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, DetailVO .class);
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
}
