<!doctype html>
<html>
<head>
<#include "common.ftl" >
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/sale.chance.js"></script>
</head>
<body style="margin: 1px">
<table id="dg"  class="easyui-datagrid"
        pagination="true" rownumbers="true"
       url="${ctx}/saleChance/querySaleChancesByParams" fit="true" toolbar="#tb" >
    <thead>
        <tr>
            <th field="cb" checkbox="true">编号</th>
            <th field="id" width="50" align="center">编号</th>
            <th field="chanceSource" width="200" align="center" >机会来源</th>
            <th field="customerName" width="50" align="center">客户名称</th>
            <th field="cgjl" width="50" align="center" >成功几率</th>
            <th field="overview" width="200" align="center">概要</th>
            <th field="linkMan" width="100" align="center">联系人</th>
            <th field="linkPhone" width="100" align="center">联系电话</th>
            <th field="description" width="200" align="center" >机会描述</th>
            <th field="createMan" width="100" align="center">创建人</th>
            <th field="createDate" width="100" align="center">创建时间</th>
            <th field="updateDate" width="100" align="center">更新时间</th>
            <th field="assignMan" width="200" align="center" >指派人</th>
            <th field="assignTime" width="200" align="center" >指派时间</th>
            <th field="state" width="100" align="center"  formatter="formatterState">分配状态</th>
            <th field="devResult" width="200" align="center" formatter="formatterDevResult" >客户开发状态</th>
        </tr>
    </thead>
</table>


<div id="tb">
    <a href="javascript:openAddSaleChanceDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
    <a href="javascript:openModifySaleChanceDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">更新</a>
    <a href="javascript:deleteSaleChance()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
    <br/>
    客户名:<input type="text" id="customerName" />
    创建人:<input type="text" id="createMan"/>
    分配状态:
        <select id="state" class="easyui-combobox" name="state" style="width:200px;" panelHeight="auto">
            <option value="">全部</option>
            <option value="0">未分配</option>
            <option value="1">已分配</option>
        </select>
    <a class="easyui-linkbutton" iconCls="icon-search" plain="true" href="javascript:querySaleChancesByParams()" >搜索</a>
</div>

<div id="dlg" class="easyui-dialog" title="添加机会数据" style="width: 600px;height: 300px" buttons="#bt" closed="true">
    <form method="post" id="fm">
        <table>
            <tr>
                <td>客户名称:</td>
                <td><input type="text" name="customerName" class="easyui-validatebox"  required="required"/></td>
                <td>机会来源:</td>
                <td><input type="text" name="chanceSource"/></td>
            </tr>
            <tr>
                <td>成功几率:</td>
                <td><input type="text" name="cgjl"/></td>
                <td>概要:</td>
                <td><input type="text" name="overview"/></td>
            </tr>
            <tr>
                <td>客联系人:</td>
                <td><input type="text" name="linkMan" class="easyui-validatebox"  required="required"/></td>
                <td>联系电话:</td>
                <td><input type="text" name="linkPhone" class="easyui-validatebox"  required="required"/></td>
            </tr>
            <tr>
                <td>描述:</td>
                <td><input type="text" name="description"/></td>
                <td>分配人</td>
                <td>
                    <input id="cc" class="easyui-combobox" name="assignMan"
                           valueField="id" textField="trueName" url="${ctx}/user/querySaleCustomerManager" panelHeight="auto" />
                </td>
        </table>
        <input  id="sid" type="hidden" name="id"/>
    </form>
</div>

<div id="bt">
    <a href="javascript:saveOrUpdateSaleChance()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" href="javascript:closeDialog()">取消</a>
</div>


</body>
