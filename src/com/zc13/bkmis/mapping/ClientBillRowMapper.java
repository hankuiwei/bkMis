package com.zc13.bkmis.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ClientBillRowMapper implements RowMapper{//告诉spring怎样把结果集转化为对象
	@Override
	public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
		ClientBill clientBill = new ClientBill();
		clientBill.setClientId(arg0.getString("clientId"));
		clientBill.setClientName(arg0.getString("clientName"));
		clientBill.setBillExpenses(arg0.getString("billExpenses"));
		clientBill.setDelayingExpenses(arg0.getString("delayingExpenses"));
		clientBill.setPayExpenses(arg0.getString("payExpenses"));
		clientBill.setActuallyPaid(arg0.getString("actuallyPaid"));
		clientBill.setRequireExpenses(arg0.getString("requireExpenses"));
		return clientBill;
	}
	
}
