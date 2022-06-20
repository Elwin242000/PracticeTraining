/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtDBDAOSearchCarrierRSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.08
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.08 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.practice4.carriermgmt.integration ;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Truong Vu
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CarrierMgmtDBDAOSearchCarrierRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * DESC Enter..
	  * </pre>
	  */
	public CarrierMgmtDBDAOSearchCarrierRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("jo_crr_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.practice4.carriermgmt.integration ").append("\n"); 
		query.append("FileName : CarrierMgmtDBDAOSearchCarrierRSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("select count(crr_cd)" ).append("\n"); 
		query.append("from mdm_carrier" ).append("\n"); 
		query.append("where crr_cd = @[jo_crr_cd]" ).append("\n"); 
		query.append("    and delt_flg = 'N'" ).append("\n"); 

	}
}