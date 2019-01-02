var lastPresentation;
$(function () {
    $("#btn_search").click(function () {
        var active = $('[role="presentation"][class="active"]');
        if (active.length == 0) {
            active = lastPresentation;
        } else {
            lastPresentation = active;
            active.removeClass("active");
        }
        console.log(active);

        var input = $('#search_control input').val();
        if (input.length == 0) {
            // $("#search .fixed-title span span").text("0");
            // $("#search ul").remove();
            setTimeout(function () {
                active.find('a').click();
            }, 300);
            //active.find('a').click();
            return;
        }
        $("#view_control").css("visibility", "hidden");
        $.ajax({
            type: "GET",dataType: "json",
            url: _path + "/users/" + sessionStorage.getItem("user_username") + "/disk/search?input=" + input,
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                var folders = result.folders;
                var files = result.files;

                $("#search .fixed-title span span").text(folders.length + files.length);
                var node = $("<ul></ul>");
                generateFolderNode(folders, node);
                generateFileNode(files, node);
                node.find(".disk-file-item").removeClass("disk-item");
                node.find(".file-name").each(function () {
                    var filename = $(this).text();
                    $(this).html(filename.replace(input, '<span class="searched-word">' + input + '</span>'));
                });
                $("#search ul").remove();
                node.appendTo($("#search"));
            }
        });

    });
});