<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <style type="text/css">
        /*ul{*/
            /*margin-left: 300px;*/
        /*}*/
        #navi{
            margin-left: 235px;
        }
        #content1{
            margin-left: 200px;
        }
        #content2{
            margin-left: 200px;
        }
         li{
            display: inline-block;
            height: 70px;
            width: 200px;
            color: white;
            font-size: 25px;
        }
        p{
            color:lightgoldenrodyellow;
            font-size:30px;
            margin-left: 600px;
        }
        hr{
            color: white;
        }
    </style>
</head>
<body>

    <ul id="navi">
            <li>商品名称</li>
            <li>库存</li>
            <li>上下架情况</li>
            <li>操作</li>
     </ul>
<div id="div0">
    <div id="div1">
        <hr/>
        <p>上架的商品</p>
          <ul id="content1">
              <%--<li class="list_name"></li>--%>
              <%--<li class="list_num"></li>--%>
              <%--<li class="list_status"></li>--%>
              <%--<button class="list_button"></button>--%>
          </ul>
    </div>
    <div id="div2">
        <hr/>
        <p>已下架的商品</p>
        <ul id="content2">
            <%--<li class="list_name"></li>--%>
            <%--<li class="list_num"></li>--%>
            <%--<li class="list_status"></li>--%>
            <%--<button class="list_button"></button>--%>
        </ul>
    </div>
    <div id="div3">

    </div>
