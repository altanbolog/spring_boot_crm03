function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/**
 * 用户退出操作
 */
function logout() {
    $.messager.confirm("来自crm","确定退出?",function (r) {
        if(r){
            // 5秒后执行退出操作
            $.messager.alert("来自crm","系统将在2秒后退出...","info");
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("trueName");
            setTimeout(function () {
                window.location.href=ctx+"/index";
            },2000);
        }
    })
}


function openPasswordModifyDialog() {
    $("#dlg").dialog("open").dialog("setTitle","密码修改");
}

function closePasswordModifyDialog() {
    $("#dlg").dialog("close");
}


function modifyPassword() {
    $("#fm").form("submit",{
        url:ctx+"/user/modifyPassword",
        onSubmit:function () {
            return $(this).form("validate");
        },
        success:function (data) {

            data=JSON.parse(data);
            console.log(data);
            if(data.code==200){
                /**
                 * 消息提示
                 */
                $.messager.alert("来自crm","密码修改成功,系统将在2秒后自动退出!","info");
                $.removeCookie("userIdStr");
                $.removeCookie("userName");
                $.removeCookie("trueName");
                setTimeout(function () {
                    window.location.href=ctx+"/index";
                },2000);
            }else{
                $.messager.alert("来自crm",data.msg);
            }
        }
    })
}