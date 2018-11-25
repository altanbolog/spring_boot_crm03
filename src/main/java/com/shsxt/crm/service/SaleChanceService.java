package com.shsxt.crm.service;

import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.constant.CrmConstant;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.querys.SaleChanceQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lp on 2018/6/26.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance>{

    @Resource
    private SaleChanceDao saleChanceDao;


   /* @Autowired
    private RedisTemplate<String,SaleChance> redisTemplate;*/

    /*@Resource(name="redisTemplate")
    private ListOperations<String,SaleChance> listOperations;*/

    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageInfo<SaleChance> pageInfo=queryForPage(saleChanceQuery);
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public  void saveSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *    客户名称
         *    联系人
         *    联系电话
         * 2.执行添加
         *    判断是否执行记录分配操作
         *       设置分配人 分配状态
         *    开发状态:默认0:未开发状态
         *    添加时间  更新时间
         */
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());

        saleChance.setState(0);//未分配
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setAssignTime(new Date());
            saleChance.setState(1);
        }
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        saleChance.setDevResult(0);//未开发
        AssertUtil.isTrue(saleChanceDao.insert(saleChance)<1, CrmConstant.OPS_FAILED_MSG);
    }

    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"联系电话不能为空!");
    }

    private void checkParams(String customerName, String linkMan, String linkPhone,Integer sid) {
        checkParams(customerName,linkMan,linkPhone);
        AssertUtil.isTrue(null ==sid||null==saleChanceDao.selectById(sid),"待更新记录不存在!");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public  void updateSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *    客户名称
         *    联系人
         *    联系电话
         *    id 记录必须存在
         * 2.执行更新
         *     更新时间
         *     判断是否执行记录分配操作
         *       设置分配人 分配状态
         */
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone(),saleChance.getId());
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        AssertUtil.isTrue(saleChanceDao.update(saleChance)<1,CrmConstant.OPS_FAILED_MSG);


        /**
         * 清除缓存
         *    根据key 清除缓存数据
         *
         */
      /*  Set<String> keys= redisTemplate.keys("saleChance::list::*");
        *//**
         * 删除key
         *//*
        redisTemplate.delete(keys);*/
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChancesBatch(Integer[] ids){
        AssertUtil.isTrue(null ==ids ||ids.length==0,"请选择待删除记录!");
        AssertUtil.isTrue(saleChanceDao.deleteBatch(ids)<ids.length,"记录删除失败!");
        int a=1/0;

/*
        saleChanceDao.deleteBatch();// success



        saleChanceDao.insert();// failed*/



    }


    /**
     * 加入redis缓存
     * @param saleChanceQuery
     * @return
     */
    /*public Map<String,Object> querySaleChancesByParams02(SaleChanceQuery saleChanceQuery){
        *//**
         * 先查询缓存
         *    缓存存在  返回缓存数据 list
         *    缓存不存在
         * 执行数据库查询
         *   记录存在
         *        将记录添加到缓存
         *       返回结果 list
         *//*
        Map<String,Object> map=new HashMap<String,Object>();
        List<SaleChance> saleChances=null;

        *//**
         * 影响key 变化的因素
         *   pageNum
         *   pageSize
         *   customerName
         *   createMan
         *   state
         *//*
        String key="saleChance::list::pageNum::"+saleChanceQuery.getPageNum()+"::pageSize::"+saleChanceQuery.getPaseSize()+"::customerName::"+saleChanceQuery.getCustomerName()+"::createMan::"+saleChanceQuery.getCreateMan()+"::state::"+saleChanceQuery.getState();
        // 从缓存查询数据
        saleChances=listOperations.range(key,0,-1);
        if(CollectionUtils.isEmpty(saleChances)){
            // 到数据库查询数据
            saleChances= saleChanceDao.selectByParams(saleChanceQuery);
            // 数据库中存在记录
            if(!CollectionUtils.isEmpty(saleChances)){
                listOperations.rightPushAll(key,saleChances);
            }
        }
        PageInfo<SaleChance> pageInfo=new PageInfo<SaleChance>(saleChances);
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }*/
}
