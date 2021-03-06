<%--
  Created by IntelliJ IDEA.
  User: Jason Xu
  Date: 2018/4/14
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>BITs云盘 - BITs Cloud Disk</title>
    <link rel="stylesheet" href="<%=___path%>css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=___path%>css/font-awesome.css"><!-- 右键菜单需要用到的图标 -->
    <link rel="stylesheet" href="<%=___path%>css/btn-circle.css">
    <link rel="stylesheet" href="<%=___path%>css/search-bar.css">
    <link rel="stylesheet" href="<%=___path%>css/main.css">
    <link rel="stylesheet" href="<%=___path%>css/jquery.fileupload.css"><!-- 美化选择文件的图标和进度条 -->
    <link rel="icon" type="image/png" href="<%=___path%>img/favicon/cloud.png" sizes="16x16">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .breadcrumb > li + li:before {
            content: "> ";
        }

        /* 右键菜单 */
        .right-click-menu > a {
            color: #337ab7 !important;
            font-size: 17px !important;
        }
    </style>
    <style>
        /* layout.css Style */
        .upload-drop-zone {
            height: 200px;
            border-width: 2px;
            margin-bottom: 20px;
        }

        /* skin.css Style*/
        .upload-drop-zone {
            color: #ccc;
            border-style: dashed;
            border-color: #ccc;
            line-height: 200px;
            text-align: center
        }

        .upload-drop-zone.drop {
            color: #222;
            border-color: #222;
        }
    </style>
</head>

