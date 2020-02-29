$(function () {
    $("#btn-comment").click(function () {
        var comment = $("#txt-comment").val();
        if (comment.length == 0) {
            $("#div-alert").html("评论内容不能为空！！！").addClass("p-2")
        } else {
            postComment(1, $("#parentId").val(), comment)
            this.blur()
        }
    });
    $("#btn-comment").focusout(function () {
        $("#div-alert").html("").removeClass("p-2")
    });
})

function postComment(type, parentId, comment) {
    var sendData = {
        "type": type,
        "parentId": parentId,
        "content": comment
    };
    $.ajax({
        url: "/comment",
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(sendData),
        success: function (data, status) {

        }
    })
}

function like(btn, id) {
    $.post("/like", {"id": id}, function (data) {
        $(btn).children("span").text(data.data);
    })
    btn.blur();
}

function getReply(btn,id) {
    if($("#comment-" + id).text()==""){
        $("#comment-" + id).load("/comment/secondary", {"id": id});
    }else{
        $("#comment-" + id).text("")
    }
    btn.blur();
}