</div>
</table>
<script>
    $(function () {
        $.ajax({
            url:"selectAll",
            success:function (data) {
                for(var i=0;i<data.length;i++){
                    if(data[i].status==0 && data[i].pNum>0) {
                        var str = "<ul><li class='list_name'>" + data[i].pName+"</li>\n" +
                        "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
                        "            <li class='list_status'>" + data[i].status + "</li>\n" +
                        "            <button class='list_button' id='down' value='"+data[i].pId+"'>下架</button><br/><ul>";
                        $("#content1").append(str);
                    }else{
                        var str = "<ul><li class='list_name'>" + data[i].pName+"</li>\n" +
                        "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
                        "            <li class='list_status'>" + data[i].status + "</li>\n" +
                        "            <button class='list_button' id='up' value='"+data[i].pId+"'>上架</button><br/><ul>";
                        $("#content2").append(str);

                    }
                }
            }
        });

        $("#content1").on("click","#down",function () {
             var pId=$(this).val();

            // $(this).parent().remove();
            // $("#content2").append($(this).parent());

            $.ajax({
                url:"down",
                data:{
                    "pId":pId
                },
               success:function () {
                   $.ajax({
                       url:"selectAll",
                       success:function (data) {
                           $("#content1").html("");
                           $("#content2").html("");
                           for(var i=0;i<data.length;i++){
                               if(data[i].status==0 && data[i].pNum>0) {
                                   var str = "<ul><li class='list_name'>" + data[i].pName+"</li>\n" +
                                       "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
                                       "            <li class='list_status'>" + data[i].status + "</li>\n" +
                                       "            <button class='list_button' id='down' value='"+data[i].pId+"'>下架</button><br/><ul>";
                                   $("#content1").append(str);
                               }else{
                                   var str = "<ul><li class='list_name'>" + data[i].pName+"</li>\n" +
                                       "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
                                       "            <li class='list_status'>" + data[i].status + "</li>\n" +
                                       "            <button class='list_button' id='up' value='"+data[i].pId+"'>上架</button><br/><ul>";
                                   $("#content2").append(str);

                               }
                           }
                       }
                   });
               }
            });


        });


        $("#content2").on("click","#up",function () {
            var pId=$(this).val();
            $(this).parent().remove();
            $("#content2").append($(this).parent())
            $.ajax({
                url:"up",
                data:{
                    "pId":pId
                },
                success:function () {
                    $.ajax({
                        url:"selectAll",
                        success:function (data) {
                            $("#content1").html("");
                            $("#content2").html("");
                            for(var i=0;i<data.length;i++){
                                if(data[i].status==0 && data[i].pNum>0) {
                                    var str = "<ul><li class='list_name'>" + data[i].pName+"</li>\n" +
                                        "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
                                        "            <li class='list_status'>" + data[i].status + "</li>\n" +
                                        "            <button class='list_button' id='down' value='"+data[i].pId+"'>下架</button><br/><ul>";
                                    $("#content1").append(str);
                                }else{
                                    var str = "<ul><li class='list_name'>" + data[i].pName+"</li>\n" +
                                        "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
                                        "            <li class='list_status'>" + data[i].status + "</li>\n" +
                                        "            <button class='list_button' id='up' value='"+data[i].pId+"'>上架</button><br/><ul>";
                                    $("#content2").append(str);

                                }
                            }
                        }
                    });
                }
            });



        });


        // $.ajax({
        //     url:"selectAllP_type",
        //     type:"post",
        //     async:false,
        //     success:function(data){
        //         for(var i=0;i<data.length;i++){
        //             var p_type=data[i];
        //             var str=" <div class='future_ui__piece'><span><a><font color ='white' size='7'>"+data[i]+"</font></a></span>" +
        //                 "            </div>";
        //             $("#div3").append(str);
        //             // alert(p_type);
        //
        //             $.ajax({
        //                 url: "selectAllProductsP_type",
        //                 data: {
        //                     p_type:p_type
        //                 },
        //                 async:false,
        //                 success: function (data) {
        //
        //                     for (var i = 0; i < data.length; i++) {
        //                         // alert("子:" + data[i]);
        //                         var str = "<ul><li class='list_name'>" + data[i].pName + "</li>\n" +
        //                             "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
        //                             "            <li class='list_status'>" + data[i].status + "</li>\n" +
        //                             "            <button class='list_button' id='down' value='" + data[i].pId + "'>下架</button><br/><ul>";
        //                         $("#div3").append(str);
        //                     }
        //                 }
        //             });
        //         }
        //     }
        // });



        var type=[];
        $.ajax({
            url: "selectAllP_type",
            type: "post",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var str = " <div class='"+data[i]+"'><span ><a><font color ='white' size='7'>" + data[i] + "</font></a></span>" +
                        "            </div>";
                    $("#div3").append(str);
                }
                type=data;
            }
        });
        var str="<p>xzzzzzzzzzzzzzzzzzzzzzzz</p>"
        // $("#div3").children().hasClass("数码").append(str);
        $("#div3").append(str);
        $("div:has('."+data[i].pType+"')").append(str);


        // for(var i=0;i<type.length;i++) {
        //     $.ajax({
        //         url: "selectAllProductsP_type",
        //         data: {
        //             p_type: type.eq(i)
        //         },
        //         success: function (data) {
        //              var str="<div>"+type.eq(i)+"</div>";
        //              $("#div3").append(str);
        //             for (var i = 0; i < data.length; i++) {
        //                 // alert(data[i].pType);
        //
        //                 var str = "<ul><li class='list_name'>" + data[i].pName + "</li>\n" +
        //                     "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
        //                     "            <li class='list_status'>" + data[i].status + "</li>\n" +
        //                     "            <button class='list_button' id='down' value='" + data[i].pId + "'>下架</button><br/><ul>";
        //                 // $("#div3").children().hasClass(data[i].pType).append(str);
        //                 // $("div:has('."+data[i].pType+"')").append(str);
        //
        //             }
        //         }
        //     });
        // }

        // $.ajax({
        //     url:"selectAllP_type",
        //     type:"post",
        //     success:function(data){
        //         for(var i=0;i<data.length;i++) {
        //              p_type=data[i];
        //
        //         }
        //     }
        // });




        //
        // $.ajax({
        //     url:"selectAll",
        //     type:"post",
        //     success:function(data){
        //         for(var i=0;i<data.length;i++){
        //             var str="<div class='future_ui__piece'><span><a><font color ='white' size='7'>"+data[i].pType+"</font></a></span><div>"
        //             $("#div0").append(str);
        //             var str = "<ul><li class='list_name'>" + data[i].pName + "</li>\n" +
        //                                         "            <li class='list_num'>" + data[i].pNum + "</li>\n" +
        //                                         "            <li class='list_status'>" + data[i].status + "</li>\n" +
        //                                         "            <button class='list_button' id='down' value='" + data[i].pId + "'>下架</button><br/><ul>";
        //                                     $("#div0").append(str);
        //         }
        //     }
        // });

    })
</script>
</body>
</html>
