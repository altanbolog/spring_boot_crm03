/**
 * Created by lp on 2018/6/26.
 */

$(function () {
   $("#dg").datagrid({
       rowStyler:function (index,row) {
           if(row.devResult==2){
               return "background-color:green";
           }else if(row.devResult==3){
               return "background-color:red";
           }else if(row.devResult==0 ||row.devResult==1 ){
               return "background-color:orange";
           }
       }
   })
});

/**
 * 多条件查询营销机会数据
 */
function querySaleChancesByParams() {
    $("#dg").datagrid("load",{
        customerName:$("#customerName").val(),
        createMan:$("#createMan").val(),
        state:$("#state").combobox("getValue")
    })
}

function formatterState(val,row,index) {
   /* console.log(val);
    console.log(row);
    console.log(index);*/
   if(val==0){
       return "未分配";
   }else if(val==1){
       return "已分配"
   }else{
       return "未定义";
   }
}

function formatterDevResult(val) {
    /**
     *   0:未开发
     *   1:开发中
     *   2:开发成功
     *   3:开发失败
      */
    if(val==0){
        return "未开发";
    }else if(val==1){
        return "开发中";
    }else if(val==2){
        return "开发成功";
    }else{
        return "开发失败";
    }

}

/**
 * 打开添加对话框
 */
function openAddSaleChanceDialog() {
    $("#dlg").dialog("open");
}

function closeDialog() {
    $("#dlg").dialog("close");
}
/**
 * 提交表单
 */
function saveOrUpdateSaleChance() {
    var url=ctx+"/saleChance/save";
    if(!isEmpty($("#sid").val())){
        url=ctx+"/saleChance/update";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function (param) {
            param.createMan=$.cookie("trueName");
            console.log(param.createMan);
            return $(this).form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if(data.code==200){
                /**
                 * 关闭对话框
                 * 刷新表格
                 */
                closeDialog();
                querySaleChancesByParams();
                /**
                 * 情况表单数据
                 */
                clearFormData();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}

function clearFormData() {
    //$("#fm").form("clear");// 可能会出现红色信息提示
    $("input[name='customerName']").val("");
    $("input[name='chanceSource']").val("");
    $("input[name='cgjl']").val("");
    $("input[name='overview']").val("");
    $("input[name='linkMan']").val("");
    $("input[name='linkPhone']").val("");
    $("input[name='description']").val("");
    $("#cc").combobox("setValue","");// 通过name 定位节点存在问题?
    // 更新时id 值需要清空
    $("#sid").val("");
}

/**
 * 打开修改对话框
 */
function openModifySaleChanceDialog() {
    /**
     * 判断选择的记录条数
     *   !=1  非法
     */
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待更新记录!","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录更新!","warning");
        return;
    }
    $("#fm").form("load",rows[0]);// 填充数据到表单中
    $("#dlg").dialog("open").dialog("setTitle","更新机会数据");
}


function deleteSaleChance() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除记录!","warning");
        return;
    }

    /*
      执行删除
        参数形式:ids=1&ids=2&ids=3
     */
    var ids="ids=";
    for(var i=0;i<rows.length;i++){
        // ids=1&ids=
        // ids=1&ids=2&ids=
       if(i!=(rows.length-1)){
           ids=ids+rows[i].id+"&ids=";
       }else{
           ids=ids+rows[i].id;
       }
    }

    /**
     *  发送ajax执行删除操作
     */
    $.messager.confirm("来自crm","确定删除选择的"+rows.length+"条记录?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/saleChance/delete",
                data:ids,
                success:function (data) {
                    if(data.code==200){
                        querySaleChancesByParams();
                    }else{
                        $.messager.alert("来自crm","请选择待删除记录!","error");
                    }
                }
            })
        }
    })


}