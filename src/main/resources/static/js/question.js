$(function () {

    $("#btn-comment").click(function () {
        var comment = $("#txt-comment").val();
        if (comment.length == 0) {
            $("#div-alert").html("评论内容不能为空！！！").addClass("p-2")
        } else {
            postComment(1, $("#parentId").val(), comment)
        }

    });

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

    $("#btn-comment").focusout(function () {
        $("#div-alert").html("").removeClass("p-2")
    });
})