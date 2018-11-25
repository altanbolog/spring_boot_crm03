package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.annotations.Log;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.querys.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lp on 2018/6/26.
 */
@Controller
@RequestMapping("saleChance")
public class SaleChanceController extends BaseController{
    @Resource
    private SaleChanceService saleChanceService;


    @RequestMapping("index")
    public  String index(){
        //request.setAttribute("ctx",request.getContextPath());
        return "sale_chance";
    }

    @RequestMapping("querySaleChancesByParams")
    @ResponseBody
    @Log(module = "营销管理",desc = "多条件查询")
    public Map<String,Object> querySaleChancesByParams(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer rows, SaleChanceQuery saleChanceQuery){
        saleChanceQuery.setPageNum(page);
        saleChanceQuery.setPaseSize(rows);
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    @Log(module = "营销管理",desc = "营销记录添加")
    public ResultInfo saveSaleChance(SaleChance saleChance){
        saleChanceService.saveSaleChance(saleChance);
        return success("机会数据添加成功!");
    }

    @RequestMapping("update")
    @ResponseBody
    @Log(module = "营销管理",desc = "营销记录更新")
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("机会数据更新成功!");
    }

    /**
     *
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    @Log(module = "营销管理",desc = "营销记录删除")
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChancesBatch(ids);
        return success("机会数据删除成功!");
    }

}