<body>
<div class="container-fluid layout-wrapper">
    <div class="row layout-header">
        <!-- LOGO -->
        <div class="col-xs-2" style="text-align: center;">
            <h3 class="logo"><img src="<%=___path%>img/favicon/cloud.png"/>BITs云盘</h3>
        </div>

        <!-- 任务列表按钮 -->
        <div class="col-xs-1">
            <a href="#transfer" data-toggle="pill" class="btn btn-default btn-circle btn-transfer" id="btn_transfer">
                <img src="./img/icon/transfer.png"/>
                <span class="badge loading_mission_count"></span>
            </a>
        </div>


        <!-- 搜索框 -->
        <div class="col-xs-2">
            <div class="input-group stylish-input-group" id="search_control">
                <input type="text" class="form-control" placeholder="输入文件名" oninput="$('#btn_search').click();"/>
                <span class="input-group-addon">
                        <a href="#search" data-toggle="pill" id="btn_search">
                            <span class="glyphicon glyphicon-search"></span>
                        </a>
                    </span>
            </div>
        </div>

        <!-- 视图/排序控制 -->
        <div id="view_control">
            <div class="col-xs-2 col-xs-offset-1">
                <div class="btn-group btn-group-view" data-toggle="buttons" style="display: none;">
                    <label class="btn btn-default active" style="border: 0px;" title="列表视图">
                        <input type="radio" name="view" id="view_by_list" autocomplete="off" checked="checked"/><span
                            class="glyphicon glyphicon-list" aria-hidden="true" style="color: #337ab7;"></span>
                    </label>
                    <label class="btn btn-default disabled" style="border: 0px;" title="略缩图视图">
                        <input type="radio" name="view" id="view_by_block" autocomplete="off"/><span
                            class="glyphicon glyphicon-th-large" aria-hidden="true" style="color: #337ab7;"></span>
                    </label>
                </div>
                <div class="btn-group btn-group-sort" data-toggle="buttons" style="display: none;">
                    <label class="btn btn-default active" style="border: 0px;" title="按字母排序" id="sort_by_alpha">
                        <input type="radio" name="sort" autocomplete="off" checked="checked"/>
                        <span class="glyphicon glyphicon-sort-by-alphabet" aria-hidden="true"
                            style="color: #337ab7;"></span>
                    </label>
                    <label class="btn btn-default" style="border: 0px;" title="按时间排序" id="sort_by_time">
                        <input type="radio" name="sort" autocomplete="off"/>
                        <span class="glyphicon glyphicon-time" aria-hidden="true" style="color: #337ab7;"></span>
                    </label>
                </div>
            </div>
        </div>


        <!-- 添加文件按钮 -->
        <div class="col-xs-2 col-xs-offset-2">
            <button type="button" class="btn btn-primary fileinput-button btn-upload" id="btn_upload">
                <span>添加文件</span>
                <input id="fileupload" type="file" name="files[]" multiple onchange="showFileUploadModal(files);">
            </button>
        </div>
    </div><!-- end of header -->


    <div class="row layout-body">
        <div class="col-xs-2 layout-body-left">
            <!-- 当前用户 -->
            <div class="layout-user">
                <img src="<%=___path%>img/icon/user.png" alt="头像" class="img-circle" id="user_photo"/>
                <p id="user_nickname">nickname</p>
                <div class="progress" data-toggle="popover" data-placement="right" data-trigger="hover">
                    <div class="progress-bar" role="progressbar" id="user_capacity" style="width: 0%;min-width: 2em;">
                        0%
                    </div>
                </div>
            </div>


            <!-- 导航栏 -->
            <div class="layout-nav">
                <ul class="nav nav-pills nav-stacked">
                    <li role="presentation"><a data-toggle="pill" href="#recent" id="nav_recent">
                        <span class="glyphicon glyphicon-time nav-title-icon"></span>
                        最近
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#all" id="nav_all">
                        <span class="glyphicon glyphicon-list-alt nav-title-icon"></span>
                        全部
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#doc" id="nav_doc">
                        <span class="glyphicon glyphicon-file nav-title-icon"></span>
                        文档
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#photo" id="nav_photo">
                        <span class="glyphicon glyphicon-picture nav-title-icon"></span>
                        图片
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#video" id="nav_video">
                        <span class="glyphicon glyphicon-facetime-video nav-title-icon"></span>
                        视频
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#audio" id="nav_audio">
                        <span class="glyphicon glyphicon-music nav-title-icon"></span>
                        音乐
                    </a></li>
                    <br/>
                    <li role="presentation" class="disabled"><a data-toggle="pill" href="#safebox">
                        <span class="glyphicon glyphicon-lock nav-title-icon"></span>
                        保险箱
                    </a></li>
                    <li role="presentation" class="disabled"><a data-toggle="pill" href="#share">
                        <span class="glyphicon glyphicon-share nav-title-icon"></span>
                        分享链接
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#recycle" id="nav_recycle">
                        <span class="glyphicon glyphicon-trash nav-title-icon"></span>
                        回收站
                    </a></li>
                    <br/>
                   <%-- <li role="presentation"><a data-toggle="pill" href="#note" id="nav_note">
                        <span class="glyphicon glyphicon-trash nav-title-icon"></span>
                        云笔记
                    </a></li>
                    <li role="presentation"><a data-toggle="pill" href="#imagebed" id="nav_imagebed">
                        <span class="glyphicon glyphicon-trash nav-title-icon"></span>
                        图床
                    </a></li>--%>
                </ul>
            </div>
        </div><!-- end of left-body -->


        <div class="col-xs-10 layout-body-content">
            <!-- 主视图 -->
            <div class="tab-content" id="main_view">

                <div id="search" class="tab-pane">
                    <div class="fixed-title">
                        <span>共<span>n</span>个结果</span>
                    </div>
                </div>


                <div id="transfer" class="tab-pane">
                    <div class="fixed-title">
                        <span>任务列表</span>
                    </div>
                    <div class="dynamic-mission-list-title" id="dynamic_title_loading">
                        <p>进行中 (剩余<span class="loading_mission_count"></span>)</p>
                        <div class="btn-modify-all">
                            <button class="btn btn-default" id="cancel_all_mission">取消全部</button>
                        </div>
                    </div>
                    <ul id="loading_mission_list">

                    </ul>
                    <div class="dynamic-mission-list-title" id="dynamic_title_complete">
                        <p>已完成 (<span id="complete_mission_count">0</span>)</p>
                        <div class="btn-modify-all" id="remove_all_record">
                            <button class="btn btn-default">清空记录</button>
                        </div>
                    </div>
                    <ul id="complete_mission_list">

                    </ul>
                </div>


                <div id="recent" class="tab-pane">
                    <div class="fixed-title">
                        <span>最近七天</span>
                    </div>
                </div>


                <div id="all" class="tab-pane">
                    <div class="disk-file-path-wrapper">
                        <div class="select-all" id="select_all">
                            <input type="checkbox">
                        </div>
                        <ol class="breadcrumb disk-file-path" id="disk_file_path">
                            <li data-folder-id="1"><a href="javascript:void(0)" class="active">我的网盘</a></li>
                        </ol>
                        <button class="btn btn-default mkfolder" id="mkfolder">新建文件夹</button>
                    </div>
                </div>


                <div id="doc" class="tab-pane"></div>


                <div id="photo" class="tab-pane">
                    <div class="picture-wall"></div>
                </div>


                <div id="video" class="tab-pane"></div>


                <div id="audio" class="tab-pane"></div>


                <div id="safebox" class="tab-pane">
                    <div class="panel panel-primary center-block input-safePassword">
                        <div class="panel-heading">请输入保险箱密码</div>
                        <div class="panel-body">
                            <form>
                                <div class="form-group">
                                    <input type="password" class="form-control" placeholder="密码">
                                </div>
                                <button class="btn btn-primary" style="width: 100%;">确认</button>
                            </form>
                        </div>
                    </div>
                </div>


                <div id="share" class="tab-pane">share</div>


                <div id="recycle" class="tab-pane">
                    <div class="fixed-title">
                        <span>文件在回收站保存7天</span>
                        <div class="btn-modify-all">
                            <button class="btn btn-default" id="clear_recycle">全部清空</button>
                        </div>
                    </div>
                    <ul id="recycle_folder"></ul>
                </div>

                <div id="note" class="tab-pane">
                    <div class="fixed-title">
                        <span>云笔记</span>
                    </div>
                    <ul></ul>
                    <ul id="note-list">

                    </ul>
                </div>

                <div id="imagebed" class="tab-pane">
                    <div class="fixed-title">
                        <span>图床</span>
                        <div class="btn-modify-all">
                            <button class="btn btn-default" id="imagebed_upload">上传</button>
                        </div>
                    </div>
                    <ul/>
                    <div class="container">
                        <div class="panel panel-primary">
                            <div class="panel-heading" style="text-align: center;"><strong> 上传文件 </strong></div>
                            <div class="panel-body">

                                <!-- Standar Form -->
                                <h4> 请选择文件 </h4>
                                <form action="/image/upload" method="post" enctype="multipart/form-data"
                                      id="js-upload-form">
                                    <div class="form-inline">
                                        <div class="form-group">
                                            <input type="file" name="file" id="js-upload-files" multiple>
                                        </div>
                                        <button type="submit" class="btn btn-sm btn-primary" id="js-upload-submit"> 上传
                                        </button>
                                    </div>
                                </form>

                                <!-- Drop Zone -->
                                <h4> 或者将文件拖拽到这里 </h4>
                                <div class="upload-drop-zone" id="drop-zone">
                                    文件显示
                                </div>

                                <!--&lt;!&ndash; Progress Bar &ndash;&gt;-->
                                <!--<div class="progress">-->
                                <!--<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"-->
                                <!--aria-valuemax="100" style="width: 60%;">-->
                                <!--<span class="sr-only"> 已上传 50% </span>-->
                                <!--</div>-->
                                <!--</div>-->

                                <!-- Upload Finished -->
                                <div class="js-upload-finished">
                                    <h3> 已上传文件 </h3>
                                    <div class="list-group" id="list-group">
                                        <!--<a href="#" class="list-group-item list-group-item-success"><span-->
                                        <!--class="badge alert-success pull-right">Success</span>image-01.jpg</a>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 模态框 -->
