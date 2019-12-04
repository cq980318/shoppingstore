<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2019/12/3
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<input type="text" id="test1" οninput="checkNum(this)" name="myNum">
<script>
//金额只能为正整数
function _checkNum(element) {
var val= element.value;
//匹配非数字
var reg = new RegExp("([^0-9]*)","g");
var ma = val.match(reg);
//如果有非数字，替换成""
if(ma.length>0){
for(var k in ma){
if(ma[k]!=""){
val = val.replace(ma[k],"");
}
}
}
//可以为0，但不能以0开头
if(val.startsWith("0")&&val.length>1){
val = val.substring(1,val.length);
}
//赋值，这样实现的效果就是用户按下非数字不会有任何反应
element.value = val;
}
</script>
</body>
</html>
