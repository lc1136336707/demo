function commentSub() {
    var questionId = $("[name='questionId']").val();
    var commentContent = $("#comment-content").val();
    $.ajax({
        url:"/comment",
        type:"post",
        contentType:"application/json",
        data:JSON.stringify({
            parentId : questionId,
            content : commentContent,
            type : 1
        }),
        dataType:"json",
        success:function(result){
            if(result.code == 200){
                $("#comment").attr("style","display:none;");
            }else{
                if(result.code == 2002){
                    var login = window.confirm("当前操作需要登录，请登录后重试");
                    if(login == true){

                    }
                }else{
                    alert(result.message);
                }
            }
        }});
}