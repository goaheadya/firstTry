/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容！！！");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=076b5374b8153eb69928&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 二级评论
 */
function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论列表
 */
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);

    // //是否展开二级评论，简单方法
    // comments.toggleClass("in");
    // e.classList.toggle("active");

    if (comments.hasClass("in")) {
        comments.removeClass("in");
        e.classList.remove("active");
    } else {
        let subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length !== 1) {
            // 展开二级评论
            comments.addClass("in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    let mediaLeftElement = $("<div/>", {
                        "class" : "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-circle",
                        "src": "https://avatars.githubusercontent.com/u/50624203?v=4"
                    }));

                    let mediaBodyElement = $("<div/>", {
                        "class" : "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": "Go_Ahead"
                    })).append($("<div/>", {
                        "text": comment.content
                    })).append($("<div/>", {
                        "class" : "menu",
                    }).append($("<span/>", {
                        "class" : "pull-right",
                        "text": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    let mediaElement = $("<div/>", {
                        "class" : "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    let commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        // html: comment.content
                    }).append(mediaElement).append($("<hr/>", {
                            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comment-sp",
                        }));

                    subCommentContainer.prepend(commentElement);
                });
                // 展开二级评论
                comments.addClass("in");
                e.classList.add("active");
            });
        }
    }
}