<div class="modal fade" id="path_modal" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" id="modal_btn_close"><span>&times;</span>
                </button>
                <h4 class="modal-title">选择路径</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="modal-file">
                        <div class="modal-file-info">
                            <div class="thumb">
                                <img src="./img/icon/word.png" class="thumb-icon" id="modal_file_thumb">
                            </div>
                            <div class="modal-file-title">
                                <span class="file-name" id="modal_file_name"></span>
                            </div>
                        </div>
                        <div class="modal-addtional-info" id="modal_addtional_info">
                            等<span>?</span>个文件
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="dirbox">
                        <div class="dirbox-header">
                            <label><span id="dirbox_header_action">上传</span>到：</label>
                            <span id="dirbox_path" data-folder-id="1">我的网盘</span>
                        </div>
                        <div class="dirbox-body">
                            <ul>
                                <li>
                                    <div class="treeNode-info" style="padding-left: 5px;" data-folder-id="1"
                                         id="treeNode_root">
                                        <span class="glyphicon glyphicon-folder-open"></span>
                                        <span class="treeNode-info-name">我的网盘</span>
                                    </div>

                                    <ul>

                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-lg" id="modal_btn_submit">开始上传</button>
                <button type="button" class="btn btn-default btn-lg" data-dismiss="modal" id="modal_btn_cancel">取消
                </button>
            </div>
        </div>
    </div>
