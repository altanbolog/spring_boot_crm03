/**
 * Created by lp on 2018/6/25.
 */

$(function () {
    $("#submit").click(function () {
        var userName=$("#userName").val();
        var userPwd=$("#userPwd").val();
        if(isEmpty(userName)){
            alert("请输入用户名!");
            return;
        }
        if(isEmpty(userPwd)){
            alert("请输入密码!");
            return;
        }

        /**
         * 发送ajax 请求队登录接口
         */
        $.ajax({
            type:"post",
            url:ctx+"/user/login",
            data:{
                userName:userName,
                userPwd:userPwd
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    /**
                     * 1.写入cookie
                     * 2.跳转至主页面
                     */
                    $.cookie("userIdStr",data.result.userIdStr);
                    $.cookie("userName",data.result.userName);
                    $.cookie("trueName",data.result.trueName);
                    window.location.href=ctx+"/main";
                }else{
                    alert(data.msg);
                }
            }
        })



    })
});