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
        data: JSON.stringify(sendData)
    })
}

function like(btn, id) {
    $.post("/like", {"id": id}, function (data) {
        $(btn).children("span").text(data.data);
    })
    btn.blur();
}

function getReply(btn, id) {
    if ($("#comment-" + id).text() == "") {
        $("#comment-" + id).load("/comment/secondary", {"id": id});
    } else {
        $("#comment-" + id).text("")
    }
    btn.blur();
}

function changeReply(id, userName, parentId) {
    $("#reply-type-" + id).val("3");
    $("#reply-parentId-" + id).val(parentId);
    $("#input-" + id).attr("placeholder", "回复 " + userName + ":");
    $("#input-" + id).focus();
}

function replyCancal(btn, id) {
    $("#reply-type-" + id).val("2");
    $("#reply-parentId-" + id).val(id);
    $("#input-" + id).val("");
    $("#input-" + id).attr("placeholder", "评论一下...");
    btn.blur();
}

function reply(btn, id) {
    var content = $("#input-" + id).val();
    if (content.length == 0) {
        alert("回复内容不能为空！");
        btn.blur();
    } else {
        postComment($("#reply-type-" + id).val(),$("#reply-parentId-" + id).val(),content);
    }
}