</div>

<script src="<%=___path%>js/jquery-3.2.1.min.js"></script>
<script src="<%=___path%>js/bootstrap.min.js"></script>
<script src="<%=___path%>js/BootstrapMenu.min.js"></script>
<script src="<%=___path%>js/jquery.ui.widget.js"></script>
<script src="<%=___path%>js/jquery.iframe-transport.js"></script>
<script src="<%=___path%>js/jquery.fileupload.js"></script>
<script src="<%=___path%>js/browser-md5-file.js"></script>
<script src="<%=___path%>js/clipboard.min.js"></script>
<script src="<%=___path%>lib/dropzone/dropzone.js"></script>

<script src="<%=___path%>js/util.js"></script>
<script src="<%=___path%>js/file-system.js"></script>
<script src="<%=___path%>js/modal.dirbox.js"></script>
<script src="<%=___path%>js/fileupload.main.js"></script>
<script src="<%=___path%>js/contextmenu.main.js"></script>
<script src="<%=___path%>js/contextmenu.recycle.js"></script>
<script src="<%=___path%>js/search.js"></script>
<script>
    var _path = '<%=___path%>';
    // 禁用默认的右键菜单
    document.oncontextmenu = function () {
        if(!event.ctrlKey){
            return false;
        }
    };
    $(function () {
        $("#nav_all").tab("show");
        $.ajax({
            type: "GET",
            url: _path + "/user/" + sessionStorage.getItem("user_username"),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                var user = result.user;
                sessionStorage.setItem("user_id", user.id);
                sessionStorage.setItem("user_username", user.username);
                sessionStorage.setItem("user_nickname", user.username);
                sessionStorage.setItem("user_usedSize", user.usedSize);
                sessionStorage.setItem("user_memorySize", user.memorySize);
                sessionStorage.setItem("user_photoURL", _path + "/user/" + user.username + "/image");

                // 加载登录用户的信息
                $("#user_photo").attr("src", sessionStorage.getItem("user_photoURL"));
                $("#user_nickname").text(sessionStorage.getItem("user_nickname"));
                var percentage = getUsedPercentage(sessionStorage.getItem("user_usedSize"), sessionStorage.getItem("user_memorySize"));
                $("#user_capacity").css("width", percentage).text(percentage);
                // 显示容量信息的悬浮框
                $("[data-toggle='popover']").popover({
                    html: true,
                    title: "容量信息",
                    content: function () {
                        var node = $('<p style="width:200px;">已使用:<span id="usedSize"></span>&nbsp;&nbsp;&nbsp;总容量:<span id="memorySize"></span></p>');
                        node.find("#usedSize").text(getReadableSize(sessionStorage.getItem("user_usedSize")));
                        node.find("#memorySize").text(getReadableSize(sessionStorage.getItem("user_memorySize")));
                        return node;
                    }
                });
                // 获取回收站文件
                getRecycleItems();
                // 显示初始文件夹
                showFolderContents(1);// 约定初始文件夹ID=1
            }
        });
    });

    // 判断是否需要重定向
    $(document).ajaxError(function (event, xhr) {
        if (xhr.status == 401) {
            alert("登录已失效，请重新登录");
            top.location = _path + "login";
            return false;
        }
    });

    $(document).ajaxSend(function (event, xhr) {
        var token = sessionStorage.getItem("token");
        xhr.setRequestHeader("Authorization", "Bearer " + token);
    });
