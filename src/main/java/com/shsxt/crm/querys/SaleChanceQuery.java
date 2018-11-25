package com.shsxt.crm.querys;

import com.shsxt.base.BaseQuery;

/**
 * Created by lp on 2018/6/26.
 */
public class SaleChanceQuery extends BaseQuery {
    private String customerName;
    private  String createMan;
    private Integer state;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
