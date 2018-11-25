package com.shsxt.crm.dao;


import com.shsxt.crm.vo.LogWithBLOBs;

public interface LogMapper {

    int insert(LogWithBLOBs record);

}