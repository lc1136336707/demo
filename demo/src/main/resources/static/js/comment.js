function commentSub(type,parentId) {
    var commParentId = $("[name="+parentId+"]").val();
    var commentContent = $("#comment-content").val();
    $.ajax({
        url:"/comment",
        type:"post",
        contentType:"application/json",
        data:JSON.stringify({
            parentId : commParentId,
            content : commentContent,
            type : type
        }),
        dataType:"json",
        success:function(result){
            if(result.code == 200){
                $("#comment").attr("style","display:none;");
            }else{
                if(result.code == 2002){
                    var login = window.confirm("当前操作需要登录，请登录后重试");
                    if(login == true){
                        var callback = window.location.href;
                        var content = $("#comment-content").val();
                        window.location.href="https://github.com/login/oauth/authorize?client_id=d9f2b40934da6c377fc8&redirect_uri=http://localhost:8080/callback&scope=user&state=1";
                        var storage = window.localStorage;
                        storage.setItem("callback",callback);
                        storage.setItem("content",content);
                    }
                }else{
                    alert(result.message);
                }
            }
        }});
}
function showComment(id){
    var idNum = id.split('-')[1];
    var status = $("#collapseExample-"+idNum).attr("class");
    if(status == "collapse"){
        $("#"+id).attr("style","color:#499ef3")
        $("#collapseExample-"+idNum).attr("class","collapse.in");

    }else{
        $("#"+id).attr("style","")
        $("#collapseExample-"+idNum).attr("class","collapse");
    }
}