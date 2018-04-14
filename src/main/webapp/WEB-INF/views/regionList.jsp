<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>区域管理</title>
    <!-- Bootstrap -->
    <link href="<%=path%>/vendors/datatable/datatables.min.css" rel="stylesheet">
    <script src="<%=path%>/vendors/requireJS/require.js"></script>
    <script type="text/javascript" src="<%=path%>/vendors/requireJS/require-config.js"></script>
    <script type="text/javascript">
        require(['datatable'],function () {
            console.log('datatable loading')
        });
    </script>
</head>
<body>

</body>
</html>