</script>
<script>
    $("#imagebed_upload").click(function () {
        $.ajax({
            type: "POST",dataType: "json",
            url: _path + "/image/upload",
            contentType: "application/json; charset=utf-8",
            success: function (user) {
                sessionStorage.setItem("image_id", user.userId);
            }
        });

        $.ajax({
            type: "GET",dataType: "json",
            url: _path + "image/" + sessionStorage.getItem("image_id"),
            contentType: "application/json; charset=utf-8",
            success: function (user) {
                $("#imagebed").append("<div class='input-group'><input class='btn btn-default copy-button form-control' id='clipboard' align='left' style='width:320px' value='https://github.com/zenorocha/clipboard.js.git'><button class='btn btn-default copy-button' data-clipboard-target='#foo'><img src='https://clipboardjs.com/assets/images/clippy.svg' width='10' alt='复制到剪贴板'></button>&nbsp;<a onclick='MyEventHub.emitEvent('track_event', ['上传', '打开Link']);' href='http://oqcr0rg2c.bkt.clouddn.com/17-12-16/87090161.jpg' target='_blank' class='btn btn-default'>打开</a></div>")
            }
        });
    });
</script>
<script>
    var clipboard = new Clipboard('.btn', {
        target: function () {
            return document.querySelector('#clipboard');
        }
    });
    clipboard.on('success', function (e) {
        console.log(e);
    });
    clipboard.on('error', function (e) {
        console.log(e);
    });
</script>
<script>
    function loadNotes() {
        $.ajax({
            type: "GET",dataType: "json",
            contentType: "application/json;charset=UTF-8",
            url: "/users/" + sessionStorage.getItem("user_username") + "/notes",
            success: function (data) {
                var node = document.createElement("li");      //创建一个li节点
                var textnode = document.createTextNode(node);   //创建一个文本节点内容
                $("#note-list").appendChild(textnode);
            }
        });
    }
</script>
<script>
    jQuery(document).ready(function () {
        var dropZone = document.getElementById('drop-zone');
        var uploadForm = document.getElementById('js-upload-form');

        var startUpload = function (files) {
            console.log(files);
//            var formData = new FormData($( "#uploadForm" )[0]);
            $.ajax({
                type: "POST",
                data: files,
                cache: false,
                processData: false,
                url: "/image/upload",
                success: function (data) {
//                    $("#content").text(data);
                },
                error: function (data) {
                    alert(data);
                }
            });
        };

        uploadForm.addEventListener('submit', function (e) {
            var uploadFiles = document.getElementById('js-upload-files').files;
            e.preventDefault();

            startUpload(uploadFiles)
        });

//        dropZone.ondrop = function (e) {
//            e.preventDefault();
//            this.className = 'upload-drop-zone';
//
////            startUpload(e.dataTransfer.files)
//        };

        dropZone.ondragover = function () {
            this.className = 'upload-drop-zone drop';
            return false;
        };

        dropZone.ondragleave = function () {
            this.className = 'upload-drop-zone';
            return false;
        }
    });
</script>
<script>
    var url;
    $("#drop-zone").dropzone({
        url: "/image/upload",
        addRemoveLinks: true,
        dictRemoveLinks: "x",
        dictCancelUpload: "x",
        maxFiles: 10,
        maxFilesize: 5,
        acceptedFiles: "image/*",
        success: function (file, data) {
            url = data.data.url;
        },
        init: function () {
            this.on("success", function (file) {
                console.log("File " + file.name + " uploaded");
                $("#list-group").append("<a href='#' class='list-group-item list-group-item-success'><span class='badge alert-success pull-right'>Success</span>" + file.name + "</a>");
                $("#list-group").append("<div class='input-group'><input class='btn btn-default copy-button form-control' id='clipboard' align='left' style='width:320px' value='" + url + "'><button class='btn btn-default copy-button' data-clipboard-target='#foo'><img src='https://clipboardjs.com/assets/images/clippy.svg' width='10' alt='复制到剪贴板'></button>&nbsp;<a onclick='MyEventHub.emitEvent('track_event', ['上传', '打开Link']);' href='" + url + "' target='_blank' class='btn btn-default'>打开</a></div>")
            });
//            this.on("removedfile", function (file) {
//                console.log("File " + file.name + " removed");
//            });
        }
    });
</script>
</body>
</html>
