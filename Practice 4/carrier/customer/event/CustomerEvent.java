package com.clt.apps.opus.esm.clv.practice4.customer.event;

import com.clt.apps.opus.esm.clv.practice4.carriermgmt.vo.CustomerVO;
import com.clt.framework.support.layer.event.EventSupport;

public class CustomerEvent extends EventSupport{
	private static final long serialVersionUID = 1L;
	
	CustomerVO customerVO = null;
	
	public CustomerEvent() {
		
	}
	
	public CustomerVO getCustomerVO(){
		return customerVO;
	}
	public void setCustomerVO(CustomerVO customerVO){
		this.customerVO = customerVO;
	}
}
